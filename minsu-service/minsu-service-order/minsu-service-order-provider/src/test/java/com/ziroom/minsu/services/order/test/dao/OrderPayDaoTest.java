package com.ziroom.minsu.services.order.test.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.order.OrderPayEntity;
import com.ziroom.minsu.services.common.vo.HouseStatsVo;
import com.ziroom.minsu.services.order.dao.OrderPayDao;

@ContextConfiguration(locations = { "classpath:META-INF/spring/applicationContext-order.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class OrderPayDaoTest {
	
    @Resource(name = "order.payDao")
    private OrderPayDao payDao;
	
    @Test
	public void insertOrderPayResTest(){
		OrderPayEntity ope = new OrderPayEntity();
		ope.setFid(UUIDGenerator.hexUUID());
		ope.setOrderSn("8a9e9cd253d0597c0153d0597c760001");
		ope.setPayType(1);
		ope.setPayMoney(45);
		ope.setPayStatus(1);
		ope.setTradeNo(UUIDGenerator.hexUUID());
		payDao.insertOrderPayRes(ope);
	}
    
    @Test
	public void findOrderPayByCondictionTest(){
    	Map<String,Object> paramMap = new HashMap<String,Object>();
    	paramMap.put("orderSn","8a9e9cd253d0597c0153d0597c760001");
    	List<OrderPayEntity> oaeList = payDao.findOrderPayByCondiction(paramMap);
	    System.out.println("----------------------"+JsonEntityTransform.Object2Json(oaeList));
    }
    
    @Test
	public void updateOrderPayByCondictionTest(){
    	OrderPayEntity ope = new OrderPayEntity();
    	ope.setOrderSn("8a9e9cd253d0597c0153d0597c760001");
    	//ope.setFid("8a9e9cd253d0866f0153d0866f1f0000");
    	ope.setPayMoney(80);
    	payDao.updateOrderPayByCondiction(ope);
	}
    
    @Test
    public void queryTradeNumByHouseFidTest(){
    	Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("startTime", "2016-10-01");
		paramMap.put("endTime", "2016-11-30");
		List<HouseStatsVo> list = this.payDao.queryTradeNumByHouseFid(paramMap);
		System.err.println(JsonEntityTransform.Object2Json(list));
    }
    
}
