/**
 * @FileName: OrderLoadlordServiceProxyTest.java
 * @Package com.ziroom.minsu.services.order.proxy
 * 
 * @author yd
 * @created 2016年4月5日 下午3:30:46
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.order.test.proxy;

import javax.annotation.Resource;
import com.ziroom.minsu.services.order.proxy.OrderLandlordServiceProxy;
import org.junit.Test;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.services.order.test.base.BaseTest;
import com.ziroom.minsu.services.order.dto.LoadlordRequest;

/**
 * <p></p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
public class OrderLoadlordServiceProxyTest extends BaseTest {

	@Resource(name = "order.orderLandlordServiceProxy")
	private OrderLandlordServiceProxy orderLoadlordService;




    @Test
    public void installHouseLock(){

        String flag = orderLoadlordService.installHouseLock("8a90a2d4549ac7990154a3ee6eee0237");
        System.out.println(flag);

    }



    @Test
	public void acceptOrderTest(){

		LoadlordRequest loadlordRequest = new LoadlordRequest();
		loadlordRequest.setOrderSn("1706074KQQCOJ4114207");
		loadlordRequest.setOrderStatus(20);
		loadlordRequest.setLandlordUid("a06f82a2-423a-e4e3-4ea8-e98317540190");
		String flag = orderLoadlordService.acceptOrder(JsonEntityTransform.Object2Json(loadlordRequest));
		System.out.println(flag);

	}

	@Test
	public void saveOtherMoneyTestUid_null(){
		LoadlordRequest loadlordRequest = new LoadlordRequest();
		loadlordRequest.setOrderSn("160825FNA81US8164315");
//		loadlordRequest.setOrderStatus(60);
		loadlordRequest.setLandlordUid("664524c5-6e75-ee98-4e0d-667d38b9eee1");
		loadlordRequest.setOtherMoney(0);
		loadlordRequest.setParamValue("10元");
		String flag = orderLoadlordService.saveOtherMoney(JsonEntityTransform.Object2Json(loadlordRequest));
		System.out.println(flag);
	}

	@Test
	public void saveOtherMoneyTest(){
		LoadlordRequest loadlordRequest = new LoadlordRequest();
		loadlordRequest.setOrderSn("1605186458D95B215855");
		loadlordRequest.setOrderStatus(60);
		loadlordRequest.setLandlordUid("9afeae98-56ff-0c35-77cf-8624b2e1efae");
		loadlordRequest.setOtherMoney(2000);
		loadlordRequest.setParamValue("cesghi");
		String flag = orderLoadlordService.saveOtherMoney(JsonEntityTransform.Object2Json(loadlordRequest));
		System.out.println(flag);
	}


	@Test
	public void saveOtherMoneyTest_ZERO(){
		LoadlordRequest loadlordRequest = new LoadlordRequest();
		loadlordRequest.setOrderSn("160515U832KU2X164716");
		loadlordRequest.setOrderStatus(60);
		loadlordRequest.setLandlordUid("8a9e9a9e543d23f901543d23f9e90000");
		loadlordRequest.setOtherMoney(1);
		loadlordRequest.setParamValue("额外给你1元sssssssssssssss");
		String flag = orderLoadlordService.saveOtherMoney(JsonEntityTransform.Object2Json(loadlordRequest));
		System.out.println(flag);
	}


	@Test
	public void refusedOrderTest(){
		LoadlordRequest loadlordRequest = new LoadlordRequest();
		loadlordRequest.setOrderSn("160627WJXOQ429195157");
		loadlordRequest.setOrderStatus(31);
		loadlordRequest.setLandlordUid("664524c5-6e75-ee98-4e0d-667d38b9eee1");
		//loadlordRequest.setParamValue("这房客，评论太差，拒绝了");
		loadlordRequest.setRefuseCode("1");
		loadlordRequest.setRefuseReason("出租日历冲");
		
		
		String flag = orderLoadlordService.refusedOrder(JsonEntityTransform.Object2Json(loadlordRequest));
		System.out.println(flag);
		System.out.println(flag);
		System.out.println(flag);
	}
	
	@Test
	public void getOtherMoneyLimitTest(){
		LoadlordRequest loadlordRequest = new LoadlordRequest();
		loadlordRequest.setOrderSn("171225LA577186154456");
		loadlordRequest.setParamValue("fdds");
		loadlordRequest.setOtherMoney(100);
		loadlordRequest.setLandlordUid("01609246-9889-48bd-95c4-b7af1bb606d9");
		String flag = orderLoadlordService.getOtherMoneyLimit(JsonEntityTransform.Object2Json(loadlordRequest));
		System.out.println(flag);
		System.out.println(flag);
		System.out.println(flag);
	}

}
