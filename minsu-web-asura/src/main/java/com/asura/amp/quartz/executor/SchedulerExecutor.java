/**
 * @FileName: SchedulerExecutor.java
 * @Package com.asura.amp.quartz.executor
 * 
 * @author zhangshaobin
 * @created 2012-12-18 上午8:11:32
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.amp.quartz.executor;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

/**
 * <p>
 * Scheduler操作类
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zhangshaobin
 * @since 1.0
 * @version 1.0
 */
public class SchedulerExecutor {

	/**
	 * Scheduler对象
	 */
	private static Scheduler scheduler = null;

	/**
	 * 静态代码，初始化SchedulerFactory
	 */
	static {
		try {
			SchedulerFactory sf = new StdSchedulerFactory("quartz.properties");
			scheduler = sf.getScheduler();
			//			scheduler.start(); 启动， 定时任务执行
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 获取Scheduler实例
	 * 
	 * @author zhangshaobin
	 * @created 2012-12-18 上午8:51:32
	 * 
	 * @return Scheduler实例
	 */
	public static Scheduler getScheduler() {
		if (null == scheduler) {
			SchedulerFactory sf;
			try {
				sf = new StdSchedulerFactory("quartz.properties");
				scheduler = sf.getScheduler();
				scheduler.start();
			} catch (SchedulerException e) {
				e.printStackTrace();
			}
		}
		return scheduler;
	}
}
