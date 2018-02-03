package com.ziroom.minsu.services.order.test.proxy;

import javax.annotation.Resource;

import org.junit.Test;

import com.ziroom.minsu.services.order.proxy.OrderTaskAccountServiceProxy;
import com.ziroom.minsu.services.order.test.base.BaseTest;

/**
 * <p>
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author lishaochuan on 2016年5月4日
 * @since 1.0
 * @version 1.0
 */
public class OrderTaskAccountServiceProxyTest extends BaseTest {

	@Resource(name = "order.orderTaskAccountServiceProxy")
	private OrderTaskAccountServiceProxy orderTaskAccountServiceProxy;

	@Test
	public void repeatAccountFillFailedTest() {
		orderTaskAccountServiceProxy.repeatAccountFillFailed();
		try {
			Thread.sleep(99999999);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
