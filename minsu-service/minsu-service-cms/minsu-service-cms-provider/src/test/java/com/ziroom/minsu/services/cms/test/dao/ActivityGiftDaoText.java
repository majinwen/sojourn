/**
 * @FileName: ActivityGiftDaoText.java
 * @Package com.ziroom.minsu.services.cms.test.dao
 * 
 * @author yd
 * @created 2016年10月9日 上午10:59:22
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.cms.test.dao;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.cms.ActivityGiftEntity;
import com.ziroom.minsu.services.cms.dao.ActivityGiftDao;
import com.ziroom.minsu.services.cms.dto.ActivityGiftRequest;
import com.ziroom.minsu.services.cms.test.base.BaseTest;
import com.ziroom.minsu.valenum.cms.GiftTypeEnum;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.msg.IsDelEnum;

/**
 * <p>活动礼物测试</p>
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
public class ActivityGiftDaoText  extends BaseTest   {

	@Resource(name = "cms.activityGiftDao")
	private  ActivityGiftDao  activityGiftDao;
	
	
	@Test
	public void saveGiftTest(){
		
		ActivityGiftEntity activityGift = new ActivityGiftEntity();
		activityGift.setCreateId(UUIDGenerator.hexUUID());
		activityGift.setCreateTime(new Date());
		activityGift.setFid(UUIDGenerator.hexUUID());
		activityGift.setGiftName(GiftTypeEnum.GIFT_THING.getName());
		activityGift.setGiftType(GiftTypeEnum.GIFT_THING.getCode());
		activityGift.setGiftUnit("元");
		activityGift.setGiftValue("250");
		activityGift.setIsDel(IsDelEnum.NOT_DEL.getCode());
		activityGift.setLastModifyDate(new Date());
		int i = this.activityGiftDao.saveGift(activityGift);
		
		System.out.println(i);
	}
	
	@Test
	public void queryActivityGifyByPageTest(){
		ActivityGiftRequest activityGift = new ActivityGiftRequest();
		
		PagingResult<ActivityGiftEntity> page = this.activityGiftDao.queryActivityGifyByPage(activityGift);
		
		System.out.println(page);
	}

	@Test
	public void queryActivityGifyByFidTest(){
		String actGiftFid = "8a9e988d57a76fcc0157a76fcc560001";
		ActivityGiftEntity entity = this.activityGiftDao.getGiftByFid(actGiftFid);
		System.err.println(JsonEntityTransform.Object2Json(entity));
	}
	
	@Test
	public void updateActivityGiftEntityByFidTest(){
		ActivityGiftEntity activityGiftEntity = new ActivityGiftEntity();
		activityGiftEntity.setFid("8a9e99c957d1b4970157d1b499430001");
		activityGiftEntity.setIsDel(YesOrNoEnum.YES.getCode());
		int upNum = this.activityGiftDao.updateActivityGiftEntityByFid(activityGiftEntity);
		System.err.println(upNum);
	}
}
