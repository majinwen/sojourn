/**
 * @FileName: IntellectWaterMeterBillLogDaoTest.java
 * @Package com.ziroom.zrp.service.houses.dao
 * 
 * @author bushujie
 * @created 2018年1月31日 上午11:47:13
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.zrp.service.houses.dao;

import java.text.ParseException;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.zrp.service.houses.base.BaseTest;
import com.ziroom.zrp.service.trading.dao.IntellectWaterMeterBillLogDao;
import com.ziroom.zrp.trading.entity.IntellectWaterMeterBillLogEntity;

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
public class IntellectWaterMeterBillLogDaoTest extends BaseTest{
	
	@Resource(name="trading.IntellectWaterMeterBillLogDao")
	private IntellectWaterMeterBillLogDao dao;
	
	
	@Test
	public void insertIntellectWaterMeterBillLogTest() throws ParseException{
		IntellectWaterMeterBillLogEntity entity=new IntellectWaterMeterBillLogEntity();
		entity.setFid(UUIDGenerator.hexUUID());
		entity.setBillFid("YS08171220288176");
		entity.setStartReading(100d);
		entity.setEndReading(200d);
		entity.setUseReading(50d);
		entity.setStartDate(DateUtil.parseDate("2017-12-21", "yyyy-MM-dd"));
		entity.setEndDate(DateUtil.parseDate("2018-01-21", "yyyy-MM-dd"));
		entity.setPrice(5000);
		entity.setType(0);
		dao.insertIntellectWaterMeterBillLog(entity);
	}
	
	@Test
	public void getIntellectWaterMeterBillLogTest(){
		IntellectWaterMeterBillLogEntity entity=dao.getIntellectWaterMeterBillLog("8a9e989c611d1a6901611d1a69390000");
		System.err.println("dddddddddddd");
		System.err.println(JsonEntityTransform.Object2Json(entity));
	}
}
