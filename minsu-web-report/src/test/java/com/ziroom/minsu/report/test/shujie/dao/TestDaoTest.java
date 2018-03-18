/**
 * @FileName: TestDaoTest.java
 * @Package com.ziroom.minsu.report.dao
 * 
 * @author bushujie
 * @created 2016年12月30日 下午2:51:57
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.report.test.shujie.dao;


import java.text.ParseException;
import java.util.Date;

import javax.annotation.Resource;

import com.ziroom.minsu.report.house.dao.TestDao;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

import com.asura.framework.base.util.DateUtil;

import base.BaseTest;

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
public class TestDaoTest extends BaseTest{
	
	@Resource(name="report.testDao")
	private TestDao testDao;
	
	@Test
	public void getAllRegNum() throws ParseException{
		int dayNum=DateUtil.getDatebetweenOfDayNum(DateUtil.parseDate("2017-01-06", "yyyy-MM-dd"), DateUtil.parseDate("2017-01-13", "yyyy-MM-dd"));
		System.err.println(dayNum);
		for(int i=0;i<dayNum;i++){
			Date startDate=DateUtils.addDays(DateUtil.parseDate("2017-01-06", "yyyy-MM-dd"), i);
			Date endDate=DateUtils.addDays(startDate, 1);
			int i1=testDao.getAllRegNum(startDate,endDate);
			int ii=testDao.getAllLandlordNum(startDate,endDate);
			int iii=testDao.getAllHouseNum(startDate,endDate);
			int iiii=testDao.getHouseSucceedNum(startDate,endDate);
			int iiiii=testDao.getHouseAuthNum(startDate,endDate);
			System.err.println(i1);
			System.err.println(ii);
			System.err.println(iii);
			System.err.println(iiii);
			System.err.println(iiiii);
			System.err.println(DateUtil.dateFormat(startDate));
			System.err.println(DateUtil.dateFormat(endDate));
			System.err.println("------------------------------");
		}
	}
}
