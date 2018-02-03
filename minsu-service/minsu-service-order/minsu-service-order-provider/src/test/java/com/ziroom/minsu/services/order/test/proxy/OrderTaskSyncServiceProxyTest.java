package com.ziroom.minsu.services.order.test.proxy;

import javax.annotation.Resource;

import org.junit.Test;

import com.ziroom.minsu.services.order.proxy.OrderTaskSyncServiceProxy;
import com.ziroom.minsu.services.order.test.base.BaseTest;

public class OrderTaskSyncServiceProxyTest extends BaseTest {

	@Resource(name = "order.orderTaskSyncServiceProxy")
	private OrderTaskSyncServiceProxy orderTaskSyncServiceProxy;

	@Test
	public void TestSyncIncomeData() {
		orderTaskSyncServiceProxy.syncIncomeData();
	}

	
	@Test
	public void TestSyncPaymentData() {
		long start = System.currentTimeMillis();
		orderTaskSyncServiceProxy.syncPaymentData();
		long end = System.currentTimeMillis();
		System.out.println(end-start);
	}
	
	@Test
	public void syncCouponStatusTest(){
		
		// orderTaskSyncServiceProxy.syncCouponStatus();
		
	}
	@Test
	public void fillLanlordCashOrder(){
		orderTaskSyncServiceProxy.fillLanlordCashOrder("");
		
		try {
			Thread.sleep(1000000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
