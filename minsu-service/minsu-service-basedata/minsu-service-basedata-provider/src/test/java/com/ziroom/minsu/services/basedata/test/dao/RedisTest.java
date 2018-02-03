/**
 * @FileName: RedisTest.java
 * @Package com.ziroom.minsu.services.basedata.test.dao
 * 
 * @author bushujie
 * @created 2016年12月26日 下午4:10:20
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.basedata.test.dao;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.asura.framework.cache.redisOne.RedisOperations;
import com.ziroom.minsu.services.basedata.test.base.BaseTest;
import com.ziroom.minsu.services.common.utils.SentinelJedisUtil;

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
public class RedisTest extends BaseTest{
	
	@Autowired
	private RedisOperations redisOperations;
	
	
	@Test
	public void redisGetTest(){
		redisOperations.setex("testkey", 60*60*60, "testvalue");
		while (true) {
			System.out.println(redisOperations.get("testkey"));
		}
	}
	
	@Test
	public void sentinelJedisTest(){
	}
}
