/**
 * @FileName: HouseBaseMsgDaoTest.java
 * @Package com.ziroom.minsu.services.house.test.dao
 * 
 * @author bushujie
 * @created 2016年4月1日 下午1:53:36
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.test.dao;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.house.HousePhyMsgEntity;
import com.ziroom.minsu.services.house.dao.HousePhyMsgDao;
import com.ziroom.minsu.services.house.dto.HousePhyListDto;
import com.ziroom.minsu.services.house.test.base.BaseTest;

/**
 * 
 * <p>房源物理信息dao测试类</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liujun
 * @since 1.0
 * @version 1.0
 */
public class HousePhyMsgDaoTest extends BaseTest{
	
	@Resource(name = "house.housePhyMsgDao")
    private HousePhyMsgDao housePhyMsgDao;

	
	@Test
	public void insertHousePhyMsgTest(){
		HousePhyMsgEntity housePhyMsg = new HousePhyMsgEntity();
		housePhyMsg.setFid(UUIDGenerator.hexUUID());
		housePhyMsg.setNationCode("86");
		housePhyMsg.setProvinceCode("110000");
		housePhyMsg.setCityCode("110001");
		housePhyMsg.setAreaCode("110010");
		housePhyMsg.setBuildingCode("testcode");
		housePhyMsg.setCommunityName("王府井小区");
		housePhyMsg.setLongitude(116.3);
		housePhyMsg.setLatitude(39.9);
		housePhyMsg.setCreateUid(UUIDGenerator.hexUUID());
		housePhyMsgDao.insertHousePhyMsg(housePhyMsg);
	}
	
	@Test
	public void updateHousePhyMsgTest(){
		HousePhyMsgEntity housePhyMsg = new HousePhyMsgEntity();
		housePhyMsg.setFid("8a9e9a8b53d4ec880153d4ec88840000");
		housePhyMsg.setNationCode("86");
		housePhyMsg.setProvinceCode("110000");
		housePhyMsg.setCityCode("110001");
		housePhyMsg.setAreaCode("110010");
		housePhyMsg.setBuildingCode("testcode");
		housePhyMsg.setCommunityName("测试小区");
		housePhyMsg.setLongitude(116.3);
		housePhyMsg.setLatitude(39.9);
		housePhyMsg.setCreateUid(UUIDGenerator.hexUUID());
		int line = housePhyMsgDao.updateHousePhyMsg(housePhyMsg);
		System.err.println(line);
	}
	
	@Test
	public void findHousePhyMsgListByConditionTest(){
		HousePhyListDto housePhyListDto=new HousePhyListDto();
		housePhyListDto.setCommunityName("王府井小区");
		PagingResult<HousePhyMsgEntity> list=housePhyMsgDao.findHousePhyMsgListByCondition(housePhyListDto);
		System.err.println(JsonEntityTransform.Object2Json(list.getRows()));
	}
	
	@Test
	public void findHousePhyMsgByCommuNameAndCityCodeTest(){
		String communityName = "将台路5号院";
		String cityCode = "BJS";
		HousePhyMsgEntity housePhyMsg = housePhyMsgDao.findHousePhyMsgByCommuNameAndCityCode(communityName, cityCode);
		System.err.println(JsonEntityTransform.Object2Json(housePhyMsg));
	}

	@Test
	public void findHousePhyMsgByHouseBaseFidTest(){
		String houseBaseFid = "8a9e9a8b53d6089f0153d608a1f80002";
		HousePhyMsgEntity housePhyMsg = housePhyMsgDao.findHousePhyMsgByHouseBaseFid(houseBaseFid);
		System.err.println(JsonEntityTransform.Object2Json(housePhyMsg));
	}
}
