/*
 * Copyright (c) 2016. Copyright (c) 2016. ziroom.com.
 */
package com.ziroom.minsu.troy.common.interceptor;

import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.troy.constant.Constant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.lang.reflect.Method;
import java.util.UUID;

/**
 * <p>Token拦截器</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author sunzhenlei
 * @version 1.0
 * @date 2016/3/7 21:10
 * @since 1.0
 */
public class TokenInterceptor extends HandlerInterceptorAdapter {

    public static final Logger LOGGER = LoggerFactory.getLogger(TokenInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            Token annotation = method.getAnnotation(Token.class);
            if (annotation != null) {
                boolean needSaveSession = annotation.saveToken();
                if (needSaveSession) {
                    handSaveSession(request, Constant.SESSION_TOKEN_KEY);
                }
                boolean needRemoveToken = annotation.removeToken();
                if (needRemoveToken) {
                    if (isRepeatSubmit(request, Constant.SESSION_TOKEN_KEY)) {
                        return false;
                    }
                    request.getSession(false).removeAttribute(Constant.SESSION_TOKEN_KEY);
                }
            }
            return true;
        } else {
            return super.preHandle(request, response, handler);
        }
    }

    private void handSaveSession(HttpServletRequest request, String sessionKey) {
        String token = UUID.randomUUID().toString();
        LogUtil.info(LOGGER, "token:", token);
        request.getSession(false).setAttribute(sessionKey, token);
    }

    private boolean isRepeatSubmit(HttpServletRequest request, String sessionKey) {
        String serverToken = (String) request.getSession(false).getAttribute(sessionKey);
        if (serverToken == null) {
            return true;
        }
        String clinetToken = request.getParameter(Constant.SESSION_TOKEN_KEY);
        if (clinetToken == null) {
            return true;
        }
        if (!serverToken.equals(clinetToken)) {
            return true;
        }
        return false;
    }
}
