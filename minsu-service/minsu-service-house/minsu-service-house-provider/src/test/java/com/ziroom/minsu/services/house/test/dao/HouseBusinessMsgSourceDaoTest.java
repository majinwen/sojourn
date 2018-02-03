/**
 * @FileName: HouseBusinessMsgDaoTest.java
 * @Package com.ziroom.minsu.services.house.test.dao
 * 
 * @author bushujie
 * @created 2016年7月5日 下午6:23:49
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.test.dao;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.house.HouseBusinessSourceEntity;
import com.ziroom.minsu.services.house.dao.HouseBusinessSourceDao;
import com.ziroom.minsu.services.house.test.base.BaseTest;

/**
 * <p>房源商机信息DAO测试</p>
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
public class HouseBusinessMsgSourceDaoTest extends BaseTest{
	
	@Resource(name="house.houseBusinessSourceDao")
	private HouseBusinessSourceDao houseBusinessSourceDao;
	
	
	@Test
	public void insertHouseBusinessSourceEntityTest(){
		HouseBusinessSourceEntity houseBusinessSourceEntity=new HouseBusinessSourceEntity();
		houseBusinessSourceEntity.setFid(UUIDGenerator.hexUUID());
		houseBusinessSourceEntity.setBusinessFid("8a9e9a9a55baa01c0155baa01c360000");
		houseBusinessSourceDao.insertHouseBusinessSourceEntity(houseBusinessSourceEntity);
	}
	
	@Test
	public void findBusinessSourceByBusinessFidTest(){
		HouseBusinessSourceEntity houseBusinessSourceEntity=houseBusinessSourceDao.findBusinessSourceByBusinessFid("8a9e9a9a55c9f74b0155ca13a059001e");
		System.err.println(JsonEntityTransform.Object2Json(houseBusinessSourceEntity));
	}
	
	@Test
	public void delHouseBusinessSourceTest(){
		houseBusinessSourceDao.delHouseBusinessSource("8a9e9a9a55c9f74b0155ca13a059001e");
	}
}
