/**
 * @FileName: HouseRoomExtDaoTest.java
 * @Package house
 * 
 * @author loushuai
 * @created 2017年6月16日 下午6:16:18
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.test.dao;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.house.HouseRoomExtEntity;
import com.ziroom.minsu.services.house.dao.HouseRoomExtDao;
import com.ziroom.minsu.services.house.test.base.BaseTest;

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
public class HouseRoomExtDaoTest extends BaseTest{
	
	@Resource(name="house.houseRoomExtDao")
	private HouseRoomExtDao houseRoomExtDao;
	
	@Test
	public void insertSelectiveTest(){
		HouseRoomExtEntity houseRoomExtEntity = new HouseRoomExtEntity();
		houseRoomExtEntity.setFid(UUIDGenerator.hexUUID());
		houseRoomExtEntity.setRoomFid("8a9e989e5cb014d7015cb014d7aa00006");
		houseRoomExtEntity.setOrderType(0);
		houseRoomExtEntity.setCheckOutRulesCode("100");
		houseRoomExtEntity.setDepositRulesCode("100");
		houseRoomExtEntity.setRoomRules("添加最小入住天数，入住时间，退房时间测试");
		houseRoomExtEntity.setCheckInTime("12:00:00");
		houseRoomExtEntity.setCheckOutTime("13:00:00");
		int insertSelective = houseRoomExtDao.insertHouseRoomExtSelective(houseRoomExtEntity);
		System.out.println(insertSelective);
		
	}
	
	@Test
	public void getByRoomfidTest(){
		HouseRoomExtEntity byRoomfid = houseRoomExtDao.getByRoomfid("8a9e989e5cb014d7015cb014d7aa00006");
		System.out.println(byRoomfid);
		
	}
	
	@Test
	public void updateHouseRoomExtTest(){
		HouseRoomExtEntity houseRoomExtEntity = new HouseRoomExtEntity();
		houseRoomExtEntity.setRoomFid("8a9e989e5cb014d7015cb014d7aa00006");
		houseRoomExtEntity.setOrderType(4);
		houseRoomExtEntity.setCheckOutRulesCode("100");
		houseRoomExtEntity.setDepositRulesCode("100");
		houseRoomExtEntity.setRoomRules("添加最小入住天数，入住时间，退房时间测试");
		houseRoomExtEntity.setMinDay(4);
		houseRoomExtEntity.setCheckInTime("11:00:00");
		houseRoomExtEntity.setCheckOutTime("12:00:00");
	    int updateHouseRoomExt = houseRoomExtDao.updateByRoomfid(houseRoomExtEntity);
		System.out.println(updateHouseRoomExt);
		
	}

}
