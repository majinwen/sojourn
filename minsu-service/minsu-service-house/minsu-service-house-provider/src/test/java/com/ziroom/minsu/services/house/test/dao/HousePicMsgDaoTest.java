/**
 * @FileName: HousePriceConfDaoTest.java
 * @Package com.ziroom.minsu.services.house.test.dao
 * 
 * @author bushujie
 * @created 2016年4月3日 下午11:37:45
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.test.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.house.HousePicMsgEntity;
import com.ziroom.minsu.services.house.dao.HousePicMsgDao;
import com.ziroom.minsu.services.house.dto.HousePicDto;
import com.ziroom.minsu.services.house.test.base.BaseTest;

/**
 * <p>房源图片dao测试类</p>
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
public class HousePicMsgDaoTest extends BaseTest{
	
	@Resource(name="house.housePicMsgDao")
	private HousePicMsgDao housePicMsgDao;
	
	@Test
	public void insertHousePicMsgTest() {
		HousePicMsgEntity housePicMsg = new HousePicMsgEntity();
		housePicMsg.setFid(UUIDGenerator.hexUUID());
		housePicMsg.setHouseBaseFid("8a9e9a8b53d6089f0153d608a1f80002");
		housePicMsg.setRoomFid("8a9e9a8b53d62d740153d62d76730002");
		housePicMsg.setOperateType(0);
		housePicMsg.setPicName("测试图片名称");
		housePicMsg.setPicType(0);
		housePicMsg.setPicBaseUrl("/test/url");
		housePicMsg.setPicSuffix(".png");
		housePicMsg.setAuditStatus(0);
		housePicMsg.setIsDefault(0);
		housePicMsg.setCreateFid(UUIDGenerator.hexUUID());
		housePicMsgDao.insertHousePicMsg(housePicMsg);
	}
	
	@Test
	public void updateHousePicMsgTest() {
		HousePicMsgEntity housePicMsg = new HousePicMsgEntity();
		housePicMsg.setFid("8a9e9a9053e59dbe0153e59dbe0e0000");
		housePicMsg.setHouseBaseFid(UUIDGenerator.hexUUID());
		housePicMsg.setRoomFid(UUIDGenerator.hexUUID());
		housePicMsg.setBedFid(UUIDGenerator.hexUUID());
		housePicMsg.setOperateType(0);
		housePicMsg.setPicName("测试图片名称1");
		housePicMsg.setPicType(2);
		housePicMsg.setPicBaseUrl("/test/url");
		housePicMsg.setPicSuffix(".png");
		housePicMsg.setPicServerUuid(UUIDGenerator.hexUUID());
		housePicMsg.setAuditStatus(0);
		housePicMsg.setReplaceFid(UUIDGenerator.hexUUID());
		housePicMsg.setIsDefault(0);
		housePicMsg.setCreateFid(UUIDGenerator.hexUUID());
		int line = housePicMsgDao.updateHousePicMsg(housePicMsg);
		System.err.println(line);
	}
	
	@Test
	public void deleteHouseRoomMsgByFidTest(){
		String housePicFid = "8a9e9a9053e59dbe0153e59dbe0e0000";
		housePicMsgDao.deleteHousePicMsgByFid(housePicFid);
	}
	
	@Test
	public void findHousePicMsgByFidTest(){
		String housePicFid = "8a9e9a9053e59dbe0153e59dbe0e0000";
		HousePicMsgEntity housePicMsg = housePicMsgDao.findHousePicMsgByFid(housePicFid);
		System.err.println(JsonEntityTransform.Object2Json(housePicMsg));
	}
	
	@Test
	public void getHousePicCountTest(){
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("houseBaseFid", "8a9e9a8b53d6089f0153d608a1f80002");
		paramMap.put("picType", 0);
		paramMap.put("roomFid", "8a9e9a8b53d62d740153d62d76730002");
		int count=housePicMsgDao.getHousePicCount(paramMap);
		System.err.println(count);
	}
	
	@Test
	public void getHousePicByTypeTest(){
		List<HousePicMsgEntity> list=housePicMsgDao.getHousePicByType("8a9e9a8b53d6da8b0153d6da8e4b0002", 1);
		System.err.println(JsonEntityTransform.Object2Json(list));
	}
	
	@Test
	public void findHouseUnapproveedPicListTest(){
		String houseBaseFid = "8a9e9aae5419cc22015419cc250a0002";
		List<HousePicMsgEntity> list = housePicMsgDao.findHouseUnapproveedPicList(houseBaseFid);
		System.err.println(JsonEntityTransform.Object2Json(list));
	}
	
	@Test
	public void findRoomUnapproveedPicListTest(){
		String houseRoomFid = "8a9e9a8b53d62d740153d62d76730002";
		List<HousePicMsgEntity> list = housePicMsgDao.findRoomUnapproveedPicList(houseRoomFid);
		System.err.println(JsonEntityTransform.Object2Json(list));
	}
	
	@Test
	public void findDefaultPicTest(){
		String houseBaseFid = "8a9e9a8b53d6da8b0153d6da8e4b0002";
		String houseRoomFid = "8a9e9a8b53d62d740153d62d76730002";
		Integer picType = 0;
		HousePicMsgEntity pic = housePicMsgDao.findDefaultPicByType(houseBaseFid, houseRoomFid, picType);
		System.err.println(JsonEntityTransform.Object2Json(pic));
	}
	
	@Test
	public void findHousePicListTest(){
		String houseBaseFid = "8a9e9aae5419cc22015419cc250a0002";
		List<HousePicMsgEntity> list = housePicMsgDao.findHousePicList(houseBaseFid);
		System.err.println(JsonEntityTransform.Object2Json(list));
	}
	
	@Test
	public void findRoomPicListTest(){
		String houseRoomFid = "8a9e9a8b53d62d740153d62d76730002";
		List<HousePicMsgEntity> list = housePicMsgDao.findRoomPicList(houseRoomFid);
		System.err.println(JsonEntityTransform.Object2Json(list));
	}
	
	@Test
	public void findHousePicMsgListTest(){
		HousePicDto housePicDto = new HousePicDto();
		housePicDto.setHouseBaseFid("8a9e9aae5419cc22015419cc250a0002");
		housePicDto.setHouseRoomFid("8a9e9aae5419d34e015419d3510a0001");
		housePicDto.setPicType(0);
		List<HousePicMsgEntity> list = housePicMsgDao.findHousePicMsgList(housePicDto);
		System.err.println(JsonEntityTransform.Object2Json(list));
	}
	
	@Test
	public void findHouseDefaultPicTest(){
		HousePicMsgEntity housePicMsgEntity=housePicMsgDao.findHouseDefaultPic("8a9084df557766d501557766d58a0001");
		System.err.println(JsonEntityTransform.Object2Json(housePicMsgEntity));
	}
	
	@Test
	public void findOldHouseDefaultPicTest(){
		HousePicMsgEntity housePicMsgEntity=housePicMsgDao.findOldHouseDefaultPic("8a9084df557766d501557766d58a0001");
		System.err.println(JsonEntityTransform.Object2Json(housePicMsgEntity));
	}
	
	@Test
	public void testfindHousePicListByRoomFid(){
		List<HousePicMsgEntity> list = housePicMsgDao.findHousePicListByRoomFid("8a9e9abb556c05cf01556c09e0470002", "8a9e9abb556c099201556c0992540001");
		System.out.println(JsonEntityTransform.Object2Json(list));
	}
	
	@Test
	public void findHousePicFirstByHouseFidTest(){
		HousePicMsgEntity housePicMsgEntity  = this.housePicMsgDao.findHousePicFirstByHouseFid("8a9e9a9a548a061301548a0613760001", null, 0);
		
		System.out.println(housePicMsgEntity);
	}
	
	@Test
	public void delAllHousePicMsgByFid(){
		List<String> picList = new ArrayList<String>();
		picList.add("0000000057500625015750b3126a0002");
		picList.add("00000000575b173101575b1731b30000");
		picList.add("00000000575b173101575b1d8c560001");
		String picFids = JsonEntityTransform.Object2Json(picList);
		housePicMsgDao.delAllHousePicMsgByFid(picFids);
	}
}
