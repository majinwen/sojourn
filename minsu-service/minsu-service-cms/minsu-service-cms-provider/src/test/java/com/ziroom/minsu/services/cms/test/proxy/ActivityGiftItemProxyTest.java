/**
 * @FileName: ActivityGiftItemProxyTest.java
 * @Package com.ziroom.minsu.services.cms.test.proxy
 * 
 * @author yd
 * @created 2016年10月9日 下午5:24:09
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
import com.ziroom.minsu.entity.cms.ActivityGiftItemEntity;
import com.ziroom.minsu.services.cms.proxy.ActivityGiftItemProxy;
import com.ziroom.minsu.services.cms.test.base.BaseTest;
import com.ziroom.minsu.valenum.msg.IsDelEnum;

/**
 * <p>TODO</p>
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
public class ActivityGiftItemProxyTest extends BaseTest{


	@Resource(name = "cms.activityGiftItemProxy")
	private ActivityGiftItemProxy activityGiftItemProxy;

	@Test
	public void saveGiftItemTest(){


		ActivityGiftItemEntity activityGiftItem = new ActivityGiftItemEntity();

		activityGiftItem.setActSn("8a9084df55e7fbc60155e7fbc6df0000");
		activityGiftItem.setCreateDate(new Date());
		activityGiftItem.setFid(UUIDGenerator.hexUUID());
		activityGiftItem.setGiftCount(6);
		activityGiftItem.setIsDel(IsDelEnum.NOT_DEL.getCode());
		activityGiftItem.setGiftFid(UUIDGenerator.hexUUID());
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.activityGiftItemProxy.saveGiftItem(JsonEntityTransform.Object2Json(activityGiftItem)));

		System.out.println(dto);
	}
}
