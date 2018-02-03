package com.ziroom.minsu.services.order.proxy.finance;

import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.order.FinancePayVouchersEntity;
import com.ziroom.minsu.services.finance.entity.CustomerInfoVo;
import com.ziroom.minsu.valenum.account.FreezeBussinessTypeEnum;
import com.ziroom.minsu.valenum.order.PaymentStatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("order.financeExecute4OverCancel")
public class FinanceExecute4OverCancel extends FinancePvExecute {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FinanceExecute4OverCancel.class);

	
	/**
	 * 有银行卡：未付款=>已消费=>已申请打款
	 * 无银行卡：未付款=>已打余额
	 * @author lishaochuan
	 * @create 2016年9月21日上午10:04:34
	 * @param payVouchers
	 * @throws Exception
	 */
	@Override
	public void runDoSth(FinancePayVouchersEntity payVouchers) throws Exception {
		LogUtil.info(LOGGER, "执行支付成功但订单超时取消生成的付款单，paySourceType：{}", payVouchers.getPaySourceType());
		
		CustomerInfoVo customerInfoVo = this.getCustomerInfo(payVouchers);
		if (canYhfkExists(payVouchers, customerInfoVo)) {
			doYhfk(payVouchers, customerInfoVo);
			return;
		}
		
		if(canAccount(payVouchers)){
			doAccount(payVouchers);
			return;
		}
	}
	
	
	private void doYhfk(FinancePayVouchersEntity payVouchers, CustomerInfoVo customerInfoVo) throws Exception {
		LogUtil.info(LOGGER, "银行卡信息校验通过");

		// 调账户系统【消费冻结金】
		if(PaymentStatusEnum.UN_PAY.getCode() == payVouchers.getPaymentStatus()){
			this.callFrozenAccountPvService(payVouchers, FreezeBussinessTypeEnum.automatic_withdraw.getCode());
		}
		
		// 调财务提现
		if(PaymentStatusEnum.HAS_CONSUME.getCode() == payVouchers.getPaymentStatus()){
			this.callSendPayVouchers(payVouchers, customerInfoVo);
		}
	}
	
	
	private void doAccount(FinancePayVouchersEntity payVouchers) throws Exception{
		LogUtil.info(LOGGER, "银行卡信息校验不通过，解冻到余额");
		
		// 调用账户系统【账户余额解冻】
		if(PaymentStatusEnum.UN_PAY.getCode() == payVouchers.getPaymentStatus()){
			this.callAccountBalanceService(payVouchers);
		}
	}
}
