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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.house.HouseBusinessMsgExtEntity;
import com.ziroom.minsu.services.house.dao.HouseBusinessMsgExtDao;
import com.ziroom.minsu.services.house.dto.HouseBusinessMsgExtDto;
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
public class HouseBusinessMsgExtDaoTest extends BaseTest{
	
	@Resource(name="house.houseBusinessMsgExtDao")
	private HouseBusinessMsgExtDao houseBusinessMsgExtDao;
	
	
	@Test
	public void insertHouseBusinessMsgExtTest(){
		HouseBusinessMsgExtEntity houseBusinessMsgExtEntity=new HouseBusinessMsgExtEntity();
		houseBusinessMsgExtEntity.setFid(UUIDGenerator.hexUUID());
		houseBusinessMsgExtEntity.setBusinessFid("8a9e9a9a55baa01c0155baa01c360000");
		houseBusinessMsgExtDao.insertBusinessMsgExtEntity(houseBusinessMsgExtEntity);
	}
	
	@Test
	public void findDtGuardCodeByLandlordTest(){
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("landlordName", "王东");
		paramMap.put("landlordMobile", "15811361402");
		String dtGuardCode=houseBusinessMsgExtDao.findDtGuardCodeByLandlord(paramMap);
		System.err.println(dtGuardCode);
	}
	
	@Test
	public void findHouseBusinessMsgExtByBusinessFidTest(){
		HouseBusinessMsgExtEntity houseBusinessMsgExtEntity=houseBusinessMsgExtDao.findHouseBusinessMsgExtByBusinessFid("8a9e9a9a55c9f74b0155ca13a059001e");
		System.err.println(JsonEntityTransform.Object2Json(houseBusinessMsgExtEntity));
	}
	
	@Test
	public void findHouseBusExtByConditionTest(){
		
		HouseBusinessMsgExtDto houseBusinessMsgExtDto = new HouseBusinessMsgExtDto();
		houseBusinessMsgExtDto.setLandlordMobile("15811361402");
		List<HouseBusinessMsgExtEntity> list = this.houseBusinessMsgExtDao.findHouseBusExtByCondition(houseBusinessMsgExtDto);
		System.out.println(JsonEntityTransform.Object2Json(list));
	}
}
