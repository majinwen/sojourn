/**
 * @FileName: CustomerInfoPcServiceProxyTest.java
 * @Package com.ziroom.minsu.services.customer.test.proxy
 * 
 * @author bushujie
 * @created 2016年7月26日 下午9:00:59
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.customer.test.proxy;

import javassist.runtime.DotClass;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.services.customer.dto.CustomerBaseExtDto;
import com.ziroom.minsu.services.customer.proxy.CustomerInfoPcServiceProxy;
import com.ziroom.minsu.services.customer.test.BaseTest;

/**
 * <p>pc客户相关接口测试</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
public class CustomerInfoPcServiceProxyTest extends BaseTest {
	
	@Resource(name="customer.customerInfoPcServiceProxy")
	private CustomerInfoPcServiceProxy customerInfoPcServiceProxy;
	
	@Test
	public void initCustomerAuthDataTest(){
		String resultJson=customerInfoPcServiceProxy.initCustomerAuthData("f877a191-4434-423e-9de7-39816ffc6e54");
		System.err.println(resultJson);
	}
	@Test
	public void updatePersonDataTest(){
		CustomerBaseExtDto dto = new CustomerBaseExtDto();
		dto.setCustomerIntroduce("2017-8-10测试2017-8-10测试2017-8-10测试2017-8-10测试2017-8-10测试2017-8-10测试2017-8-10测试2017-8-10测试2017-8-10测试2017-8-10测试2017-8-10测试2017-8-10测试2017-8-10测试2017-8-10测试");
		dto.setNickName("帅哥");
		dto.setUid("52a4aea1-5527-7421-1b25-83fbca1c1856");
		String resultJson=customerInfoPcServiceProxy.updatePersonData(JsonEntityTransform.Object2Json(dto));
		System.err.println(resultJson);
	}
}
