package com.ziroom.minsu.services.cms.test.service;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.cms.ActCouponEntity;
import com.ziroom.minsu.entity.cms.ActCouponUserEntity;
import com.ziroom.minsu.services.cms.dto.ActCouponRequest;
import com.ziroom.minsu.services.cms.entity.ActCouponInfoUserVo;
import com.ziroom.minsu.services.cms.service.ActCouponServiceImpl;
import com.ziroom.minsu.services.cms.service.ActivityGroupReceiveLogServiceImpl;
import com.ziroom.minsu.services.cms.test.base.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

public class ActCouponServiceImplTest extends BaseTest{
	
	@Resource(name = "cms.actCouponServiceImpl")
	private ActCouponServiceImpl actCouponServiceImpl;
	
	@Resource(name="cms.activityGroupReceiveLogServiceImpl")
	private ActivityGroupReceiveLogServiceImpl ac;
	
	
	@Test
	public void bindCouponTest(){
		
		actCouponServiceImpl.updateBindCoupon("123", "lishaochuan",null,null);
	}
	
	@Test
	public void getMasterForUpdateCouponByActSn(){
		ActCouponEntity act = actCouponServiceImpl.masterForUpdateOfgetCouponByActSn("123");
		System.err.println(act);
	}
	
	
	@Test
	public void getCouponBySnTest(){
		ActCouponEntity act = actCouponServiceImpl.getCouponBySn("123");
		System.err.println(act);
	}
	
	@Test
	public void getCouponListByActSnTest(){
		ActCouponRequest request = new ActCouponRequest();
    	request.setActSn("ziroom");
        PagingResult<ActCouponEntity> result = actCouponServiceImpl.getCouponListByActSn(request);
        System.err.println(JsonEntityTransform.Object2Json(result));
	}
	
	@Test
	public void getActCouponOrderVoByCouponSnTest(){
		ActCouponUserEntity v = actCouponServiceImpl.getActCouponUserVoByCouponSn("xianz3AUMFOV");
		System.err.println(JsonEntityTransform.Object2Json(v));
	}
	
	
	@Test
    public void TestgetActCouponInfoVoByCouponSn(){
		ActCouponInfoUserVo v = actCouponServiceImpl.getActCouponInfoVoByCouponSn("123");
    	
    	System.err.println(JsonEntityTransform.Object2Json(v));
    }
	
	@Test
	public void testGetRank(){
		Integer i=ac.getMobileRank("HAIYANJIHUA2017", "18611267826", null);
		System.err.println(i);
	}

	@Test
	public void testSaveCouponOperateLog() {
		ActCouponEntity actCouponEntity = new ActCouponEntity();
		actCouponEntity.setCouponStatus(1);
		actCouponEntity.setCouponSn("Test1.6");

		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("empCode", "222");
		paramMap.put("empName","yanb");
		paramMap.put("remark", "NewJunitTestOperateLog1");
		paramMap.put("actSn", "test1");
		paramMap.put("actCoupon", actCouponEntity);
		paramMap.put("toStatus", 5);

		actCouponServiceImpl.saveCouponOperateLog(paramMap);

	}

	@Test
	public void testCancelCoupon() {
		ActCouponEntity actCouponEntity = new ActCouponEntity();
		actCouponEntity.setCouponStatus(1);
		actCouponEntity.setCouponSn("Test1.4");

		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("empCode", "2222");
		paramMap.put("empName","yanb");
		paramMap.put("remark", "NewJunitTest1");
		paramMap.put("actCoupon", actCouponEntity);
		int i = actCouponServiceImpl.cancelCoupon(paramMap);
		System.err.println(i);
	}

	@Test
	public void testGetActSnStatusByCouponSn(){
		ActCouponEntity act = actCouponServiceImpl.getActSnStatusByCouponSn("Test1.4");
		System.err.println(act);
	}
	
}
