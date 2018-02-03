/**
 * @FileName: HouseManageServiceProxyTest.java
 * @Package com.ziroom.minsu.services.house.test.proxy
 * 
 * @author bushujie
 * @created 2016年4月3日 下午1:10:09
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.test.service;

import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ziroom.minsu.services.house.service.HouseIssueServicePcImpl;
import com.ziroom.minsu.services.house.test.base.BaseTest;

/**
 * <p>房东端房源管理实现测试类</p>
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
public class HouseIssueServicePcImplTest extends BaseTest{
	
	
	@Resource(name="house.HouseIssueServicePcImpl")
	private HouseIssueServicePcImpl houseIssueServicePcImpl;
	
	
	
	
	/*{picType:0,roomfid:'8a9e9aad568cd0fb01568cd0fb570000',housefid:'8a9e9aad568ca29d01568ca29dbb0001',imgList:[{isdefault:0,picbaseurl:'group1/M00/00/D0/ChAiMFe1X8CASDEbAAArpQcVXio661',picfid:'8a9e9a9e541a404c01541a498df70002',picserveruuid:'',degrees:0},{isdefault:0,picbaseurl:'group1/M00/00/D1/ChAiMFe1fayAL7NiAANUx_PHN1Q357',picfid:'8a9e9893569cc09101569cc091020000',picserveruuid:'',degrees:0},{isdefault:1,picbaseurl:'group1/M00/00/D0/ChAiMFe1cwOAQAxbAAAU0MQ1_Zs305',picfid:'8a9e9893569cc95401569cc954410000',picserveruuid:'',degrees:0}]}
	m_photo.js:754 Object {code: 0, msg: "", data: Object}*/
	
	@Test
	public void testsaveHousePicByType(){
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("picType", 0);
		jsonObj.put("roomfid", "8a9e9aad568cd0fb01568cd0fb570000");
		jsonObj.put("housefid", "8a9e9aad568ca29d01568ca29dbb0001");
		JSONArray array = new JSONArray();
		JSONObject obj = new JSONObject();
		obj.put("isdefault", 1);
		obj.put("picbaseurl", "group1/M00/00/D0/ChAiMFe1X8CASDEbAAArpQcVXio661");
		obj.put("degrees", 0);
		obj.put("picserveruuid", "");
		obj.put("picfid", "8a9e9a9e541a404c01541a498df70002");
		array.add(obj);
		jsonObj.put("list", array);
		Map<String, Object> count = houseIssueServicePcImpl.saveHousePicByType(jsonObj.toJSONString());
		System.out.println(count);
		
	}
	
	
}
