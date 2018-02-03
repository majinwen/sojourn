/**
 * @FileName: MsgReplySetDaoTest.java
 * @Package com.ziroom.minsu.services.message.test.dao
 * 
 * @author yd
 * @created 2016年4月18日 上午11:44:51
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.test.dao;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.ziroom.minsu.entity.message.MsgReplySetEntity;
import com.ziroom.minsu.services.message.dao.MsgReplySetDao;
import com.ziroom.minsu.services.message.dto.MsgReplySetRequest;
import com.ziroom.minsu.services.message.test.base.BaseTest;
import com.ziroom.minsu.valenum.msg.IsReplayEnum;
import com.ziroom.minsu.valenum.msg.LableTypeEnum;

/**
 * <p>房东设置自动回复测试DAO</p>
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
public class MsgReplySetDaoTest extends BaseTest{

	
	@Resource(name = "message.msgReplySetDao")
	private MsgReplySetDao msgReplySetDao;
	@Test
	public void queryByCoditionTest() {
		MsgReplySetRequest msgReplySetRequest = new MsgReplySetRequest();
		List<MsgReplySetEntity> listMsgReplySetEntities = msgReplySetDao.queryByCodition(msgReplySetRequest);
		
		System.out.println(listMsgReplySetEntities);
	}
	
	@Test
	public void saveTest(){
		
		MsgReplySetEntity msgReplySetEntity = new MsgReplySetEntity();
		msgReplySetEntity.setCreateTime(new Date());
		msgReplySetEntity.setHouseFid("456f4d56s4f");
		msgReplySetEntity.setIsReply(IsReplayEnum.AUTO_REPLAY.getCode());
		msgReplySetEntity.setLableType(LableTypeEnum.HOUSE_LABLE.getCode());
		msgReplySetEntity.setLandlordUid("456f4ds56f");
		msgReplySetEntity.setLastModifyDate(new  Date());
		int index = this.msgReplySetDao.save(msgReplySetEntity);
		System.out.println(index);
	}
	
	
	@Test
	public void updateByConditionTest(){
		
		
		MsgReplySetEntity msgReplySetEntity = new MsgReplySetEntity();
		msgReplySetEntity.setHouseFid("456f4d56s4f");
		msgReplySetEntity.setIsReply(IsReplayEnum.UNAUTO_REPLAY.getCode());
		msgReplySetEntity.setLableType(LableTypeEnum.HOUSE_LABLE.getCode());
		msgReplySetEntity.setLandlordUid("fdsfdsfdsfdsfdsfdsfsdfd");
		msgReplySetEntity.setLastModifyDate(new  Date());
		msgReplySetEntity.setFid("8a9e9c99542787650154278765640000");
		int index = this.msgReplySetDao.updateByCondition(msgReplySetEntity);
		System.out.println(index);
	}

}
