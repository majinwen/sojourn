/**
 * @FileName: ActivityRecordDaoTest.java
 * @Package com.ziroom.minsu.services.cms.test.dao
 * 
 * @author yd
 * @created 2016年10月9日 下午2:30:29
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.cms.test.dao;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.cms.ActivityRecordEntity;
import com.ziroom.minsu.services.cms.dao.ActivityRecordDao;
import com.ziroom.minsu.services.cms.dto.ActivityRecordRequest;
import com.ziroom.minsu.services.cms.entity.ActRecordVo;
import com.ziroom.minsu.services.cms.test.base.BaseTest;
import com.ziroom.minsu.valenum.cms.IsPickUpEnum;

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
public class ActivityRecordDaoTest extends BaseTest{


	@Resource(name = "cms.activityRecordDao")
	private ActivityRecordDao activityRecordDao;

	@Test
	public void saveActivityRecordTest(){

		ActivityRecordEntity activityRecord = new ActivityRecordEntity();

		activityRecord.setActSn(UUIDGenerator.hexUUID());
		activityRecord.setFid(UUIDGenerator.hexUUID());
		activityRecord.setGroupSn(UUIDGenerator.hexUUID());
		activityRecord.setIsPickUp(IsPickUpEnum.NO_PICK_UP.getCode());
		activityRecord.setUserAdress("北京 朝阳 芳华园 113号");
		activityRecord.setGiftFid(UUIDGenerator.hexUUID());
		this.activityRecordDao.saveActivityRecord(activityRecord);
	}

	
	@Test
	public void updateActivityRecordByFidTest(){
		ActivityRecordEntity activityRecord = new ActivityRecordEntity();

		activityRecord.setFid("8a9e988d57a82ae20157a82ae2970001");
		activityRecord.setIsPickUp(IsPickUpEnum.PICK_UP.getCode());
		activityRecord.setUserUid(UUIDGenerator.hexUUID());
		
		this.activityRecordDao.updateActivityRecordByFid(activityRecord);
	}
	
	@Test
	public void queryAcRecordInfoByPageTest(){
		
		ActivityRecordRequest activityRecordRequest = new ActivityRecordRequest();
		
		//activityRecordRequest.setActName("");
		PagingResult<ActRecordVo>  page = this.activityRecordDao.queryAcRecordInfoByPage(activityRecordRequest);
		
		System.out.println(page);
	}
}
