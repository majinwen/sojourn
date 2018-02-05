/**
 * @FileName: fillFirstPageRequestImpl.java
 * @Package com.ziroom.minsu.api.common.controller
 * 
 * @author yd
 * @created 2017年5月25日 上午10:28:23
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.api.common.abs;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * <p>线程执行返回结果</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
public abstract class CallableCommonImpl {
	
	
	public static final int SLEEP_MILLS = 3000;  


	public static final int SECOND_MILLS = 1000;  


	public static int sleepSeconds = SLEEP_MILLS / SECOND_MILLS;  
	
	
	private static ExecutorService executorService = Executors.newCachedThreadPool();  
	
	
	public CallableCommonImpl(){
	
	}
	
	/**
	 * 
	 * 执行 http请求的线程
	 *
	 * @author yd
	 * @created 2017年5月26日 下午7:24:05
	 *
	 * @param url
	 * @return
	 */
	public abstract Future<String> executeHttpRequest(String url);
	
	
	/**
	 * 
	 * 获取执行 线程服务
	 *
	 * @author yd
	 * @created 2017年5月25日 上午10:34:57
	 *
	 * @return
	 */
	public static ExecutorService getExecutorService(){
		return executorService;
	}


}
