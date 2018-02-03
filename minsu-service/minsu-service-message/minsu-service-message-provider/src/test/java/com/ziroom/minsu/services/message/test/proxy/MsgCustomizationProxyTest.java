/**
 * @FileName: MsgBaseServiceProxy.java
 * @Package com.ziroom.minsu.services.message.proxy
 * 
 * @author yd
 * @created 2016年4月18日 下午4:51:36
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.test.proxy;

import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.message.MsgCustomizationEntity;
import com.ziroom.minsu.services.message.proxy.MsgCustomizationProxy;
import com.ziroom.minsu.services.message.test.base.BaseTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.annotation.Resource;


/**
 * <p>自定义回复信息代理</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author lunan
 * @since 1.0
 * @version 1.0
 */
public class MsgCustomizationProxyTest extends BaseTest {

	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(MsgCustomizationProxyTest.class);

	@Resource(name = "message.msgCustomizationProxy")
	private MsgCustomizationProxy msgCustomizationProxy;

	@Test
	public void testAddCustom(){
		MsgCustomizationEntity msgCustom = new MsgCustomizationEntity();
		msgCustom.setUid("SYSTEM201703311701ooxx");
		msgCustom.setContent("系统回复5");
		msgCustom.setMsgType(1);
		String resultJson = msgCustomizationProxy.addMsgCustomization(JsonEntityTransform.Object2Json(msgCustom));
		System.err.println(resultJson);
	}

	@Test
	public void testDelCustom(){
		MsgCustomizationEntity msgCustom = new MsgCustomizationEntity();
		msgCustom.setFid("8a9e9aa855541b6c0155541b6c8e0000");
		msgCustom.setIsDel(1);
		String resultJson = msgCustomizationProxy.updateMsgCustomization(JsonEntityTransform.Object2Json(msgCustom));
		System.err.println(resultJson);
	}

	@Test
	public void testUpdateCustom(){
		MsgCustomizationEntity msgCustom = new MsgCustomizationEntity();
		msgCustom.setFid("8a9e988b5b2368e7015b2368e7f80000");
		msgCustom.setContent("旅客你好，谢谢您喜欢我的民宿");
		String resultJson = msgCustomizationProxy.updateMsgCustomization(JsonEntityTransform.Object2Json(msgCustom));
		System.err.println(resultJson);
	}

	@Test
	public void testQueryCustom(){
		String resultJson = msgCustomizationProxy.queryMsgCustomizationByUid("a06f82a2-423a-e4e3-4ea8-e98317540190");
		System.err.println(resultJson);
	}

}
