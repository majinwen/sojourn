/**
 * @FileName: MsgReplySetServiceImplTest.java
 * @Package com.ziroom.minsu.services.message.test.service
 * 
 * @author yd
 * @created 2016年4月18日 下午3:50:27
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.test.service;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;

import com.ziroom.minsu.entity.message.MsgReplySetEntity;
import com.ziroom.minsu.services.message.dto.MsgReplySetRequest;
import com.ziroom.minsu.services.message.service.MsgReplySetServiceImpl;
import com.ziroom.minsu.services.message.test.base.BaseTest;
import com.ziroom.minsu.valenum.msg.IsReplayEnum;
import com.ziroom.minsu.valenum.msg.LableTypeEnum;

/**
 * <p>房东设置是否需要自动回复测试</p>
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
public class MsgReplySetServiceImplTest extends BaseTest{

	
	@Resource(name = "message.msgReplySetServiceImpl")
	private MsgReplySetServiceImpl msgReplySetServiceImpl;
	@Test
	public void queryByLanglordFidtest() {
		
		MsgReplySetRequest msgReplySetRequest = new MsgReplySetRequest();
		msgReplySetRequest.setLandlordUid("fdsfdsfdsfdsfdsFDSFDSFfdsfsdfd");
		MsgReplySetEntity msgReplySetEntity = this.msgReplySetServiceImpl.queryByLanglordFid(msgReplySetRequest);
		
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
		int index = this.msgReplySetServiceImpl.save(msgReplySetEntity);
		System.out.println(index);
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
		int index = this.msgReplySetServiceImpl.updateByCondition(msgReplySetEntity);
		System.out.println(index);
	}

	
	@Test
	public void queryByLanglordFidTest(){
		
		MsgReplySetRequest msgReplySetRequest = new MsgReplySetRequest();
		
		msgReplySetRequest.setLandlordUid("fdsfdsfdsfdsfdsFDSFDSFfdsfsdfd");
		MsgReplySetEntity msgReplySetEntity =this.msgReplySetServiceImpl.queryByLanglordFid(msgReplySetRequest);
		
		System.out.println(msgReplySetEntity);
		
	}

}
