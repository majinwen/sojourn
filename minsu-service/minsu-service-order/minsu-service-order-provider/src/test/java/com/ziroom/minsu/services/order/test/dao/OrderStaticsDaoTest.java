package com.ziroom.minsu.services.order.test.dao;

import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.services.order.dao.OrderStaticsDao;
import com.ziroom.minsu.services.order.dto.OrderLandlordStaticsDto;
import com.ziroom.minsu.services.order.test.base.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * <p>订单类测测试</p>
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
public class OrderStaticsDaoTest extends BaseTest {


    @Resource(name = "order.orderStaticsDao")
    private OrderStaticsDao orderStaticsDao;




	@Test
	public void staticsLandOrderCountInfo() {
		OrderLandlordStaticsDto infoVo = orderStaticsDao.staticsLandOrderCountInfo("8a9e9aba549e703301549e70qwed0000");
		System.out.println(JsonEntityTransform.Object2Json(infoVo));
	}


}
