/**
 * @FileName: ActivityGiftProxyTest.java
 * @Package com.ziroom.minsu.services.cms.test.proxy
 * 
 * @author yd
 * @created 2016年10月9日 下午3:52:04
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.cms.test.proxy;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.cms.ActivityFreeEntity;
import com.ziroom.minsu.entity.cms.ActivityGiftEntity;
import com.ziroom.minsu.services.cms.dto.ActivityGiftRequest;
import com.ziroom.minsu.services.cms.proxy.ActivityGiftProxy;
import com.ziroom.minsu.services.cms.test.base.BaseTest;
import com.ziroom.minsu.valenum.cms.GiftTypeEnum;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.msg.IsDelEnum;

/**
 * <p>活动礼物 代理测试类</p>
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
public class ActivityGiftProxyTest extends BaseTest{


	@Resource(name = "cms.activityGiftProxy")
	private ActivityGiftProxy activityGiftProxy;



	@Test
	public void getLandFreeCommTest(){
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.activityGiftProxy.getLandFreeComm("111"));
		System.out.println(dto.toJsonString());
	}

	@Test
	public void getLandFreeCommNullTest(){
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.activityGiftProxy.getLandFreeComm("222"));

		System.out.println(dto.toJsonString());
	}

	@Test
	public void queryActivityGifyByPageTest(){
		
		ActivityGiftRequest activityGift = new ActivityGiftRequest();
		
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.activityGiftProxy.queryActivityGifyByPage(JsonEntityTransform.Object2Json(activityGift)));
		
		System.out.println(dto);
	}
	
	@Test
	public void insertActivityGiftEntityTest(){
		ActivityGiftEntity activityGift = new ActivityGiftEntity();
		activityGift.setCreateId(UUIDGenerator.hexUUID());
		activityGift.setCreateTime(new Date());
		activityGift.setGiftName(GiftTypeEnum.GIFT_THING.getName());
		activityGift.setGiftType(GiftTypeEnum.GIFT_THING.getCode());
		activityGift.setGiftUnit("元");
		activityGift.setGiftValue("250");
		activityGift.setIsDel(IsDelEnum.NOT_DEL.getCode());
		activityGift.setLastModifyDate(new Date());
		String resultJson = this.activityGiftProxy.insertActivityGiftEntity(JsonEntityTransform.Object2Json(activityGift));
		System.err.println(resultJson);
	}
	
	@Test
	public void queryActivityGifyByFidTest(){
		String actGiftFid = "8a9e988d57a76fcc0157a76fcc560001";
		String resultJson = this.activityGiftProxy.queryActivityGifyByFid(actGiftFid);
		System.err.println(resultJson);
	}
	
	@Test
	public void updateActivityGiftEntityTest(){
		ActivityGiftEntity activityGiftEntity = new ActivityGiftEntity();
		activityGiftEntity.setFid("8a9e99c957d1b4970157d1b499430001");
		activityGiftEntity.setIsDel(YesOrNoEnum.YES.getCode());
		String resultJson = this.activityGiftProxy.updateActivityGiftEntity(JsonEntityTransform.Object2Json(activityGiftEntity));
		System.err.println(resultJson);
	}
	
	@Test
	public void testcancelFreeCommission(){
		ActivityFreeEntity activityFreeEntity =new ActivityFreeEntity();
		activityFreeEntity.setUid("0f163457-ad6a-09ce-d5de-de452a251cf6");
		activityFreeEntity.setIsDel(YesOrNoEnum.YES.getCode());
		String paramJson = JsonEntityTransform.Object2Json(activityFreeEntity);
		String cancelFreeCommissionJson = activityGiftProxy.cancelFreeCommission(paramJson);
		System.err.println(cancelFreeCommissionJson);
	}
	
}
