package com.ziroom.minsu.spider.commons;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * <p>TODO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zl
 * @since 1.0
 * @version 1.0
 */ 
public class BaseTasker {
	
	private static ApplicationContext ac;

	static{
	    ac = new FileSystemXmlApplicationContext("classpath:spring-config.xml"); 
	}

	public <T> T getBeanByClass(Class<T> t){
		T b=ac.getBean(t);
		if (b==null) {
			System.err.println("bean ["+t.getName()+"] not fount ");
		}
		return b;
	}
	
	
	
}
