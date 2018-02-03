/**
 * @FileName: MsgBaseServiceImplTest.java
 * @Package com.ziroom.minsu.services.message.test.service
 * 
 * @author yd
 * @created 2016年4月18日 下午3:06:09
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.test.service;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.message.MsgBaseEntity;
import com.ziroom.minsu.services.common.dto.PageRequest;
import com.ziroom.minsu.services.message.dto.MsgBaseRequest;
import com.ziroom.minsu.services.message.entity.MsgNotReplyVo;
import com.ziroom.minsu.services.message.service.MsgBaseServiceImpl;
import com.ziroom.minsu.services.message.test.base.BaseTest;
import com.ziroom.minsu.valenum.common.UserTypeEnum;
import com.ziroom.minsu.valenum.msg.IsDelEnum;
import com.ziroom.minsu.valenum.msg.IsReadEnum;

/**
 * <p>测试</p>
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
public class MsgBaseServiceImplTest extends BaseTest{

	@Resource(name = "message.msgBaseServiceImpl")
	private  MsgBaseServiceImpl msgBaseServiceImpl;
	@Test
	public void queryByPageTest() {
		MsgBaseRequest msgBaseRequest = new MsgBaseRequest();
		msgBaseRequest.setMsgHouseFid("8a9e9c8b541e32c001541e32c0150000");
		PagingResult<MsgBaseEntity> listPagingResult = msgBaseServiceImpl.queryByPage(msgBaseRequest);
		
		System.out.println(listPagingResult);
	}
	
	@Test
	public void saveTest() {

		MsgBaseEntity msgBaseEntity = new MsgBaseEntity();

		msgBaseEntity.setCreateTime(new Date());
		msgBaseEntity.setIsDel(IsDelEnum.NOT_DEL.getCode());
		msgBaseEntity.setIsRead(IsReadEnum.UNREAD.getCode());
		msgBaseEntity.setMsgContent("fd4s56ffdsfdsf4d65f46");
		msgBaseEntity.setMsgHouseFid("f4d5s6fdsfdsfdsff4d56");
		msgBaseEntity.setMsgSenderType(UserTypeEnum.TENANT.getUserType());
		int index = msgBaseServiceImpl.save(msgBaseEntity);

		System.out.println(index);
	}
	
	
	@Test
	public void getNotReplyListTest(){
		PagingResult<MsgNotReplyVo> notReplyList = msgBaseServiceImpl.getNotReplyList(new PageRequest());
		System.err.println(JsonEntityTransform.Object2Json(notReplyList));
	}

}
