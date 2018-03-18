package com.zra.common.filter;

import com.alibaba.fastjson.JSON;
import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import com.zra.common.utils.PropUtils;
import com.zra.m.tools.MResultReturn;
import com.zra.pay.entity.ThirdParam;
import com.zra.pay.utils.CryptAES;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Map;

/**
 * Author: wangxm113
 * CreateDate: 2017/3/9.
 */
public class MParamFilter implements Filter {
    private static final Logger LOGGER = LoggerFactoryProxy.getLogger("M-FILTERLOG");

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        try {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("token".equals(cookie.getName())) {
                        LOGGER.info("[M站过滤器]请求URL:" + request.getRequestURL() + ";Cookie:" + cookie.getName() + "=" + cookie.getValue());
                        Map parameterMap = request.getParameterMap();
                        parameterMap.put("token", cookie.getValue());
                    }
                }
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            LOGGER.error("[M站过滤器]请求URL:" + request.getRequestURL() + "出错！", e);
            response.getWriter().print(JSON.toJSONString(MResultReturn.toFail()));
            response.getWriter().flush();
            response.getWriter().close();
        }
    }

    @Override
    public void destroy() {

    }
}
