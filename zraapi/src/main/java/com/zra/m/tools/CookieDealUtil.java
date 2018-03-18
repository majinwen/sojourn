package com.zra.m.tools;

import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Author: wangxm113
 * CreateDate: 2017/3/17.
 */
public class CookieDealUtil {
    private static final Logger LOGGER = LoggerFactoryProxy.getLogger(CookieDealUtil.class);

    public static String getCookieByRequest(HttpServletRequest servletRequest, String key) {
        LOGGER.info("[M站获取token]请求URL:" + servletRequest.getRequestURL());
        Cookie[] cookies = servletRequest.getCookies();
        if (cookies != null || key != null) {
            for (Cookie cookie : cookies) {
                LOGGER.info("[M站获取token]请求URL:" + servletRequest.getRequestURL() + ";key=" + cookie.getName() + ";value=" + cookie.getValue());
                if (key.equals(cookie.getName())) {
                    LOGGER.info("[M站获取token]请求URL:" + servletRequest.getRequestURL() + ";token:" + cookie.getValue());
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
