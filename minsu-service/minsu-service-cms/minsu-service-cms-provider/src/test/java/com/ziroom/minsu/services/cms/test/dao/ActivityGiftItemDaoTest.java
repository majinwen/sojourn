/**
 * @FileName: ActivityGiftItemDaoTest.java
 * @Package com.ziroom.minsu.services.cms.test.dao
 * 
 * @author yd
 * @created 2016年10月9日 上午11:29:02
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.cms.test.dao;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.cms.ActivityGiftItemEntity;
import com.ziroom.minsu.services.cms.dao.ActivityGiftItemDao;
import com.ziroom.minsu.services.cms.dto.ActivityGiftItemRequest;
import com.ziroom.minsu.services.cms.entity.AcGiftItemVo;
import com.ziroom.minsu.services.cms.test.base.BaseTest;
import com.ziroom.minsu.valenum.msg.IsDelEnum;

/**
 * <p>活动礼品项 测试</p>
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
public class ActivityGiftItemDaoTest extends BaseTest{

	
	@Resource(name = "cms.activityGiftItemDao")
	private  ActivityGiftItemDao  activityGiftItemDao;
	
	@Test
	public void saveGiftItemTest(){
		
		ActivityGiftItemEntity activityGiftItem = new ActivityGiftItemEntity();
		
		activityGiftItem.setActSn("8a9084df55e7fbc60155e7fbc6df0000");
		activityGiftItem.setCreateDate(new Date());
		activityGiftItem.setFid(UUIDGenerator.hexUUID());
		activityGiftItem.setGiftCount(6);
		activityGiftItem.setIsDel(IsDelEnum.NOT_DEL.getCode());
		activityGiftItem.setGiftFid(UUIDGenerator.hexUUID());
		int i = this.activityGiftItemDao.saveGiftItem(activityGiftItem);
		
		System.out.println(i);
	}
	
	@Test
	public void getAcGiftItemByConTest(){
		
		ActivityGiftItemRequest activityGiftItemRe = new ActivityGiftItemRequest();
		
		activityGiftItemRe.setActSn("8a9e988d57ac71cc0157ac71cc090000");
		List<AcGiftItemVo> list = this.activityGiftItemDao.getAcGiftItemByCon(activityGiftItemRe);
		
		System.out.println(list);
	}
}
