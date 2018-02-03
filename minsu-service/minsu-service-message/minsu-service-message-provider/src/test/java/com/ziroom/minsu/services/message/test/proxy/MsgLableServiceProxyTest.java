/**
 * @FileName: MsgLableServiceProxyTest.java
 * @Package com.ziroom.minsu.services.message.test.proxy
 * 
 * @author yd
 * @created 2016年4月18日 下午9:37:30
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.test.proxy;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.codehaus.jackson.type.TypeReference;
import org.junit.Test;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.message.MsgLableEntity;
import com.ziroom.minsu.services.message.dto.MsgLableRequest;
import com.ziroom.minsu.services.message.proxy.MsgLableServiceProxy;
import com.ziroom.minsu.services.message.test.base.BaseTest;
import com.ziroom.minsu.valenum.msg.IsGloabalEnum;
import com.ziroom.minsu.valenum.msg.IsReadEnum;
import com.ziroom.minsu.valenum.msg.LableTypeEnum;

/**
 * <p>自动回复代理层测试</p>
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
public class MsgLableServiceProxyTest extends BaseTest{


	@Resource(name = "message.msgLableServiceProxy")
	private MsgLableServiceProxy msgLableServiceProxy;
	@Test
	public void queryByPageTest() {
		MsgLableRequest	msgLableRequest = new MsgLableRequest();
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.msgLableServiceProxy.queryByPage(JsonEntityTransform.Object2Json(msgLableRequest)));
		List<MsgLableEntity> listmMsgLableEntities = dto.parseData("listMsgLable", new TypeReference<List<MsgLableEntity>>() {
		});
		System.out.println(listmMsgLableEntities);
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
		msgLableEntity.setMsgContent("f4ds56f4ds65ffdsfdsffdsfdsfsaaaaa4h哈哈哈");
		msgLableEntity.setMsgKey("aafdsfdsaa");
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.msgLableServiceProxy.save(JsonEntityTransform.Object2Json(msgLableEntity)));
		
		System.out.println(dto.getData().get("result"));
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
		
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.msgLableServiceProxy.updateByFid(JsonEntityTransform.Object2Json(msgLableEntity)));
		
		System.out.println(dto.getData().get("result"));
	}
	
	@Test
	public void queryMsgContentByKeyTest(){
		MsgLableRequest msRequest = new MsgLableRequest();
		msRequest.setLandlordFid("f4d5s6f4");
		msRequest.setMsgKey("aa");
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.msgLableServiceProxy.queryMsgContentByKey(JsonEntityTransform.Object2Json(msRequest)));
		
		List<String> listContent = dto.parseData("listContent", new TypeReference<List<String> >() {
		}) ;
		System.out.println(listContent);
	}
}
