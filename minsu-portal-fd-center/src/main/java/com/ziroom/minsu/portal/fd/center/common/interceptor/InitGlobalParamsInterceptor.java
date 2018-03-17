package com.ziroom.minsu.portal.fd.center.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.asura.framework.base.util.Check;

/**
 * 初始化全局参数(具体其他全局参数都可以在这里配置)
 * @author yd
 * @date 2016-03-25
 * @version 1.0
 *
 */
public class InitGlobalParamsInterceptor extends  HandlerInterceptorAdapter{

	/**
	 * 日志对象
	 */
	private static final Logger log = LoggerFactory.getLogger(InitGlobalParamsInterceptor.class);
	

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		
		String serverHost = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
		String serverContext = request.getContextPath().equalsIgnoreCase("/") ? "" : request.getContextPath();
		 // 增加服务器路径
		request.setAttribute("SERVER_HOST",serverHost);
        // 上下文
		request.setAttribute("SERVER_CONTEXT", serverContext);
      
		request.setAttribute("path", serverContext);
		
		String uri = request.getRequestURI();
		if(Check.NuNObj(uri)){
			uri = "";
		}
		String queryStr = request.getQueryString();
		if(Check.NuNStr(queryStr)){
			queryStr = "";
		}else{
			queryStr = "?" + queryStr;
		}
        request.setAttribute("currentUrl", serverHost + uri + queryStr);
		
        String basePath = null;
        if(serverHost!=null&&serverContext!=null) basePath = serverHost+serverContext+"/";
        request.setAttribute("basePath", basePath);
		
		return super.preHandle(request, response, handler);
	}

	
}
