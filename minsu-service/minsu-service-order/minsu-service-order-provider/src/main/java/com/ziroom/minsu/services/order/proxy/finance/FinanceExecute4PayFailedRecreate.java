package com.ziroom.minsu.services.order.proxy.finance;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.order.FinancePayVouchersEntity;
import com.ziroom.minsu.entity.order.OrderPayEntity;
import com.ziroom.minsu.services.finance.entity.CustomerInfoVo;
import com.ziroom.minsu.services.order.service.OrderPayServiceImpl;
import com.ziroom.minsu.services.order.service.PayVouchersRunServiceImpl;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.order.OrderPaymentTypeEnum;
import com.ziroom.minsu.valenum.order.PaymentStatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component("order.financeExecute4PayFailedRecreate")
public class FinanceExecute4PayFailedRecreate extends FinancePvExecute {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FinanceExecute4PayFailedRecreate.class);
	
	@Resource(name = "order.payVouchersRunServiceImpl")
	private PayVouchersRunServiceImpl payVouchersRunService;

	@Resource(name = "order.orderPayServiceImpl")
	private OrderPayServiceImpl orderPayServiceImpl;

	@Override
	public void runDoSth(FinancePayVouchersEntity payVouchers) throws Exception {
		LogUtil.info(LOGGER, "执行打款失败重新生成的付款单，paySourceType：{}", payVouchers.getPaySourceType());

		if(OrderPaymentTypeEnum.YLFH.getCode().equals(payVouchers.getPaymentType())){
			LogUtil.info(LOGGER, "原路返回：{}", payVouchers.getPvSn());
			OrderPayEntity orderPay = orderPayServiceImpl.getOrderPayByOrderSn(payVouchers.getOrderSn());
			this.callSendYlfhVouchers(payVouchers, orderPay);

		}else if(OrderPaymentTypeEnum.YHFK.getCode().equals(payVouchers.getPaymentType())){
			LogUtil.info(LOGGER, "银行付款：{}", payVouchers.getPvSn());

			// 获取用户银行卡信息，根据fid，从数据库获取
			CustomerInfoVo customerInfoVo = this.getCustomerInfoByBankcardFid(payVouchers);
			if(!customerInfoVo.checkBankCard() || Check.NuNStr(customerInfoVo.getBankcard().getFid())){
				LogUtil.error(LOGGER, "银行卡信息校验不通过，记录日志，pvSn:{}, orderSn:{}, customerInfoVo：{}", payVouchers.getPvSn(), payVouchers.getOrderSn(), JsonEntityTransform.Object2Json(customerInfoVo));
				String msg = "获取用户或银行卡信息错误";
				payVouchers.setPaymentStatus(PaymentStatusEnum.FAILED_PAY_UNDO.getCode());
				payVouchersRunService.updatePayVouchersStatus(payVouchers, YesOrNoEnum.NO.getCode(), null, msg);
				return;
			}
			// 调财务提现
			this.callSendPayVouchers(payVouchers, customerInfoVo);
		}else{
			LogUtil.error(LOGGER, "错误的paymentType:{}", payVouchers);
		}
	}
}
