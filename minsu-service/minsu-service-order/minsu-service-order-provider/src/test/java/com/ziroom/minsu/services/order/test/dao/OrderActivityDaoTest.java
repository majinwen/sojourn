package com.ziroom.minsu.services.order.test.dao;

import java.util.*;

import javax.annotation.Resource;

import com.asura.framework.base.paging.PagingResult;
import com.ziroom.minsu.services.common.dto.PageRequest;
import com.ziroom.minsu.services.finance.entity.OrderActivityInfoVo;
import com.ziroom.minsu.services.order.dao.OrderActivityDao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.order.OrderActivityEntity;

@ContextConfiguration(locations = { "classpath:test-applicationContext-order.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class OrderActivityDaoTest {
	
    @Resource(name = "order.activityDao")
    private OrderActivityDao activityDao;




    @Test
    public void findCouponByOrderSn(){
        OrderActivityEntity ch = activityDao.findCouponByOrderSn("160615M78KJF37211510");
        System.out.println(JsonEntityTransform.Object2Json(ch));
    }

    @Test
    public void checkCouponOk(){
        boolean ch = activityDao.checkCouponOk("1242");
        System.out.println(ch);
    }

    @Test
	public void insertActivityResTest(){
		OrderActivityEntity oae = new OrderActivityEntity();
		oae.setAcFid(UUIDGenerator.hexUUID());
		oae.setOrderSn(UUIDGenerator.hexUUID());
		oae.setAcName("test");
		oae.setAcMoney(30);
		oae.setAcStatus(1);
		oae.setAcWeight(22);
		oae.setPreferentialSources(1);
		oae.setPreferentialUser(2);
		oae.setAcMoneyAll(30);
		activityDao.insertActivityRes(oae);
	}
    
    @Test
	public void findActivityByCondictionTest(){
    	Map<String,Object> par = new HashMap<String,Object>();
		par.put("orderSn","8a9e9cd253d0597c0153d0597c760001");
		par.put("acFid","8a9e9cd253d0597c0153d0597c760000");
		java.util.List<OrderActivityEntity> oaeList = activityDao.findActivityByCondiction(par);
	    System.out.println("----------------------"+JsonEntityTransform.Object2Json(oaeList));
    }

    @Test
	public void testlistOrderActByOrderSnAndType(){
		List<Integer> integers = Arrays.asList(2, 7);
		List<OrderActivityInfoVo> orderActivityInfoVos = activityDao.listOrderActByOrderSnAndType("1706074KQQCOJ4114207", integers);
		System.err.println(JsonEntityTransform.Object2Json(orderActivityInfoVos));
	}
    
    @Test
    public void testlistTodayCheckInOrderAndUseCouponPage() {
        PagingResult<OrderActivityEntity> orderActivityEntityPagingResult = activityDao.listTodayCheckInOrderAndUseCouponPage(new PageRequest());

    }
    
    @Test
    public void testfindOrderAcByOrderSn() {
    	List<OrderActivityEntity> findOrderAcByOrderSn = activityDao.findOrderAcByOrderSn("161223FM7F702Z134615");
    	System.out.println(findOrderAcByOrderSn);
    }
}
