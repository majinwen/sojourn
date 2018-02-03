package com.ziroom.minsu.services.cms.test.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.ziroom.minsu.services.cms.dto.ZrpActRequest;
import org.junit.Test;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.cms.ActivityEntity;
import com.ziroom.minsu.services.cms.dto.ActivityInfoRequest;
import com.ziroom.minsu.entity.cms.ActivityVo;
import com.ziroom.minsu.services.cms.service.ActivityServiceImpl;
import com.ziroom.minsu.services.cms.test.base.BaseTest;

public class ActivityServiceImplTest extends BaseTest {

	@Resource(name = "cms.activityServiceImpl")
	private ActivityServiceImpl activityServiceImpl;
	
	@Test
	public void getActivityBySnTest(){
		ActivityEntity str = activityServiceImpl.selectByActSn("8a9e9ab35581c38a0155822e4824000a");
		System.err.println(JsonEntityTransform.Object2Json(str));
	}

	@Test
	public void getActivityVoListByCondiction() {
		ActivityInfoRequest request = new ActivityInfoRequest();
		PagingResult<ActivityVo> page = activityServiceImpl.getActivityVoListByCondiction(request);
		System.err.println(JsonEntityTransform.Object2Json(page));
	}

	@Test
	public void saveActivityInfoTest() {
		ActivityEntity activityInfoEntity = new ActivityEntity();
		activityInfoEntity.setActSn("lisc004");
		activityInfoEntity.setActName("lisc免佣金活动4");
		activityInfoEntity.setActStatus(1);
		activityInfoEntity.setActStartTime(new Date());
		activityInfoEntity.setActEndTime(new Date());

		int num = activityServiceImpl.saveActivityInfo(activityInfoEntity, "0");
		System.err.println(num);
	}

	@Test
	public void updateByActivitySn() {
		ActivityEntity activityInfoEntity = new ActivityEntity();
		activityInfoEntity.setActSn("lisc004");
		activityInfoEntity.setActName("lisc免佣金活动4修改一下名字");
		activityInfoEntity.setActStatus(1);
		activityInfoEntity.setActStartTime(new Date());
		activityInfoEntity.setActEndTime(new Date());

		int i = activityServiceImpl.updateByActSn(activityInfoEntity);
		System.err.println(i);
	}

	@Test
	public void testlistActConditionForZrp() {
		ZrpActRequest zrpActRequest = new ZrpActRequest();
		zrpActRequest.setProjectId("222");
		zrpActRequest.setRoomId("444");
		zrpActRequest.setLayoutId("3444");
		zrpActRequest.setRentDay(90);
		zrpActRequest.setSignType(1);
		zrpActRequest.setCustomerType(1);
		zrpActRequest.setCommonFee(BigDecimal.valueOf(2000));
		zrpActRequest.setUid("0f163457-ad6a-09ce-d5de-de452a251cf6");
		List<ActivityEntity> activityEntities = activityServiceImpl.listActConditionForZrp(zrpActRequest);
	}
	
}
