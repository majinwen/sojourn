/**
 * @FileName: LandlordEvaluateDaoTest.java
 * @Package com.ziroom.minsu.services.evaluate.test.dao
 * 
 * @author yd
 * @created 2016年4月7日 下午4:10:24
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.evaluate.test.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.ziroom.minsu.entity.evaluate.LandlordEvaluateEntity;
import com.ziroom.minsu.services.evaluate.dao.LandlordEvaluateDao;
import com.ziroom.minsu.services.evaluate.test.base.BaseTest;

/**
 * <p>测试</p>
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
public class LandlordEvaluateDaoTest extends BaseTest{



	@Resource(name="evaluate.landlordEvaluateDao")
	private LandlordEvaluateDao landlordEvaluateDao;
	@Test
	public void testSaveLandlordEvaluate() {

		LandlordEvaluateEntity landlordEvaluateEntity = new LandlordEvaluateEntity();

		landlordEvaluateEntity.setContent("非常好,又感觉差一点点");
		landlordEvaluateEntity.setCreateTime(new Date());
		landlordEvaluateEntity.setEvaOrderFid("8a9e9cb353f19aa50153f19aa5a300004564");
		landlordEvaluateEntity.setIsDel(0);
		landlordEvaluateEntity.setLandlordSatisfied(4);
		landlordEvaluateEntity.setLastModifyDate(new Date());
		int index = this.landlordEvaluateDao.saveLandlordEvaluate(landlordEvaluateEntity);

		System.out.println(index);
	}

	@Test
	public void testUpdateByEvaOrderFid(){
		LandlordEvaluateEntity landlordEvaluateEntity = new LandlordEvaluateEntity();

		landlordEvaluateEntity.setContent("非常好,fsdfsdfdsf又感觉差一点点fds6f4fsfsfsdf56d");
		landlordEvaluateEntity.setEvaOrderFid("48979871459578747009");
		
		int index = this.landlordEvaluateDao.updateByEvaOrderFid(landlordEvaluateEntity);
		
		System.out.println(index);
	}
	
	@Test
	public void testUpdateByFid(){
		LandlordEvaluateEntity landlordEvaluateEntity = new LandlordEvaluateEntity();

		landlordEvaluateEntity.setContent("非常好,又感觉差一点点,yagndogndfgdsfd");
		landlordEvaluateEntity.setFid("8a9e9cb353f1ac310153f1ac31310000");
		
		int index = this.landlordEvaluateDao.updateByFid(landlordEvaluateEntity);
		
		System.out.println(index);
	}

	@Test
	public void testQueryByEvaOrderFid(){
		
		String evaOrderFid = "8a9e9cb353f19aa50153f19aa5a30000";
		LandlordEvaluateEntity landlordEvaluateEntity = this.landlordEvaluateDao.queryByEvaOrderFid(evaOrderFid);
		
		System.out.println(landlordEvaluateEntity);
		
	}

	@Test
	public void testlistLanEvaByEvaOrderFids(){
		List<String> list = new ArrayList<>();
		list.add("8a90a2d45a60ddda015a652a04f210d9");
		landlordEvaluateDao.listLanEvaByEvaOrderFids(list);
	}
}
