package com.ziroom.minsu.services.order.test.dao;

import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.order.OrderSpecialPriceEntity;
import com.ziroom.minsu.services.order.dao.OrderSpecialPriceDao;
import com.ziroom.minsu.services.order.test.base.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>订单的特殊价格</p>
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
public class OrderSpecialPriceDaoTest extends BaseTest {

    @Resource(name = "order.orderSpecialPriceDao")
    private OrderSpecialPriceDao orderSpecialPriceDao;

    @Test
    public void getOrderSpecialPriceMapByOrderSn() {
        Map<String,Integer> list = orderSpecialPriceDao.getOrderSpecialPriceMapByOrderSn("170303U08F716T164615",100);
        System.out.println(JsonEntityTransform.Object2Json(list));
    }

    @Test
    public void TestgetOrderConfigList() {
        List<OrderSpecialPriceEntity> list = orderSpecialPriceDao.getOrderSpecialPriceList();
        System.out.println(JsonEntityTransform.Object2Json(list));
    }


    @Test
    public void TestgetOrderConfigListByOrderSn() {

        List<OrderSpecialPriceEntity> list = orderSpecialPriceDao.getOrderSpecialPriceByOrderSn("sc");
        System.out.println(JsonEntityTransform.Object2Json(list));
    }

    @Test
    public void TestinsertOrderSpecialPriceOrderSnIsNULL() {
        OrderSpecialPriceEntity price = new OrderSpecialPriceEntity();
        price.setPriceDate("20150235");
        price.setPriceValue(123);
        orderSpecialPriceDao.insertOrderSpecialPrice(price);
    }


    @Test
    public void TestinsertOrderSpecialPrice() {
        OrderSpecialPriceEntity price = new OrderSpecialPriceEntity();
        price.setOrderSn("sn");
        Date now = new Date();
        price.setPriceDate(DateUtil.dateFormat(now));
        price.setPriceValue(123);
        orderSpecialPriceDao.insertOrderSpecialPrice(price);
    }

    @Test
    public void TestinsertOrderSpecialPricePrice() {
        OrderSpecialPriceEntity price = new OrderSpecialPriceEntity();
        price.setOrderSn("sn");
        Date now = new Date();
        price.setPriceDate(DateUtil.dateFormat(now));
        price.setPriceValue(0);
        orderSpecialPriceDao.insertOrderSpecialPrice(price);
    }

}
