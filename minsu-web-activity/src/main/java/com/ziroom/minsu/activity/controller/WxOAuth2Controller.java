/**
 * @FileName: WxOauth2Controller.java
 * @Package com.ziroom.minsu.activity.controller
 * 
 * @author bushujie
 * @created 2017年8月28日 下午6:27:32
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.activity.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.activity.common.utils.PropertiesUtil;

/**
 * <p>微信授权登录回调页</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
@RequestMapping("/oauth")
@Controller
public class WxOAuth2Controller {
	
	/**
	 * 日志对象
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(WxOAuth2Controller.class);
	
	@Value("#{'${APPID}'.trim()}")
	private String appId;
	
	@Value("#{'${APPSECRET}'.trim()}")
	private String appSecret;
	
	
	@Value("#{'${PAGE_ACCESS_TOKEN_URL}'.trim()}")
	private String pageAccessTokenUrl;
	
	@Value("#{'${USER_INFO_URL}'.trim()}")
	private String userInfoUrl;
	
	@Value("#{'${IS_OAUTH_URL}'.trim()}")
	private String isOauthUrl;
	
	@Value("#{'${TO_OAUTH_URL}'.trim()}")
	private String toOauthUrl;
	
	@Resource(name="propertyConfigurer")
	private PropertiesUtil propertiesUtil;
	
	private static final RestTemplate restTemplate = new RestTemplate();
	
	/**
	 * 
	 * 微信网页授权获取用户信息
	 *
	 * @author bushujie
	 * @created 2017年8月28日 下午7:01:27
	 *
	 * @param code
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("oauthUserInfo")
	public String oauthUserInfo(String code,String state) throws UnsupportedEncodingException{
		//code获取网页授权access_token
		String accessTokenJson = restTemplate.getForObject(pageAccessTokenUrl, String.class, appId, appSecret,code);
		LogUtil.info(LOGGER, "获取网页授权access_token结果："+accessTokenJson);
		JSONObject accessTokenObject = SOAResParseUtil.getJsonObj(accessTokenJson);
		if (Check.NuNObj(accessTokenObject) || !accessTokenObject.containsKey("access_token") || !accessTokenObject.containsKey("expires_in")) {
			LogUtil.info(LOGGER, "获取微信网页授权access_token失败");
			return "";
		}
		//access_token和openid获取用户信息
		String userInfoJson = restTemplate.getForObject(userInfoUrl, String.class,accessTokenObject.getString("access_token"),accessTokenObject.getString("openid"));
		LogUtil.info(LOGGER, "获取用户信息结果："+userInfoJson);
		JSONObject userInfoObject = SOAResParseUtil.getJsonObj(userInfoJson);
		if (Check.NuNObj(userInfoObject) || !userInfoObject.containsKey("nickname") || !userInfoObject.containsKey("headimgurl")) {
			LogUtil.info(LOGGER, "获取用户信息失败");
			return "";
		}
		LogUtil.info(LOGGER, "微信返回state："+state);
		state=URLDecoder.decode(state, "utf-8");
		//获取配置活动页面地址
		String groupSnPageKey="";
		if(state.contains("&")){
			groupSnPageKey=state.split("&")[state.split("&").length-1].split("=")[1];
		} else {
			groupSnPageKey=state;
		}
		LogUtil.info(LOGGER, "groupSnPageKey："+groupSnPageKey);
		String actUrl=propertiesUtil.get("ACG_"+groupSnPageKey);
		LogUtil.info(LOGGER, "获取到的活动地址："+actUrl);
		//获取活动页面地址
		if(state.contains("&")){
			state=state.substring(0, state.lastIndexOf("&"));
			return "redirect:"+actUrl+"?"+state+"&nickname="+userInfoObject.getString("nickname")+"&headimgurl="+userInfoObject.getString("headimgurl");
		} else {
			return "redirect:"+actUrl+"?nickname="+userInfoObject.getString("nickname")+"&headimgurl="+userInfoObject.getString("headimgurl");
		}
	}
	
	/**
	 * 
	 * 判断是否授权
	 *
	 * @author bushujie
	 * @created 2017年8月29日 下午6:50:02
	 *
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("isOauth")
	@ResponseBody
	public void isOauth(HttpServletResponse response,HttpServletRequest request) throws IOException{
        response.setContentType("text/plain");
        String callBackName = request.getParameter("callback");
		DataTransferObject dto=new DataTransferObject();
		dto.putValue("oauthUrl", toOauthUrl);
		LogUtil.info(LOGGER, "是否授权返回地址:"+toOauthUrl);
		response.getWriter().write(callBackName + "("+dto.toJsonString()+")");
	}
	
	/**
	 * 
	 * 去授权页面
	 *
	 * @author bushujie
	 * @created 2017年8月29日 下午7:50:31
	 *
	 * @param returnUrl
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("toOauth")
	public String toOauth(String returnUrl,String groupSn) throws Exception{
		LogUtil.info(LOGGER, "前端返回returnUrl:"+returnUrl+"活动编号："+groupSn);
		LogUtil.info(LOGGER, "去认证isOauthUrl:"+isOauthUrl);
		//传给微信的参数state
		String state="";
		if(returnUrl.contains("?")){
			state=returnUrl.split("\\?")[1]+"&groupSn="+groupSn;
		} else {
			state=groupSn;
		}
		LogUtil.info(LOGGER, "state:"+state);
		LogUtil.info(LOGGER, "state:"+URLEncoder.encode(state, "utf-8"));
		String oauthUrl=isOauthUrl.replace("{state}",URLEncoder.encode(state, "utf-8"));
		LogUtil.info(LOGGER, "oauthUrl:"+oauthUrl);
		return "redirect:"+oauthUrl;
	}
}
