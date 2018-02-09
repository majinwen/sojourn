/*
 * Copyright (c) 2016. Copyright (c) 2016. ziroom.com.
 */
package com.ziroom.minsu.troy.common.interceptor;

import com.asura.framework.base.util.Check;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.troy.constant.Constant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * <p>登录拦截器</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author sunzhenlei
 * @version 1.0
 * @date 2016/3/7 20:09
 * @since 1.0
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();

        String contextPath = request.getContextPath();

        String userName = "";

        if (Check.NuNObj(userName)) {
            int contextPathLen = 0;
            if (!Check.NuNStrStrict(contextPath)) {
                contextPathLen = contextPath.length();
            }
            // 获取此请求的地址，请求地址包含application name，进行subString操作，去除application name
            String path = request.getRequestURI().substring(contextPathLen);
            // 获得请求中的参数
            String queryString = request.getQueryString();
            // 预防空指针
            if (Check.NuNStrStrict(queryString)) {
                queryString = "";
            }
            // 拼凑得到登陆之前的地址
            String realPath = path + "?" + queryString;
            // 存入session，方便调用
            session.setAttribute(Constant.SESSION_RETURN_URL_KEY, realPath);
            response.sendRedirect(contextPath + "/login?" + Constant.SESSION_RETURN_URL_KEY + "=" + realPath);
            return false;
        } else {
            String returnUrl = request.getParameter(Constant.SESSION_RETURN_URL_KEY);
            String seesionReturnUrl = (String) session.getAttribute(Constant.SESSION_RETURN_URL_KEY);
            if (!Check.NuNStrStrict(returnUrl) && !returnUrl.equals(seesionReturnUrl)) {
                LogUtil.warn(LOGGER, Constant.LOG_FORMAT, "requestReturnUrl" + returnUrl, "sessionReturnUrl" + seesionReturnUrl);
                response.sendRedirect(contextPath + "/login");
                return false;
            }
            request.setAttribute(Constant.SESSION_USER_KEY, userName);
            return super.preHandle(request, response, handler);
        }
    }
}
