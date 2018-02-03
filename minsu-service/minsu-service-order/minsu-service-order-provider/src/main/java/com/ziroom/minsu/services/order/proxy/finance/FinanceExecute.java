package com.ziroom.minsu.services.order.proxy.finance;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.BigDecimalUtil;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.customer.CustomerBankCardMsgEntity;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.entity.order.FinanceIncomeEntity;
import com.ziroom.minsu.entity.order.FinancePayVouchersDetailEntity;
import com.ziroom.minsu.entity.order.FinancePayVouchersEntity;
import com.ziroom.minsu.entity.order.OrderPayEntity;
import com.ziroom.minsu.services.account.dto.BalanceMoneyRequest;
import com.ziroom.minsu.services.account.dto.BalanceThawRequest;
import com.ziroom.minsu.services.account.dto.FreezeConsumeRequest;
import com.ziroom.minsu.services.account.dto.TransfersAccountRequest;
import com.ziroom.minsu.services.common.constant.SysConst;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.customer.api.inner.CustomerInfoService;
import com.ziroom.minsu.services.finance.dto.PayVouchersRequest;
import com.ziroom.minsu.services.finance.entity.CustomerInfoVo;
import com.ziroom.minsu.services.order.proxy.CallAccountServiceProxy;
import com.ziroom.minsu.services.order.proxy.CallFinanceServiceProxy;
import com.ziroom.minsu.services.order.proxy.OrderMsgProxy;
import com.ziroom.minsu.services.order.service.FinanceCallBackServiceImpl;
import com.ziroom.minsu.services.order.service.OrderCommonServiceImpl;
import com.ziroom.minsu.services.order.service.PayVouchersRunServiceImpl;
import com.ziroom.minsu.valenum.account.ConsumeTypeEnum;
import com.ziroom.minsu.valenum.account.CustomerTypeEnum;
import com.ziroom.minsu.valenum.account.FreezeBussinessTypeEnum;
import com.ziroom.minsu.valenum.common.ErrorCodeEnum;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.order.*;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public abstract class FinanceExecute{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FinanceExecute.class);
	
	@Resource(name = "order.callAccountServiceProxy")
	private CallAccountServiceProxy callAccountServiceProxy;

	@Resource(name = "order.callFinanceServiceProxy")
	private CallFinanceServiceProxy callFinanceServiceProxy;
	
	@Resource(name = "customer.customerInfoService")
    private CustomerInfoService customerInfoService;

	@Resource(name = "order.orderCommonServiceImpl")
	private OrderCommonServiceImpl orderCommonServiceImpl ;

	@Resource(name = "order.payVouchersRunServiceImpl")
	protected PayVouchersRunServiceImpl payVouchersRunService;

	@Resource(name = "order.financeCallBackServiceImpl")
	private FinanceCallBackServiceImpl financeCallBackService;

	@Resource(name = "order.orderMsgProxy")
	private OrderMsgProxy orderMsgProxy;
	
	/**
	 * 调用账户系统【账户余额解冻】
	 * 将账户冻结金变为余额，冻结金减少，余额增加
	 * @author lishaochuan
	 * @create 2016年4月22日
	 * @param payVouchers
	 * @throws Exception 
	 */
	public void callAccountBalanceService(FinancePayVouchersEntity payVouchers) throws Exception{
		LogUtil.info(LOGGER, "调用账户系统【账户余额解冻】");
		Map<String, String> result = new HashMap<String, String>();
		try {
			BalanceThawRequest btr = this.fillAccountBalanceRequest(payVouchers);
			result = callAccountServiceProxy.accountBalanceThaw(btr);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "paySn{}, Exception:{}", payVouchers.getPvSn(), e);
			result.put("result", "异常:" + e.toString());
		} finally{
			result.put("apiName", "账户余额解冻");
			this.checkAccountResult(result, payVouchers, PaymentStatusEnum.HAVE_ACCOUNT.getCode());
		}
	}
	
	
	/**
	 * 调账户系统【消费冻结金】（付款单）
	 * 消费冻结金，冻结金减少
	 * @author lishaochuan
	 * @create 2016年4月28日
	 * @param payVouchers
	 * @param bussinessType
	 * @throws Exception
	 */
	public void callFrozenAccountPvService(FinancePayVouchersEntity payVouchers, Integer bussinessType) throws Exception{
		LogUtil.info(LOGGER, "调账户系统【消费冻结金】");
		Map<String, String> result = new HashMap<String, String>();
		try {
			FreezeConsumeRequest fsr = this.fillFrozenAccount(payVouchers, bussinessType);
			result = callAccountServiceProxy.consumeRfrozenAccount(fsr);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "pvSn:{}, Exception:{}", payVouchers.getPvSn(), e);
			result.put("result", "异常:" + e.toString());
		} finally{
			result.put("apiName", "消费冻结金");
			this.checkAccountResult(result, payVouchers, PaymentStatusEnum.HAS_CONSUME.getCode());
		}
	}
	
	/**
	 * 调账户系统【消费冻结金】（收入表）
	 * @author lishaochuan
	 * @param income
	 * @throws Exception 
	 * @create 2016年4月22日
	 */
	public void callFrozenAccountIncomeService(FinanceIncomeEntity income) throws Exception{
		LogUtil.info(LOGGER, "调账户系统【消费冻结金】（收入表）");
		Map<String, String> result = new HashMap<String, String>();
		try {
			FreezeConsumeRequest fsr = this.fillFrozenAccount4Income(income);
			result = callAccountServiceProxy.consumeRfrozenAccount(fsr);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "incomeSn:{}, Exception:{}", income.getIncomeSn(), e);
			result.put("result", "异常:" + e.toString());
		} finally{
			result.put("apiName", "消费冻结金");
			this.checkAccountIncomeResult(result, income.getIncomeSn(), income.getOrderSn(), null);
		}
	}
	
	/**
	 * 调账户系统【账户转账冻结金到余额】
	 * @author lishaochuan
	 * @create 2016年4月23日
	 * @param payVouchers
	 * @param origBusinessType
	 * @throws Exception
	 */
	public void callTransfersAccountService(FinancePayVouchersEntity payVouchers, Integer origBusinessType) throws Exception{
		LogUtil.info(LOGGER, "调账户系统【账户转账冻结金到余额】");
		Map<String, String> result = new HashMap<String, String>();
		try {
			int reduceType = ConsumeTypeEnum.freeze.getCode(); // from 冻结
			int addType = ConsumeTypeEnum.filling_balance.getCode();//to 余额
			TransfersAccountRequest tar = this.fillTransfersAccount(payVouchers, reduceType, addType, origBusinessType);
			result = callAccountServiceProxy.transfersAccount(tar);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "pvSn:{}, Exception:{}", payVouchers.getPvSn(), e);
			result.put("result", "异常:" + e.toString());
		} finally{
			result.put("apiName", "账户转账冻结金到余额");
			this.checkAccountResult(result, payVouchers, PaymentStatusEnum.HAVE_ACCOUNT.getCode());
		}
	}
	
	
	/**
	 * 调账户系统【账户转账冻结金到冻结】
	 * @author lishaochuan
	 * @create 2016年4月23日
	 * @param payVouchers
	 * @param origBusinessType
	 * @throws Exception
	 */
	public void callTransfersFreezeService(FinancePayVouchersEntity payVouchers, Integer origBusinessType) throws Exception{
		LogUtil.info(LOGGER, "调账户系统【账户转账冻结金到冻结】");
		Map<String, String> result = new HashMap<String, String>();
		try {
			int reduceType = ConsumeTypeEnum.freeze.getCode(); // from 冻结
			int addType = ConsumeTypeEnum.filling_freeze.getCode(); //to 冻结
			TransfersAccountRequest tar = this.fillTransfersAccount(payVouchers, reduceType, addType, origBusinessType);
			result = callAccountServiceProxy.transfersAccount(tar);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "pvSn:{}, Exception:{}", payVouchers.getPvSn(), e);
			result.put("result", "异常:" + e.toString());
		} finally{
			result.put("apiName", "账户转账冻结金到冻结金");
			this.checkAccountResult(result, payVouchers, PaymentStatusEnum.HAVE_FREEZE.getCode());
		}
	}
	
	
	/**
	 * 调财务系统【提现付款】接口
	 * @author lishaochuan
	 * @create 2016年4月22日
	 * @param payVouchers
	 * @param customerInfoVo
	 * @throws Exception
	 */
	public void callSendPayVouchers(FinancePayVouchersEntity payVouchers, CustomerInfoVo customerInfoVo) throws Exception{
		LogUtil.info(LOGGER, "调财务系统【提现付款】接口");
		Map<String, String> resultMap = new HashMap<String, String>();
		try {
			List<FinancePayVouchersDetailEntity> payVouchersDetailList = payVouchersRunService.findPayVouchersDetailByPvSn(payVouchers.getPvSn());
			PayVouchersRequest payVouchersRequest = fillPayVouchersRequest(payVouchers, payVouchersDetailList, customerInfoVo);
			resultMap = callFinanceServiceProxy.sendPayVouchers(payVouchersRequest);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "pvSn:{}, Exception:{}", payVouchers.getPvSn(), e);
			resultMap.put("mesName", "异常:" + e.toString());
		} finally{
			String resultStatus = resultMap.get("status");
			String resultCode = resultMap.get("resultCode");
			String resultMsg = "api:财务提现付款 , mesName:" + resultMap.get("mesName");
			payVouchers.setPreviousPaymentStatus(payVouchers.getPaymentStatus()); //记录上一步的付款单状态
			if(!ErrorCodeEnum.success.getCodeEn().equals(resultStatus)){
				payVouchers.setPaymentStatus(PaymentStatusEnum.FAILED_PAY_UNDO.getCode());
				payVouchersRunService.updatePayVouchersStatus(payVouchers, YesOrNoEnum.NO.getCode(), resultCode, resultMsg);
				LogUtil.error(LOGGER, "调财务系统失败，记录日志，pvSn:{}, orderSn:{}, resultMap：{}", payVouchers.getPvSn(), payVouchers.getOrderSn(), resultMap);
				throw new BusinessException("调财务系统【提现付款】失败");
			}
			LogUtil.info(LOGGER, "调财务系统成功");
			payVouchers.setPaymentStatus(PaymentStatusEnum.HAS_REQUEST_PAY.getCode());
		    payVouchersRunService.updatePayVouchersStatus(payVouchers, YesOrNoEnum.YES.getCode(), resultCode, resultMsg);
		}
	}
	
	/**
	 * 调财务系统【原路返回】
	 * @author lishaochuan
	 * @create 2016年8月24日下午2:18:20
	 * @param payVouchers
	 * @param orderPay
	 * @throws Exception
	 */
	public void callSendYlfhVouchers(FinancePayVouchersEntity payVouchers, OrderPayEntity orderPay) throws Exception{
		LogUtil.info(LOGGER, "调财务系统【原路返回】接口");
		Map<String, String> resultMap = new HashMap<String, String>();
		try {
			PayVouchersRequest payVouchersRequest = fillYlfhVouchersRequest(payVouchers, orderPay);
			resultMap = callFinanceServiceProxy.sendPayVouchers(payVouchersRequest);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "pvSn:{}, Exception:{}", payVouchers.getPvSn(), e);
			resultMap.put("mesName", "异常:" + e.toString());
		} finally{
			String resultStatus = resultMap.get("status");
			String resultCode = resultMap.get("resultCode");
			String resultMsg = "api:财务原路返回 , mesName:" + resultMap.get("mesName");
			payVouchers.setPreviousPaymentStatus(payVouchers.getPaymentStatus()); //记录上一步的付款单状态
			if(!ErrorCodeEnum.success.getCodeEn().equals(resultStatus)){
				payVouchers.setPaymentStatus(PaymentStatusEnum.FAILED_PAY_UNDO.getCode());
				payVouchersRunService.updatePayVouchersStatus(payVouchers, YesOrNoEnum.NO.getCode(), resultCode, resultMsg);
				LogUtil.error(LOGGER, "调财务系统失败，记录日志，pvSn:{}, orderSn:{}, resultMap：{}", payVouchers.getPvSn(), payVouchers.getOrderSn(), resultMap);
				throw new BusinessException("调财务系统【原路返回】失败");
			}
			// 更新为已发送、已消费，日志记录成功
			LogUtil.info(LOGGER, "调财务系统成功");
			payVouchers.setPaymentStatus(PaymentStatusEnum.HAS_REQUEST_PAY.getCode());
			payVouchersRunService.updatePayVouchersStatus(payVouchers, YesOrNoEnum.YES.getCode(), resultCode, resultMsg);
		}
	}
	
	/**
	 * 拼账户系统【余额解冻】接口入参
	 * @author lishaochuan
	 * @create 2016年4月22日
	 * @param payVouchers
	 * @return
	 */
	private BalanceThawRequest fillAccountBalanceRequest(FinancePayVouchersEntity payVouchers){
		BalanceThawRequest btr = new BalanceThawRequest();
		btr.setUid(payVouchers.getPayUid());
		btr.setUid_type(CustomerTypeEnum.getCodeByStatusCode(payVouchers.getPayType()));
		btr.setTrade_no(payVouchers.getPvSn());
		btr.setTotalFee(payVouchers.getTotalFee());
		btr.setUnique_num(payVouchers.getPvSn());
		btr.setOrderSn(payVouchers.getOrderSn());
		return btr;
	}
	
	/**
	 * 拼账户系统【消费冻结金】接口入参（付款单表）
	 * @author lishaochuan
	 * @create 2016年4月28日
	 * @param payVouchers
	 * @param bussinessType
	 * @return
	 */
	private FreezeConsumeRequest fillFrozenAccount(FinancePayVouchersEntity payVouchers, Integer bussinessType){
		FreezeConsumeRequest fsr = new FreezeConsumeRequest();
		if (PaySourceTypeEnum.USER_SETTLEMENT.getCode() == payVouchers.getPaySourceType() && ReceiveTypeEnum.TENANT.getCode() == payVouchers.getReceiveType()) {
			// 如果是用户结算，并且收款人是租客，则特殊处理
			fsr.setUid(payVouchers.getReceiveUid());
			fsr.setUidType(CustomerTypeEnum.getCodeByStatusCode(payVouchers.getReceiveType()));
		} else {
			fsr.setUid(payVouchers.getPayUid());
			fsr.setUidType(CustomerTypeEnum.getCodeByStatusCode(payVouchers.getPayType()));
		}
		if(PaySourceTypeEnum.CLEAR_COUPON.getCode() == payVouchers.getPaySourceType()
				|| PaySourceTypeEnum.CLEAR_ACT.getCode() == payVouchers.getPaySourceType()){
			fsr.setBiz_common_type(SysConst.account_virtual_consume_freeze);
		}else{
			fsr.setBiz_common_type(SysConst.account_consume_freeze);
		}
		fsr.setTotalFee(payVouchers.getTotalFee());
		fsr.setUnique_num(payVouchers.getPvSn());
		fsr.setBussiness_type(ValueUtil.getStrValue(bussinessType));
		fsr.setOrderSn(payVouchers.getOrderSn());
		return fsr;
	}
	
	/**
	 * 拼账户系统【消费冻结金】接口入参（收入表）
	 * @author lishaochuan
	 * @create 2016年4月22日
	 * @param financeIncomeEntity
	 * @return
	 */
	private FreezeConsumeRequest fillFrozenAccount4Income(FinanceIncomeEntity financeIncomeEntity){
		FreezeConsumeRequest fsr = new FreezeConsumeRequest();
		fsr.setUid(financeIncomeEntity.getPayUid());
		fsr.setUidType(CustomerTypeEnum.getCodeByStatusCode(financeIncomeEntity.getPayType()));
		fsr.setTotalFee(financeIncomeEntity.getTotalFee());
		fsr.setUnique_num(financeIncomeEntity.getIncomeSn());
		fsr.setBiz_common_type(SysConst.account_consume_freeze);
		fsr.setBussiness_type(ValueUtil.getStrValue(FreezeBussinessTypeEnum.service_charges.getCode()));
		fsr.setOrderSn(financeIncomeEntity.getOrderSn());
		return fsr;
	}
	
	/**
	 * 拼账户系统【消费余额】接口入参
	 * @author lishaochuan
	 * @create 2016年4月29日
	 * @param payVouchers
	 * @return
	 */
	/*private BalanceMoneyRequest fillBalanceMoneyRequest(FinancePayVouchersEntity payVouchers){
		BalanceMoneyRequest bmr = new BalanceMoneyRequest();
		bmr.setUid(payVouchers.getPayUid());
		bmr.setUidType(CustomerTypeEnum.getCodeByStatusCode(payVouchers.getPayType()));
		bmr.setTotalFee(payVouchers.getTotalFee());
		bmr.setUnique_num(payVouchers.getPvSn());
		return bmr;
	}*/
	
	/**
	 * 拼账户系统【转账】接口入参
	 * @author lishaochuan
	 * @create 2016年4月28日
	 * @param payVouchers
	 * @param reduceType
	 * @param addType
	 * @param origBusinessType
	 * @return
	 */
	private TransfersAccountRequest fillTransfersAccount(FinancePayVouchersEntity payVouchers, int reduceType, int addType, Integer origBusinessType){
		TransfersAccountRequest tar = new TransfersAccountRequest();
		tar.setReduceType(reduceType); // ConsumeTypeEnum
		tar.setOrigUid(payVouchers.getPayUid());
		tar.setUidType(CustomerTypeEnum.getCodeByStatusCode(payVouchers.getPayType()));
		tar.setOrigBusinessType(ValueUtil.getStrValue(origBusinessType));
		tar.setTargetUid(payVouchers.getReceiveUid());
		tar.setTargetUidType(CustomerTypeEnum.getCodeByStatusCode(payVouchers.getReceiveType()));
		tar.setAddType(addType); // ConsumeTypeEnum
		tar.setTradeNo(payVouchers.getPvSn());
		tar.setTotalFee(payVouchers.getTotalFee());
		tar.setOrderSn(payVouchers.getOrderSn());
		return tar;
	}
	
	
	/**
	 * 统一校验【账户系统】接口出参
	 * @author lishaochuan
	 * @create 2016年4月22日
	 * @param resultMap
     * @param payVouchers
     * @param nextPaymentStatus 操作完毕后的状态
	 */
	private void checkAccountResult(Map<String, String> resultMap, FinancePayVouchersEntity payVouchers, Integer nextPaymentStatus){
		String resultStatus = resultMap.get("status");
		String resultMsg = this.getResultMsg4Account(resultMap);
		payVouchers.setPreviousPaymentStatus(payVouchers.getPaymentStatus()); //记录上一步的付款单状态
		if(!ErrorCodeEnum.success.getCodeEn().equals(resultStatus)){
			payVouchers.setPaymentStatus(PaymentStatusEnum.FAILED.getCode());
			payVouchersRunService.updatePayVouchersStatus(payVouchers, YesOrNoEnum.NO.getCode(), null, resultMsg);
			LogUtil.error(LOGGER, "调用账户系统接口失败，记录日志，pvSn:{}, orderSn:{}, resultMsg:{}", payVouchers.getPvSn(), payVouchers.getOrderSn(), JsonEntityTransform.Object2Json(resultMap));
			throw new BusinessException("调用账户系统接口失败");
		}
		LogUtil.info(LOGGER, "调用账户系统接口成功");
		payVouchers.setPaymentStatus(nextPaymentStatus);
		int updatePayVouchersStatus = payVouchersRunService.updatePayVouchersStatus(payVouchers, YesOrNoEnum.YES.getCode(), null, resultMsg);
		if (PaymentStatusEnum.HAVE_ACCOUNT.getCode() == nextPaymentStatus){
			financeCallBackService.updateOrderAccountStatus(payVouchers.getPvSn(), payVouchers.getOrderSn());
		}
		if(!Check.NuNObj(payVouchers.getReceiveType()) && ReceiveTypeEnum.LANDLORD.getCode() == payVouchers.getReceiveType()
				&& updatePayVouchersStatus > 0 && PaymentStatusEnum.HAVE_ACCOUNT.getCode() == nextPaymentStatus){
			orderMsgProxy.sendMsgToLanForPayVouchers(payVouchers.getOrderSn(), payVouchers.getPaySourceType(), payVouchers.getTotalFee(), "的账户空间",payVouchers.getPvSn());
		}
	}
	
	
	/**
	 * 校验收入表消费冻结接口出参
	 * @author lishaochuan
	 * @create 2016年4月29日
	 * @param resultMap
	 * @param incomeSn
	 * @param paymentStatus
	 */
	private void checkAccountIncomeResult(Map<String, String> resultMap, String incomeSn, String orderSn, Integer paymentStatus){
		String resultStatus = resultMap.get("status");
		String resultMsg = this.getResultMsg4Account(resultMap);
		if(!ErrorCodeEnum.success.getCodeEn().equals(resultStatus)){
			payVouchersRunService.updateIncomeStatusFail(incomeSn, orderSn, resultMsg);
			LogUtil.error(LOGGER, "收入表调账户系统消费冻结失败，记录日志，incomeSn:{}, orderSn:{}, resultMsg:{}", incomeSn, orderSn, JsonEntityTransform.Object2Json(resultMap));
			throw new BusinessException("收入表调账户系统消费冻结失败");
		}
		LogUtil.info(LOGGER, "收入表调账户系统消费冻结成功");
		payVouchersRunService.updateIncomeStatusSuccess(incomeSn, orderSn, resultMsg);
	}
	
	
	private String getResultMsg4Account(Map<String, String> resultMap){
		StringBuilder resultMsg = new StringBuilder(resultMap.get("apiName"));
		resultMsg.append(",api:");
		resultMsg.append(resultMap.get("api"));
		resultMsg.append(",status:");
		resultMsg.append(resultMap.get("status"));
		resultMsg.append(",result:");
		resultMsg.append(resultMap.get("result"));
		resultMsg.append(",error_string:");
		resultMsg.append(resultMap.get("error_string"));
		return resultMsg.toString();
	}
	
	
	/**
	 * 拼财务系统【提现付款】接口入参
	 * @author lishaochuan
	 * @create 2016年4月28日
	 * @param payVouchers
	 * @param pvDetailList
	 * @param customerInfoVo
	 * @return
	 */
	private PayVouchersRequest fillPayVouchersRequest(FinancePayVouchersEntity payVouchers, List<FinancePayVouchersDetailEntity> pvDetailList, CustomerInfoVo customerInfoVo) {
		CustomerBaseMsgEntity customerBase = customerInfoVo.getCustomerBase();
		CustomerBankCardMsgEntity bankcard = customerInfoVo.getBankcard();
		
		PayVouchersRequest payVouchersRequest = new PayVouchersRequest();
		payVouchersRequest.setPvSn(payVouchers.getPvSn());
		payVouchersRequest.setReceiveType(payVouchers.getReceiveType()); 
		payVouchersRequest.setAuditStatus(AuditStatusEnum.COMPLETE.getCode());
		payVouchersRequest.setPaymentStatus(PaymentStatusEnum.UN_PAY.getCode());
		payVouchersRequest.setCreator(payVouchers.getCreateId());
		payVouchersRequest.setOrderSn(payVouchers.getOrderSn());
		payVouchersRequest.setCustomerBankName(bankcard.getBankName());
		payVouchersRequest.setCustomerAccountName(customerBase.getRealName());
		payVouchersRequest.setCustomerAccountNo(bankcard.getBankcardNo());
		payVouchersRequest.setPayTime(new Date());
		payVouchersRequest.setReceiveUid(payVouchers.getReceiveUid()); // 收款人UID
		payVouchersRequest.setCustomerName(customerBase.getRealName());
		payVouchersRequest.setCustomerPhone(customerBase.getCustomerMobile());
		payVouchersRequest.setPaymentTypeCode(payVouchers.getPaymentType());
		
		//付款单明细
		payVouchersRequest.setDetailList(pvDetailList);
		
		return payVouchersRequest;
	}
	
	/**
	 * 拼财务系统【原路返回】接口入参
	 * @author lishaochuan
	 * @create 2016年8月24日下午3:30:38
	 * @param payVouchers
	 * @param orderPay
	 * @return
	 */
	private PayVouchersRequest fillYlfhVouchersRequest(FinancePayVouchersEntity payVouchers, OrderPayEntity orderPay) {
		List<FinancePayVouchersDetailEntity> pvDetailList = payVouchersRunService.findPayVouchersDetailByPvSn(payVouchers.getPvSn());
		CustomerBaseMsgEntity customerBase = this.getCustomerBase(payVouchers.getReceiveUid());
		
		PayVouchersRequest payVouchersRequest = new PayVouchersRequest();
		payVouchersRequest.setPvSn(payVouchers.getPvSn());
		payVouchersRequest.setReceiveType(payVouchers.getReceiveType()); 
		payVouchersRequest.setAuditStatus(AuditStatusEnum.COMPLETE.getCode());
		payVouchersRequest.setPaymentStatus(PaymentStatusEnum.UN_PAY.getCode());
		payVouchersRequest.setCreator(payVouchers.getCreateId());
		payVouchersRequest.setOrderSn(payVouchers.getOrderSn());
		//payVouchersRequest.setCustomerBankName(bankcard.getBankName());
		//payVouchersRequest.setCustomerAccountName(customerBase.getRealName());
		//payVouchersRequest.setCustomerAccountNo(bankcard.getBankcardNo());
		payVouchersRequest.setPayTime(new Date());
		payVouchersRequest.setReceiveUid(payVouchers.getReceiveUid()); // 收款人UID
		payVouchersRequest.setCustomerName(customerBase.getRealName());
		payVouchersRequest.setCustomerPhone(customerBase.getCustomerMobile());
		payVouchersRequest.setPaymentTypeCode(payVouchers.getPaymentType());
		
		//原路返回参数
		payVouchersRequest.setChannal(String.valueOf(OrderPayTypeChannelEnum.getPayStatusByName(orderPay.getPayType()).getPayChannel()));
		payVouchersRequest.setBizCode(orderPay.getOrderSn());
		payVouchersRequest.setOutTradeNo(orderPay.getTradeNo());
		//payVouchersRequest.setTransactionId(transactionId);
		payVouchersRequest.setPayorderCodeOrigin(orderPay.getPayCode());
		Double payTotalFee = BigDecimalUtil.div(orderPay.getPayMoney(), 100,2); 
		payVouchersRequest.setPayTotalFee(String.valueOf(payTotalFee));  
		
		//付款单明细
		payVouchersRequest.setDetailList(pvDetailList);
		
		return payVouchersRequest;
	}
	
	/**
     * 获取用户基本信息、银行卡信息
     * 如果是默认银行卡支付方式，将银行卡fid落地到bankcardFid
     * @author lishaochuan
     * @create 2016年4月29日
     * @param payVouchers
     * @return
     */
	public CustomerInfoVo getCustomerInfo(FinancePayVouchersEntity payVouchers) {
		CustomerInfoVo customerInfoVo = new CustomerInfoVo();
		try {
			LogUtil.info(LOGGER, "获取用户银行卡信息，uid：{}", payVouchers.getReceiveUid());
			CustomerBaseMsgEntity customerBase = this.getCustomerBase(payVouchers.getReceiveUid()); // 获得用户信息
			CustomerBankCardMsgEntity bankcard = null;
			if (Check.NuNStr(payVouchers.getBankcardFid())) {
				bankcard = this.getCustomerBankcard(payVouchers.getReceiveUid()); // 调用户接口获得用户银行卡信息
			} else {
				bankcard = this.getCustomerBankCardDbByFid(payVouchers.getBankcardFid(), payVouchers.getReceiveUid()); // 根据bankcardFid从数据库获取银行卡信息
			}
			customerInfoVo.setCustomerBase(customerBase);
			customerInfoVo.setBankcard(bankcard);

		} catch (Exception e) {
			LogUtil.error(LOGGER, "获取用户银行卡信息异常,uid:{},Exception:{}", payVouchers.getReceiveUid(), e);
			throw new BusinessException("获取用户银行卡信息异常");
		}
		return customerInfoVo;
	}
	
	
	/**
	 * 获取用户基本信息、银行卡信息
     * 如果银行卡存在，将银行卡fid落地到bankcardFid
	 * @author lishaochuan
	 * @create 2016年9月20日下午6:14:00
	 * @param payVouchers
	 * @return
	 */
	/*public CustomerInfoVo getCustomerAndExistsBank(FinancePayVouchersEntity payVouchers) {
		CustomerInfoVo customerInfoVo = new CustomerInfoVo();
		try {
			LogUtil.info(LOGGER, "获取用户银行卡信息，uid：{}", payVouchers.getReceiveUid());
			CustomerBaseMsgEntity customerBase = this.getCustomerBase(payVouchers.getReceiveUid()); // 获得用户信息
			CustomerBankCardMsgEntity bankcard = null;
			if (Check.NuNStr(payVouchers.getBankcardFid())) {
				bankcard = this.getCustomerBankcard(payVouchers.getReceiveUid()); // 调用户接口获得用户银行卡信息
			} else {
				bankcard = this.getCustomerBankCardDbByFid(payVouchers.getBankcardFid(), payVouchers.getReceiveUid()); // 根据bankcardFid从数据库获取银行卡信息
			}
			customerInfoVo.setCustomerBase(customerBase);
			customerInfoVo.setBankcard(bankcard);

			// 未落地银行卡fid，并且校验银行卡是否存在
			if (Check.NuNStr(payVouchers.getBankcardFid()) && customerInfoVo.checkBankCard()) {
				// 落地bankcardFid
				payVouchers.setBankcardFid(bankcard.getFid());
				payVouchersRunService.updateYhfk(payVouchers);
			}

		} catch (Exception e) {
			LogUtil.error(LOGGER, "获取用户银行卡信息异常,uid:{},Exception:{}", payVouchers.getReceiveUid(), e);
			throw new BusinessException("获取用户银行卡信息异常");
		}
		return customerInfoVo;
	}*/
    
    /**
     * 获取用户基本信息、银行卡信息
     * 银行卡信息通过bankcardFid获取
     * @author lishaochuan
     * @create 2016年8月17日下午6:35:00
     * @param payVouchers
     * @return
     */
    public CustomerInfoVo getCustomerInfoByBankcardFid(FinancePayVouchersEntity payVouchers){
    	CustomerInfoVo customerInfoVo = new CustomerInfoVo();
        try {
        	LogUtil.info(LOGGER, "获取用户银行卡信息，uid：{}", payVouchers.getReceiveUid());
            CustomerBaseMsgEntity customerBase = this.getCustomerBase(payVouchers.getReceiveUid()); // 获取用户信息
            CustomerBankCardMsgEntity bankcard = this.getCustomerBankCardDbByFid(payVouchers.getBankcardFid(), payVouchers.getReceiveUid()); // 获得用户银行卡信息，根据fid，从数据库获取
            customerInfoVo.setCustomerBase(customerBase);
            customerInfoVo.setBankcard(bankcard);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "获取用户银行卡信息异常,uid:{}Exception:{}", payVouchers.getReceiveUid(), e);
        }
        return customerInfoVo;
    }
    
    /**
     * 获取用户基本信息
     * @author lishaochuan
     * @create 2016年4月26日
     * @param uid
     * @return
     */
    private CustomerBaseMsgEntity getCustomerBase(String uid){
        String customJson = customerInfoService.getCustomerInfoByUid(uid);
        DataTransferObject customDto = JsonEntityTransform.json2DataTransferObject(customJson);
        CustomerBaseMsgEntity customerBase = null;
        if(customDto.getCode() == DataTransferObject.SUCCESS){
            customerBase = customDto.parseData("customerBase", new TypeReference<CustomerBaseMsgEntity>() {});
        }
        return customerBase;
    }

    /**
     * 获取用户银行卡信息
     * @author lishaochuan
     * @create 2016年4月26日
     * @param uid
     * @return
     */
    private CustomerBankCardMsgEntity getCustomerBankcard(String uid){
        String bankJson = customerInfoService.getCustomerBankcard(uid);
        DataTransferObject bankDto = JsonEntityTransform.json2DataTransferObject(bankJson);
        CustomerBankCardMsgEntity bankcard = null;
        if(bankDto.getCode() == DataTransferObject.SUCCESS){
            bankcard = bankDto.parseData("bankcard", new TypeReference<CustomerBankCardMsgEntity>() {});
        }
        return bankcard;
    }
    
    /**
     * 获取用户银行卡信息
     * 通过fid获取
     * @author lishaochuan
     * @create 2016年8月17日下午7:27:08
     * @param fid
     * @param uid
     * @return
     */
    private CustomerBankCardMsgEntity getCustomerBankCardDbByFid(String fid, String uid){
        String bankJson = customerInfoService.getCustomerBankCardDbByFid(fid, uid);
        DataTransferObject bankDto = JsonEntityTransform.json2DataTransferObject(bankJson);
        CustomerBankCardMsgEntity bankcard = null;
        if(bankDto.getCode() == DataTransferObject.SUCCESS){
            bankcard = bankDto.parseData("bankcard", new TypeReference<CustomerBankCardMsgEntity>() {});
        }
        return bankcard;
    }
    
    
    

    
    
}
