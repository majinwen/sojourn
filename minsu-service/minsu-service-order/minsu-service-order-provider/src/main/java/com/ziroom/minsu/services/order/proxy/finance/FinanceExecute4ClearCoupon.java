package com.ziroom.minsu.services.order.proxy.finance;

import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.order.FinancePayVouchersEntity;
import com.ziroom.minsu.valenum.order.PaymentStatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("order.financeExecute4ClearCoupon")
public class FinanceExecute4ClearCoupon extends FinancePvExecute {

	private static final Logger LOGGER = LoggerFactory.getLogger(FinanceExecute4ClearCoupon.class);
	
	
	/**
	 * 未付款=>已消费
	 * @author lishaochuan
	 * @create 2016年9月21日上午10:04:34
	 * @param payVouchers
	 * @throws Exception
	 */
	@Override
	public void runDoSth(FinancePayVouchersEntity payVouchers) throws Exception {
		LogUtil.info(LOGGER, "执行清空优惠券金额生成的付款单，paySourceType：{}", payVouchers.getPaySourceType());
		
		// 调账户系统【消费冻结金】
		if(PaymentStatusEnum.UN_PAY.getCode() == payVouchers.getPaymentStatus()){
			this.callFrozenAccountPvService(payVouchers, null);
		}
	}
}
