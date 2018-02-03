package com.ziroom.minsu.services.order.test.service;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.services.common.dto.PageRequest;
import com.ziroom.minsu.services.finance.entity.RefuseOrderVo;
import com.ziroom.minsu.services.finance.entity.RemindOrderVo;
import com.ziroom.minsu.services.order.service.CustomerServeServiceImpl;
import com.ziroom.minsu.services.order.test.base.BaseTest;

public class CustomerServeServiceImplTest extends BaseTest {

	@Resource(name = "order.customerServeServiceImpl")
	private CustomerServeServiceImpl customerServeServiceImpl;
	
	@Test
	public void getRemindOrderListTest(){
		
		PageRequest pageRequest = new PageRequest();
		PagingResult<RemindOrderVo> remindOrderList = customerServeServiceImpl.getRemindOrderList(pageRequest);
		System.err.println(JsonEntityTransform.Object2Json(remindOrderList));
	}
	
	@Test
	public void getRefuseOrderListTest(){
		
		PageRequest pageRequest = new PageRequest();
		PagingResult<RefuseOrderVo> refuseOrderList = customerServeServiceImpl.getRefuseOrderList(pageRequest);
		System.err.println(JsonEntityTransform.Object2Json(refuseOrderList));
	}
}
