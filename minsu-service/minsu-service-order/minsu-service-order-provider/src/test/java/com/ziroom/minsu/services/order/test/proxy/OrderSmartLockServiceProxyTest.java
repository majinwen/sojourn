package com.ziroom.minsu.services.order.test.proxy;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.DateUtil.IntervalUnit;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.order.OrderSmartLockEntity;
import com.ziroom.minsu.services.order.proxy.OrderSmartLockServiceProxy;
import com.ziroom.minsu.services.order.test.base.BaseTest;

/**
 * <p>订单智能锁代理类测试</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/3.
 * @version 1.0
 * @since 1.0
 */
public class OrderSmartLockServiceProxyTest extends BaseTest {

    @Resource(name = "order.orderSmartLockServiceProxy")
    private OrderSmartLockServiceProxy orderSmartLockServiceProxy;
	
	@Test
	public void findOrderSmartLockByFidTest(){
		String fid = "8a9e9ab1557d18f501557d18f5210000";
		String resultJson = orderSmartLockServiceProxy.findOrderSmartLockByFid(fid);
		System.err.println(resultJson);
	}
	
	@Test
	public void findOrderSmartLockByOrderSnTest(){
		String orderSn = "16051176ZD432U160931";
		String resultJson = orderSmartLockServiceProxy.findOrderSmartLockByOrderSn(orderSn);
		System.err.println(JsonEntityTransform.Object2Json(resultJson));
	}
	
	@Test
	public void updateOrderSmartLockTest(){
		OrderSmartLockEntity orderSmartLock = new OrderSmartLockEntity();
		orderSmartLock.setFid("8a9e9ab1557d18f501557d18f5210000");
		orderSmartLock.setServiceId("111111");
		orderSmartLock.setPasswordId("fid");
		orderSmartLock.setTempPswd("654321Fid");
		orderSmartLock.setTempStartTime(DateUtil.intervalDate(-1, IntervalUnit.DAY));
		orderSmartLock.setTempExpiredTime(DateUtil.intervalDate(6, IntervalUnit.DAY));
		orderSmartLock.setTempPswdStatus(1);
		orderSmartLock.setDynaPswd("123456Fid");
		orderSmartLock.setDynaNum(10);
		orderSmartLock.setDynaExpiredTime(DateUtil.intervalDate(7, IntervalUnit.DAY));
		orderSmartLock.setCreateDate(new Date());
		orderSmartLock.setLastModifyDate(new Date());
		String resultJson = orderSmartLockServiceProxy.
				updateOrderSmartLockByFid(JsonEntityTransform.Object2Json(orderSmartLock));
		System.err.println(resultJson);
	}
	
	@Test
	public void updateOrderSmartLockByOrderSnTest(){
		OrderSmartLockEntity orderSmartLock = new OrderSmartLockEntity();
		orderSmartLock.setOrderSn("1605117Y6VG0CF213442");
		orderSmartLock.setDynaPswd("123456OrderSn");
		orderSmartLock.setDynaNum(10);
		orderSmartLock.setDynaExpiredTime(DateUtil.intervalDate(7, IntervalUnit.DAY));
		orderSmartLock.setCreateDate(new Date());
		orderSmartLock.setLastModifyDate(new Date());
		String resultJson = orderSmartLockServiceProxy.
				updateOrderSmartLockByOrderSn(JsonEntityTransform.Object2Json(orderSmartLock));
		System.err.println(resultJson);
	}
	
	@Test
	public void closeSmartlockPswdTest(){
		String orderSn = "160627NHJM81XN161209";
		String resultJson = orderSmartLockServiceProxy.closeSmartlockPswd(orderSn);
		System.err.println(resultJson);
	}

}
