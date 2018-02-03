/**
 * @FileName: MsgReplySetServiceProxyTest.java
 * @Package com.ziroom.minsu.services.message.test.proxy
 * 
 * @author yd
 * @created 2016年4月18日 下午10:09:10
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.test.proxy;

import static org.junit.Assert.*;

import java.util.Date;

import javax.annotation.Resource;
import javax.swing.JScrollBar;

import org.codehaus.jackson.type.TypeReference;
import org.junit.Test;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.message.MsgReplySetEntity;
import com.ziroom.minsu.services.message.dto.MsgReplySetRequest;
import com.ziroom.minsu.services.message.proxy.MsgReplySetServiceProxy;
import com.ziroom.minsu.services.message.test.base.BaseTest;
import com.ziroom.minsu.valenum.msg.IsReplayEnum;
import com.ziroom.minsu.valenum.msg.LableTypeEnum;

/**
 * <p>房东自动回复代理层设置</p>
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
public class MsgReplySetServiceProxyTest extends BaseTest{
	
	
	@Resource(name = "message.msgReplySetServiceProxy")
	private MsgReplySetServiceProxy msgReplySetServiceProxy;

	@Test
	public void queryByLanglordFidTest() {
		MsgReplySetRequest msgReplySetRequest = new MsgReplySetRequest();
		msgReplySetRequest.setLandlordUid("fdsfdsfdsfdsfdsFDSFDSFfdsfsdfd");
		
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.msgReplySetServiceProxy.queryByLanglordFid(JsonEntityTransform.Object2Json(msgReplySetRequest)));
		MsgReplySetEntity msgReplySetEntity = dto.parseData("msgReplySetEntity", new TypeReference<MsgReplySetEntity>() {
		});
		System.out.println(msgReplySetEntity);
	}

	@Test
	public void saveTest(){
		
		MsgReplySetEntity msgReplySetEntity = new MsgReplySetEntity();
		msgReplySetEntity.setCreateTime(new Date());
		msgReplySetEntity.setHouseFid("456f4d5FDSFDSFD6s4f");
		msgReplySetEntity.setIsReply(IsReplayEnum.AUTO_REPLAY.getCode());
		msgReplySetEntity.setLableType(LableTypeEnum.HOUSE_LABLE.getCode());
		msgReplySetEntity.setLandlordUid("fdsfdsfdsfdsfdsFDSFDSFfdsfsdfd");
		msgReplySetEntity.setLastModifyDate(new  Date());
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.msgReplySetServiceProxy.save(JsonEntityTransform.Object2Json(msgReplySetEntity)));
		System.out.println(dto.getData().get("result"));
	}
	
	@Test
	public void updateByConditionTest(){
		MsgReplySetEntity msgReplySetEntity = new MsgReplySetEntity();
		msgReplySetEntity.setHouseFid("456fFDSFDSF4d56s4f");
		msgReplySetEntity.setIsReply(IsReplayEnum.UNAUTO_REPLAY.getCode());
		msgReplySetEntity.setLableType(LableTypeEnum.HOUSE_LABLE.getCode());
		msgReplySetEntity.setLandlordUid("fdsfdsfdsfdsfdsFDSFDSFfdsfsdfd");
		
		msgReplySetEntity.setLastModifyDate(new  Date());
		msgReplySetEntity.setFid("8a9e9c99542787650154278765640000");
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.msgReplySetServiceProxy.updateByCondition(JsonEntityTransform.Object2Json(msgReplySetEntity)));
		System.out.println(dto.getData().get("result"));
	}
}
