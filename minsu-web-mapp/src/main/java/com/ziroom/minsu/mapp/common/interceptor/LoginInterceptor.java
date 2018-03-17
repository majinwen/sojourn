package com.ziroom.minsu.mapp.common.interceptor;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.mapp.common.constant.MappMessageConst;
import com.ziroom.minsu.mapp.common.entity.AppRequest;
import com.ziroom.minsu.mapp.common.enumvalue.SourceTypeEnum;
import com.ziroom.minsu.mapp.common.util.StringUtil;
import com.ziroom.minsu.services.common.utils.CloseableHttpUtil;
import com.ziroom.minsu.services.customer.api.inner.CustomerInfoService;
import com.ziroom.minsu.services.customer.api.inner.CustomerMsgManagerService;
import com.ziroom.minsu.services.customer.entity.CustomerVo;
import com.ziroom.minsu.valenum.version.VersionCodeEnum;

/**
 * 
 * <p>登陆拦截器 H5整合到移动端
 *   算法：
 *   1.获取参数（uid token sourceType来源 时间戳  随机串  签名）  sourceType  1=安卓  2=ios   sourceType放session
 *   2.来源是M站，查询session  不存在，到M站的登录页面，存在直接访问
 *   3.来源是安卓（或ios），解码   H5签名和 传来的签名做对比  ，通过
 *   查询session,不存在，保存session，存在，对比uid，uid相同，直接访问，不存在，直接到错误页面，错误页面直接向客户端发请求，交给他们处理
 *   4.不通过，，直接到错误页面，错误页面直接向客户端发请求，交给他们处理
 * </p>
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
public class LoginInterceptor extends HandlerInterceptorAdapter{

	private static Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

	/** 请求头属性名 */
	public final static String USERID = LoginInterceptor.class.getName() + ".UserId";

	@Resource(name = "customer.customerMsgManagerService")
	private CustomerMsgManagerService customerMsgManagerService;

	@Resource(name="mapp.messageSource")
	private MessageSource messageSource;

	@Resource(name = "customer.customerInfoService")
	private CustomerInfoService customerInfoService;

	@Value("#{'${CUSTOMER_BLACK_URL}'.trim()}")
	private String CUSTOMER_BLACK_URL;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		//ios或安卓的登录流程
		String contextPath = request.getContextPath();
		//获取当前的session
		HttpSession session = request.getSession();
		

		try {


	
			String sourceType =  request.getParameter("sourceType");//请求来源
			String force = request.getParameter("force");//请求来源
			
			LogUtil.info(logger, "当前sourceType={},force={}", sourceType,force);

			if(!Check.NuNStr(sourceType)){
				Integer  versionCode = null;//版本号
				String versionCodeStr = request.getParameter("versionCode");//请求版本号
				if(!Check.NuNStr(versionCodeStr)){
					versionCode = Integer.valueOf(versionCodeStr);
					LogUtil.info(logger, "当前app版本号versionCode={}", versionCode);
				}
				SourceTypeEnum sourceTypeEnum = SourceTypeEnum.getSourceTypeEnumByCode(Integer.valueOf(sourceType));
				
				if(!Check.NuNObj(sourceTypeEnum)){
					AppRequest appRequest = new AppRequest();
					appRequest.setSourceType(Integer.valueOf(sourceType));
					//校验参数 不通过 直接到客户端 的登录页面
					if(!checkParam(request,appRequest)){
						LogUtil.info(logger, "参数错误，请求参数appRequest={}", appRequest.toJsonStr());
						response.sendRedirect(contextPath + "/app/appError");
						return false;
					}
					String mySign = StringUtil.getSign(new String[]{appRequest.getUid(),appRequest.getToken(),sourceType,appRequest.getTimestamp(),appRequest.getEchostr()});
					if(!mySign.equals(appRequest.getSignature())){
						LogUtil.info(logger, "加密串错误请求signature={},M站sign={}", appRequest.getSignature(),mySign);
						response.sendRedirect(contextPath + "/app/appError");
						return false;
					}

					Object customerVoObj = session.getAttribute(MappMessageConst.SESSION_USER_KEY);
					//存储信息请求来源
					session.setAttribute("sourceType", sourceType);
					session.setAttribute("versionCode", VersionCodeEnum.checkClean(versionCode));
					session.setAttribute("versionCodeInit",versionCode);
					if (VersionCodeEnum.checkOrg(versionCode)){

						/*if (Check.NuNStr(force) || !"1".equals(force)){
							//当前的版本信息验证通过
							session.setAttribute("sourceOrg", sourceTypeEnum.getCode());
						}
*/
						if (!Check.NuNObj(sourceTypeEnum)){
							//当前的版本信息验证通过
							session.setAttribute("sourceOrg", sourceTypeEnum.getCode());
						}

					}
					CustomerVo customerVo = null;
					//session 为空  用户存在，保存session，用户不存在，保存用户信息，session只存uid
					if (Check.NuNObj(customerVoObj)) {
						customerVo = saveCusotmerVo(appRequest, request, customerVo);
						session.setAttribute(MappMessageConst.SESSION_USER_KEY, customerVo);
						if (!Check.NuNObj(customerVo)){
							request.setAttribute(LoginInterceptor.USERID, customerVo.getUid());
						}
						return super.preHandle(request, response, handler);
					}
					customerVo =  (CustomerVo) customerVoObj;
					//用户不一致 清空当前session 保存新的session
					if(!customerVo.getUid().equals(appRequest.getUid())){
						session.removeAttribute(MappMessageConst.SESSION_USER_KEY);
						customerVo = saveCusotmerVo(appRequest, request, customerVo);
						session.setAttribute(MappMessageConst.SESSION_USER_KEY, customerVo);
						return super.preHandle(request, response, handler);
					}

					/*if (isBlack(customerVo.getUid())){
						LogUtil.info(logger, "账号被禁用 uid={}",customerVo.getUid());
						response.sendRedirect(contextPath + "/app/error");
					}*/
					request.setAttribute("customerVo", customerVo);
					request.setAttribute(LoginInterceptor.USERID, customerVo.getUid());
					return super.preHandle(request, response, handler);
				}
			}
		} catch (IOException e) {
			LogUtil.error(logger, "客户端登录异常e={}", e);
			response.sendRedirect(contextPath + "/app/appError");
			return false;
		}
		return mappLogin(request, response, handler);

	}

	/**
	 * 用户是否在黑名单列表中
	 * @author jixd
	 * @param uid
	 * @return
	 */
	protected boolean isBlack(String uid){
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
			LogUtil.error(logger,"黑名单接口调用异常e={}",e);
		}
		return is;
	}

	/**
	 * 
	 * 保存用户信息到session
	 *
	 * @author yd
	 * @created 2016年5月30日 下午2:50:00
	 *
	 */
	protected   CustomerVo  saveCusotmerVo(AppRequest appRequest,HttpServletRequest request,CustomerVo customerVo){
		String customerJson = customerMsgManagerService.getCutomerVoFromDb(appRequest.getUid());
		DataTransferObject customerDto = JsonEntityTransform.json2DataTransferObject(customerJson);
		if(customerDto.getCode() == DataTransferObject.SUCCESS){
			customerVo = customerDto.parseData("customerVo", new TypeReference<CustomerVo>() {});
			//用户信息不存在处理  数据库就保存当前用户的uid
			if(Check.NuNObj(customerVo)){
				customerVo = new CustomerVo();
				customerVo.setUid(appRequest.getUid());
				customerVo.setToken(appRequest.getToken());
				CustomerBaseMsgEntity customerBaseMsg= new CustomerBaseMsgEntity();
				customerBaseMsg.setUid(customerVo.getUid());
				request.setAttribute("customerVo", customerVo);
				JsonEntityTransform.json2DataTransferObject(customerInfoService.insertCustomerInfo(JsonEntityTransform.Object2Json(customerBaseMsg)));
			}
		}
		return customerVo;
	}

	/**
	 * 
	 * M站的登录流程
	 *
	 * @author yd
	 * @created 2016年5月25日 下午9:57:39
	 *
	 * @param request
	 * @param response
	 * @param handler
	 * @return
	 * @throws IOException 
	 * @throws Exception 
	 */
	private boolean mappLogin(HttpServletRequest request,HttpServletResponse response, Object handler) throws IOException{

		HttpSession session = request.getSession();
		String contextPath = request.getContextPath();
		Object customerVoObj = session.getAttribute(MappMessageConst.SESSION_USER_KEY);

		Integer  versionCode = null;//版本号
		String versionCodeStr = request.getParameter("versionCode");//请求版本号
		if(!Check.NuNStr(versionCodeStr)){
			versionCode = Integer.valueOf(versionCodeStr);
			LogUtil.info(logger, "当前app版本号versionCode={}", versionCode);
		}
		session.setAttribute("versionCode", VersionCodeEnum.checkClean(versionCode));
		session.setAttribute("versionCodeInit",versionCode);
		int contextPathLen = 0;
		if (!Check.NuNStrStrict(contextPath)) {
			contextPathLen = contextPath.length();
		}
		Object sourceTypeObj =session.getAttribute("sourceOrg");//请求来源
		if(Check.NuNObj(sourceTypeObj)){
			  session.setAttribute("sourceOrg", "0");
		}
		LogUtil.info(logger, "当前sourceTypeObj={}", sourceTypeObj);
		try {
			if (Check.NuNObj(customerVoObj)) {
				// 获取此请求的地址，请求地址包含application name，进行subString操作，去除application name
				String path = request.getRequestURI().substring(contextPathLen);
				// 获得请求中的参数
				String queryString = request.getQueryString();
				// 预防空指针
				if (Check.NuNStrStrict(queryString)) {
					queryString = "";
				}
				// 拼凑得到登陆之前的地址
				if(!Check.NuNStr(queryString)){
					queryString = "?" + queryString;
				}
				String realPath = path+queryString;
				// 存入session，方便调用
				session.setAttribute(MappMessageConst.SESSION_RETURN_URL_KEY, realPath);
				response.sendRedirect(contextPath + "/customer/login?" + MappMessageConst.SESSION_RETURN_URL_KEY + "=" + realPath);
				return false;
			} else {
				String returnUrl = request.getParameter(MappMessageConst.SESSION_RETURN_URL_KEY);
				if(!Check.NuNStrStrict(returnUrl) ){
					Object seesionReturnUrl =  session.getAttribute(MappMessageConst.SESSION_RETURN_URL_KEY);
					if (Check.NuNObj(seesionReturnUrl)&&!returnUrl.equals(seesionReturnUrl.toString())) {
						LogUtil.warn(logger, MappMessageConst.LOG_FORMAT, "requestReturnUrl" + returnUrl, "sessionReturnUrl" + seesionReturnUrl);
						response.sendRedirect(contextPath + "/customer/login");
						return false;
					}
				}
				CustomerVo customerVo =  (CustomerVo) customerVoObj;
				request.setAttribute("customerVo", customerVo);
				return super.preHandle(request, response, handler);
			}

		} catch (Exception e) {
			LogUtil.error(logger, "客户端登录异常e={}", e);
			response.sendRedirect(contextPath + "/app/appError");
			return false;
		}

	}

	/**
	 * 
	 * 校验参数
	 *
	 * @author yd
	 * @created 2016年5月25日 下午8:51:52
	 *
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	protected boolean checkParam(HttpServletRequest request,AppRequest appRequest) throws UnsupportedEncodingException{

		String uid = request.getParameter("dWlk");
		String token = request.getParameter("token");
		String timestamp = request.getParameter("timestamp");
		String echostr = request.getParameter("echostr");
		String signature = request.getParameter("signature");

		if(Check.NuNStr(uid)
				||Check.NuNStr(token)
				||Check.NuNStr(timestamp)
				||Check.NuNStr(echostr)
				||Check.NuNStr(signature)){
			return false;
		}

		if(appRequest == null) appRequest = new AppRequest();
		uid = new String(Base64.decodeBase64(uid),"utf-8");
		appRequest.setUid(uid);
		appRequest.setEchostr(echostr);
		appRequest.setSignature(signature);
		appRequest.setTimestamp(timestamp);
		appRequest.setToken(token);
		return true;
	}

}
