package com.ziroom.minsu.services.cms.test.dao;

import javax.annotation.Resource;

import org.junit.Test;

import com.ziroom.minsu.entity.cms.ActivityApplyEntity;
import com.ziroom.minsu.services.cms.dao.ActivityApplyDao;
import com.ziroom.minsu.services.cms.test.base.BaseTest;

public class ActivityApplyDaoTest extends BaseTest {

	@Resource(name = "cms.activityApplyDao")
	private ActivityApplyDao activityApplyDao;
	
	@Test
	public void saveTest(){
		ActivityApplyEntity apply = new ActivityApplyEntity();
		apply.setCustomerMoblie("18633033235");
		apply.setCustomerName("川");
		apply.setRoleCode(11);
		
		int num = activityApplyDao.save(apply);
		System.err.println(num);
	}

}
