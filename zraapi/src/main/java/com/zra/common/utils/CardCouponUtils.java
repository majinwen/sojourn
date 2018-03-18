package com.zra.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.services.common.utils.CloseableHttpUtil;
import com.ziroom.platform.tesla.client.TeslaClientFactory;
import com.zra.cardCoupon.entity.ExtCouponEntity;
import com.zra.common.dto.pay.CardCouponQueryRequest;
import com.zra.common.dto.pay.CardCouponQueryResponse;
import com.zra.common.dto.pay.CardCouponUseRequest;
import com.zra.common.dto.pay.CardCouponUseResponse;
import com.zra.common.security.Md5Utils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.springframework.web.client.RestTemplate;

import javax.annotation.concurrent.ThreadSafe;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;

/**
 * Created by cuigh6 on 2017/5/22.
 * 卡券系统调用工具类
 * <p>
 * 1.查询用户的优惠券<br>
 * 2.检查优惠券<br>
 * 3.使用优惠券<br>
 * 4.恢复优惠券<br>
 * <p>
 * 5.查询用户的租金卡<br>
 * 6.检查租金卡<br>
 * 7.使用租金卡<br>
 * 8.恢复租金卡<br>
 * </p>
 */
@ThreadSafe
public enum CardCouponUtils {

	INSTANCE;

	private static final Logger LOGGER = LoggerFactoryProxy.getLogger(CardCouponUtils.class);

	private static final String APP_NAME = "cardCoupon";
	private static final String AUTH_CODE = "auth_code";
	private static final String SIGN = "sign";
	private static final String TIMESTAMP = "timestamp";
	private static final String UID = "uid";
	private static final String COUPON_STATUS = "coupon_status";
	private static final String SERVICE_LINE_ID = "service_line_id";
	private static final String COUPON_CODE = "coupon_code";
	private static final String MOBILE = "mobile";
	private static final String RECOVERY_TYPE_STR = "recovery_type";
	private static final String RENT_CARD_CODE = "rent_card_code";
	private static final int COUPON_RECOVERY_TYPE = 4; //回退类型标识（1关闭合同 2取消订单 3退租 4其他）
	private static final int CARD_RECOVERY_TYPE = 6; //回退类型标识（1关闭合同 2取消订单 3退租 4取消冻结 5支付失败 6其他）
	private static final String SUCCESS = "0";
	private static final RestTemplate REST_TEMPLATE = new RestTemplate();
	private static final String PHONE_KEY = "phone";
	private static final String RESONSE_PASSPORT_CODE = "20000";        //SSO单点登录接口 判断返回的 code
	private static final String PAGE_KEY = "page";
	private static final String PAGENUM_KEY = "pageNum";
	private static final int SERVICE_LINE_ID_INT = 2;
	private static final int PAGE = 1;
	private static final int PAGENUM=10000;//暂时写死 不分页

	private static final String SYSTEM_ID = "system_id";
	private static final int SYSTEM_ID_INT = 100005;
	private static final String PERIODS = "periods";

	private static final String ORDER_ID = "order_id";
	private static final String CARD_LIST = "card_list";

	//-----------------------------优惠券开始-//

	/**
	 * 查询优惠券 created by cuigh6 on 2017/05/22
	 *
	 * @param uid 用户标识
	 * @return
	 */
	public List<ExtCouponEntity> couponGet(String uid,Integer couponStatus) {
		final String logMsg = "#查询优惠券#-uid:[{}]-响应结果：{}";
		Map<String, Object> map = new TreeMap<>();
		map.put(UID, uid.trim());
		map.put(COUPON_STATUS, couponStatus);
		map.put(SERVICE_LINE_ID, SERVICE_LINE_ID_INT);
		map.put(PAGE_KEY, PAGE);
		map.put(PAGENUM_KEY, PAGENUM);
		Map<String, Object> entity = buildRequestEntity(map);

		WebTarget target = TeslaClientFactory.newDynamicClient(APP_NAME).target("");
		Response response = target.path("/coupon/coupon/search.json")
				.queryParam(AUTH_CODE, entity.get(AUTH_CODE))
				.queryParam(SIGN, entity.get(SIGN))
				.queryParam(TIMESTAMP, entity.get(TIMESTAMP))
				.queryParam(UID, uid)
				.queryParam(COUPON_STATUS,couponStatus)
				.queryParam(SERVICE_LINE_ID, SERVICE_LINE_ID_INT)
				.queryParam(PAGE_KEY,PAGE)
				.queryParam(PAGENUM_KEY,PAGENUM)
				.request(MediaType.APPLICATION_JSON_TYPE).get();

		String result = response.readEntity(String.class);
		try {
			ResponseStructure<List<ExtCouponEntity>> responseStructure = JSON.parseObject(result, new TypeReference<ResponseStructure<List<ExtCouponEntity>>>() {
			});
			//添加日志
			LOGGER.info(logMsg, uid, result);
			if (responseStructure != null && SUCCESS.equals(responseStructure.getError_code())) {
				return responseStructure.getData();
			}
		} catch (Exception e) {
			LOGGER.error("#查询优惠券#-解析失败", e);
		}
		LOGGER.error(logMsg, uid, result);
		return new ArrayList<>();
	}

	/**
	 * 验证优惠券 created by cuigh6 on 2017/05/23
	 *
	 * @param couponCode 优惠券编码
	 * @param uid        用户标识
	 * @return
	 */
	public ExtCouponEntity couponCheck(String couponCode, String uid) {
		final String logMsg = "#验证优惠券#-uid:[{}]-code:[{}]-响应结果：{}";
		Map<String, Object> map = new TreeMap<>();
		map.put(UID, uid.trim());
		map.put(COUPON_CODE, couponCode.trim());
		Map<String, Object> entity = buildRequestEntity(map);
		Form form = getForm(entity);

		WebTarget target = TeslaClientFactory.newDynamicClient(APP_NAME).target("");
		Response response = target.path("/coupon/coupon/check.json")
				.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

		String result = response.readEntity(String.class);
		try {
			ResponseStructure<ExtCouponEntity> responseStructure = JSON.parseObject(result, new TypeReference<ResponseStructure<ExtCouponEntity>>() {
			});
			//添加日志
			LOGGER.info(logMsg, uid, couponCode, result);
			if (responseStructure != null && SUCCESS.equals(responseStructure.getError_code())) {
				return responseStructure.getData();
			}
		} catch (Exception e) {
			LOGGER.error("#验证优惠券#-解析失败", e);
		}

		LOGGER.error(logMsg, uid, couponCode, result);
		return null;
	}

	/**
	 * 使用优惠券 created by cuigh6 on 2017/05/23
	 *
	 * @param couponCode 优惠券编码
	 * @param uid        用户标识
	 * @param mobile     手机号
	 * @return
	 */
	public boolean couponUse(String couponCode, String uid, String mobile) {
		final String logMsg = "#使用优惠券#-uid:[{}]-code:[{}]-响应结果：{}";
		Map<String, Object> map = new TreeMap<>();
		map.put(UID, uid.trim());
		map.put(COUPON_CODE, couponCode.trim());
		map.put(MOBILE, mobile);
		Map<String, Object> entity = buildRequestEntity(map);
		Form form = getForm(entity);
		WebTarget target = TeslaClientFactory.newDynamicClient(APP_NAME).target("");
		Response response = target.path("/coupon/coupon/use.json")
				.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

		String result = response.readEntity(String.class);

		try {
			ResponseStructure<String> responseStructure = JSON.parseObject(result, new TypeReference<ResponseStructure<String>>() {
			});
			//添加日志
			LOGGER.info(logMsg, uid, couponCode, result);
			if (responseStructure != null && SUCCESS.equals(responseStructure.getError_code())) {
				return true;
			}
		} catch (Exception e) {
			LOGGER.error("#使用优惠券#-解析失败", e);
		}
		LOGGER.error(logMsg, uid, couponCode, result);
		return false;
	}

	/**
	 * 恢复优惠券 created by cuigh6 on 2017/05/23
	 *
	 * @param couponCode 优惠券编码
	 * @return
	 */
	public boolean couponRecovery(String couponCode) {
		final String logMsg = "#恢复优惠券#-couponCode:[{}]-响应结果：{}";
		Map<String, Object> map = new TreeMap<>();
		map.put(COUPON_CODE, couponCode.trim());
		map.put(RECOVERY_TYPE_STR, COUPON_RECOVERY_TYPE);
		Map<String, Object> entity = buildRequestEntity(map);
		Form form = getForm(entity);
		WebTarget target = TeslaClientFactory.newDynamicClient(APP_NAME).target("");
		Response response = target.path("/coupon/coupon/recovery.json")
				.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));


		String result = response.readEntity(String.class);

		try {
			ResponseStructure<List<ExtCouponEntity>> responseStructure = JSON.parseObject(result, new TypeReference<ResponseStructure<List<ExtCouponEntity>>>() {
			});
			//添加日志
			LOGGER.info(logMsg, couponCode, result);
			if (responseStructure != null && SUCCESS.equals(responseStructure.getError_code())) {
				return true;
			}
		} catch (Exception e) {
			LOGGER.error("#恢复优惠券#-解析失败", e);
		}
		LOGGER.error(logMsg, couponCode, result);
		return false;
	}

	/**
	 * 兑换优惠券 created by cuigh6 on 2017/05/24
	 *
	 * @param couponCode 优惠券编码
	 * @param uid        用户标识
	 * @return
	 */
	public ExtCouponEntity couponBind(String couponCode, String uid) {
		final String logMsg = "#绑定优惠券#-uid:[{}]-code:[{}]-响应结果：{}";
		Map<String, Object> map = new TreeMap<>();
		map.put(UID, uid.trim());
		map.put(COUPON_CODE, couponCode.trim());
		Map<String, Object> entity = buildRequestEntity(map);

		Form form = getForm(entity);
		WebTarget target = TeslaClientFactory.newDynamicClient(APP_NAME).target("");
		Response response = target.path("/coupon/coupon/bind.json")
				.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));


		String result = response.readEntity(String.class);
		try {
			ResponseStructure<ExtCouponEntity> responseStructure = JSON.parseObject(result, new TypeReference<ResponseStructure<ExtCouponEntity>>() {
			});
			//添加日志
			LOGGER.info(logMsg, uid, couponCode, result);
			if (responseStructure != null && SUCCESS.equals(responseStructure.getError_code())) {
				return responseStructure.getData();
			}
		} catch (Exception e) {
			LOGGER.error("#绑定优惠券#-解析失败", e);
		}
		LOGGER.error(logMsg, uid, couponCode, result);
		return null;
	}

	//-----------------------------优惠券结束-//

	//-----------------------------租金卡开始-//

	/**
	 * 查询租金卡 created by cuigh6 on 2017/05/23
	 *
	 * @param uid 用户标识
	 * @return
	 */
	public List<ExtCouponEntity> cardGet(String uid,Integer couponStatus) {
		final String logMsg = "#查询租金卡#-uid:[{}]-响应结果：{}";
		Map<String, Object> map = new TreeMap<>();
		map.put(UID, uid.trim());
		map.put(COUPON_STATUS, couponStatus);
		map.put(SERVICE_LINE_ID, SERVICE_LINE_ID_INT);
		map.put(PAGE_KEY, PAGE);
		map.put(PAGENUM_KEY, PAGENUM);
		Map<String, Object> entity = buildRequestEntity(map);

		Map<String,String> paramMap = new HashMap<>();
		paramMap.put(UID, uid.trim());
		paramMap.put(COUPON_STATUS, String.valueOf(couponStatus));
		paramMap.put(SERVICE_LINE_ID, String.valueOf(SERVICE_LINE_ID_INT));
		paramMap.put(PAGE_KEY, String.valueOf(PAGE));
		paramMap.put(PAGENUM_KEY, String.valueOf(PAGENUM));
		paramMap.put(AUTH_CODE,entity.get(AUTH_CODE).toString());
		paramMap.put(SIGN,entity.get(SIGN).toString());
		paramMap.put(TIMESTAMP,entity.get(TIMESTAMP).toString());
		String result = null;
		try {
			StringBuilder url = new StringBuilder(PropUtils.getString("tesla.service.cardCoupon.endpoint"));
			url.append("/coupon/rent-card/search.json");
			URIBuilder builder = new URIBuilder(url.toString());
			Set<String> set = map.keySet();
			for(String key: set){
				builder.setParameter(key, paramMap.get(key));
			}
			result = CloseableHttpUtil.sendGet(builder.toString(),null);
			ResponseStructure<List<ExtCouponEntity>> responseStructure = JSON.parseObject(result, new TypeReference<ResponseStructure<List<ExtCouponEntity>>>() {
			});
			//添加日志
			LOGGER.info(logMsg, uid, result);
			if (responseStructure != null && SUCCESS.equals(responseStructure.getError_code())) {
				return responseStructure.getData();
			}
		} catch (Exception e) {
			LOGGER.error("#查询租金卡#-解析失败", e);
		}
		LOGGER.error(logMsg, uid, result);
		return new ArrayList<>();
	}

	/**
	 * @description: 查询员工租金卡及卡券新方法
	 * @author: lusp
	 * @date: 2017/12/23 下午 17:00
	 * @params:
	 * @return:
	 */
	public CardCouponQueryResponse cardGetNew(CardCouponQueryRequest cardCouponQueryRequest) {
		final String logMsg = "#查询租金卡#-uid:[{}]-响应结果：{}";
		Map<String, Object> map = new TreeMap<>();
		map.put(UID, cardCouponQueryRequest.getUid());
		map.put(SYSTEM_ID, SYSTEM_ID_INT);
		map.put(PERIODS, cardCouponQueryRequest.getPeriods());
		Map<String, Object> entity = buildRequestEntity(map);

		Map<String,String> paramMap = new HashMap<>();
		paramMap.put(UID, cardCouponQueryRequest.getUid());
		paramMap.put(SYSTEM_ID, String.valueOf(SYSTEM_ID_INT));
		paramMap.put(PERIODS, String.valueOf(cardCouponQueryRequest.getPeriods()));
		paramMap.put(AUTH_CODE,entity.get(AUTH_CODE).toString());
		paramMap.put(SIGN,entity.get(SIGN).toString());
		paramMap.put(TIMESTAMP,entity.get(TIMESTAMP).toString());
		String result = null;
		try {
			StringBuilder url = new StringBuilder(PropUtils.getString("tesla.service.cardCoupon.endpoint"));
			url.append("/pay/card/search.json");
			URIBuilder builder = new URIBuilder(url.toString());
			Set<String> set = map.keySet();
			for(String key: set){
				builder.setParameter(key, paramMap.get(key));
			}
			result = CloseableHttpUtil.sendGet(builder.toString(),null);
			ResponseStructure<CardCouponQueryResponse> responseStructure = JSON.parseObject(result, new TypeReference<ResponseStructure<CardCouponQueryResponse>>() {
			});
			//添加日志
			LOGGER.info(logMsg, cardCouponQueryRequest.getUid(), result);
			if (responseStructure != null && SUCCESS.equals(responseStructure.getError_code())) {
				return responseStructure.getData();
			}
		} catch (Exception e) {
			LOGGER.error("#查询租金卡#-解析失败", e);
		}
		LOGGER.error(logMsg, cardCouponQueryRequest.getUid(), result);
		return new CardCouponQueryResponse();
	}

	/**
	 * @description: 消费员工租金卡及卡券新方法
	 * @author: lusp
	 * @date: 2017/12/24 下午 22:00
	 * @params:
	 * @return:
	 */
	public CardCouponUseResponse cardUseNew(CardCouponUseRequest cardCouponUseRequest) {
		final String logMsg = "#消费租金卡#-uid:[{}]-响应结果：{}";
		Map<String, Object> map = new TreeMap<>();
		map.put(UID, cardCouponUseRequest.getUid());
		map.put(ORDER_ID, cardCouponUseRequest.getOrder_id());
		map.put(CARD_LIST, JsonEntityTransform.Object2Json(cardCouponUseRequest.getCard_list()));
		Map<String, Object> entity = buildRequestEntity(map);

		String result = null;
		CardCouponUseResponse cardCouponUseResponse = new CardCouponUseResponse();
		try {
			StringBuilder url = new StringBuilder(PropUtils.getString("tesla.service.cardCoupon.endpoint"));
			url.append("/pay/card/use.json");
			HttpPost httpPost = new HttpPost(url.toString());
			HttpEntity reqEntity = MultipartEntityBuilder.create()
					.addPart(UID, new StringBody(cardCouponUseRequest.getUid(), ContentType.TEXT_PLAIN))
					.addPart(ORDER_ID, new StringBody(cardCouponUseRequest.getOrder_id(), ContentType.TEXT_PLAIN))
					.addPart(CARD_LIST, new StringBody(JsonEntityTransform.Object2Json(cardCouponUseRequest.getCard_list()), ContentType.TEXT_PLAIN))
					.addPart(AUTH_CODE, new StringBody(entity.get(AUTH_CODE).toString(), ContentType.TEXT_PLAIN))
					.addPart(SIGN, new StringBody(entity.get(SIGN).toString(), ContentType.TEXT_PLAIN))
					.addPart(TIMESTAMP, new StringBody(entity.get(TIMESTAMP).toString(), ContentType.TEXT_PLAIN))
					.build();
			httpPost.setEntity(reqEntity);
			HttpResponse httpResponse = CloseableHttpUtil.getHttpClient().execute(httpPost);
			HttpEntity httpEntity =  httpResponse.getEntity();
			result = EntityUtils.toString(httpEntity);
			ResponseStructure<CardCouponUseResponse> responseStructure = JSON.parseObject(result, new TypeReference<ResponseStructure<CardCouponUseResponse>>() {
			});
			//添加日志
			LOGGER.info(logMsg, cardCouponUseRequest.getUid(), result);
			if (responseStructure != null && SUCCESS.equals(responseStructure.getError_code())) {
				cardCouponUseResponse = responseStructure.getData();
			}
		} catch (Exception e) {
			LOGGER.error("#消费租金卡#-解析失败", e);
		}
		LOGGER.error(logMsg, cardCouponUseRequest.getUid(), result);
		return cardCouponUseResponse;
	}

	/**
	 * 检查租金卡 created by cuigh6 on 2017/05/23
	 *
	 * @param couponCode 优惠券编码
	 * @param uid        用户标识
	 * @return
	 */
	public ExtCouponEntity cardCheck(String couponCode, String uid) {
		final String logMsg = "#验证租金卡#-uid:[{}]-code:[{}]-响应结果：{}";
		Map<String, Object> map = new TreeMap<>();
		map.put(UID, uid.trim());
		map.put(COUPON_CODE, couponCode.trim());
		Map<String, Object> entity = buildRequestEntity(map);
		Form form = getForm(entity);
		WebTarget target = TeslaClientFactory.newDynamicClient(APP_NAME).target("");
		Response response = target.path("/coupon/rent-card/check.json")
				.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

		String result = response.readEntity(String.class);
		try {
			ResponseStructure<ExtCouponEntity> responseStructure = JSON.parseObject(result, new TypeReference<ResponseStructure<ExtCouponEntity>>() {
			});
			//添加日志
			LOGGER.info(logMsg, uid, couponCode, result);
			if (responseStructure != null && SUCCESS.equals(responseStructure.getError_code())) {
				return responseStructure.getData();
			}
		} catch (Exception e) {
			LOGGER.error("#验证租金卡#-解析失败", e);
		}
		LOGGER.error(logMsg, uid, couponCode, result);
		return null;
	}

	/**
	 * 租金卡使用 created by cuigh6 on 2017/05/23
	 *
	 * @param rentCardCode 租金卡编号
	 * @param uid          用户标识
	 * @return
	 */
	public boolean cardUse(String rentCardCode, String uid) {
		final String logMsg = "#使用租金卡#-uid:[{}]-code:[{}]-响应结果：{}";
		Map<String, Object> map = new TreeMap<>();
		map.put(UID, uid.trim());
		map.put(RENT_CARD_CODE, rentCardCode.trim());
		Map<String, Object> entity = buildRequestEntity(map);
		Form form = getForm(entity);
		WebTarget target = TeslaClientFactory.newDynamicClient(APP_NAME).target("");
		Response response = target.path("/coupon/rent-card/use.json")
				.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

		String result = response.readEntity(String.class);

		try {
			ResponseStructure<String> responseStructure = JSON.parseObject(result, new TypeReference<ResponseStructure<String>>() {
			});
			//添加日志
			LOGGER.info(logMsg, uid, rentCardCode, result);
			if (responseStructure != null && SUCCESS.equals(responseStructure.getError_code())) {
				return true;
			}
		} catch (Exception e) {
			LOGGER.error("#使用租金卡#-解析失败", e);
		}
		LOGGER.error(logMsg, uid, rentCardCode, result);
		return false;
	}

	/**
	 * 恢复租金卡 created by cuigh6 on 2017/05/23
	 *
	 * @param rentCardCode 租金卡编号
	 * @return
	 */
	public boolean cardRecovery(String rentCardCode) {
		final String logMsg = "#恢复租金卡#-rentCardCode:[{}]-响应结果：{}";
		Map<String, Object> map = new TreeMap<>();
		map.put(RENT_CARD_CODE, rentCardCode.trim());
		map.put(RECOVERY_TYPE_STR, CARD_RECOVERY_TYPE);
		Map<String, Object> entity = buildRequestEntity(map);

		Form form = getForm(entity);
		WebTarget target = TeslaClientFactory.newDynamicClient(APP_NAME).target("");
		Response response = target.path("/coupon/rent-card/recovery.json")
				.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

		String result = response.readEntity(String.class);

		try {
			ResponseStructure<List<ExtCouponEntity>> responseStructure = JSON.parseObject(result, new TypeReference<ResponseStructure<List<ExtCouponEntity>>>() {
			});
			//添加日志
			LOGGER.info(logMsg, rentCardCode, result);
			if (responseStructure != null && SUCCESS.equals(responseStructure.getError_code())) {
				return true;
			}
		} catch (Exception e) {
			LOGGER.error("#恢复租金卡#-解析失败", e);
		}
		LOGGER.error(logMsg, rentCardCode, result);
		return false;
	}
	//-----------------------------租金卡结束-//

	/**
	 * 根据uid获取手机号
	 *
	 * @param uid 用户uid
	 * @return phone
	 * @throws Exception
	 */
	public String getPhoneByUid(String uid) throws Exception {
		Map<String, String> params = new HashMap<>();
		params.put("uid", uid);
		String jsonParam = JSON.toJSONString(params);
		LOGGER.info("根据uid获取手机号,参数：{}", jsonParam);
		Map map = REST_TEMPLATE.getForObject(PropUtils.getString("ZRA_CUSTOMER_GET_PHONE"), Map.class, params);
		String responseResult = JSON.toJSONString(map);
		LOGGER.info("根据uid获取手机号，结果:{}", responseResult);
		if (map != null && RESONSE_PASSPORT_CODE.equals(map.get("code"))) {
			Map<String, String> resp = (Map<String, String>) map.get("resp");
			return resp.get(PHONE_KEY);
		} else {
			LOGGER.info(responseResult);
			return "";
		}
	}

	/**
	 * 构建请求实体 created by cuigh6 on 2017/05/22
	 *
	 * @param map 参数
	 * @return
	 */
	private Map<String, Object> buildRequestEntity(Map<String, Object> map) {
		RequestEntity requestEntity = new RequestEntity();
		map.put(AUTH_CODE, requestEntity.getAuth_code());
		map.put(TIMESTAMP, requestEntity.getTimestamp());
		requestEntity.setParams(map);
		String sign = requestEntity.getSign();
		Map<String, Object> result = requestEntity.getParams();
		result.put(SIGN, sign);
		String params = JSON.toJSONString(result);
		LOGGER.info("#卡券系统#-请求参数:{}", params);
		return result;
	}

	/**
	 * 构造post 请求的form
	 *
	 * @param entity 请求参数对象
	 * @return
	 */
	private Form getForm(Map<String, Object> entity) {
		Form form = new Form();
		Iterator<String> it = entity.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			form.param(key, String.valueOf(entity.get(key)));
		}
		return form;
	}

	/**
	 * 卡券系统接口返回结构 created by cuigh6 on 2017/05/22
	 * 注意：必须为静态内部类 否则fastjson解析会报错，因为内部类外部不能访问，需要实例化
	 *
	 * @param <T>
	 */
	static class ResponseStructure<T> {
		private String status;
		private String error_code;
		private String error_message;
		private T data;

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getError_code() {
			return error_code;
		}

		public void setError_code(String error_code) {
			this.error_code = error_code;
		}

		public String getError_message() {
			return error_message;
		}

		public void setError_message(String error_message) {
			this.error_message = error_message;
		}

		public T getData() {
			return data;
		}

		public void setData(T data) {
			this.data = data;
		}
	}

	/**
	 * 请求实体
	 */
	class RequestEntity {
		private String auth_code = "600006";
		private String sign;
		private int timestamp = Integer.parseInt(String.valueOf(System.currentTimeMillis()).substring(0, 10));
		private Map<String, Object> params;

		public Map<String, Object> getParams() {
			return params;
		}

		public void setParams(Map<String, Object> params) {
			this.params = params;
		}

		public String getAuth_code() {
			return auth_code;
		}

		public void setAuth_code(String auth_code) {
			this.auth_code = auth_code;
		}

		public String getSign() {
			Set keySet = params.keySet();
			Iterator it = keySet.iterator();
			StringBuilder queryParam = new StringBuilder();
			while (it.hasNext()) {
				String key = (String) it.next();
				queryParam.append(key).append("=").append(params.get(key)).append("&");
			}
			queryParam.deleteCharAt(queryParam.length() - 1);
			queryParam.append(PropUtils.getString("cardCoupon.authSecretKey"));//系统访问秘钥
			sign = Md5Utils.md5s(queryParam.toString());
			return sign;
		}

		public void setSign(String sign) {
			this.sign = sign;
		}

		public int getTimestamp() {
			return timestamp;
		}

		public void setTimestamp(int timestamp) {
			this.timestamp = timestamp;
		}
	}
}