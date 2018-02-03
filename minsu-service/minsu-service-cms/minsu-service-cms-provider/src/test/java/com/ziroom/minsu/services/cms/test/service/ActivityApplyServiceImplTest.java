package com.ziroom.minsu.services.cms.test.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.cms.ActivityApplyDescEntity;
import com.ziroom.minsu.entity.cms.ActivityApplyEntity;
import com.ziroom.minsu.entity.cms.ActivityApplyExtEntity;
import com.ziroom.minsu.services.cms.dto.ActivityApplyRequest;
import com.ziroom.minsu.services.cms.entity.ActivityApplyVo;
import com.ziroom.minsu.services.cms.service.ActivityApplyServiceImpl;
import com.ziroom.minsu.services.cms.test.base.BaseTest;
import com.ziroom.minsu.valenum.cms.ApplyExtType;
import com.ziroom.minsu.valenum.customer.CustomerRoleEnum;

public class ActivityApplyServiceImplTest extends BaseTest{

	@Resource(name = "cms.activityApplyServiceImpl")
	private ActivityApplyServiceImpl activityApplyServiceImpl;
	
	@Test
	public void saveTest(){
		ActivityApplyEntity apply = new ActivityApplyEntity();
//		ActivityApplyRequest apply = new ActivityApplyRequest();
		
		apply.setCustomerName("川");
		apply.setCustomerMoblie("18633033235");
		apply.setCityCode("110100");
		apply.setAreaCode("100101");
		apply.setCustomerIntroduce("房东个人介绍");
		apply.setHouseStory("房源故事");
		apply.setGiftAddress("礼包收货地址");
		apply.setRemark("备注");
		
		ActivityApplyDescEntity desc  = new ActivityApplyDescEntity();
		desc.setCustomerIntroduce("房东个人介绍");
		desc.setHouseStory("房源故事");
		
//		List<String> houseUrlList = new ArrayList<String>();
//		houseUrlList.add("www.ziroom.com");
//		apply.setHouseUrlList(houseUrlList);
//		System.out.println(JSON.toJSONString(apply));
		apply.setRoleCode(CustomerRoleEnum.SEED.getCode());
		
		List<ActivityApplyExtEntity> applyExtList = new ArrayList<ActivityApplyExtEntity>();
		ActivityApplyExtEntity applyExt = new ActivityApplyExtEntity();
		applyExt.setApplyFid(UUIDGenerator.hexUUID());
		applyExt.setExtType(ApplyExtType.URL.getCode());
		applyExt.setContent("www.ziroom.com");
		applyExtList.add(applyExt);
		
		
		
		activityApplyServiceImpl.save(apply, desc,applyExtList);
	}
	
	
	@Test
	public void getApplyDetailWithBLOBs(){
		ActivityApplyVo vo = activityApplyServiceImpl.getApplyDetailWithBLOBs("8a9e9896587c389901587c389a0c0001");		
		System.err.println(JSON.toJSONString(vo));		
	}
	
}
