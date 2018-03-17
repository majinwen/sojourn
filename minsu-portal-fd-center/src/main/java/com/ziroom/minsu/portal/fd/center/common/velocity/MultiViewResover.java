package com.ziroom.minsu.portal.fd.center.common.velocity;

import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

/**
 * 多视图控制器（默认的是使用vm视图解析）
 * @author yd
 * @date 2016-3-23 下午
 * @version 1.0
 *
 */
public class MultiViewResover implements ViewResolver {

	private Map<String, ViewResolver> resolvers;  
	
	/**
	 * 日志对象
	 */
	private static Logger log = Logger.getLogger(MultiViewResover.class);

	@Override  
	public View resolveViewName(String viewName, Locale locale) throws Exception {  

		int n = viewName.lastIndexOf("."); // 获取  viewName(modelAndView中的名字)看其有没有下划线  
		String suffix = "";  

		// 没有默认使用“vm“方式 解析,有的话截取下划线后面的字符串 这里一般是jsp,ftl,vm与配置文件中的<entry  
		// key="ftl">的key匹配  
		if (n == (-1)) {  
			suffix = "vm";  
		}  
		else {  
			suffix = viewName.substring(n + 1);  
			// 取下划线前面的部分 那时真正的资源名.比如我们要使用hello.jsp 那viewName就应该是hello_jsp  
			viewName = viewName.substring(0, n);  
		}  
		
		log.info("");
		// 根据下划线后面的字符串去获取托管的视图解析类对象  
		ViewResolver resolver = resolvers.get(suffix);  

		if (resolver != null) {  
			return resolver.resolveViewName(viewName, locale);  
		}  
		else {  
			return null;  
		}  
	}  

	public Map<String, ViewResolver> getResolvers() {  
		return resolvers;  
	}  

	public void setResolvers(Map<String, ViewResolver> resolvers) {  
		this.resolvers = resolvers;  
	} 
}
