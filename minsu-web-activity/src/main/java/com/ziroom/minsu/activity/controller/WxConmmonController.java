/**
 * @FileName: WxConmmonController.java
 * @Package com.ziroom.minsu.activity.controller
 * 
 * @author yd
 * @created 2016年12月26日 下午5:30:22
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.activity.controller;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.activity.common.utils.WxShareVo;

/**
 * <p>微信二次分享</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
public abstract class WxConmmonController {

	/**
	 * 日志对象
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(TopicsController.class);

	private static final RestTemplate restTemplate = new RestTemplate();

	private static final Map<String, Object> TOKEN_MAP = new ConcurrentHashMap<String, Object>();

	private static final Map<String, Object> TICKET_MAP = new ConcurrentHashMap<String, Object>();

	private static final Map<String, Object> HOLDER_MAP = new ConcurrentHashMap<String, Object>();
	
	@Value("#{'${APPSECRET}'.trim()}")
	private String appSecret;

	@Value("#{'${NONCESTR}'.trim()}")
	private String nonceStr;

	@Value("#{'${GET_ACCESS_TOKEN_URL}'.trim()}")
	private String accessTokenUrl;

	@Value("#{'${GET_API_TICKET_URL}'.trim()}")
	private String apiTicketUrl;
	

	@Value("#{'${APPID}'.trim()}")
	private String appId;
	
	/**
	 * 
	 * 获取微信二次分享参数
	 *
	 * @author yd
	 * @created 2016年12月26日 下午5:37:45
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	public void wxShare(HttpServletRequest request){
		
		try {
			synchronized (request) {
				setAttributes(request);
			}
		}catch (Exception e) {
			LogUtil.error(LOGGER, "微信二次分享error:{}", e);
		} 
	}
	

	/**
	 * 
	 * 设置微信二次分享参数
	 *
	 * @author liujun
	 * @created 2016年9月1日
	 *
	 * @param request
	 */
	private void setAttributes(HttpServletRequest request) {
		this.getAccessToken();
		this.getTicket();
		this.sha1(request);
	}


	/**
	 * sha1加密
	 *
	 * @author liujun
	 * @param request 
	 * @created 2016年9月1日
	 *
	 */
	private void sha1(HttpServletRequest request) {
		if(!HOLDER_MAP.isEmpty() && HOLDER_MAP.size() == 3){
			StringBuilder sb = new StringBuilder(200);
			String jsApiTicket = (String) HOLDER_MAP.get("jsapi_ticket");
			sb.append("jsapi_ticket=").append(jsApiTicket).append("&");
			String noncestr = (String) HOLDER_MAP.get("noncestr");
			sb.append("noncestr=").append(noncestr).append("&");
			long timestamp = (long) HOLDER_MAP.get("timestamp");
			sb.append("timestamp=").append(timestamp).append("&");

			StringBuilder requestUrl = new StringBuilder(200);
			String urlHttps = request.getRequestURL().toString();
			if(!urlHttps.contains("https")){
				urlHttps = urlHttps.replace("http", "https");
			}
			requestUrl.append(urlHttps);
			LogUtil.info(LOGGER, "url={}",urlHttps);
			requestUrl.append("?");
			requestUrl.append(request.getQueryString());
			String url = requestUrl.toString();
			if(url.indexOf("#") > -1){
				url = url.substring(0, url.indexOf("#"));
			}

			sb.append("url=").append(url);
			String signature = DigestUtils.sha1Hex(sb.toString());
			request.setAttribute("signature", signature);
			request.setAttribute("appId", appId);
			request.setAttribute("nonceStr", nonceStr);
			request.setAttribute("timestamp", HOLDER_MAP.get("timestamp"));
		}

	}

	/**
	 * 获取微信 ticket
	 *
	 * @author liujun
	 * @return 
	 * @created 2016年9月1日
	 *
	 */
	private void getTicket() {
		if(!isTicketValid()){
			TICKET_MAP.clear();
			String accessToken = (String) TOKEN_MAP.get("accessToken");
			if(Check.NuNStr(accessToken)){
				LogUtil.info(LOGGER, "accessToken is null or blank", JsonEntityTransform.Object2Json(TOKEN_MAP));
				return;
			}

			long currentMillis = System.currentTimeMillis();
			LogUtil.info(LOGGER, "获取微信ticket，标识weixin={}", currentMillis);
			//请求微信获得签名参数，超时重试
			String ticketJson = "";
			for(int i=0;i<3;i++){
				try{
					ticketJson = restTemplate.getForObject(apiTicketUrl, String.class, accessToken);
					if(!Check.NuNObj(ticketJson)){
						break;
					}
				}catch (RestClientException e){
					LogUtil.info(LOGGER,"第"+i+"次请求失败，重试......");
					ticketJson = restTemplate.getForObject(apiTicketUrl, String.class, accessToken);
					if(!Check.NuNObj(ticketJson)){
						break;
					}
				}
			}
			JSONObject ticketObject = SOAResParseUtil.getJsonObj(ticketJson);
			if (Check.NuNObj(ticketObject) || !ticketObject.containsKey("errcode")) {
				LogUtil.info(LOGGER, "获取微信ticket失败");
				return;
			}

			int errcode = ticketObject.getIntValue("errcode");
			if (Check.NuNObj(errcode) || errcode != 0) {
				LogUtil.info(LOGGER, "获取微信ticket失败");
				return;
			}

			String ticket = ticketObject.getString("ticket");
			int expiresIn = ticketObject.getIntValue("expires_in");
			if (!Check.NuNStr(ticket) && !Check.NuNObj(expiresIn)) {
				long expireTime = currentMillis +(expiresIn*1000 >> 1) + (expiresIn*1000 >> 2);;
				LogUtil.info(LOGGER, "ticketExpireTime失效时间:{}", expireTime);
				TICKET_MAP.put("ticketExpireTime", expireTime);
				TICKET_MAP.put("ticket", ticket);

				HOLDER_MAP.put("jsapi_ticket", ticket);
				HOLDER_MAP.put("noncestr", nonceStr);
				HOLDER_MAP.put("timestamp", currentMillis);
			}
		}
	}

	/**
	 * 判断ticket是否有效
	 *
	 * @author liujun
	 * @created 2016年9月1日
	 *
	 * @return
	 */
	private boolean isTicketValid() {
		if(TICKET_MAP.isEmpty() || !TICKET_MAP.containsKey("ticket") || !TICKET_MAP.containsKey("ticketExpireTime")){
			return false;
		}

		Object ticket = TICKET_MAP.get("ticket");
		Object ticketExpireTime = TICKET_MAP.get("ticketExpireTime");
		if(Check.NuNObj(ticket) || Check.NuNObj(ticketExpireTime)){
			return false;
		}

		if(!(ticket instanceof String) || !(ticketExpireTime instanceof Long)){
			return false;
		}

		if(System.currentTimeMillis() > (long)ticketExpireTime){
			return false;
		}

		return true;
	}
	
	/**
	 * 获取微信access_token
	 * {"expires_in":7200,"access_token":"j2_sn6U2lAV8uqYw4SUhN7w8t8S6i-54bfIbmWZhiajdtZWsjLut0KR6X3VA2biYI9uXTjIeqvzZmjMkzW7mNcQnbMh2SemP3KDNqODTzikTAAaAAAKRR"}
	 * @author liujun
	 * @created 2016年9月1日
	 *
	 */
	private void getAccessToken() {
		if(!isTokenValid()){
			TOKEN_MAP.clear();
			long currentMillis = System.currentTimeMillis();
			LogUtil.info(LOGGER, "获取微信token，标识weixin={}", currentMillis);
			String accessTokenJson = restTemplate.getForObject(accessTokenUrl, String.class, appId, appSecret);
			JSONObject accessTokenObject = SOAResParseUtil.getJsonObj(accessTokenJson);
			if (Check.NuNObj(accessTokenObject) || !accessTokenObject.containsKey("access_token") || !accessTokenObject.containsKey("expires_in")) {
				LogUtil.info(LOGGER, "获取微信access_token失败");
				return;
			}

			String accessToken = accessTokenObject.getString("access_token");
			int expiresIn = accessTokenObject.getIntValue("expires_in");
			if (Check.NuNStr(accessToken) || Check.NuNObj(expiresIn)) {
				LogUtil.info(LOGGER, "获取微信access_token为空");
				return;
			}

			long expireTime = currentMillis + (expiresIn*1000 >> 1) + (expiresIn*1000 >> 2);
			LogUtil.info(LOGGER, "tokenExpireTime失效时间:{}", expireTime);
			TOKEN_MAP.put("tokenExpireTime", expireTime);
			TOKEN_MAP.put("accessToken", accessToken);
		}
	}
	

	/**
	 * 判断access_token是否有效
	 *
	 * @author liujun
	 * @return 
	 * @created 2016年9月1日
	 *
	 */
	private boolean isTokenValid() {
		if(TOKEN_MAP.isEmpty() || !TOKEN_MAP.containsKey("accessToken") || !TOKEN_MAP.containsKey("tokenExpireTime")){
			return false;
		}

		Object accessToken = TOKEN_MAP.get("accessToken");
		Object tokenExpireTime = TOKEN_MAP.get("tokenExpireTime");
		if(Check.NuNObj(accessToken) || Check.NuNObj(tokenExpireTime)){
			return false;
		}

		if(!(accessToken instanceof String) || !(tokenExpireTime instanceof Long)){
			return false;
		}

		if(System.currentTimeMillis() > (long)tokenExpireTime){
			return false;
		}

		return true;
	}
}
