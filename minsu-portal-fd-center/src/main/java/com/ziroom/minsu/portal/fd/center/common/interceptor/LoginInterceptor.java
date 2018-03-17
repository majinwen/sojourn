/**
 * @FileName: LoginInterceptor.java
 * @Package com.ziroom.minsu.portal.front.common.interceptor
 * 
 * @author jixd
 * @created 2016年7月28日 下午1:33:31
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.portal.fd.center.common.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.portal.fd.center.common.constant.Constant;
import com.ziroom.minsu.services.common.utils.CloseableHttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>PC登陆拦截器</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author jixd
 * @since 1.0
 * @version 1.0
 */
public class LoginInterceptor extends HandlerInterceptorAdapter{
	
	/**
	 * 日志对象
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(LoginInterceptor.class);
	
	@Value("#{'${SSO_USER_INFO}'.trim()}")
	private String SSO_USER_INFO;
	
	@Value("#{'${SSO_USER_AUTH}'.trim()}")
	private String SSO_USER_AUTH;
	
	@Value("#{'${SSO_USER_LOGIN_URL}'.trim()}")
	private String SSO_USER_LOGIN_URL;
	
	@Value("#{'${MINSU_WEB_SYS}'.trim()}")
	private String MINSU_WEB_SYS;

	@Value("#{'${MINSU_WEB_ACCEPT}'.trim()}")
	private String MINSU_WEB_ACCEPT;

	@Value("#{'${CUSTOMER_BLACK_URL}'.trim()}")
	private String CUSTOMER_BLACK_URL;


	/**
	 * 请求拦截
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//String token = CookieUtils.getCookieValue(request, Constant.COOKIE_TOKEN, true);
		HttpSession session = request.getSession();
		session.setAttribute(Constant.CURRENT_TOKEN, "token");
		session.setAttribute("token", "a06f82a2-423a-e4e3-4ea8-e98317540190");
//		String queryStr = request.getQueryString();
//		String currentUrl = request.getScheme()+"://"+ request.getServerName()+request.getRequestURI();
//		
//		String reqType = request.getHeader("X-Requested-With");
//		
//		if(!Check.NuNStr(queryStr)){
//			currentUrl += "?"+queryStr;
//		}
//		String returnUrl = SSO_USER_LOGIN_URL + currentUrl;
//		if(Check.NuNStr(token)){
//			if ("XMLHttpRequest".equalsIgnoreCase(reqType)) {
//				DataTransferObject dto = new DataTransferObject();
//        		//重新登陆
//        		dto.setErrCode(2);
//        		dto.setMsg("登陆失效，请重新登陆");
//        		response.getWriter().print(dto.toJsonString());
//        		return false;
//        	}
//			//返回跳转登陆页
//			response.sendRedirect(returnUrl);
//			return false;
//		}
//		//获取请求头部参数
//		Map<String,String> headerMap = ApiUtils.getHeaderMap(request,MINSU_WEB_ACCEPT,MINSU_WEB_SYS);
//		Map<String,String> paramMap = new HashMap<>();
//		headerMap.put("token", token);
//		String valResp = CloseableHttpsUtil.sendFormPost(SSO_USER_AUTH, paramMap, headerMap);
//		
//		UserSimpleResponse simpleResponse = JSONObject.parseObject(valResp,UserSimpleResponse.class);
//		if(!LoginCodeEnum.SUCCESS.getCode().equals(simpleResponse.getCode())){
//			LogUtil.info(LOGGER, "登陆拦截器 token失效，返回登陆页,token={}", token);
//			if ("XMLHttpRequest".equalsIgnoreCase(reqType)) {
//				DataTransferObject dto = new DataTransferObject();
//        		//重新登陆
//        		dto.setErrCode(2);
//        		dto.setMsg("登陆失效，请重新登陆");
//        		response.getWriter().print(dto.toJsonString());
//        		return false;
//        	}
//			response.sendRedirect(returnUrl);
//			return false;
//		}
//		
//		//token有效状态
//		HttpSession session = request.getSession();
//		String seesionToken = (String) session.getAttribute(Constant.CURRENT_TOKEN);
//		String uid = (String) session.getAttribute(token);
//		//判断sessionToken是否与cookie中相同
//		if(!token.equals(seesionToken)){
//			session.setAttribute(Constant.CURRENT_TOKEN, token);
//			session.removeAttribute(seesionToken);
//			uid = null;
//		}
//		if(Check.NuNStr(uid)){
//			//获取用户基本信息
//			String infoResp = CloseableHttpsUtil.sendGet(SSO_USER_INFO, headerMap);
//			UserInfoResponse userInfoResponse = JSONObject.parseObject(infoResp, UserInfoResponse.class);
//			if(LoginCodeEnum.SUCCESS.getCode().equals(userInfoResponse.getCode())){
//				uid = userInfoResponse.getResp().getUid();
//				session.setAttribute(token,uid);
//			}else{
//				if ("XMLHttpRequest".equalsIgnoreCase(reqType)) {
//					DataTransferObject dto = new DataTransferObject();
//	        		//重新登陆
//	        		dto.setErrCode(2);
//	        		dto.setMsg("登陆失效，请重新登陆");
//	        		response.getWriter().print(dto.toJsonString());
//	        		return false;
//	        	}
//				LogUtil.error(LOGGER, "登陆拦截器获取用户UID失败:token={},message={}",token,userInfoResponse.getMessage());
//				//失败  返回登陆页
//				response.sendRedirect(returnUrl);
//				return false;
//			}
//		}
//
//
//		if(isBlack(uid)){
//			response.sendRedirect("/error/500");
//		}

		return true;
	}

	/**
	 * 黑名单接口
	 * @param uid
	 * @return
	 */
	private boolean isBlack(String uid){
		boolean is = false;
		try{
			Map<String,String> paramMap = new HashMap<>();
			paramMap.put("uid",uid);
			String resultJson = CloseableHttpUtil.sendFormPost(CUSTOMER_BLACK_URL, paramMap);
			JSONObject jsonObject = JSONObject.parseObject(resultJson);
			Integer errorCode = jsonObject.getInteger("error_code");
			if (errorCode == 0){
				Integer flag = jsonObject.getInteger("data");
				if (flag == 1){
					is = true;
				}
			}
		}catch (Exception e){
			LogUtil.error(LOGGER,"黑名单接口调用异常e={}",e);
		}
		return is;
	}

}
