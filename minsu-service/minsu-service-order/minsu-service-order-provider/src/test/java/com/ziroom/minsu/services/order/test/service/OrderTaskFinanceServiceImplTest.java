package com.ziroom.minsu.services.order.test.service;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.ziroom.minsu.entity.order.FinanceIncomeEntity;
import com.ziroom.minsu.entity.order.FinancePayVouchersEntity;
import com.ziroom.minsu.services.order.service.OrderTaskFinanceServiceImpl;
import com.ziroom.minsu.services.order.test.base.BaseTest;

public class OrderTaskFinanceServiceImplTest extends BaseTest {

	@Resource(name = "order.orderTaskFinanceServiceImpl")
	private OrderTaskFinanceServiceImpl orderTaskFinanceService;

	@Test
	public void TestGetNotPayVouchersCount() {
		long num = orderTaskFinanceService.getNotPayVouchersCount();
		System.out.println("num----------------------------:" + num);
	}

	@Test
	public void TestGetNotPayVouchersList() {
		List<FinancePayVouchersEntity> list = orderTaskFinanceService.getNotPayVouchersList(10);
		System.out.println("list:" + list);
	}
	
	
	@Test
	public void TestGetNotIncomeCount(){
		//long num = orderTaskFinanceService.getNotIncomeCount();
		//System.err.println("num:"+num);
	}
	
	@Test
	public void TestGetNotIncomeList(){
		//List<FinanceIncomeEntity> list = orderTaskFinanceService.getNotIncomeList(10);
		//System.err.println("list:"+list);
	}
}
