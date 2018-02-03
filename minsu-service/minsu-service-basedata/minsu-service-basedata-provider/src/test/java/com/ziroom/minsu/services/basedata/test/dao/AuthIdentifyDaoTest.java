/**
 * @FileName: AuthIdentifyDaoTest.java
 * @Package com.ziroom.minsu.services.basedata.test.dao
 * 
 * @author lunan
 * @created 2017年8月29日 下午4:51:54
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.basedata.test.dao;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.entity.BaseEntity;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.base.AuthIdentifyEntity;
import com.ziroom.minsu.services.basedata.dao.AuthIdentifyDao;
import com.ziroom.minsu.services.basedata.test.base.BaseTest;

/**
 * <p></p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author loushuai
 * @since 1.0
 * @version 1.0
 */
public class AuthIdentifyDaoTest extends BaseTest{

	@Resource(name="basedata.authIdentifyDao")
	private AuthIdentifyDao authIdentifyDao;
	
	@Test
	public void testInsertSelective(){
		AuthIdentifyEntity record  = new AuthIdentifyEntity();
		record.setCode("ZIROOM_MINSU_IM");
		record.setEmpCode("8a9e989e5cc590b7015cc5990c16000b");
		record.setFid(UUIDGenerator.hexUUID());
		record.setRemark("这是新建表插入测试");
		authIdentifyDao.insertSelective(record);
	}
	
	@Test
	public void testupdateByPrimaryKeySelective(){
		AuthIdentifyEntity record  = new AuthIdentifyEntity();
		record.setCode("ZIROOM_MINSU_IM");
		record.setEmpCode("8a9e989e5cc590b7015cc5990c16000b");
		record.setFid("8a9e9aa35e31a8f7015e31a8f76d0000");
		record.setRemark("这是新建表插入测试这是新建表插入测试这是新建表插入测试");
		authIdentifyDao.updateByPrimaryKeySelective(record);
	}
	
	@Test
	public void testgetByFid(){
		AuthIdentifyEntity record  = new AuthIdentifyEntity();
		record.setCode("ZIROOM_MINSU_IM");
		AuthIdentifyEntity record1 = authIdentifyDao.getByParam(record);
		System.out.println(record1);
	}
	
	
}
