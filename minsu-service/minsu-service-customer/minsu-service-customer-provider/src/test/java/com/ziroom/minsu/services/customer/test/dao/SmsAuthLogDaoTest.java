/**
 * @FileName: SmsAuthLogDaoTest.java
 * @Package com.ziroom.minsu.services.customer.test.dao
 * 
 * @author bushujie
 * @created 2016年4月21日 下午10:56:35
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.customer.test.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.DateUtil.IntervalUnit;
import com.asura.framework.base.util.RandomUtil;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.customer.SmsAuthLogEntity;
import com.ziroom.minsu.services.customer.dao.SmsAuthLogDao;
import com.ziroom.minsu.services.customer.dto.SmsAuthLogDto;
import com.ziroom.minsu.services.customer.test.BaseTest;
import com.ziroom.minsu.valenum.msg.IsDelEnum;

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
public class SmsAuthLogDaoTest extends BaseTest{
	
	@Resource(name="customer.smsAuthLogDao")
	private SmsAuthLogDao smsAuthLogDao;
	
	
	@Test
	public void insertSmsAuthLogTest(){
		SmsAuthLogEntity smsAuthLogEntity=new SmsAuthLogEntity();
		smsAuthLogEntity.setFid(UUIDGenerator.hexUUID());
		smsAuthLogEntity.setMobileNo("15811361402");
		smsAuthLogEntity.setTimeoutDate(DateUtil.intervalDate(20, IntervalUnit.MINUTE));
		smsAuthLogEntity.setAuthCode(RandomUtil.genRandomNum(6));
		
		smsAuthLogDao.insertSmsAuthLog(smsAuthLogEntity);
	}
	
	@Test
	public void getSmsAuthLogCountByMobileTest(){
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("mobileNo", "15811236523");
		paramMap.put("authCode", "1234576");
		paramMap.put("nowDate", new Date());
		int count=smsAuthLogDao.getSmsAuthLogCountByMobile(paramMap);
		System.err.println(count);
	}
	
	@Test
	public void getSmsAuthLogCountByConditionTest(){
		SmsAuthLogDto smsAuthLogDto = new SmsAuthLogDto();
		smsAuthLogDto.setIsDel(IsDelEnum.NOT_DEL.getCode());
		smsAuthLogDto.setStartTime("2016-05-10 00:00:00");
		smsAuthLogDto.setEndTime("2016-05-10 23:59:59");
		smsAuthLogDto.setMobileNo("15811361402");
		int count=smsAuthLogDao.getSmsAuthLogCountByCondition(smsAuthLogDto);
		
		System.out.println(count);
	}
}
