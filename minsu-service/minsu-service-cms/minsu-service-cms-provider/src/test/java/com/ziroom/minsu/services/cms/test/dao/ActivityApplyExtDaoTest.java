package com.ziroom.minsu.services.cms.test.dao;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.cms.ActivityApplyExtEntity;
import com.ziroom.minsu.services.cms.dao.ActivityApplyExtDao;
import com.ziroom.minsu.services.cms.test.base.BaseTest;
import com.ziroom.minsu.valenum.cms.ApplyExtType;

public class ActivityApplyExtDaoTest extends BaseTest {

	@Resource(name = "cms.activityApplyExtDao")
	private ActivityApplyExtDao activityApplyExtDao;
	
	
	@Test
	public void saveTest(){
		ActivityApplyExtEntity applyExt = new ActivityApplyExtEntity();
		applyExt.setApplyFid(UUIDGenerator.hexUUID());
		applyExt.setExtType(ApplyExtType.URL.getCode());
		applyExt.setContent("www.ziroom.com");
		
		int num = activityApplyExtDao.save(applyExt);
		System.err.println(num);
	}
}
