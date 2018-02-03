package com.ziroom.minsu.services.order.test.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.ziroom.minsu.services.order.entity.OrderInfoVo;
import com.ziroom.minsu.services.order.service.OrderCommonServiceImpl;
import com.ziroom.minsu.services.order.service.OrderMoneyServiceImpl;
import com.ziroom.minsu.services.order.service.OrderUserServiceImpl;
import org.junit.Test;

import com.asura.framework.base.util.DateUtil;
import com.ziroom.minsu.entity.order.OrderEntity;
import com.ziroom.minsu.services.order.test.base.BaseTest;
import com.ziroom.minsu.services.order.entity.OrderDayPriceVo;

/**
 * <p>订单金额的测试</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/2.
 * @version 1.0
 * @since 1.0
 */
public class OrderMoneyServiceImplTest extends BaseTest{

    @Resource(name = "order.orderMoneyServiceImpl")
    private OrderMoneyServiceImpl orderMoneyService;


    @Resource(name = "order.orderCommonServiceImpl")
    private OrderCommonServiceImpl orderCommonService;


    @Resource(name = "order.orderUserServiceImpl")
    private OrderUserServiceImpl orderUserService;

    @Test
    public void TestgetCouponUseTime() throws Exception{
        OrderInfoVo order =orderCommonService.getOrderInfoByOrderSn("16042847E2OYWL212522");
        Date date = orderMoneyService.setPaymentRunTime(order);
        System.out.println(date);
    }





    @Test
    public void TestGetDayPrices() throws Exception{
    	OrderEntity order = new OrderEntity();
    	order.setOrderSn("8a9e9cd253d0b29d0153d0b29d460001");
    	
    	Date start = DateUtil.parseDate("2016-04-01", "yyyy-MM-dd");
        Date end = DateUtil.parseDate("2016-04-02", "yyyy-MM-dd");
        
    	order.setStartTime(start);
    	order.setEndTime(end);
    	
    	List<OrderDayPriceVo> dayPrice = orderUserService.getDayPrices(order);
    }

}
