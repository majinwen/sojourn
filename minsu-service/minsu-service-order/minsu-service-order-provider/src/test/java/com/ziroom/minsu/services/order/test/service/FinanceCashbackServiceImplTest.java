package com.ziroom.minsu.services.order.test.service;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.services.order.dto.OrderCashbackRequest;
import com.ziroom.minsu.services.order.entity.OrderCashbackVo;
import com.ziroom.minsu.services.order.service.FinanceCashbackServiceImpl;
import com.ziroom.minsu.services.order.test.base.BaseTest;

public class FinanceCashbackServiceImplTest extends BaseTest {

	@Resource(name = "order.financeCashbackServiceImpl")
	private FinanceCashbackServiceImpl financeCashbackServiceImpl;
	
	@Test
	public void testGetOrderCashbackList(){
		OrderCashbackRequest request = new OrderCashbackRequest();
		PagingResult<OrderCashbackVo> orderCashbackList = financeCashbackServiceImpl.getOrderCashbackList(request);
		System.err.println(JsonEntityTransform.Object2Json(orderCashbackList));
	}

}
