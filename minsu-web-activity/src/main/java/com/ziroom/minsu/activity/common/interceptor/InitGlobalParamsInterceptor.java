package com.ziroom.minsu.activity.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

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
	private static final Logger LOGGER = LoggerFactory.getLogger(InitGlobalParamsInterceptor.class);
	/**
	 * 静态资源地址
	 */
	private   String staticResourceUrl ;

	/**
	 * 图片地址
	 */
	private   String picResourceUrl;

	/**
	 * 图片规格默认
	 */
	private   String picSize;


	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


		String serverHost = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
		String serverHostHttps = request.getScheme() + "s://" + request.getServerName() + ":" + request.getServerPort();
		String serverContext = request.getContextPath().equalsIgnoreCase("/") ? "" : request.getContextPath();
		// 增加服务器路径
		request.setAttribute("SERVER_HOST",serverHost);
		// 上下文
		request.setAttribute("SERVER_CONTEXT", serverContext);

		request.setAttribute("path", serverContext);

		String basePath = null;
		String basePathHttps = null;
		if(serverHost!=null&&serverContext!=null){
			basePath = serverHost+serverContext+"/";
			basePathHttps =  serverHostHttps+serverContext+"/";
		}
		request.setAttribute("basePathHttps", basePathHttps);
		request.setAttribute("basePath", basePath);
		LOGGER.info("basePathHttps=["+basePathHttps+"]");
		//LOGGER.info("当前项目静态资源地址staticResourceUrl=["+staticResourceUrl+"],服务器路径SERVER_HOST=["+serverHost+"],上下文路径path=["+serverContext+"],项目请求路径basePath=["+basePath+"],默认图片大小picSize=["+picSize+"]");
		//CDN的静态地址
		request.setAttribute("staticResourceUrl", this.getStaticResourceUrl()); 
		//图片地址
		request.setAttribute("picResourceUrl", this.getPicResourceUrl());
		//图片默认大小
		request.setAttribute("picSize", this.getPicSize());

		return super.preHandle(request, response, handler);
	}

	public String getStaticResourceUrl() {
		return staticResourceUrl;
	}

	public void setStaticResourceUrl(String staticResourceUrl) {
		this.staticResourceUrl = staticResourceUrl;
	}

	public String getPicResourceUrl() {
		return picResourceUrl;
	}

	public void setPicResourceUrl(String picResourceUrl) {
		this.picResourceUrl = picResourceUrl;
	}


	/**
	 * @return the picSize
	 */
	public String getPicSize() {
		return picSize;
	}

	/**
	 * @param picSize the picSize to set
	 */
	public void setPicSize(String picSize) {
		this.picSize = picSize;
	}

}
