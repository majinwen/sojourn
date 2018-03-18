package com.zra.common.filter;

import com.alibaba.fastjson.JSON;
import com.zra.common.enums.ErrorEnum;
import com.zra.common.error.AppResult;
import org.slf4j.Logger;
import com.apollo.logproxy.slf4j.LoggerFactoryProxy;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by cuigh6 on 2016/4/26.
 * 过滤请求
 */
public class ServerParamFilter implements Filter {
    private static final Logger LOGGER = LoggerFactoryProxy.getLogger("FILTERLOG");


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        try {
            //==========获取请求路径=================================
            String uri = request.getRequestURI();
            if (uri.contains("harmonia") || uri.startsWith("/client/")){
                filterChain.doFilter(request,response);
                return;
            }
            //==========获取请求参数=================================
            Map<String, String[]> paramMap = request.getParameterMap();

            String param = JSON.toJSONString(paramMap);
            LOGGER.info(uri +" " + request.getMethod() +" " + param);

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            LOGGER.error("参数过滤", e);
            response.getWriter().print(JSON.toJSONString(AppResult.toFail(null, ErrorEnum.MSG_FAIL)));
            response.getWriter().flush();
            response.getWriter().close();
        }
    }

    @Override
    public void destroy() {

    }
}
