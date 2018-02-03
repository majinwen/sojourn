/**
 * @FileName: PointSumDaoTest.java
 * @Package com.ziroom.minsu.services.cms.test.dao
 * 
 * @author lunan
 * @created 2017年12月1日 下午2:55:08
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.cms.test.dao;

import com.ziroom.minsu.entity.cms.PointSumEntity;
import com.ziroom.minsu.services.cms.dao.PointSumDao;
import com.ziroom.minsu.services.cms.test.base.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * <p>TODO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author lunan
 * @since 1.0
 * @version 1.0
 */
public class PointSumDaoTest extends BaseTest{
	
	@Resource(name="cms.pointSumDao")
	private PointSumDao pointSumDao;
	
	@Test
	public void insertPointSumTest(){
		PointSumEntity record = new PointSumEntity();
		record.setPointsSource(1);
		record.setHasExchangePoints(0);
		record.setSumPerson(1);
		record.setSumPoints(10);
		record.setUid("yanb1");
		int insertPointDetail = pointSumDao.insertPointSum(record);
		System.out.println(insertPointDetail);
	}
	
	@Test
	public void selectByUidTest(){
		//PointSumEntity result = pointSumDao.selectByUid("loushuai");
		//System.out.println(result);
	}
	
	@Test
	public void updateByUidTest(){
		PointSumEntity record = new PointSumEntity();
		record.setHasExchangePoints(1);
		record.setSumPerson(5);
		record.setSumPoints(8);
		record.setUid("loushuai111");
		int insertPointDetail = pointSumDao.updateByParam(record);
		System.out.println(insertPointDetail);
	}

	@Test
	public void getPointSumByUidResourceTest(){
		HashMap<String, Object> map = new HashMap<>();
		map.put("uid", "yanb");
		map.put("pointsSource", 1);

		PointSumEntity result = pointSumDao.getPointSumByUidSource(map);
		System.out.println(result);
	}


}
