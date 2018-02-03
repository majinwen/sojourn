/**
 * @FileName: PointDetailDaoTest.java
 * @Package com.ziroom.minsu.services.cms.test.dao
 * 
 * @author loushuai
 * @created 2017年12月1日 下午2:04:09
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.cms.test.dao;

import com.ziroom.minsu.entity.cms.PointDetailEntity;
import com.ziroom.minsu.services.cms.dao.PointDetailDao;
import com.ziroom.minsu.services.cms.test.base.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>TODO</p>
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
public class PointDetailDaoTest extends BaseTest{

	@Resource(name="cms.pointDetailDao")
	private PointDetailDao pointDetailDao;
	
	@Test
	public void insertPointDetailTest(){
		PointDetailEntity record = new PointDetailEntity();
		record.setActSn("MSTYS1");
		record.setCreateId("loushuai3");
		record.setInviteUid("loushuai3");
		record.setOrderSn("160706C12B7HHP181740");
		record.setPoints(100);
		record.setPointsSource(1);
		record.setPointsExchageCashRate(0.50);
		record.setRemark("建表测试");
		record.setUid("loushuai3");
		int insertPointDetail = pointDetailDao.insertPointDetail(record);
		System.out.println(insertPointDetail);
	}
	
	@Test
	public void selectByParamTest(){
		PointDetailEntity record = new PointDetailEntity();
		record.setUid("loushuai");
		record.setInviteUid("664524c5-6e75-ee98-4e0d-667d38b9eee1");
		record.setPointsSource(1);
		PointDetailEntity result = pointDetailDao.selectByParam(record);
		System.out.println(result);
	}
	
	@Test
	public void updateByParamTest(){
		PointDetailEntity record = new PointDetailEntity();
		record.setUid("loushuai");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uid", "loushuai");
		map.put("remark", "建试1");
		int insertPointDetail = pointDetailDao.updateByParam(map);
		System.out.println(insertPointDetail);
	}

	@Test
	public void countByParam() {
		PointDetailEntity pointDetail=new PointDetailEntity();
		pointDetail.setInviteUid("loushuai2");
		pointDetail.setPointsSource(1);
		Integer detailCount = pointDetailDao.countByParam(pointDetail);
		System.err.println(detailCount);
	}
	
}
