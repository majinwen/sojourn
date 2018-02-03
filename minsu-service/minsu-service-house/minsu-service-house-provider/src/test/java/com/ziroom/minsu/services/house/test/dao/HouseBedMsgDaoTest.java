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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.house.HouseBedMsgEntity;
import com.ziroom.minsu.services.house.dao.HouseBedMsgDao;
import com.ziroom.minsu.services.house.entity.HouseBedNumVo;
import com.ziroom.minsu.services.house.entity.OrderNeedHouseVo;
import com.ziroom.minsu.services.house.test.base.BaseTest;

/**
 * 
 * <p>房源床位信息dao测试类</p>
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
public class HouseBedMsgDaoTest extends BaseTest{
	
	@Resource(name = "house.houseBedMsgDao")
    private HouseBedMsgDao houseBedMsgDao;

	
	@Test
	public void insertHouseBedMsgTest(){
		HouseBedMsgEntity houseBedMsg = new HouseBedMsgEntity();
		houseBedMsg.setFid(UUIDGenerator.hexUUID());
		houseBedMsg.setRoomFid(UUIDGenerator.hexUUID());
		houseBedMsg.setHouseBaseFid(UUIDGenerator.hexUUID());
		houseBedMsg.setBedStatus(10);
		houseBedMsg.setBedSn(101);
		houseBedMsg.setBedPrice(80);
		houseBedMsg.setCreateUid(UUIDGenerator.hexUUID());
		houseBedMsgDao.insertHouseBedMsg(houseBedMsg);
	}
	
	@Test
	public void updateHouseBaseMsgTest(){
		HouseBedMsgEntity houseBedMsg = new HouseBedMsgEntity();
		houseBedMsg.setFid("8a9e9a8b53d4d7650153d4d765fe0000");
		houseBedMsg.setRoomFid(UUIDGenerator.hexUUID());
		houseBedMsg.setHouseBaseFid(UUIDGenerator.hexUUID());
		houseBedMsg.setBedStatus(10);
		houseBedMsg.setBedSn(101);
		houseBedMsg.setBedPrice(80);
		houseBedMsg.setCreateUid(UUIDGenerator.hexUUID());
		int line = houseBedMsgDao.updateHouseBedMsg(houseBedMsg);
		System.err.println(line);
	}
	
	@Test
	public void findHouseRoomMsgDtoByFidTest(){
		String houseBedFid = "8a9e9a8b53d640d00153d640d2800003"; 
		HouseBedMsgEntity houseBedMsg = houseBedMsgDao.findHouseBedMsgByFid(houseBedFid);
		System.err.println(JsonEntityTransform.Object2Json(houseBedMsg));
	}
	
	@Test
	public void findBedListByRoomFidTest(){
		String houseRoomFid = "8a9e9a8b53d65d790153d65d79ee0000"; 
		List<HouseBedMsgEntity> bedList = houseBedMsgDao.findBedListByRoomFid(houseRoomFid);
		System.err.println(JsonEntityTransform.Object2Json(bedList));
	}

	@Test
	public void deleteHouseBedMsgByFidTest(){
		String houseBedFid = "8a9e9a94547faae001547faae1e50011";
		int i=houseBedMsgDao.deleteHouseBedMsgByFid(houseBedFid);
		System.err.println(i);
	}
	
	@Test
	public void countBedNumByRoomFidTest(){
		String roomFid = "8a9e9a8b53d65d790153d65d79ee0000";
		int countNum = houseBedMsgDao.getRoomBedCount(roomFid);
		System.err.println(countNum);
	}
	
	@Test
	public void getOrderNeedHouseVoByBedFidTest(){
		OrderNeedHouseVo orderNeedHouseVo=houseBedMsgDao.getOrderNeedHouseVoByBedFid("8a9e9a9653f915110153f91513300001");
		System.err.println(JsonEntityTransform.Object2Json(orderNeedHouseVo));
	}
	
	@Test
	public void getBedNumByHouseFidTest(){
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("houseBaseFid", "8a9e9a9454801ac501548026fb610029");
		List<HouseBedNumVo> list=houseBedMsgDao.getBedNumByHouseFid(paramMap);
		System.err.println(JsonEntityTransform.Object2Json(list));
	}

}
