/**
 * @FileName: SpringUtil.java
 * @Package com.ziroom.minsu.report.common.util
 * 
 * @author bushujie
 * @created 2016年12月15日 下午3:36:59
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.report.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Repository;

/**
 * <p>springbean获取工具</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
@Repository("report.springUtil")
public class SpringUtil implements ApplicationContextAware {
	
    /**
     * 当前IOC
     */
    private static ApplicationContext applicationContext;

    /**
     * 设置当前上下文环境，此方法由spring自动装配
     */

	@Override
	public void setApplicationContext(ApplicationContext act) throws BeansException {
		applicationContext = act;
	}
    /**
     * 从当前IOC获取bean
     * 
     * @param id
     *            bean的id
     * @return
     */
    public static Object getObject(String id) {
        Object object = null;
        object = applicationContext.getBean(id);
        return object;
    }
}
