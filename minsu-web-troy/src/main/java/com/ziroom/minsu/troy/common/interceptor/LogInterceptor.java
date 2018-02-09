/*
 * Copyright (c) 2016. Copyright (c) 2016. ziroom.com.
 */
package com.ziroom.minsu.troy.common.interceptor;

import com.asura.framework.base.util.Check;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.troy.constant.Constant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.UUID;

/**
 * <p>访问日志拦截器</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author sunzhenlei
 * @version 1.0
 * @date 2016/3/7 21:04
 * @since 1.0
 */
public class LogInterceptor extends HandlerInterceptorAdapter {

    public static final Logger LOGGER = LoggerFactory.getLogger(LoginInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();

        String userName = "";

        String logToken = (String) session.getAttribute(Constant.LOG_TOKEN_KEY);

        if (Check.NuNStrStrict(logToken)) {
            logToken = UUID.randomUUID().toString();
            session.setAttribute(Constant.LOG_TOKEN_KEY, logToken);
        }
        if (!Check.NuNObj(userName)) {
            LogUtil.info(LOGGER, Constant.LOG_REQUEST_FORMAT, request.getRequestURI().toString(), request.getQueryString(), logToken, userName);
        } else {
            LogUtil.info(LOGGER, Constant.LOG_REQUEST_FORMAT, request.getRequestURI().toString(), request.getQueryString(), logToken);
        }
        request.setAttribute(Constant.PRE_REQUEST_MILLSECOND, System.currentTimeMillis());
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String requestUrl = request.getRequestURI().toString();
        Long preMillsecond = (Long) request.getAttribute(Constant.PRE_REQUEST_MILLSECOND);
        Long millSecond = System.currentTimeMillis() - preMillsecond;
        LogUtil.info(LOGGER, "url:[{}],time:[{}]", requestUrl, millSecond);
        super.postHandle(request, response, handler, modelAndView);
    }
}
