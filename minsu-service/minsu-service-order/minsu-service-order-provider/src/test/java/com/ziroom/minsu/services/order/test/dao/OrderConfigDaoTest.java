package com.ziroom.minsu.services.order.test.dao;

import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.order.OrderConfigEntity;
import com.ziroom.minsu.services.order.dao.OrderConfigDao;
import com.ziroom.minsu.services.order.test.base.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>订单的配置文件快照</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/3/31.
 * @version 1.0
 * @since 1.0
 */
public class OrderConfigDaoTest extends BaseTest {

    @Resource(name = "order.orderConfigDao")
    private OrderConfigDao orderConfigDao;

    @Test
    public void TestgetOrderConfigList() {
        List<OrderConfigEntity> list = orderConfigDao.getOrderConfigList();
        System.out.println(JsonEntityTransform.Object2Json(list));
    }


    @Test
    public void TestgetOrderConfigListByOrderSn() {

        List<OrderConfigEntity> list = orderConfigDao.getOrderConfigListByOrderSn("sc");
        System.out.println(JsonEntityTransform.Object2Json(list));
    }



    @Test
    public void TestinsertOrderConfigOrderSnIsNULL() {
        OrderConfigEntity config = new OrderConfigEntity();
        config.setConfigCode("cd");
        config.setConfigValue("value");
         orderConfigDao.insertOrderConfig(config);
    }


    @Test
    public void TestinsertOrderConfig() {
        OrderConfigEntity config = new OrderConfigEntity();
        config.setOrderSn("sc");
        config.setConfigCode("cd");
        config.setConfigValue("value");
        orderConfigDao.insertOrderConfig(config);
    }

}
