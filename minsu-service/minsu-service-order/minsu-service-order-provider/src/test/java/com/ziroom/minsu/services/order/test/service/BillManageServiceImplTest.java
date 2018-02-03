package com.ziroom.minsu.services.order.test.service;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.services.order.entity.FinancePayDetailInfoVo;
import com.ziroom.minsu.services.order.service.BillManageServiceImpl;
import com.ziroom.minsu.services.order.test.base.BaseTest;

public class BillManageServiceImplTest extends BaseTest {

	@Resource(name = "order.billManageServiceImpl")
	BillManageServiceImpl billManageServiceImpl;
	
	
	@Test
	public void getFinancePayInfoVoTest(){
		FinancePayDetailInfoVo financePayInfoVo = billManageServiceImpl.getFinancePayInfoVo("FK160824SDQ5S7Y6163509");
		System.err.println(JsonEntityTransform.Object2Json(financePayInfoVo));
	}
}
