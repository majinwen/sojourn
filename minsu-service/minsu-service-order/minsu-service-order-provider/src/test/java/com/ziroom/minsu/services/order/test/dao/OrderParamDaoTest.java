package com.ziroom.minsu.services.order.test.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.ziroom.minsu.services.order.dao.OrderParamDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.order.OrderActivityEntity;
import com.ziroom.minsu.entity.order.OrderParamEntity;

@ContextConfiguration(locations = { "classpath:META-INF/spring/applicationContext-order.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class OrderParamDaoTest {
	
    @Resource(name = "order.paramDao")
    private OrderParamDao paramDao;
	
    @Test
	public void insertParamResTest(){
		OrderParamEntity ope = new OrderParamEntity();
		ope.setFid(UUIDGenerator.hexUUID());
		ope.setOrderSn("8a9e9cd253d0597c0153d0597c760001");
		ope.setParCode("test");
		ope.setParValue("test");
		paramDao.insertParamRes(ope);;
	}
    
    @Test
	public void findActivityByCondictionTest(){
    	Map<String,Object> paramMap = new HashMap<String,Object>();
    	paramMap.put("orderSn","8a9e9cd253d0597c0153d0597c760001");
    	
		List<OrderParamEntity> oaeList = paramDao.findParamByCondiction(paramMap);
	    System.out.println("----------------------"+JsonEntityTransform.Object2Json(oaeList));
    }
}
