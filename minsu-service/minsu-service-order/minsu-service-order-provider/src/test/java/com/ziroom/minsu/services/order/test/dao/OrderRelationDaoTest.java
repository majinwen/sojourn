/**
 * @FileName: OrderRelationDaoTest.java
 * @Package com.ziroom.minsu.services.order.dao
 * 
 * @author yd
 * @created 2016年4月25日 下午3:34:36
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.order.test.dao;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.paging.PagingResult;
import com.ziroom.minsu.entity.order.OrderRelationEntity;
import com.ziroom.minsu.services.order.dao.OrderRelationDao;
import com.ziroom.minsu.services.order.test.base.BaseTest;
import com.ziroom.minsu.services.order.dto.OrderRelationRequest;
import com.ziroom.minsu.services.order.entity.OrderRelationVo;
import com.ziroom.minsu.valenum.order.CheckedStatusEnum;

/**
 * <p>新旧订单关联持久化测试</p>
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
public class OrderRelationDaoTest extends BaseTest{

	
	@Resource(name = "order.orderRelationDao")
	private  OrderRelationDao orderRelationDao;
	
	
	@Test
	public void queryOrderRelanionByPageTest(){
		
		OrderRelationRequest orderRelationRequest = new OrderRelationRequest();
		PagingResult<OrderRelationVo> listPagingResult = this.orderRelationDao.queryOrderRelanionByPage(orderRelationRequest);
		
		List<OrderRelationVo> listOrderRelationVos = listPagingResult.getRows();
		System.out.println(listOrderRelationVos);
	}
	
	@Test
	public void insertTest() {
		
		OrderRelationEntity orderRelationEntity = new OrderRelationEntity();
		orderRelationEntity.setAuditPersonFid("fdsfdsfdsfdfd");
		orderRelationEntity.setCheckedStatus(CheckedStatusEnum.WAITTING_CHECKED.getCode());
		orderRelationEntity.setCreatedFid("56ds5f6+d5sf");
		orderRelationEntity.setCreatedTime(new Date());
		orderRelationEntity.setLastModifyDate(new Date());
		orderRelationEntity.setNewOrderSn("4f5ds4f65sd4f56ds4f56s4df");
		orderRelationEntity.setOldOrderSn("160419V6621S1J172304");
		orderRelationEntity.setOrderBalance(85);
		
		int index = orderRelationDao.insert(orderRelationEntity);
		
		System.out.println(index);
	}
	
	@Test
	public void updateByConditionTest(){
		OrderRelationEntity orderRelationEntity = new OrderRelationEntity();
		orderRelationEntity.setCheckedStatus(CheckedStatusEnum.HAD_REFUSED.getCode());
		orderRelationEntity.setLastModifyDate(new Date());
		orderRelationEntity.setFid("8a9e9cb3544c71a601544c71a69f0000");
		
		int index = orderRelationDao.updateByCondition(orderRelationEntity);
		
		System.out.println(index);
	}

}
