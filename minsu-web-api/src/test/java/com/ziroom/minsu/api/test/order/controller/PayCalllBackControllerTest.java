package com.ziroom.minsu.api.test.order.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.asura.framework.utils.LogUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MD5Util;
import com.ziroom.minsu.api.common.encrypt.EncryptFactory;
import com.ziroom.minsu.api.common.encrypt.IEncrypt;
import com.ziroom.minsu.api.order.dto.ToPayApiDto;
import com.ziroom.minsu.api.order.service.CallPayService;
import com.ziroom.minsu.services.common.utils.CryptAES;
import com.ziroom.minsu.services.order.api.inner.OrderPayService;
import com.ziroom.minsu.services.order.dto.ToPayRequest;
import com.ziroom.minsu.services.order.entity.PayVo;
import com.ziroom.minsu.valenum.pay.ToPayTypeEnum;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:spring-config.xml"})
public class PayCalllBackControllerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(PayCalllBackControllerTest.class);

	@Resource(name = "order.orderPayService")
	private OrderPayService orderPayService;
	
	@Test
	public void TestGetPayCode()  {
		
		String meString = "f2a4UZGtgY5JBWxcJyiGqzp8q2tMBd02xqtxgaDjh1R9oJPI%2F2JaJjg0FVXzEWIluoal%2B8Hdq3N7Cd2MthyKusWhbvydRP1kmAceNcvqCoy7mVqLP%2Bg8YpTXwnfk1FaPp6%2Fr5D2lym7QKLoPnAM7rC8BUlNBWE%2Fo5ODlCcoUuLSFlsKvWw0qB%2BTb3YlkI0tkN9xjQ35hYtO2gShfod3T7bwcJ0dw82mKR6vWGu69GpZQnGJEh4j%2FNIqGD7dTwAOrS02fI4JSFZzYHl4S%2FnrE7hqtJIL5u543E4P6z0HQqkbHVx7%2Fe7ZPPE4UTL9odwhVur0X9dMOb9pZtd5jW1uVWK5VPW1uD48YbeUANv1w6QEIJz361stTtFZ1SaUIF6T4wsnpICs3sIj4B%2BiqMYZJHA%3D%3D";
		String newEncryption;
		try {
			newEncryption = URLDecoder.decode(meString, "UTF-8");
			String payBackJson = CryptAES.AES_Decrypt("k9t58qwgal02iy19", newEncryption);
			System.out.println("-----------"+payBackJson);
		} catch (UnsupportedEncodingException e) {
            LogUtil.error(LOGGER,"e:{}",e);
		}
		
		String reString = orderPayService.payCallBack(URLEncoder.encode(meString), 1);
		System.out.println("-----------"+reString);
		
	}
}
