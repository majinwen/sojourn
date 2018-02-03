/**
 * @FileName: ActivityGroupReceiveLogDaoTest.java
 * @Package com.ziroom.minsu.services.cms.test.dao
 * 
 * @author bushujie
 * @created 2017年5月25日 下午2:54:12
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.cms.test.dao;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.cms.ActivityGroupReceiveLogEntity;
import com.ziroom.minsu.services.cms.dao.ActivityGroupReceiveLogDao;
import com.ziroom.minsu.services.cms.test.base.BaseTest;

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
public class ActivityGroupReceiveLogDaoTest extends BaseTest{
	
	@Resource(name="cms.activityGroupReceiveLogDao")
	private ActivityGroupReceiveLogDao activityGroupReceiveLogDao;
	
	
	@Test
	public void insertActivityGroupReceiveLogTest(){
		ActivityGroupReceiveLogEntity activityGroupReceiveLogEntity=new ActivityGroupReceiveLogEntity();
		activityGroupReceiveLogEntity.setFid(UUIDGenerator.hexUUID());
		activityGroupReceiveLogEntity.setGroupSn("ceshi001");
		activityGroupReceiveLogEntity.setMobile("13652525252");
		activityGroupReceiveLogEntity.setUid(UUIDGenerator.hexUUID());
		activityGroupReceiveLogDao.insertActivityGroupReceiveLog(activityGroupReceiveLogEntity);
	}
	
	@Test
	public void countGroupReceiveLogByUidAndGroupSn(){
		
		Long i = activityGroupReceiveLogDao.countGroupReceiveLogByUidAndGroupSn("HAIYANJIHUA2017", "7a8c4184-8e2e-37b4-08e8-f4c20225e350");
		
		System.out.println(i);
	}

}
