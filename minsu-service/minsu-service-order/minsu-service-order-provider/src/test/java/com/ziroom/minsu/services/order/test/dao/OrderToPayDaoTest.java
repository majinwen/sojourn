package com.ziroom.minsu.services.order.test.dao;

import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.order.OrderPayEntity;
import com.ziroom.minsu.entity.order.OrderToPayEntity;
import com.ziroom.minsu.services.order.dao.OrderPayDao;
import com.ziroom.minsu.services.order.dao.OrderToPayDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>TODO</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/9/22.
 * @version 1.0
 * @since 1.0
 */
@ContextConfiguration(locations = { "classpath:test-applicationContext-order.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class OrderToPayDaoTest {


    @Resource(name = "order.toPayDao")
    private OrderToPayDao orderToPayDao;

    @Test
    public void insertToPay(){
        OrderToPayEntity toPayEntity = new OrderToPayEntity();
        toPayEntity.setBizCode("123");
        toPayEntity.setOrderSn("1231");
        toPayEntity.setPayCode("code");
        orderToPayDao.insertToPay(toPayEntity);
    }

    @Test
    public void selectByPayCode(){
        OrderToPayEntity oaeList = orderToPayDao.selectByPayCode("code");
        System.out.println("----------------------"+ JsonEntityTransform.Object2Json(oaeList));
    }

    @Test
    public void selectByOrderSn(){
        OrderToPayEntity oaeList = orderToPayDao.selectByOrderSn("1231");
        System.out.println("----------------------"+ JsonEntityTransform.Object2Json(oaeList));
    }


}
