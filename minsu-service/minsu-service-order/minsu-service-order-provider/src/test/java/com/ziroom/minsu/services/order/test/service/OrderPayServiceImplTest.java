package com.ziroom.minsu.services.order.test.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.order.OrderPayEntity;
import com.ziroom.minsu.services.order.service.OrderPayServiceImpl;

import org.junit.Test;

import com.ziroom.minsu.services.order.test.base.BaseTest;
import com.ziroom.minsu.services.order.dto.ToPayRequest;
import com.ziroom.minsu.services.order.entity.PayVo;

public class OrderPayServiceImplTest extends BaseTest {

	@Resource(name = "order.orderPayServiceImpl")
	private OrderPayServiceImpl orderPayServiceImpl;



	@Test
	public void TestGetPayCode() throws Exception {
		ToPayRequest toPayRequest = new ToPayRequest();
		List<PayVo> payList = new ArrayList<PayVo>();
		PayVo payVo = new PayVo();
		payVo.setOrderType(50);
		payVo.setPayMoney(100);
		payList.add(payVo);

		toPayRequest.setUserUid("lishaochuan");
		toPayRequest.setOrderSn("8a9e9cd253d0b29d0153d0b29d460001");
		toPayRequest.setSourceType("ios");
		toPayRequest.setCityCode("110000");
		toPayRequest.setPayList(payList);

		System.out.println("11111111111111111111111111111");
		/*String retsult = orderPayServiceImpl.getPayCode(toPayRequest);
		System.out.println("000000000000000000000000000retsult:" + retsult);*/
	}

	@Test
	public void getOrderPayByOrderSnTest(){
		OrderPayEntity orderPayByOrderSn = orderPayServiceImpl.getOrderPayByOrderSn("160619E6U3R445122334");
		System.err.println(JsonEntityTransform.Object2Json(orderPayByOrderSn));
	}
}
