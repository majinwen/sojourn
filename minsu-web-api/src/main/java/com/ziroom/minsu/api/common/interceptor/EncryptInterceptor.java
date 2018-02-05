/**
 * @FileName: EncryptInterceptor.java
 * @Package: com.ziroom.cleaning.common.interceptor
 * @author sence
 * @created 9/8/2015 2:47 PM
 * <p/>
 * Copyright 2015 ziroom
 */
package com.ziroom.minsu.api.common.interceptor;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MD5Util;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.api.common.constant.ConstDef;
import com.ziroom.minsu.api.common.constant.HeaderParamName;
import com.ziroom.minsu.api.common.dto.ResponseDto;
import com.ziroom.minsu.api.common.encrypt.EncryptFactory;
import com.ziroom.minsu.api.common.encrypt.IEncrypt;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * 
 * <p>请求加密解密拦截器，针对所有请求进行解密，针对所有响应进行加密</p>
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
public class EncryptInterceptor extends HandlerInterceptorAdapter {

    public static final Logger LOGGER = LoggerFactory.getLogger(EncryptInterceptor.class);

    @Value("#{'${X-Forwarded-Ziroom}'.trim()}")
    private String X_Forwarded_Ziroom;

    /**
     * 解密类型工具:默认DES
     */
    private String encryptType="DES";

    /**
     * 加密参数
     */
    private String paramName="2y5QfvAy";

    /**
     * 签名参数
     */
    private String signName="hPtJ39Xs";

    /**
     * 请求到来前对结果进行解密
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//    	 // zhangshaobin  测试token失效
//    	IEncrypt encrypt2 = EncryptFactory.createEncryption(encryptType);
//		LogUtil.info(LOGGER, "kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk"+request.getRequestURI());
//		ResponseDto responseDto33 = new ResponseDto();
//		if (request.getRequestURI().contains("queryEvaluate")){
//			response.setContentType("application/json; charset=utf-8");
//			responseDto33.setStatus("-1");
//			responseDto33.setMessage("token失效,请重新登录");
//			response.getOutputStream().write(encrypt2.encrypt(JsonEntityTransform.Object2Json(responseDto33)).getBytes());
//			response.flushBuffer();
//			return false;
//			
//		}
//		// zhangshaobin 测试token失效
        //来自网关的数据不进行加解密
        request.setAttribute(ConstDef.PRE_REQUEST_MILLSECOND, System.currentTimeMillis());
        String forwarded = request.getHeader(HeaderParamName.X_Forwarded_Ziroom);
        if (X_Forwarded_Ziroom.equals(forwarded)) {
            //重新设置参数
            setGatewayParamVal(request, response);
            //加密的数据 重新处理数据
            return super.preHandle(request, response, handler);
        }
        String paramValue = request.getParameter(paramName);
        if (!Check.NuNStr(paramValue)) {
        	IEncrypt encrypt = EncryptFactory.createEncryption(encryptType);
            String _paramVal = encrypt.decrypt(paramValue);
            String sign = request.getParameter(signName);
            String _sign = MD5Util.MD5Encode(_paramVal,ConstDef.DEFAULT_CHARSET);
            //签名不对
            if (Check.NuNStr(sign) || !sign.equals(_sign)) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                ResponseDto responseDto = new ResponseDto("参数非法");
                responseDto.setStatus(ConstDef.INVALID_PARAM);
                response.getOutputStream().println(encrypt.encrypt(JsonEntityTransform.Object2Json(responseDto)));
                return false;
            }
            request.setAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE, _paramVal);

        }
        return super.preHandle(request, response, handler);
    }

    /**
     * 移除掉解密后的数据，防止在公网上明文传输
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String requestUrl = request.getRequestURI().toString();
        String paramValue = request.getParameter(paramName);
        String sign = request.getParameter(signName);
        String _paramValue = (String)request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
        Long preMillsecond = (Long)request.getAttribute(ConstDef.PRE_REQUEST_MILLSECOND);
        Long millSecond = System.currentTimeMillis()-preMillsecond;
        LogUtil.debug(LOGGER, "url:[{}],encrypt:[{}],param:[{}],sign:[{}],time:[{}]", requestUrl, paramValue, _paramValue, sign,millSecond);
        request.removeAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
        super.postHandle(request, response, handler, modelAndView);
    }

    /**
     * 设置网关参数
     *
     * @param request
     * @param response
     * @throws IOException
     */
    private void setGatewayParamVal(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        InputStream is = request.getInputStream();
        try {
            request.setAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE, IOUtils.toString(is, "utf-8"));
        } finally {
            IOUtils.closeQuietly(is);
        }

    }

}
