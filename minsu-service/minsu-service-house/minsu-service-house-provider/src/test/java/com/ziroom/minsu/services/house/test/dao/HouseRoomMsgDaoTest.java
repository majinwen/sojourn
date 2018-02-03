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

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.house.HouseRoomMsgEntity;
import com.ziroom.minsu.services.house.dao.HouseRoomMsgDao;
import com.ziroom.minsu.services.house.dto.HouseBaseListDto;
import com.ziroom.minsu.services.house.dto.HouseDetailDto;
import com.ziroom.minsu.services.house.entity.*;
import com.ziroom.minsu.services.house.issue.vo.HouseHallVo;
import com.ziroom.minsu.services.house.test.base.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * <p>房源房间信息dao测试类</p>
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
public class HouseRoomMsgDaoTest extends BaseTest{
	
	@Resource(name = "house.houseRoomMsgDao")
    private HouseRoomMsgDao houseRoomMsgDao;

	
	@Test
	public void insertHouseRoomMsgTest(){
		HouseRoomMsgEntity houseRoomMsg = new HouseRoomMsgEntity();
		houseRoomMsg.setFid(UUIDGenerator.hexUUID());
		houseRoomMsg.setHouseBaseFid("yanb");
		houseRoomMsg.setRoomType(1);
		houseRoomMsg.setRoomPrice(0);
		/*
		houseRoomMsg.setRoomName("yanbTest");
		houseRoomMsg.setBedNum(1);
		houseRoomMsg.setRoomArea(10.56);
		houseRoomMsg.setRoomStatus(10);
		houseRoomMsg.setRoomPrice(120);
		houseRoomMsg.setIsToilet(0);
		houseRoomMsg.setIsBalcony(0);
		houseRoomMsg.setRoomAspect(1);
		houseRoomMsg.setCreateUid(UUIDGenerator.hexUUID());
		houseRoomMsg.setRoomCleaningFees(200);
		*/
		houseRoomMsgDao.insertHouseRoomMsg(houseRoomMsg);
	}
	
	@Test
	public void updateHouseRoomMsgTest(){
		HouseRoomMsgEntity houseRoomMsg = new HouseRoomMsgEntity();
		houseRoomMsg.setFid("8a9e9aae5419d73b015419d73ddb0001");
		houseRoomMsg.setHouseBaseFid(UUIDGenerator.hexUUID());
		houseRoomMsg.setRoomName("101");
		houseRoomMsg.setBedNum(1);
		houseRoomMsg.setRoomArea(10.56);
		houseRoomMsg.setRoomStatus(10);
		houseRoomMsg.setRoomPrice(120);
		houseRoomMsg.setIsToilet(0);
		houseRoomMsg.setIsBalcony(0);
		houseRoomMsg.setRoomAspect(1);
		houseRoomMsg.setCreateUid(UUIDGenerator.hexUUID());
		houseRoomMsg.setRoomCleaningFees(100);
		int line = houseRoomMsgDao.updateHouseRoomMsg(houseRoomMsg);
		System.err.println(line);
	}
	
	@Test
	public void findHouseRoomListTest(){
		HouseBaseListDto paramDto=new HouseBaseListDto();
		paramDto.setLandlordUid("8a9e9a8b53d6089f0153d6089f710000");
		paramDto.setPage(0);
		paramDto.setLimit(10);
		PagingResult<HouseRoomListVo> result=houseRoomMsgDao.findHouseRoomList(paramDto);
		System.err.println(JsonEntityTransform.Object2Json(result.getRows()));
	}
	
	@Test
	public void findHouseRoomMsgByFidTest(){
		String houseRoomFid = "8a9e9a8b53d6e07a0153d6e07c390002"; 
		HouseRoomMsgEntity houseRoomMsg = houseRoomMsgDao.findHouseRoomMsgByFid(houseRoomFid);
		System.err.println(JsonEntityTransform.Object2Json(houseRoomMsg));
	}
	
	@Test
	public void findRoomListByHouseBaseFidTest(){
		String houseBaseFid = "8a9e9a8b53d6089f0153d608a1f80002"; 
		List<HouseRoomMsgEntity> roomList = houseRoomMsgDao.findRoomListByHouseBaseFid(houseBaseFid);
		System.err.println(JsonEntityTransform.Object2Json(roomList));
	}
	
	@Test
	public void getHouseRoomByFidTest(){
		HouseRoomMsgEntity houseRoomMsgEntity=houseRoomMsgDao.getHouseRoomByFid("8a9e9a8b53d62d740153d62d76730002");
		System.err.println(JsonEntityTransform.Object2Json(houseRoomMsgEntity));
	}
	
	@Test
	public void deleteHouseRoomMsgByFidTest(){
		String houseRoomFid = "9f50fc1a-f8f7-11e5-9cf9-0050568f07f8";
		houseRoomMsgDao.deleteHouseRoomMsgByFid(houseRoomFid);
	}
	
	@Test
	public void getOrderNeedHouseVoByRoomFidTest(){
		OrderNeedHouseVo orderNeedHouseVo=houseRoomMsgDao.getOrderNeedHouseVoByRoomFid("8a9e989c5bd7f70b015bd80606f9004b");
		System.err.println(JsonEntityTransform.Object2Json(orderNeedHouseVo));
	}
	
	@Test
	public void findRoomDetailByFidTest(){
		String houseRoomFid = "8a9e9aae5419d34e015419d3510a0001";
		HouseMsgVo houseMsg = houseRoomMsgDao.findRoomDetailByFid(houseRoomFid);
		System.err.println(JsonEntityTransform.Object2Json(houseMsg));
	}
	
	@Test
	public void getHouseRoomCountTest(){
		String houseBaseFid = "8a9e9a8b53d6089f0153d608a1f80002";
		long count = houseRoomMsgDao.getHouseRoomCount(houseBaseFid);
		System.err.println(count);
	}
	
	@Test
	public void findRoomPicVoTest(){
		List<HousePicVo> list=houseRoomMsgDao.findRoomPicVoList("8a9e9aae5419cc22015419cc250a0002","8a9e9aae5419d34e015419d3510a0001");
		System.err.println(JsonEntityTransform.Object2Json(list));
	}
	
	@Test
	public void findHouseRoomVoListTest(){
		HouseBaseListDto houseBaseListDto = new HouseBaseListDto();
		houseBaseListDto.setLandlordUid("8a9e9a8b53d6da8b0153d6da8bae0000");
		PagingResult<HouseRoomVo> list = houseRoomMsgDao.findHouseRoomVoList(houseBaseListDto);
		System.err.println(JsonEntityTransform.Object2Json(list));
	}
	
	@Test
	public void getHouseRoomDetailTest(){
		HouseDetailDto houseDetailDto=new HouseDetailDto();
		houseDetailDto.setFid("8a9e9aae53e97a9e0153e97a9ecb0000");
		houseDetailDto.setRentWay(1);
		TenantHouseDetailVo tenantHouseDetailVo=houseRoomMsgDao.getHouseRoomDetail(houseDetailDto);
		System.err.println(JsonEntityTransform.Object2Json(tenantHouseDetailVo));
	}
	
	@Test
	public void getRooFidListByHouseFid(){
		
		List<String> listFid = this.houseRoomMsgDao.getRooFidListByHouseFid("8a90a2d4549341c601549427b6ee012c");
		
		System.out.println(listFid);
	}
	
	@Test
	public void countByRoomInfoTest(){
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		List<Integer> roomStatusList = new ArrayList<Integer>();
		roomStatusList.add(20);
		roomStatusList.add(21);
		roomStatusList.add(30);
		roomStatusList.add(40);
		params.put("roomStatusList", roomStatusList);
		params.put("houseBaseFid", "8a9e9a9a548a061301548a0613760001");
		Long count = this.houseRoomMsgDao.countByRoomInfo(params);
		
		System.out.println(count);
	}
	@Test
	public void getRoomBaseMsgListByRoomSnsTest(){
		List<String> roomSns = new ArrayList<String>();
		roomSns.add("110100476735F");
		roomSns.add("110100705944F");
		roomSns.add("110100727053F");
		List<HouseRoomMsgEntity> roomList = houseRoomMsgDao.getRoomBaseListByRoomSns(roomSns);
		System.err.println(roomList.size());
	}

	@Test
	public void getHallByHouseBaseFidTest() {
		HouseHallVo hallVo;
		hallVo = houseRoomMsgDao.getHallByHouseBaseFid("yanbRoom");
		System.err.println(hallVo.toString());
	}

	@Test
	public void getRoomNameByHouseBaseFidTest() {
		String houseBaseFid = "8a9084df5fe3025b015fe3ce10d10345";
		String roomName=houseRoomMsgDao.getRoomNameByHouseBaseFid(houseBaseFid);
		System.err.println(roomName);
	}

	@Test
	public void getRoomTypeByHouseBaseFid() {
		String houseBaseFid = "8a9084df5fe3025b015fe3ce123543210345";
		Integer roomType=houseRoomMsgDao.getRoomTypeByHouseBaseFid(houseBaseFid);
		System.err.println(roomType);
	}

	@Test
	public void deleteHallMsgByhouseBaseFid() {
		String houseBaseFid = "8a9084df5fe7b63e015fe7c8973d007b";
		Integer i=houseRoomMsgDao.deleteHallMsgByhouseBaseFid(houseBaseFid);
		System.err.println(i);
	}


}
