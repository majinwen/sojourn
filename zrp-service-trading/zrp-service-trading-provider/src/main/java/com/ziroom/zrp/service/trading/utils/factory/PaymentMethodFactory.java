package com.ziroom.zrp.service.trading.utils.factory;

import com.ziroom.minsu.valenum.zrpenum.ContractTradingEnum002;
import com.ziroom.zrp.service.trading.dto.PaymentTermsDto;
import com.ziroom.zrp.service.trading.pojo.PaymentBaseDataPojo;
import com.ziroom.zrp.service.trading.proxy.calculation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>付款方式工厂类</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuigh6
 * @Date Created in 2017年09月12日 16:20
 * @version 1.0
 * @since 1.0
 */
public class PaymentMethodFactory {
	private static final Logger LOGGER = LoggerFactory.getLogger(PaymentMethodFactory.class);

	public static PaymentMethod createPaymentMethod(String conCycleCode) {
		PaymentMethod paymentMethod;
		if (isPayMonthly(conCycleCode)) {
			paymentMethod = new PayMonthlyImpl();
		} else if (isPayQuarterly(conCycleCode)) {
			paymentMethod = new PayQuarterlyImpl();
		} else if (isPayHalfYearly(conCycleCode)) {
			paymentMethod = new PayHalfYearlyImpl();
		} else if (isPayYearly(conCycleCode)) {
			paymentMethod = new PayYearlyImpl();
		} else if (isPayAll(conCycleCode)) {
			paymentMethod = new PayAllImpl();
		}else {
			paymentMethod = new PaymentMethod() {
				@Override
				public PaymentTermsDto calculate(PaymentBaseDataPojo paymentBaseDataPojo) {
					return null;
				}
			};
		}
		return paymentMethod;
	}

	/**
	 * 月付
	 * @param conCycleCode 付款方式
	 * @return
	 */
	private static boolean isPayMonthly(String conCycleCode) {
		return Integer.parseInt(conCycleCode) == ContractTradingEnum002.ContractTradingEnum002001.getCode();
	}

	/**
	 * 季付
	 * @param conCycleCode 付款方式
	 * @return
	 */
	private static boolean isPayQuarterly(String conCycleCode) {
		return Integer.parseInt(conCycleCode) == ContractTradingEnum002.ContractTradingEnum002002.getCode();
	}

	/**
	 * 半年付
	 * @param conCycleCode 付款方式
	 * @return
	 */
	private static boolean isPayHalfYearly(String conCycleCode) {
		return Integer.parseInt(conCycleCode) == ContractTradingEnum002.ContractTradingEnum002003.getCode();
	}

	/**
	 * 年付
	 * @param conCycleCode 付款方式
	 * @return
	 */
	private static boolean isPayYearly(String conCycleCode) {
		return Integer.parseInt(conCycleCode) == ContractTradingEnum002.ContractTradingEnum002004.getCode();
	}

	/**
	 * 一次结清
	 * @param conCycleCode 付款方式
	 * @return
	 */
	private static boolean isPayAll(String conCycleCode) {
		return Integer.parseInt(conCycleCode) == ContractTradingEnum002.ContractTradingEnum002005.getCode();
	}
}
