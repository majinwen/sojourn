package com.ziroom.minsu.services.order.test.dao;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.order.OrderRemarkEntity;
import com.ziroom.minsu.services.order.dao.OrderRemarkDao;
import com.ziroom.minsu.services.order.test.base.BaseTest;

/**
 * 
 * <p>
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liyingjie on 2016年6月23日
 * @since 1.0
 * @version 1.0
 */
public class OrderRemarkDaoTest extends BaseTest {

	@Resource(name = "order.orderRemarkDao")
	private OrderRemarkDao orderRemarkDao;
	
	@Test
	public void countRemarkByOrderSn(){
		Long l = orderRemarkDao.countRemarkByOrderSn("160620L96R4O2P164107","uid");
		System.err.println("----------------"+l);
	}
	
	@Test
	public void insertOrderRemarkTest(){
		OrderRemarkEntity remarkEntity = new OrderRemarkEntity();
		remarkEntity.setRemarkContent("测试测试");
		remarkEntity.setFid(UUIDGenerator.hexUUID());
		remarkEntity.setOrderSn("hhhhhhhhhhhhmmmmmm");
		orderRemarkDao.insertRemark(remarkEntity);
	}

	@Test
	public void taskGetListTest() {
		List<OrderRemarkEntity>  resList = orderRemarkDao.getOrderInfoListByCondiction("hhhhhhhhhhhhmmmmmm","uid");
		System.out.println("ttttttt"+JsonEntityTransform.Object2Json(resList));
    }

}
