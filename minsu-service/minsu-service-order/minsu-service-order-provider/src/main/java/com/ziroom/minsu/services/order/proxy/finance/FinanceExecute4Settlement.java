package com.ziroom.minsu.services.order.proxy.finance;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.BigDecimalUtil;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.order.FinancePayVouchersEntity;
import com.ziroom.minsu.entity.order.OrderPayEntity;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.customer.api.inner.CustomerMsgManagerService;
import com.ziroom.minsu.services.customer.entity.CustomerVo;
import com.ziroom.minsu.services.finance.entity.CustomerInfoVo;
import com.ziroom.minsu.services.order.proxy.OrderMsgProxy;
import com.ziroom.minsu.services.order.service.FinanceCallBackServiceImpl;
import com.ziroom.minsu.services.order.service.OrderPayServiceImpl;
import com.ziroom.minsu.valenum.account.FreezeBussinessTypeEnum;
import com.ziroom.minsu.valenum.account.OriginalBussinessTypeEnum;
import com.ziroom.minsu.valenum.order.OrderPaymentTypeEnum;
import com.ziroom.minsu.valenum.order.PaymentStatusEnum;
import com.ziroom.minsu.valenum.order.ReceiveTypeEnum;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component("order.financeExecute4Settlement")
public class FinanceExecute4Settlement extends FinancePvExecute {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FinanceExecute4Settlement.class);
	
	@Resource(name = "order.orderMsgProxy")
	private OrderMsgProxy orderMsgProxy;
	
	@Resource(name = "order.orderPayServiceImpl")
	private OrderPayServiceImpl orderPayServiceImpl;

	@Resource(name = "order.financeCallBackServiceImpl")
	private FinanceCallBackServiceImpl financeCallBackService;
	
	@Resource(name = "customer.customerMsgManagerService")
	private CustomerMsgManagerService customerMsgManagerService;
	
	@Override
	public void runDoSth(FinancePayVouchersEntity payVouchers) throws Exception {
		LogUtil.info(LOGGER, "执行用户结算生成的付款单计划，paySourceType：{}", payVouchers.getPaySourceType());
		
		OrderPayEntity orderPay = orderPayServiceImpl.getOrderPayByOrderSn(payVouchers.getOrderSn());
		if(canYlfh(payVouchers, orderPay)){
			doYlfh(payVouchers, orderPay);
			return;
		}
		
		CustomerInfoVo customerInfoVo = this.getCustomerInfo(payVouchers);
		if(canYhfkDefult(payVouchers, customerInfoVo)){
			doYhfk(payVouchers, customerInfoVo);
			return;
		}
		
		if(canAccount(payVouchers)){
			doAccount(payVouchers);
			return;
		}
	}

	
	/**
	 * 原路返回
	 * 未付款=>已打冻结=>已消费=>已申请打款
	 * @author lishaochuan
	 * @create 2016年9月21日上午11:29:35
	 * @param payVouchers
	 * @param orderPay
	 * @throws Exception
	 */
	private void doYlfh(FinancePayVouchersEntity payVouchers, OrderPayEntity orderPay)throws Exception{
		//判断是否已经同步到财务
		if(!this.isSyncPayment(payVouchers.getOrderSn(), payVouchers.getPvSn())){
			return;
		}
		//原路返回校验通过
		payVouchers.setPaymentType(OrderPaymentTypeEnum.YLFH.getCode());
		
		// 转账到租客冻结金
		if(PaymentStatusEnum.UN_PAY.getCode() == payVouchers.getPaymentStatus()){
			this.callTransfersFreezeService(payVouchers, null);
		}
		
		// 调账户系统【消费冻结金】
		if(PaymentStatusEnum.HAVE_FREEZE.getCode() == payVouchers.getPaymentStatus()){
			this.callFrozenAccountPvService(payVouchers, FreezeBussinessTypeEnum.automatic_withdraw.getCode());
		}
		
		// 调财务原路返回
		if(PaymentStatusEnum.HAS_CONSUME.getCode() == payVouchers.getPaymentStatus()){
			this.callSendYlfhVouchers(payVouchers, orderPay);
		}
	}
	
	
	/**
	 * 银行付款
	 * 房东：未付款=>已消费=>已申请打款
	 * 房客：未付款=>已打冻结=>已消费=>已申请打款
	 * @author lishaochuan
	 * @create 2016年9月21日上午11:56:01
	 * @param payVouchers
	 * @param customerInfoVo
	 * @throws Exception
	 */
	private void doYhfk(FinancePayVouchersEntity payVouchers, CustomerInfoVo customerInfoVo) throws Exception{
		LogUtil.info(LOGGER, "默认支付方式是银行卡");
		// 收款人是房东
		if(payVouchers.getReceiveType() == ReceiveTypeEnum.LANDLORD.getCode()){
			// 调账户系统【消费冻结金】
			if(PaymentStatusEnum.UN_PAY.getCode() == payVouchers.getPaymentStatus()){
				this.callFrozenAccountPvService(payVouchers, FreezeBussinessTypeEnum.automatic_withdraw.getCode());
			}
			
			// 调财务提现
			if(PaymentStatusEnum.HAS_CONSUME.getCode() == payVouchers.getPaymentStatus()){
				this.callSendPayVouchers(payVouchers, customerInfoVo); 
			}
		}
		
		// 收款人是租客
		if(payVouchers.getReceiveType() == ReceiveTypeEnum.TENANT.getCode()){
			// 转账到租客冻结金
			if(PaymentStatusEnum.UN_PAY.getCode() == payVouchers.getPaymentStatus()){
				this.callTransfersFreezeService(payVouchers, null);
			}
			
			// 调账户系统【消费冻结金】
			if(PaymentStatusEnum.HAVE_FREEZE.getCode() == payVouchers.getPaymentStatus()){
				this.callFrozenAccountPvService(payVouchers, FreezeBussinessTypeEnum.automatic_withdraw.getCode());
			}
			
			// 调财务提现
			if(PaymentStatusEnum.HAS_CONSUME.getCode() == payVouchers.getPaymentStatus()){
				this.callSendPayVouchers(payVouchers, customerInfoVo); 
			}
		}
	}
	
	/**
	 * 非银行卡结算方式
	 * 房东：未付款=>已打余额
	 * 房客：未付款=>已打余额
	 * @author lishaochuan
	 * @create 2016年9月21日上午11:56:44
	 * @param payVouchers
	 * @throws Exception
	 */
	private void doAccount(FinancePayVouchersEntity payVouchers) throws Exception{
		LogUtil.info(LOGGER, "默认支付方式不是银行卡");
		if(payVouchers.getReceiveType() == ReceiveTypeEnum.LANDLORD.getCode()){
			LogUtil.info(LOGGER, "收款人是房东");
			// 调用账户系统【账户余额解冻】
			if(PaymentStatusEnum.UN_PAY.getCode() == payVouchers.getPaymentStatus()){
				this.callAccountBalanceService(payVouchers);
			}
			
		}
		if(payVouchers.getReceiveType() == ReceiveTypeEnum.TENANT.getCode()){
			LogUtil.info(LOGGER, "收款人是租客,转账到租客余额");
			try {
				// 转账到租客余额
				if(PaymentStatusEnum.UN_PAY.getCode() == payVouchers.getPaymentStatus()){
					this.callTransfersAccountService(payVouchers, OriginalBussinessTypeEnum.original_bussiness_type.getCode());
				}
				
				this.sendMsg4UserSettlementSuccess(payVouchers);
			} catch (Exception e) {
				this.sendMsg4UserSettlementFail(payVouchers);
				throw e;
			}
		}
		
		// 查询所有付款单是否都已付款，更新订单结算状态 为已完成
		financeCallBackService.updateOrderAccountStatus(payVouchers.getPvSn(), payVouchers.getOrderSn());
	}
	
	
	/**
     * 房客结算退款（成功）时发送极光推送、短信
     * @author lishaochuan
     * @create 2016年5月10日下午6:58:05
     * @param payVouchers
     */
    private void sendMsg4UserSettlementSuccess(FinancePayVouchersEntity payVouchers){
    	LogUtil.info(LOGGER, "房客结算退款（成功）时发送极光推送、短信");
    	try {
    		DataTransferObject customerDto = JsonEntityTransform.json2DataTransferObject(customerMsgManagerService.getCutomerVo(payVouchers.getReceiveUid()));
    		CustomerVo customerVo = customerDto.parseData("customerVo", new TypeReference<CustomerVo>() {});
    		if(Check.NuNStr(customerVo.getCustomerMobile())){
    			LogUtil.error(LOGGER, "customerMobile is null");
    			return;
    		}
    		String refundFee = ValueUtil.getStrValue(BigDecimalUtil.div(payVouchers.getTotalFee(), 100));
    		
    		orderMsgProxy.sendMsg4UserSettlement(refundFee, payVouchers.getReceiveUid(), customerVo.getShowMobile(),customerVo.getCountryCode());
		} catch (Exception e) {
			LogUtil.error(LOGGER, "e:{}", e);
		}
    }
    
    /**
     * 房客结算退款（失败）时发送短信
     * @author lishaochuan
     * @create 2016年5月16日下午6:14:43
     * @param payVouchers
     */
    private void sendMsg4UserSettlementFail(FinancePayVouchersEntity payVouchers){
    	LogUtil.info(LOGGER, "房客结算退款（失败）时发送短信");
    	try {
    		DataTransferObject customerDto = JsonEntityTransform.json2DataTransferObject(customerMsgManagerService.getCutomerVo(payVouchers.getReceiveUid()));
    		CustomerVo customerVo = customerDto.parseData("customerVo", new TypeReference<CustomerVo>() {});
    		if(Check.NuNStr(customerVo.getCustomerMobile())){
    			LogUtil.error(LOGGER, "customerMobile is null");
    			return;
    		}
    		
    		orderMsgProxy.sendMsg4UserSettlementFail(customerVo.getShowMobile(),customerVo.getCountryCode());
		} catch (Exception e) {
			LogUtil.error(LOGGER, "e:{}", e);
		}
    }
}
