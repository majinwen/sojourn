package com.ziroom.minsu.services.order.test.proxy;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.order.OrderRemarkEntity;
import com.ziroom.minsu.services.order.api.inner.OrderLoadlordService;
import com.ziroom.minsu.services.order.dto.OrderProfitRequest;
import com.ziroom.minsu.services.order.proxy.OrderLandlordServiceProxy;
import com.ziroom.minsu.services.order.proxy.OrderRemarkServiceProxy;
import com.ziroom.minsu.services.order.proxy.ProfitServiceProxy;
import com.ziroom.minsu.services.order.service.OrderLoadlordServiceImpl;
import com.ziroom.minsu.services.order.test.base.BaseTest;

public class ProfitServiceProxyTest extends BaseTest {
	
	
	@Resource(name = "order.profitServiceProxy")
	private ProfitServiceProxy profitServiceProxy;
	
	@Resource(name = "order.orderLandlordServiceProxy")
	private OrderLandlordServiceProxy orderLoadlordService;
	
	@Resource(name = "order.orderLoadlordServiceImpl")
	private OrderLoadlordServiceImpl orderLoadlordServiceImpl;
	
	@Test
	public void queryProfitOrderList(){
		OrderProfitRequest param = new OrderProfitRequest();
		param.setHouseFid("8a9084df556cef6e01556d0ded1e0014");
		param.setRoomFid("8a9084df556cd72c01556d0eaf1b000d");
		param.setMonth(7);
		param.setRentWay(1);
		param.setType(1);
		param.setPage(1);
		param.setLimit(5);
		param.setType(2);
		param.setUid("a06f82a2-423a-e4e3-4ea8-e98317540190");
		String resultJson = orderLoadlordService.queryProfitOrderList(JsonEntityTransform.Object2Json(param));
		System.err.println(resultJson);
		System.err.println(resultJson);
		
	}
	
	@Test
	public void queryHouseRoomList(){
		OrderProfitRequest param = new OrderProfitRequest();
		param.setMonth(7);
		/*param.setRentWay(0);
		param.setType(0);*/
		param.setPage(1);
		param.setLimit(5);
		param.setUid("c6a5b9d6-ef0c-46b9-8c41-7d6d0ebe158f");
		String resultJson = profitServiceProxy.getUserHouseList(JsonEntityTransform.Object2Json(param));
		System.err.println(resultJson);
		System.err.println(resultJson);
		
	}
	
	@Test
	public void queryProfitMoneyList(){
		OrderProfitRequest param = new OrderProfitRequest();
		param.setHouseFid("8a90a2d455b47fe00155ba335b37021f");
		param.setRoomFid("8a90a2d455b47fe00155ba335b81022d");
		param.setMonth(7);
		param.setRentWay(1);
		param.setType(2);
		param.setPage(1);
		param.setLimit(50);
		param.setUid("53df78d5-b599-47e5-8acf-d1e19eb53394");
		String resultJson = profitServiceProxy.getHouseMonthProfit(JsonEntityTransform.Object2Json(param));
		System.err.println(resultJson);
		System.err.println(resultJson);
		
	}
	
	@Test
	public void countOrderList(){
		OrderProfitRequest param = new OrderProfitRequest();
		param.setHouseFid("8a90a2d455beeb8c0155bf1221e40034");
		//param.setRoomFid("8a9084df556cd72c01556d0eaf1b000d");
		param.setMonth(7);
		param.setRentWay(0);
		param.setType(1);
		param.setPage(1);
		param.setLimit(50);
		param.setUid("53df78d5-b599-47e5-8acf-d1e19eb53394");
		long resultJson = orderLoadlordServiceImpl.countOrderList(param);
		System.out.println(resultJson);
		System.out.println(resultJson);
		
	}
	
	
	@Test
	public void queryAllHouseMoneyList(){
		OrderProfitRequest param = new OrderProfitRequest();
		param.setMonth(7);
		param.setUid("664524c5-6e75-ee98-4e0d-667d38b9eee1");
		
		String resultJson = profitServiceProxy.getUserAllHouseMonthProfit(JsonEntityTransform.Object2Json(param));
		System.err.println(resultJson);
		System.err.println(resultJson);
		
	}

}
