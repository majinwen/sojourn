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

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.house.HouseBusinessMsgEntity;
import com.ziroom.minsu.services.house.dao.HouseBusinessMsgDao;
import com.ziroom.minsu.services.house.dto.HouseBusinessDto;
import com.ziroom.minsu.services.house.entity.HouseBusinessListVo;
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
public class HouseBusinessMsgDaoTest extends BaseTest{
	
	@Resource(name="house.houseBusinessMsgDao")
	private HouseBusinessMsgDao houseBusinessMsgDao;
	
	
	@Test
	public void insertHouseBusinessMsgTest(){
		
		HouseBusinessMsgEntity houseBusinessMsgEntity=new HouseBusinessMsgEntity();
		houseBusinessMsgEntity.setFid(UUIDGenerator.hexUUID());
		houseBusinessMsgEntity.setNationCode("100000");
		houseBusinessMsgEntity.setProvinceCode("110000");
		houseBusinessMsgEntity.setCityCode("110100");
		houseBusinessMsgEntity.setAreaCode("110101");
		houseBusinessMsgDao.insertHouseBusinessMsg(houseBusinessMsgEntity);
	}
	
	@Test
	public void findHouseBaseListTest(){
		HouseBusinessDto houseBusinessDto=new HouseBusinessDto();
		houseBusinessDto.setDtGuardCode("000");
		PagingResult<HouseBusinessListVo> listPagingResult=houseBusinessMsgDao.findBusinessList(houseBusinessDto);
		System.err.println(JsonEntityTransform.Object2Json(listPagingResult.getRows()));
	}
	
	@Test
	public void getHouseBusinessMsgByFidTest(){
		HouseBusinessMsgEntity houseBusinessMsgEntity=houseBusinessMsgDao.findBusinessMsgEntityByFid("8a9e9a9a55c9f74b0155ca13a059001e");
		System.err.println(JsonEntityTransform.Object2Json(houseBusinessMsgEntity));
	}
	
	@Test
	public void findBusinessMsgByHouseFidTest(){
		HouseBusinessMsgEntity houseBusinessMsgEntity=houseBusinessMsgDao.findBusinessMsgByHouseFid("");
		System.err.println(JsonEntityTransform.Object2Json(houseBusinessMsgEntity));
	}
}
