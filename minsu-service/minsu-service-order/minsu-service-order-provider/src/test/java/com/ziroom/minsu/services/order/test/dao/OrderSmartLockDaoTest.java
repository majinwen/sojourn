package com.ziroom.minsu.services.order.test.dao;


import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.DateUtil.IntervalUnit;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.order.OrderSmartLockEntity;
import com.ziroom.minsu.services.order.dao.OrderSmartLockDao;
import com.ziroom.minsu.services.order.test.base.BaseTest;


/**
 * 
 * <p>订单智能锁测试类</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liujun
 * @since 1.0
 * @version 1.0
 */
public class OrderSmartLockDaoTest extends BaseTest {


    @Resource(name = "order.orderSmartLockDao")
    private OrderSmartLockDao orderSmartLockDao;

	@Test
	public void insertOrderSmartLockTest(){
		OrderSmartLockEntity orderSmartLock = new OrderSmartLockEntity();
		orderSmartLock.setFid(UUIDGenerator.hexUUID());
		orderSmartLock.setOrderSn("16051176ZD432U160931");
		orderSmartLock.setTempPswd("654321");
		orderSmartLock.setTempStartTime(DateUtil.intervalDate(-1, IntervalUnit.DAY));
		orderSmartLock.setDynaPswd("123456");
		orderSmartLock.setDynaNum(10);
		orderSmartLock.setDynaExpiredTime(DateUtil.intervalDate(7, IntervalUnit.DAY));
		orderSmartLock.setCreateDate(new Date());
		orderSmartLock.setLastModifyDate(new Date());
		int upLine = orderSmartLockDao.insertOrderSmartLock(orderSmartLock);
		System.err.println(upLine);
    }
	
	@Test
	public void findOrderSmartLockByFidTest(){
		String fid = "8a9e9ab1557d18f501557d18f5210000";
		OrderSmartLockEntity orderSmartLock = orderSmartLockDao.findOrderSmartLockByFid(fid);
		System.err.println(JsonEntityTransform.Object2Json(orderSmartLock));
	}
	
	@Test
	public void findOrderSmartLockByOrderSnTest(){
		String orderSn = "16051176ZD432U160931";
		OrderSmartLockEntity orderSmartLock = orderSmartLockDao.findOrderSmartLockByOrderSn(orderSn);
		System.err.println(JsonEntityTransform.Object2Json(orderSmartLock));
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
		int upLine = orderSmartLockDao.updateOrderSmartLock(orderSmartLock);
		System.err.println(upLine);
	}
	
	@Test
	public void updateOrderSmartLockByOrderSnTest(){
		OrderSmartLockEntity orderSmartLock = new OrderSmartLockEntity();
		orderSmartLock.setOrderSn("16051176ZD432U160931");
		orderSmartLock.setServiceId("222222");
		orderSmartLock.setPasswordId("orderSn");
		orderSmartLock.setTempPswd("654321OrderSn");
		orderSmartLock.setTempStartTime(DateUtil.intervalDate(-1, IntervalUnit.DAY));
		orderSmartLock.setTempExpiredTime(DateUtil.intervalDate(6, IntervalUnit.DAY));
		orderSmartLock.setTempPswdStatus(2);
		orderSmartLock.setDynaPswd("123456OrderSn");
		orderSmartLock.setDynaNum(10);
		orderSmartLock.setDynaExpiredTime(DateUtil.intervalDate(7, IntervalUnit.DAY));
		orderSmartLock.setCreateDate(new Date());
		orderSmartLock.setLastModifyDate(new Date());
		int upLine = orderSmartLockDao.updateOrderSmartLockByOrderSn(orderSmartLock);
		System.err.println(upLine);
	}

}

