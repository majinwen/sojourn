package com.zra.pay.utils;


import com.zra.common.enums.ErrorEnum;
import com.zra.common.error.ResultException;
import com.zra.common.utils.NetUtil;
import com.zra.common.utils.PropUtils;
import com.zra.pay.entity.ResultOfPaymentPlatform;
import com.zra.pay.entity.SubmitOrderToPaymentPlatform;
import com.zra.pay.entity.ThirdParam;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import com.apollo.logproxy.slf4j.LoggerFactoryProxy;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: wangxm113
 * @CreateDate: 2016年5月5日
 */
public class PaymentPlatform {

	private static final Logger LOGGER = LoggerFactoryProxy.getLogger(PaymentPlatform.class);

	/**
	 * 下单接口
	 * 
	 * @Author: wangxm113
	 * @CreateDate: 2016年5月5日
	 * @param payment
	 * @return 支付订单号
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	public static String submitOrdersToPaymentPlatform(SubmitOrderToPaymentPlatform payment)
			throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper om = new ObjectMapper();
		String order = om.writeValueAsString(payment);
		LOGGER.info("[向支付平台下单]订单bid:" + payment.getBizCode() + "调用支付平台下单的参数:" + order);
		// 调用下单接口，获取返回结果
		String resultStr = getCreatePayOrder(order);
		LOGGER.info("[向支付平台下单]订单bid:" + payment.getBizCode() + "调用支付平台下单的结果:" + resultStr);

		// 判断返回字符为空下单失败，更新下单直接返回
		if (resultStr == null || resultStr.length() <= 0) {
			throw new ResultException(ErrorEnum.MSG_SUBMIT_PAYMENT_PLATFORM_FAIL);
		}

		// 封装支付平台下单返回结果
		ResultOfPaymentPlatform result = om.readValue(resultStr, ResultOfPaymentPlatform.class);
		if ("success".equals(result.getStatus())) {
			return result.getData();// 支付平台返回的信息都在result中，目前，如果正确只返回给调用者支付订单号，错误抛异常
		} else {
			LOGGER.error("[向支付平台下单]订单bid:" + payment.getBizCode() + "，调用支付平台下单返回错误！异常码:" + result.getCode() + ";异常信息:"
					+ result.getMessage() + "!");
			throw new ResultException(ErrorEnum.MSG_SUBMIT_PAYMENT_PLATFORM_FAIL);
		}
	}

	/**
	 * 下订单
	 * 
	 * @param order
	 * @return
	 */
	private static String getCreatePayOrder(String order) {
		String resultContent = "";
		try {
			Map<String, String> param = new HashMap<String, String>();
			param.put("encryption", CryptAES.AES_Encrypt(PropUtils.getString(ThirdParam.PAY_AES_KEY), order)); // 加密
			param.put("systemId", PropUtils.getString(ThirdParam.PAY_SYSTEM_ID));
			InputStream is = NetUtil.sendPostRequest(ThirdParam.PAY_GEN_ORDER_URL, param);
			resultContent = NetUtil.getTextContent(is, ThirdParam.ENCODING_UTF);
		} catch (Exception e) {
			LOGGER.error("[向支付平台下单]", e);
			throw new ResultException(ErrorEnum.MSG_SUBMIT_PAYMENT_PLATFORM_FAIL);
		}
		return resultContent;
	}

	/**
	 * 向支付平台查询结果
	 * 
	 * @Author: wangxm113
	 * @CreateDate: 2016年5月5日
	 * @param orderCode
	 * @return
	 * @throws Exception
	 */
	public static String quryFromPaymentPlatform(String orderCode) throws Exception {
		Map<String, String> param = new HashMap<String, String>();
		param.put("orderCode", orderCode);
		InputStream is = NetUtil.sendPostRequest(ThirdParam.QUERY_URL, param);
		String resultContent = NetUtil.getTextContent(is, ThirdParam.ENCODING_UTF);
		if (resultContent != null) {
			ResultOfPaymentPlatform result = new ObjectMapper().readValue(resultContent, ResultOfPaymentPlatform.class);
			if ("success".equals(result.getStatus())) {
				return result.getData();// 支付平台返回的信息都在result中，目前，如果正确只返回给调用者data，错误抛异常
			} else {
				throw new ResultException(ErrorEnum.MSG_QUERY_PAYMENT_PLATFORM_FAIL);
			}
		} else {
			throw new ResultException(ErrorEnum.MSG_QUERY_PAYMENT_PLATFORM_FAIL);
		}
	}

}
