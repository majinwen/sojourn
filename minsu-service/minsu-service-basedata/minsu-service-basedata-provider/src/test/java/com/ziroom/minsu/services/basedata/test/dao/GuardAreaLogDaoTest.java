/**
 * @FileName: GuardAreaLogDaoTest.java
 * @Package com.ziroom.minsu.services.basedata.test.dao
 * 
 * @author yd
 * @created 2016年7月5日 下午4:57:38
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.basedata.test.dao;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.conf.GuardAreaLogEntity;
import com.ziroom.minsu.services.basedata.dao.GuardAreaLogDao;
import com.ziroom.minsu.services.basedata.dto.GuardAreaLogRequest;
import com.ziroom.minsu.services.basedata.test.base.BaseTest;

/**
 * <p>区域管家日志测试</p>
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
public class GuardAreaLogDaoTest extends BaseTest{

	
	@Resource(name ="basedata.guardAreaLogDao")
	private GuardAreaLogDao guardAreaLogDao;
	
	
	@Test
	public void saveGuardAreaLogTest() {
		
		
		GuardAreaLogEntity guardAreaLog = new GuardAreaLogEntity();
		
		guardAreaLog.setCreateDate(new Date());
		guardAreaLog.setCreateFid("123456465456456");
		guardAreaLog.setFid(UUIDGenerator.hexUUID());
		guardAreaLog.setGuardCode("20223709");
		guardAreaLog.setGuardName("杨东");
		guardAreaLog.setGuradAreaFid("8a9e9cd955ba1b2e0155ba1b2ee30000");
		guardAreaLog.setOldAreaCode("110101");
		guardAreaLog.setOldCityCode("110100");
        guardAreaLog.setOldNationCode("100000");
        guardAreaLog.setOldProvinceCode("110000");
		
		int i = guardAreaLogDao.saveGuardAreaLog(guardAreaLog);
		
		System.out.println(i);
	}
	
	
	@Test
	public void queryGuardAreaLogByConditionTest(){
		
		GuardAreaLogRequest guardAreaLogRe = new GuardAreaLogRequest();
		List<GuardAreaLogEntity>  page = this.guardAreaLogDao.queryGuardAreaLogByCondition(guardAreaLogRe);
		System.out.println(page);
	}

}
