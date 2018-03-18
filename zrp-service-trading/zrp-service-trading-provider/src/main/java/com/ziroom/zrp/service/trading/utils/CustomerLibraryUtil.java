package com.ziroom.zrp.service.trading.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.common.utils.CloseableHttpUtil;
import com.ziroom.minsu.services.common.utils.PropertiesUtils;
import com.ziroom.zrp.service.trading.dto.PersonalInfoDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

//import com.ziroom.zrp.service.trading.proxy.RentContractServiceProxy;

/**
 * <p>查询友家信息工具类</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author xiangb
 * @version 1.0
 * @Date Created in 2017年9月22日
 * @since 1.0
 */
public class CustomerLibraryUtil {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerLibraryUtil.class);
	/**
	 * <p>通过uid获取友家客户综合信息,包括客户认证信息和资质信息</p>
	 * @author xiangb
	 * @created 2017年9月22日
	 * @param uid 用户uid
	 * @return String DataTransferObject类型，可以通过“personalInfo”获取PersonalInfoDto实体
	 */
	public static PersonalInfoDto findAuthInfoFromCustomer(String uid){
		
		if(Check.NuNStr(uid)){
			return null;
		}
		try{
			CloseableHttpUtil closeableHttpUtil = new CloseableHttpUtil();
			Properties properties = PropertiesUtils.getProperties("trading.properties");
	    	String auth_url = properties.getProperty("customer_auth_url");
	    	String customer_auth_secret_key = properties.getProperty("customer_auth_secret_key");
	    	String customer_auth_code = properties.getProperty("customer_auth_code");
	    	
	    	auth_url = auth_url.format(auth_url, customer_auth_code,customer_auth_secret_key,uid);
	    	//根据UID去友家查用户信息
	    	String returnJson = closeableHttpUtil.sendGet(auth_url, null);
	    	LogUtil.info(LOGGER, "【查询友家客户库返回信息】：{}", returnJson);
	    	
	    	JSONObject jsonObject = JSONObject.parseObject(returnJson);
	    	String status = jsonObject.getString("status");
	    	String error_code = jsonObject.getString("error_code");
	    	if("success".equals(status) && "0".equals(error_code)){
	    		String dataString = jsonObject.getString("data");
	    		PersonalInfoDto personalInfo = JsonEntityTransform.json2Object(dataString, PersonalInfoDto.class);
	    		return personalInfo;
	    	}else{
	    		return null;
	    	}
		}catch(Exception e){
			LogUtil.info(LOGGER, "【查询友家客户库异常信息】：e={}", e);
			return null;
		}
		
	}

	public static String findUidByPhone(String phone) {
		LogUtil.info(LOGGER, "【查询友家客户库获取uid】：{}",phone);
		if(Check.NuNStr(phone)) {
			return null;
		}
		try{
			Properties properties = PropertiesUtils.getProperties("trading.properties");
			String customer_query_uid_url = properties.getProperty("customer_query_uid_url");
			LogUtil.info(LOGGER,"customer_query_uid_url={}",customer_query_uid_url);
			customer_query_uid_url = String.format(customer_query_uid_url, phone);
			LogUtil.info(LOGGER,"customer_query_uid_url={}",customer_query_uid_url);
			//根据UID去友家查用户信息
			String returnJson = CloseableHttpUtil.sendGet(customer_query_uid_url,null);
			LogUtil.info(LOGGER, "查询友家客户库获取uid;返回信息phone={},result={}",phone, returnJson);

			JSONObject jsonObject = JSONObject.parseObject(returnJson);
			String code = jsonObject.getString("code");
			String message = jsonObject.getString("message");
			if("20000".equals(code) && "success".equals(message)){
				String uid = jsonObject.getJSONObject("resp").getString("uid");
				return uid;
			}else{
				return null;
			}
		}catch(Exception e){
			LogUtil.error(LOGGER, "【查询友家客户库异常信息】：e={}", e);
			return null;
		}
	}

	/**
	 * 获取用户认证状态
	 * <p>
	 * 1.wiki地址:http://wiki.ziroom.com/pages/viewpage.action?pageId=5472307#id-信用系统接口文档-8.获取用户认证状态
	 * </p>
	 *
	 * @param uid
	 * @return
	 * @author cuigh6
	 * @Date 2017年10月
	 */
	public static JSONObject getUserAuthStatus(String uid) {
		LogUtil.info(LOGGER, "【查询用户实名认证状态】：uid={}", uid);
		if (Check.NuNStr(uid)) {
			return null;
		}
		try {
			Properties properties = PropertiesUtils.getProperties("trading.properties");
			String credit_auth_query_url = properties.getProperty("credit_auth_status_url");

			Map<String, String> paramMap = new HashMap<>();
			paramMap.put("uid", uid);


			//根据UID去查询用户信用信息
			String returnJson = CloseableHttpUtil.sendFormPost(credit_auth_query_url, paramMap);
			LogUtil.info(LOGGER, "【获取用户认证状态】返回结果：uid={}; result={}", uid, returnJson);

			JSONObject jsonObject = JSONObject.parseObject(returnJson);
			Integer code = jsonObject.getInteger("error_code");
			String status = jsonObject.getString("status");
			String message = jsonObject.getString("error_message");
			if (0 == code && "success".equals(status)) {
				JSONObject data = jsonObject.getJSONObject("data");
				return data;
			} else {
				return null;
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "【查询用户实名认证状态异常信息】：e={}", e);
			return null;
		}
	}

	/**
	 * 修改客户库用户信息
	 * wiki:http://wiki.ziroom.com/pages/viewpage.action?pageId=40337453
	 * @param params 参数
	 * @return
	 * @author cuigh6
	 * @Date 2017年12月
	 */
	public static JSONArray saveSocialProof(Map<String, String> params) {
		try {
			Properties properties = PropertiesUtils.getProperties("trading.properties");
			String customer_auth_secret_key = properties.getProperty("customer_auth_secret_key");
			String customer_auth_code = properties.getProperty("customer_auth_code");
			String customer_save_uid_url = properties.getProperty("customer_save_uid_url");

			params.put("auth_code", customer_auth_code);
			params.put("auth_secret_key", customer_auth_secret_key);

			//根据UID修改用户信息
			String returnJson = CloseableHttpUtil.sendFormPost(customer_save_uid_url, params);
			LogUtil.info(LOGGER, "【保存客户信息】返回结果：uid={}; result={}", params.get("uid"), returnJson);

			JSONObject jsonObject = JSONObject.parseObject(returnJson);
			Integer code = jsonObject.getInteger("error_code");
			String status = jsonObject.getString("status");
			String message = jsonObject.getString("error_message");
			if (0 == code && "success".equals(status)) {
				JSONArray data = jsonObject.getJSONArray("data");
				return data;
			} else {
				return null;
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "【保存客户信息】：e={}", e);
			return null;
		}
	}


	public static void main(String[] args) {

		String format = String.format("http://passport.api.ziroom.com/inside/users/uid/v1?phone=%s", "13810327187");
		System.out.printf(format);
		//String uidByPhone = findUidByPhone("13810327187");
	}
}
