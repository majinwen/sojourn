package com.ziroom.minsu.services.order.test.dao;

import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.order.OrderMoneyEntity;
import com.ziroom.minsu.services.order.dao.OrderMoneyDao;
import com.ziroom.minsu.services.order.test.base.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>订单金额测试</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/8.
 * @version 1.0
 * @since 1.0
 */
public class OrderMoneyDaoTest extends BaseTest {


    @Resource(name = "order.orderMoneyDao")
    private OrderMoneyDao orderMoneyDao;

    @Test
    public void TestGetCityList() {
        List<OrderMoneyEntity> list = orderMoneyDao.getOrderMoneyList();
        System.out.println(JsonEntityTransform.Object2Json(list));
    }




    @Test
    public void TestgetOrderMoneyByOrderSn() {
        OrderMoneyEntity orderMoneyEntity = orderMoneyDao.getOrderMoneyByOrderSn("16042157HI7218182613");
        System.out.println(JsonEntityTransform.Object2Json(orderMoneyEntity));
    }

    @Test
    public void TestinsertOrderMoney() {
        OrderMoneyEntity orderMoneyEntity = new OrderMoneyEntity();
        orderMoneyEntity.setOrderSn("sn");
        orderMoneyEntity.setCouponMoney(1);
        orderMoneyEntity.setDepositMoney(1);
        orderMoneyEntity.setDiscountMoney(1);
        orderMoneyEntity.setInsuranceMoney(1);
        orderMoneyEntity.setInsuranceType(2);
        orderMoneyEntity.setNeedPay(1);
        orderMoneyEntity.setOtherMoney(1);
        orderMoneyEntity.setPayMoney(1);
        orderMoneyEntity.setRealMoney(1);
        orderMoneyEntity.setRentalMoney(1);
        orderMoneyEntity.setSumMoney(111);
        orderMoneyDao.insertOrderMoney(orderMoneyEntity);
    }
}
