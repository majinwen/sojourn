/**
 * @FileName: OrderCsrCancleDaoTest.java
 * @Package com.ziroom.minsu.services.order.test.dao
 * 
 * @author loushuai
 * @created 2017年6月2日 上午9:01:08
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.order.test.dao;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.ziroom.minsu.entity.order.OrderContactEntity;
import com.ziroom.minsu.entity.order.OrderCsrCancleEntity;
import com.ziroom.minsu.services.order.dao.OrderCsrCancleDao;
import com.ziroom.minsu.services.order.test.base.BaseTest;



/**
 * <p>TODO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author loushuai
 * @since 1.0
 * @version 1.0
 */
public class OrderCsrCancleDaoTest extends BaseTest {

	 @Resource(name = "order.orderCsrCancleDao")
     private OrderCsrCancleDao orderCsrCancleDao;
	 
	  @Test
      public void testgetDoFailLandQXOrderPunish() {
              List<OrderCsrCancleEntity> doFailLandQXOrderPunish = orderCsrCancleDao.getDoFailLandQXOrderPunish(38, 1, 10);
              System.out.println(doFailLandQXOrderPunish);
      }
	  
	  @Test
      public void testgetupdateOrderCsrCancle() {
           int updateOrderCsrCancle = orderCsrCancleDao.updateOrderCsrCancle("1705153PE5TKU1170447", 11);
              System.out.println(updateOrderCsrCancle);
      }
	  
	  @Test
      public void testgetCountInTimes() {
           long updateOrderCsrCancle = orderCsrCancleDao.getCountInTimes("a395ada9-1bf8-0405-f0cc-8baf635ba661", null, null);
              System.out.println(updateOrderCsrCancle);
      }
}
