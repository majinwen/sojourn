/**
 * @FileName: MsgUserLivenessProxyTest.java
 * @Package com.ziroom.minsu.services.message.test.proxy
 * 
 * @author loushuai
 * @created 2017年9月1日 下午1:50:19
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.test.proxy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONObject;
import org.junit.Test;

import com.alibaba.fastjson.JSONArray;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.message.MsgUserLivenessEntity;
import com.ziroom.minsu.services.common.utils.HttpUtil;
import com.ziroom.minsu.services.common.utils.ResponseCallback;
import com.ziroom.minsu.services.message.proxy.MsgUserLivenessProxy;
import com.ziroom.minsu.services.message.test.base.BaseTest;

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
public class MsgUserLivenessProxyTest extends BaseTest{

	@Resource(name="message.msgUserLivenessProxy")
	private MsgUserLivenessProxy msgUserLivenessProxy;
	
	@Test
	public void tessyncLivenessTime(){
		List<MsgUserLivenessEntity> list = new ArrayList<MsgUserLivenessEntity>();
		/*for(int i=0;i<10;i++){
			MsgUserLivenessEntity tempEntity = new MsgUserLivenessEntity();
			tempEntity.setUid(UUIDGenerator.hexUUID());
			tempEntity.setLastLiveTime(new Date());
			list.add(tempEntity);
		}*/
		MsgUserLivenessEntity tempEntity = new MsgUserLivenessEntity();
		tempEntity.setUid("8a9e9aa35e3c3474015e3c34743f0000");
		tempEntity.setLastLiveTime(new Date());
		list.add(tempEntity);
		String syncLivenessTime = msgUserLivenessProxy.syncLivenessTime(JsonEntityTransform.Object2Json(list));
        System.out.println(syncLivenessTime);
	}
	
	@Test
	public void test() {
    	String url = "http://10.30.26.35:8080/im/syncLivenessTime";
    	JSONArray list = new JSONArray();
		for(int i=0;i<10;i++){
			JSONObject tempEntity = new JSONObject();
			tempEntity.put("uid", UUIDGenerator.hexUUID());
			long currentTimeMillis = System.currentTimeMillis();
			System.out.println(currentTimeMillis);
			list.add(tempEntity);
		}
    	String string = list.toString();
    	System.out.println();
	}
	
	@Test
	public void testsyncLivenessTime() {
    	String code = "ZIROOM_CHANGZU_IM";
    	long timeStamp = System.currentTimeMillis();
    	String timeStamps = String.valueOf(timeStamp);
    	String md5Hex = DigestUtils.md5Hex(code+timeStamps);
    	System.out.println();
	}
}
