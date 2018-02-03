/**
 * @FileName: OrderLoadlordServiceImplTest.java
 * @Package com.ziroom.minsu.services.order.service
 * 
 * @author yd
 * @created 2016年4月3日 下午2:34:04
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.order.test.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.ziroom.minsu.services.order.entity.OrderInfoVo;
import com.ziroom.minsu.services.order.service.OrderLoadlordServiceImpl;
import org.junit.Test;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.ziroom.minsu.services.order.test.base.BaseTest;
import com.ziroom.minsu.services.order.dto.OrderRequest;
import com.ziroom.minsu.services.order.entity.OrderHouseVo;

/**
 * <p>房东管理测试case</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @date 2016-04-03 
 * @version 1.0
 */
public class OrderLoadlordServiceImplTest extends BaseTest{

	
	@Resource(name = "order.orderLoadlordServiceImpl")
	private OrderLoadlordServiceImpl orderLoadlordServiceImpl ;
	@Test
	public void queryLoadlordOrderByConditionTest() {
		

		List<Integer> listOrderstatus = new ArrayList<Integer>();
		listOrderstatus.add(0);
		OrderRequest orderRequest = new OrderRequest();
		orderRequest.setLandlordUid("testfafngdong");
		orderRequest.setListOrderStatus(listOrderstatus);
		PagingResult<OrderInfoVo> listPagingResult = orderLoadlordServiceImpl.queryLoadlordOrderByCondition(orderRequest);
		
		if(Check.NuNObj(listPagingResult)&&Check.NuNCollection(listPagingResult.getRows())){
			System.out.println(listPagingResult.getRows());
		}
	}
	@Test
	public void acceptOrderTest(){
		/*boolean flag = this.orderLoadlordServiceImpl.acceptOrder("8a9e9cd253d0b29d0153d0b29d460001", 20, "fdsafdsfdsfdsffsdfdsfds");
		System.out.println(flag);*/
	}
	
	@Test
	public void refusedOrderTest(){
		/*
		boolean flag = this.orderLoadlordServiceImpl.refusedOrder("8a9e9cd253d0b29d0153d0b29d460001", 0, 31,  "不想然你住了",  "fdsafdsfdsfdsffsdfdsfds");
		
		System.out.println(flag);*/
	}
	
	@Test
	public void saveOtherMoneyTest(){
		
		boolean flag = this.orderLoadlordServiceImpl.saveOtherMoney("8a9e9cd253d0b29d0153d0b29d460001", 60,  "床损坏", 1000, "fdsafdsfdsfdsffsdfdsfds");
		
		System.out.println(flag);
	}
	
	
	@Test
	public void acceptOrderNum(){
		long n = orderLoadlordServiceImpl.acceptOrderNum("664524c5-6e75-ee98-4e0d-667d38b9eee1");
		System.err.println(n);
	}
	
	@Test
	public void countLanOrderNum(){
		long n = orderLoadlordServiceImpl.countLanOrderNum("664524c5-6e75-ee98-4e0d-667d38b9eee1");
		System.err.println(n);
	}
	
	@Test
	public void countLanRefuseOrderNum(){
		long n = orderLoadlordServiceImpl.countLanRefuseOrderNum("664524c5-6e75-ee98-4e0d-667d38b9eee1");
		System.err.println(n);
	}
	
	@Test
	public void countSysRefuseOrderNum(){
		long n = orderLoadlordServiceImpl.countSysRefuseOrderNum("664524c5-6e75-ee98-4e0d-667d38b9eee1");
		System.err.println(n);
	}
	
	@Test
	public void countCanEvaNum(){
		long n = orderLoadlordServiceImpl.countCanEvaNum("664524c5-6e75-ee98-4e0d-667d38b9eee1");
		System.err.println(n);
	}
	
	@Test
	public void countLandlordEvaedNum(){
		long n = orderLoadlordServiceImpl.countLandlordEvaedNum("664524c5-6e75-ee98-4e0d-667d38b9eee1");
		System.err.println(n);
	}
	
	@Test
	public void countTenantEvaedNum(){
		long n = orderLoadlordServiceImpl.countTenantEvaedNum("664524c5-6e75-ee98-4e0d-667d38b9eee1");
		System.err.println(n);
	}
	
	@Test
	public void countWaitLandlordEvaNum(){
		long n = orderLoadlordServiceImpl.countWaitLandlordEvaNum("664524c5-6e75-ee98-4e0d-667d38b9eee1");
		System.err.println(n);
	}
	
	@Test
	public void countWaitTenantEvaNum(){
		long n = orderLoadlordServiceImpl.countWaitTenantEvaNum("664524c5-6e75-ee98-4e0d-667d38b9eee1");
		System.err.println(n);
	}
	

}
