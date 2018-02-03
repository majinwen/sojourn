package com.ziroom.minsu.services.order.test.proxy;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.services.order.dto.RemarkRequest;
import com.ziroom.minsu.services.order.proxy.OrderRemarkServiceProxy;
import com.ziroom.minsu.services.order.test.base.BaseTest;

public class OrderRemarkServiceProxyTest extends BaseTest {
	
	
	@Resource(name = "order.orderRemarkServiceProxy")
	private OrderRemarkServiceProxy orderRemarkServiceProxy;
	
	@Test
	public void getRemarkList(){
		String str ="{\"orderSn\":\"1606239D1J4O1V170504\",\"uid\":\"e0a0f779-9117-6283-84e1-43e0be20ecf4\"}";
		String resultJson = orderRemarkServiceProxy.getRemarkList(str);
		System.err.println(resultJson);
	}
	
	@Test
	public void insertRemarkRes(){
		/*OrderRemarkEntity remarkEntity = new OrderRemarkEntity();
		remarkEntity.setOrderSn("160620L96R4O2P164107");
		remarkEntity.setRemarkContent("12312312312");
		
		String resultJson = orderRemarkServiceProxy.insertRemarkRes(JsonEntityTransform.Object2Json(remarkEntity));
		System.err.println(resultJson);*/
		
		String str ="{\"id\":null,\"fid\":null,\"orderSn\":\"160620M0085VBN162735\",\"remarkContent\":\"2\",\"uid\":\"eaaf194b-067e-4289-bcd7-63a9433d3ef4\",\"createTime\":null,\"isDel\":null}";
		String resultJson = orderRemarkServiceProxy.insertRemarkRes(str);
		System.err.println(resultJson);
	}
	
	
	@Test
	public void delRemark(){
		RemarkRequest req = new RemarkRequest();
		req.setFid("8a9e9ab35591f8e6015591f8e6160000");
		req.setUid("664524c5-6e75-ee98-4e0d-667d38b9eee1");
		String resultJson = orderRemarkServiceProxy.delRemark(JsonEntityTransform.Object2Json(req));
		System.err.println(resultJson);
	}

}
