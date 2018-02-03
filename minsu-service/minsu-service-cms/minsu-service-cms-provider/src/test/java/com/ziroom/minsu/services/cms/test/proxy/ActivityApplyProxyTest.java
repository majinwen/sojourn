package com.ziroom.minsu.services.cms.test.proxy;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.services.cms.dto.ActivityApplyRequest;
import com.ziroom.minsu.services.cms.dto.LanApplayRequest;
import com.ziroom.minsu.services.cms.proxy.ActivityApplyProxy;
import com.ziroom.minsu.services.cms.test.base.BaseTest;

public class ActivityApplyProxyTest extends BaseTest{
	
	@Resource(name = "cms.activityApplyProxy")
	private ActivityApplyProxy activityApplyProxy;

	@Test
	public void saveApplyTest(){
		ActivityApplyRequest request = new ActivityApplyRequest();
		request.setUid("123");
		request.setCustomerMoblie("18637033236");
		request.setCustomerName("chuan");
		
		
		List<String> houseUrlList = new ArrayList<String>();
		houseUrlList.add("www1");
		houseUrlList.add("www2");
		request.setHouseUrlList(houseUrlList);;
		
		String str = activityApplyProxy.saveApply(JsonEntityTransform.Object2Json(request));
		
		//String request = "{\"customerName\":\"李少川\",\"customerMoblie\":\"18633039235\",\"cityCode\":\"110000\",\"areaCode\":\"110101\",\"houseScore\":\"5\",\"houseNum\":\"5\",\"isZlan\":\"1\",\"houseUrlList\":[\"123123\"],\"remark\":\"123123\"}";
		//String str = activityApplyProxy.saveApply(JsonEntityTransform.Object2Json(request));
		System.err.println(str);
	}
	
	@Test
	public void applyListTest(){
		LanApplayRequest request = new LanApplayRequest();
		String str = activityApplyProxy.applyList(JsonEntityTransform.Object2Json(request));
		System.err.println(str);
		System.err.println(str);
		System.err.println(str);
	}
}
