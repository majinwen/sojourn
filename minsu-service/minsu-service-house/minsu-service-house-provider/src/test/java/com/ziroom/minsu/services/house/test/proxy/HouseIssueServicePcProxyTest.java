package com.ziroom.minsu.services.house.test.proxy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.google.gson.JsonObject;
import com.ziroom.minsu.entity.house.HouseBaseExtEntity;
import com.ziroom.minsu.entity.house.HouseBaseMsgEntity;
import com.ziroom.minsu.entity.house.HouseBedMsgEntity;
import com.ziroom.minsu.entity.house.HouseConfMsgEntity;
import com.ziroom.minsu.entity.house.HousePicMsgEntity;
import com.ziroom.minsu.entity.house.HouseRoomMsgEntity;
import com.ziroom.minsu.services.house.dto.HouseBaseDto;
import com.ziroom.minsu.services.house.dto.HouseBaseExtDto;
import com.ziroom.minsu.services.house.dto.HouseBaseMsgDto;
import com.ziroom.minsu.services.house.dto.HouseOpLogDto;
import com.ziroom.minsu.services.house.dto.HouseOpLogSpDto;
import com.ziroom.minsu.services.house.dto.HousePicDto;
import com.ziroom.minsu.services.house.dto.HouseRoomListDto;
import com.ziroom.minsu.services.house.dto.HouseRoomMsgDto;
import com.ziroom.minsu.services.house.entity.HouseBaseDetailVo;
import com.ziroom.minsu.services.house.entity.HouseBaseExtVo;
import com.ziroom.minsu.services.house.pc.dto.HousePicDelDto;
import com.ziroom.minsu.services.house.proxy.HouseIssueServicePcProxy;
import com.ziroom.minsu.services.house.proxy.HouseIssueServiceProxy;
import com.ziroom.minsu.services.house.test.base.BaseTest;
import com.ziroom.minsu.valenum.house.HouseStatusEnum;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum;

/**
 * <p>房东端-房源发布</p>
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
public class HouseIssueServicePcProxyTest extends BaseTest {
	
	@Resource(name = "house.houseIssueServicePcProxy")
	private HouseIssueServicePcProxy houseIssueServicePcProxy;
	
	@Resource(name = "house.houseIssueServiceProxy")
	private HouseIssueServiceProxy houseIssueServiceProxy;
	
	
	@Test
	public void findHouseConfigByPcodeTest(){
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("houseBaseFid", "8a9e9aa255de7a570155de7a57950001");
		paramMap.put("dicCode", ProductRulesEnum.ProductRulesEnum002.getValue());
		String resultJson=houseIssueServicePcProxy.findHouseConfigByPcode(JsonEntityTransform.Object2Json(paramMap));
		System.out.println(resultJson);
	}
	@Test
	public void delAllHousePicMsgByFidTest(){
		//8a9e98915819a757015819a996c50008-8a9e9891581a60a001581a7a1e6a000c-8a9e9891581a806401581a8064930000-8a9e9891581a806401581a8067010001-8a9e9891581a806401581a8069170002-
		HousePicDto housePicDto = new HousePicDto();
		housePicDto.setHouseBaseFid("8a9084df5751bbe00157524f370101fa");
		housePicDto.setHouseRoomFid("8a9084df5751bbe00157525006230200");
		housePicDto.setPicType(0);
		housePicDto.getPicFidS().add("8a9e9891581a60a001581a71ab970002");
		housePicDto.getPicFidS().add("8a9e9891581a60a001581a71aeb10003");
		housePicDto.getPicFidS().add("00000000576bdb7701576bdb77ff0000");
//		housePicDto.getPicFidS().add("0000000057e0d49b0157e0d71199002d");
		String resultJson = houseIssueServiceProxy.delAllHousePicMsgByFid(JsonEntityTransform.Object2Json(housePicDto));
		System.out.println(resultJson.toString());
		/*List<String> list = new ArrayList<String>();
		list.add("8a9e98915819c2f2015819c2f2e60000");
		list.add("8a9e98915819c2f2015819c2f5560001");
		list.add("8a9e98915819c2f2015819c2f7080002");
		list.add("0000000057e0d49b0157e0d71199002d");
		list.remove("0000000057e0d49b0157e0d71199002d");
		System.out.println(list.size());*/
		/*00000000576bdb7701576bdb77ff0000
		00000000580443e7015804bce7220001*/
	}
	
	@Test  
	public void issueRoomsTest(){
		JSONObject requestObj = new JSONObject();
		//JSONObject requestObj = JSONObject.parseObject(param);
		requestObj.put("houseFid", "8a9084df5d0e3a24015d1072131b0a5a");
		requestObj.put("rentWay", 1);
		List<String> list = new ArrayList<>();
		list.add("8a9084df5d0e3a24015d1073386b0a61");
		requestObj.put("roomList", list);
		String param = requestObj.toJSONString();
		houseIssueServicePcProxy.issueRooms(param);
		
		
	}
	
	@Test  
	public void deleteHousePicMsgByFidTest(){
		HousePicDelDto picParam = new HousePicDelDto(); 
		//JSONObject requestObj = JSONObject.parseObject(param);
		picParam.setHouseBaseFid("8a90a2d45d40588d015d4084c2da01b6");
		picParam.setHousePicFid("8a90a2d45d40588d015d4085a63b01c8");
		picParam.setHouseRoomFid("8a90a2d45d40588d015d4085806a01be");
		picParam.setPicType(0);
		houseIssueServicePcProxy.deleteHousePicMsgByFid(JsonEntityTransform.Object2Json(picParam));		
		
	}
	
	@Test  
	public void findHouseRoomWithBedsListTest(){

		houseIssueServicePcProxy.findHouseRoomWithBedsList("8a9099786059e386016096d779931368");		
		
	}
	
	@Test  
	public void issueHouseTest(){
		JsonObject param=new JsonObject();
		param.addProperty("houseFid", "8a90997757ffb63f0157ffd77df70071");
		param.addProperty("lanUid", "74b8c7b1-b67e-482e-a818-88a0113775ed");
		houseIssueServicePcProxy.issueHouse(param.toString());		
		
	}
	
}
