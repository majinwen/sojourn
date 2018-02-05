/**
 * @FileName: LandlordHouseControllerTest.java
 * @Package com.ziroom.minsu.api.test.house.controller
 * 
 * @author yd
 * @created 2016年7月12日 下午2:59:44
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.api.test.house.controller;

import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MD5Util;
import com.ziroom.minsu.api.common.encrypt.EncryptFactory;
import com.ziroom.minsu.api.common.encrypt.IEncrypt;
import com.ziroom.minsu.api.order.dto.RulesRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>房源 api测试</p>
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
public class LandlordHouseControllerTest {
	
	
	private static 	IEncrypt iEncrypt=EncryptFactory.createEncryption("DES");

	public static void leaseCalendarTest() {
		
		/*CalendarParamDto requestDto = new CalendarParamDto();
		
		requestDto.setStartDate(DateUtil.dateFormat(new Date(),"yyyy-MM-dd HH:mm:ss"));
		requestDto.setEndDate("2017-07-12 15:04:00");
		requestDto.setHouseBaseFid("8a9e9cd955dd0db90155dd0dba760001");
		requestDto.setRentWay(0);*/
		Map<String ,String> param = new HashMap<String,String>();
		param.put("orderSn","17020659Z5JU59144022");
		param.put("score","9");
		param.put("uid","d185f535-2b4c-4dc3-8d9a-2eafab152ef4");
		
		System.err.println(JsonEntityTransform.Object2Json(param));
		String paramJson=iEncrypt.encrypt(JsonEntityTransform.Object2Json(param));
		System.err.println("2y5QfvAy="+paramJson);
		System.err.println("hPtJ39Xs="+MD5Util.MD5Encode(JsonEntityTransform.Object2Json(param).toString(),"UTF-8"));
		
	}
	
	
	public static void saveHousePicTest() {
		
		
	/*	PicParamDto requestDto = new PicParamDto();
        requestDto.setHouseBaseFid("8a9e9a8b53d6da8b0153d6da8e4b0002");
        requestDto.setHouseRoomFid("");
        requestDto.setPicType(1);*/
		
		  RulesRequest rulesRequest = new RulesRequest();
		  //rulesRequest.setCode(50);
		  rulesRequest.setFid("1234567890yanb");
		  rulesRequest.setRentWay(0);
		
		System.err.println(JsonEntityTransform.Object2Json(rulesRequest));
		String paramJson=iEncrypt.encrypt(JsonEntityTransform.Object2Json(rulesRequest));
		System.err.println("2y5QfvAy="+paramJson);
		System.err.println("hPtJ39Xs="+MD5Util.MD5Encode(JsonEntityTransform.Object2Json(rulesRequest).toString(),"UTF-8"));

	}
	
	public static void improveHouseRankTest() {
		
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("uid","d185f535-2b4c-4dc3-8d9a-2eafab152ef4");
		
		System.err.println(JsonEntityTransform.Object2Json(map));
		String paramJson=iEncrypt.encrypt(JsonEntityTransform.Object2Json(map));
		System.err.println("2y5QfvAy="+paramJson);
		System.err.println("hPtJ39Xs="+MD5Util.MD5Encode(JsonEntityTransform.Object2Json(map).toString(),"UTF-8"));
		
	}
	
	
	public static void main(String[] args) {
//		leaseCalendarTest();
		saveHousePicTest();
		
	}

}
