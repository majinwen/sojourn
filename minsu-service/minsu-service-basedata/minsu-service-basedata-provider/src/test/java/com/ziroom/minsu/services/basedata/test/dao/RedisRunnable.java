/**
 * @FileName: RedisRunnable.java
 * @Package com.ziroom.minsu.services.basedata.test.dao
 * 
 * @author bushujie
 * @created 2016年12月26日 下午4:21:59
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.basedata.test.dao;

import com.asura.framework.cache.redisOne.RedisOperations;

/**
 * <p>TODO</p>
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
public class RedisRunnable implements Runnable{
	
	private RedisOperations redisd;
	
	public RedisRunnable(RedisOperations redis){
		redisd=redis;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		for(int i=0;i<100000;i++){
			System.out.println(redisd.get("testkey"));
		}
	}
}
