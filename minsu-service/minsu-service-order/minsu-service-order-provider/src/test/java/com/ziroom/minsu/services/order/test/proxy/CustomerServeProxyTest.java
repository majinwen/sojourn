package com.ziroom.minsu.services.order.test.proxy;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.services.common.dto.PageRequest;
import com.ziroom.minsu.services.order.proxy.CustomerServeProxy;
import com.ziroom.minsu.services.order.test.base.BaseTest;

public class CustomerServeProxyTest extends BaseTest {

	
	@Resource(name = "order.customerServeProxy")
	private CustomerServeProxy customerServeProxy;
	
	
	@Test
	public void getRemindOrderListTest(){
		PageRequest pageRequest = new PageRequest();
		String remindOrderList = customerServeProxy.getRemindOrderList(JsonEntityTransform.Object2Json(pageRequest));
		System.err.println(remindOrderList);
	}
	
	
	
	@Test
	public void getRefuseOrderListTest(){
		PageRequest pageRequest = new PageRequest();
		String refuseOrderList = customerServeProxy.getRefuseOrderList(JsonEntityTransform.Object2Json(pageRequest));
		System.err.println(refuseOrderList);
	}
}
