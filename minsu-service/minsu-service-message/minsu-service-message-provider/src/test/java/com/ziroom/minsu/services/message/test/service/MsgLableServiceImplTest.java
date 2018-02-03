/**
 * @FileName: MsgLableServiceImplTest.java
 * @Package com.ziroom.minsu.services.message.test.service
 * 
 * @author yd
 * @created 2016年4月18日 下午3:46:31
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.test.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.ziroom.minsu.entity.message.MsgLableEntity;
import com.ziroom.minsu.services.message.dto.MsgLableRequest;
import com.ziroom.minsu.services.message.entity.MsgKeyVo;
import com.ziroom.minsu.services.message.service.MsgLableServiceImpl;
import com.ziroom.minsu.services.message.test.base.BaseTest;
import com.ziroom.minsu.valenum.msg.IsGloabalEnum;
import com.ziroom.minsu.valenum.msg.IsReadEnum;
import com.ziroom.minsu.valenum.msg.LableTypeEnum;

/**
 * <p>自动回复标签管理测试service</p>
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
public class MsgLableServiceImplTest extends BaseTest{

	
	@Resource(name = "message.msgLableServiceImpl")
	private MsgLableServiceImpl msgLableServiceImpl;
	@Test
	public void queryByPageTest() {
		
		MsgLableRequest	msgLableRequest = new MsgLableRequest();
		PagingResult<MsgLableEntity> listPagingResult = this.msgLableServiceImpl.queryByPage(msgLableRequest);
		
		if(!Check.NuNObj(listPagingResult)){
			System.out.println(listPagingResult);
		}
	}
	
	
	@Test
	public void queryByConditionTest(){
		MsgLableRequest	msgLableRequest = new MsgLableRequest();
		List<MsgLableEntity>  listMsgLableEntities= this.msgLableServiceImpl.queryByCondition(msgLableRequest);
		if(!Check.NuNCollection(listMsgLableEntities)){
			System.out.println(listMsgLableEntities);
		}
	}
	@Test
	public void saveTest(){
		
		
		MsgLableEntity msgLableEntity = new MsgLableEntity();
		msgLableEntity.setCreateTime(new Date());
		msgLableEntity.setHouseFid("45fdfdf64564");
		msgLableEntity.setIsGlobal(IsGloabalEnum.NOT_ADAPT_GOLBAL.getCode());
		msgLableEntity.setIsRelease(IsReadEnum.READ.getCode());
		msgLableEntity.setLableType(LableTypeEnum.HOUSE_LABLE.getCode());
		msgLableEntity.setLandlordFid("f4ds69f47d8");
		msgLableEntity.setLastModifyDate(new Date());
		msgLableEntity.setMsgContent("f4ds56f4ds65ffdsfdsfsaaaaa4h哈哈哈");
		msgLableEntity.setMsgKey("aaaa");
		int index = this.msgLableServiceImpl.save(msgLableEntity);
		
		System.out.println(index);
	}
	
	@Test
	public void queryMsgKeyByConditionTest(){
		MsgLableRequest	msgLableRequest = new MsgLableRequest();
		List<MsgKeyVo> listMsgKeyVos = this.msgLableServiceImpl.queryMsgKeyByCondition(msgLableRequest);
		if(!Check.NuNCollection(listMsgKeyVos)){
			System.out.println(listMsgKeyVos);
		}
	}
	
	
	@Test
	public void updateByFidTest(){
		MsgLableEntity msgLableEntity = new MsgLableEntity();
		msgLableEntity.setIsGlobal(IsGloabalEnum.ADAPT_GOLBAL.getCode());
		msgLableEntity.setIsRelease(IsReadEnum.UNREAD.getCode());
		msgLableEntity.setLableType(LableTypeEnum.HOUSE_LABLE.getCode());
		msgLableEntity.setLandlordFid("f4ds69f47d8");
		msgLableEntity.setLastModifyDate(new Date());
		msgLableEntity.setMsgContent("f4ds56f4ds65fdsfdsfdff4h哈哈哈");
		
		msgLableEntity.setFid("8a9e9c99542771fb01542771fb4b0000");
		int index = this.msgLableServiceImpl.updateByFid(msgLableEntity);
		
		System.out.println(index);
	}

}
