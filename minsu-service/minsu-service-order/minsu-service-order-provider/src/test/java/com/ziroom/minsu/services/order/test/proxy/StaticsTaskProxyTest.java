package com.ziroom.minsu.services.order.test.proxy;

import javax.annotation.Resource;

import org.junit.Test;

import com.ziroom.minsu.services.order.proxy.OrderTaskSyncServiceProxy;
import com.ziroom.minsu.services.order.test.base.BaseTest;

public class StaticsTaskProxyTest extends BaseTest {

	@Resource(name = "order.orderTaskSyncServiceProxy")
	private OrderTaskSyncServiceProxy orderTaskSyncServiceProxy;

	@Test
	public void TestSyncIncomeData() {
		orderTaskSyncServiceProxy.syncIncomeData();
	}

}
