package com.ziroom.minsu.services.order.test.proxy;

import javax.annotation.Resource;

import com.asura.framework.utils.LogUtil;
import org.junit.Test;

import com.ziroom.minsu.services.order.proxy.OrderPayServiceProxy;
import com.ziroom.minsu.services.order.test.base.BaseTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PayCalllBackProxyTest  extends BaseTest{

    /** log */
    private static final Logger LOGGER = LoggerFactory.getLogger(PayCalllBackProxyTest.class);
	
	@Resource(name = "order.orderPayServiceProxy")
	private OrderPayServiceProxy orderPayServiceProxy;
	
	
	@Test
	public void TestGetPayCode()  {

        String meString = "X0SJAYw1Kvhrdmc6VKVbwYST89QBMBtoYVU3kUZphdyIEUsONu5oGSu0mIlV%2F%2BAo1oJ2FlAr2iHcwqkQjwulLxIXjXMAFXxv2SjqMH%2BdgzD2o%2F249oCaczVrPp6IBz6ICjCqShwE9Ffp6K547hxHKEVNBXgrTYL5sjHds75NQdnDZAjxYlHeKt4utMdlMWOGryy2nA6%2FsQ84kVQTqgjestPoc3mK%2BF53h29BgS0%2BNXhKXxkpkVETc1AnyvUJ4AcJUAZk0O5DVTy9Z%2FrqtMHUEUQl4PCGYOFNxO0zQeIbn0MskJhtU%2BjOcIV1D5E%2BHHhSY40jzA8umr1KSbd8V4KGECY7ItcT8cdZE14%2FaJrdb8mgeiPiC6K5PFM9obE1UGfugsQboVP8bgAbbNvkWlKqOQ%3D%3D";
        String reString;
		try {
			reString = orderPayServiceProxy.payCallBack(meString, 1);
			System.out.println("-----------"+reString);
		} catch (Exception e) {
			// TODO Auto-generated catch block
            LogUtil.error(LOGGER, "e:{}", e);
		}
		
		
	}
}
