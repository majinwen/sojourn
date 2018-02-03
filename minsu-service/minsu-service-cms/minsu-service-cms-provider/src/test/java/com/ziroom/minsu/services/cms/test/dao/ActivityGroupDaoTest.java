/**
 * @FileName: ActivityGroupDaoTest.java
 * @Package com.ziroom.minsu.services.cms.test.dao
 * 
 * @author yd
 * @created 2016年10月9日 上午11:50:32
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.cms.test.dao;

import javax.annotation.Resource;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.services.cms.dto.GroupRequest;
import org.junit.Test;

import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.cms.ActivityGroupEntity;
import com.ziroom.minsu.services.cms.dao.ActivityGroupDao;
import com.ziroom.minsu.services.cms.test.base.BaseTest;

import java.util.List;

/**
 * <p>活动组 测试</p>
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
public class ActivityGroupDaoTest extends BaseTest{

	
	@Resource(name = "cms.activityGroupDao")
	private ActivityGroupDao activityGroupDao;



	@Test
	public void getGroupByPage(){
		GroupRequest request = new GroupRequest();
		request.setGroupSn("456465456456465");
		request.setGroupName("10");
		PagingResult<ActivityGroupEntity> pagingResult = activityGroupDao.getGroupByPage(request);
		System.out.println(JsonEntityTransform.Object2Json(pagingResult));
	}

	@Test
	public void getGroupBySN(){

		ActivityGroupEntity list = activityGroupDao.getGroupBySN("456465456456465");

		System.out.println(JsonEntityTransform.Object2Json(list));
	}



	@Test
	public void getAllGroup(){

		List<ActivityGroupEntity> list = activityGroupDao.getAllGroup();

		System.out.println(JsonEntityTransform.Object2Json(list));
	}



	
	@Test
	public void saveActivityGroup(){
		
		ActivityGroupEntity activityGroup = new ActivityGroupEntity();
		
		activityGroup.setCreateId(UUIDGenerator.hexUUID());
		activityGroup.setGroupName("1018");
		activityGroup.setGroupSn("456465456456465");
		activityGroup.setRemark("1018组的活动");
		this.activityGroupDao.saveActivityGroup(activityGroup);
		
	}
	
	@Test
	public void updateActivityGiftEntityByFidTest(){
		ActivityGroupEntity activityGroup = new ActivityGroupEntity();
    	activityGroup.setFid("8a9e988d57a805850157a8058e420001");
    	activityGroup.setGroupName("5-109");
		int upNum = this.activityGroupDao.updateActivityGroupEntityByFid(activityGroup);
		System.err.println(upNum);
	}
}
