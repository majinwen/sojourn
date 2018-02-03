/**
 * @FileName: HouseSensitiveWordDaoTest.java
 * @Package com.ziroom.minsu.services.house.test.dao
 * 
 * @author loushuai
 * @created 2017年11月28日 下午8:07:22
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.test.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.http.HttpResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.house.HouseRoomMsgEntity;
import com.ziroom.minsu.entity.house.HouseSensitiveWord;
import com.ziroom.minsu.services.common.utils.CloseableHttpUtil;
import com.ziroom.minsu.services.house.dao.HouseRoomMsgDao;
import com.ziroom.minsu.services.house.dao.HouseSensitiveWordDao;
import com.ziroom.minsu.services.house.entity.HouseResultVo;
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
public class HouseSensitiveWordDaoTest extends BaseTest{
	
	
	private static Logger logger = LoggerFactory.getLogger(HouseSensitiveWordDao.class);

	@Resource(name = "house.houseSensitiveWordDao")
    private HouseSensitiveWordDao houseSensitiveWordDao;

	
	@Test
	public void insertHouseSensitiveWordTest(){
		HouseSensitiveWord houseSensitiveWord = new HouseSensitiveWord();
		houseSensitiveWord.setAroundDescSensitiveWord("中央");
		houseSensitiveWord.setCreateUid("loushuai");
		houseSensitiveWord.setFid(UUIDGenerator.hexUUID());
		houseSensitiveWord.setHouseBaseFid("8a90997854e397200154e6fff5bf0003");
		houseSensitiveWord.setHouseDescSensitiveWord("中央");
		houseSensitiveWord.setHouseRulesSensitiveWord("中央");
		houseSensitiveWord.setRentWay(0);
		int insertSelective = houseSensitiveWordDao.insertSelective(houseSensitiveWord);
		System.out.println(insertSelective);
	}
	
	@Test
	public void houseSensitiveWordTest(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", 40);
		HouseSensitiveWord selectByFid = houseSensitiveWordDao.selectByFid("8a9e9a8a60028ade0160028aded60000");
		String token = sensitiveWordCheck();
		Map<String, String> param = new HashMap<String, String>();
		param.put("content-type", "application/json");
		param.put("authorization", token);
		param.put("cache-control", "no-cache");
		
		JSONObject obejct = new JSONObject();
		obejct.put("content", selectByFid.getHouseDescSensitiveWord());
		String result = CloseableHttpUtil.sendPost("http://eunomia.ziroom.com/api/sensitive", obejct.toJSONString(),param);
		LogUtil.info(logger, "敏感词校验方法   sensitiveWordCheck result={}", result);
		 if(!Check.NuNStr(result)){
				JSONObject resultObj = JSONObject.parseObject(result);
				int code  = resultObj.getIntValue("code");
				if(code==10000){
					JSONObject jsonObject = resultObj.getJSONObject("data");
					JSONArray jsonArray = jsonObject.getJSONArray("ikList");
					for (int i=0; i<jsonArray.size()-1 ; i++) {
						StringBuffer buffer = new StringBuffer();
						JSONObject o1 = (JSONObject) jsonArray.get(i);
						buffer.append(o1.get("word"));
						buffer.append(";");
						String string = buffer.toString();
					}
				}
		 }
		//List<HouseResultVo> list = houseSensitiveWordDao.getAllHaveShelvesHouse(map);
		//System.out.println(list);
	}
	
	/**
	 * 
	 * 敏感词校验公用方法
	 *
	 * @author loushuai
	 * @created 2017年11月21日 下午8:16:31
	 *
	 * @return String
	 */
	public String  sensitiveWordCheck(){
		 //欧诺弥亚项目     获取AccessToken
		String accessToken = null;
		try {
			Map<String, String> param = new HashMap<String, String>();
			param.put("username", "minsu-troy");
			param.put("password", "8a90b7a85fb0a798015fb8645af90004");
			String result  = CloseableHttpUtil.sendFormPost("http://eunomia.ziroom.com/api/oauth2/token", param);
			LogUtil.info(logger, "敏感词校验方法   sensitiveWordCheck result={}", result);
			 if(!Check.NuNStr(result)){
					JSONObject resultObj = JSONObject.parseObject(result);
					int code  = resultObj.getIntValue("code");
					if(code==10000){
						JSONObject jsonObject = resultObj.getJSONObject("data");
						String uid = (String) jsonObject.get("userId");
						String token = (String) jsonObject.get("token");
						accessToken = uid+"#"+token;
					}
			 }
		} catch (Exception e) {
			LogUtil.info(logger, "敏感词校验方法  sensitiveWordCheck， 发生错误", e);
			return accessToken;
		}
		return accessToken;
	}
	
}
