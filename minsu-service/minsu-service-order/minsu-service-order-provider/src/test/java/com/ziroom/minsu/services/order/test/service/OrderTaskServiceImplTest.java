package com.ziroom.minsu.services.order.test.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.DateUtil;
import com.ziroom.minsu.entity.order.OrderEntity;
import com.ziroom.minsu.services.common.utils.DateSplitUtil;
import com.ziroom.minsu.services.order.service.OrderTaskServiceImpl;
import com.ziroom.minsu.services.order.test.base.BaseTest;

public class OrderTaskServiceImplTest extends BaseTest {
	
	@Resource(name = "order.orderTaskServiceImpl")
	private OrderTaskServiceImpl orderTaskServiceImpl;
	
	
	@Test
	public void TestTaskConfirmOtherFeeLandlordCount() {
		
		Date limitTime = DateSplitUtil.jumpHours(new Date(), -12);
		System.err.println("limitTime:" + DateUtil.timestampFormat(limitTime));

		Long count = orderTaskServiceImpl.taskConfirmOtherFeeLandlordCount(limitTime);
		System.err.println("count:" + count);
	}
	
	
	@Test
	public void TestTaskConfirmOtherFeeLandlordList(){
		Date limitTime = DateSplitUtil.jumpHours(new Date(), -12);
		System.err.println("limitTime:" + DateUtil.timestampFormat(limitTime));

		List<OrderEntity> list = orderTaskServiceImpl.taskConfirmOtherFeeLandlordList(limitTime, 100);
		System.err.println("list:"+list);
	}
	
	
	@Test
	public void TestTaskConfirmOtherFeeUserCount(){
		Date limitTime = DateSplitUtil.jumpHours(new Date(), -12);
		System.err.println("limitTime:" + DateUtil.timestampFormat(limitTime));

		Long count = orderTaskServiceImpl.taskConfirmOtherFeeUserCount(limitTime);
		System.err.println("count:" + count);
	}
	

	@Test
	public void TestTaskConfirmOtherFeeUserList(){
		Date limitTime = DateSplitUtil.jumpHours(new Date(), -12);
		System.err.println("limitTime:" + DateUtil.timestampFormat(limitTime));

		List<OrderEntity> list = orderTaskServiceImpl.taskConfirmOtherFeeUserList(limitTime, 100);
		System.err.println("list:"+list);
	}


	@Test
	public void TesupdateOrderAndUnLockHouse(){
		int i = orderTaskServiceImpl.updateOrderAndUnLockHouse("17062087XF38L4210520");
		System.err.println(i);
	}

}
