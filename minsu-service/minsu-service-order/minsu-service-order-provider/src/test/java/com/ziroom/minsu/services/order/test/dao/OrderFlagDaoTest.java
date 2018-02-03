package com.ziroom.minsu.services.order.test.dao;

import javax.annotation.Resource;

import org.junit.Test;

import com.ziroom.minsu.entity.order.OrderFlagEntity;
import com.ziroom.minsu.services.order.dao.OrderFlagDao;
import com.ziroom.minsu.services.order.test.base.BaseTest;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.order.OrderFlagEnum;

public class OrderFlagDaoTest extends BaseTest {

	@Resource(name = "order.orderFlagDao")
	private OrderFlagDao orderFlagDao;
	
	
	@Test
	public void saveOrderFlagTest(){
		OrderFlagEntity orderFlag = new OrderFlagEntity();
		orderFlag.setOrderSn("123");
		orderFlag.setFlagCode(OrderFlagEnum.REMIND_ACCEPT_ORDER.getCode());
		orderFlag.setFlagValue(YesOrNoEnum.YES.getStr());
		int num = orderFlagDao.saveOrderFlag(orderFlag);
		System.err.println(num);
	}
}
