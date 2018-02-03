package com.ziroom.minsu.services.order.test.service;

import java.util.List;

import javax.annotation.Resource;

import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.order.service.OrderUserServiceImpl;
import org.junit.Test;

import com.ziroom.minsu.entity.order.OrderEntity;
import com.ziroom.minsu.services.order.dao.OrderDao;
import com.ziroom.minsu.services.order.entity.OrderConfigVo;
import com.ziroom.minsu.services.order.entity.OrderDayPriceVo;
import com.ziroom.minsu.services.order.service.OrderConfigServiceImpl;
import com.ziroom.minsu.services.order.service.OrderMoneyServiceImpl;
import com.ziroom.minsu.services.order.service.PayVouchersCreateServiceImpl;
import com.ziroom.minsu.services.order.test.base.BaseTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PayVouchersCreateServiceImplTest extends BaseTest {

    /** log */
    private static final Logger LOGGER = LoggerFactory.getLogger(PayVouchersCreateServiceImplTest.class);

	@Resource(name = "order.payVouchersCreateServiceImpl")
	private PayVouchersCreateServiceImpl payVouchersCreateService;

	@Resource(name = "order.orderConfigServiceImpl")
	private OrderConfigServiceImpl orderConfigService;

	@Resource(name = "order.orderMoneyServiceImpl")
	private OrderMoneyServiceImpl orderMoneyService;

	@Resource(name = "order.orderUserServiceImpl")
	private OrderUserServiceImpl orderUserService;

	@Resource(name = "order.orderDao")
	private OrderDao orderDao;

	@Test
	public void TestCreateFinance() {
		try {
			String orderSn = "161220682W76Q4211121";
			OrderEntity orderEntity = orderDao.getOrderInfoByOrderSn(orderSn);
			// 获取订单配置：优惠折扣、佣金、结算方式
			OrderConfigVo orderConfigVo = orderConfigService.getOrderConfigVo(orderEntity.getOrderSn());
			// 获取订单每天的价格
			List<OrderDayPriceVo> dayPrice = orderUserService.getDayPrices(orderEntity);
			payVouchersCreateService.createFinance(orderEntity, orderConfigVo, dayPrice);
		} catch (Exception e) {
            LogUtil.error(LOGGER,"e:{}",e);
		}
	}

}
