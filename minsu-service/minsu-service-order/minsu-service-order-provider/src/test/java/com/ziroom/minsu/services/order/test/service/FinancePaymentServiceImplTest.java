package com.ziroom.minsu.services.order.test.service;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.order.FinancePaymentVouchersEntity;
import com.ziroom.minsu.services.order.service.FinancePaymentServiceImpl;
import com.ziroom.minsu.services.order.test.base.BaseTest;

public class FinancePaymentServiceImplTest extends BaseTest {

	@Resource(name = "order.financePaymentServiceImpl")
	private FinancePaymentServiceImpl financePaymentServiceImpl;
	
	@Test
	public void getOrderPaymentTest(){
		FinancePaymentVouchersEntity orderPayment = financePaymentServiceImpl.getOrderPayment("160515U832KU2X164716");
		System.err.println(JsonEntityTransform.Object2Json(orderPayment));
	}
}
