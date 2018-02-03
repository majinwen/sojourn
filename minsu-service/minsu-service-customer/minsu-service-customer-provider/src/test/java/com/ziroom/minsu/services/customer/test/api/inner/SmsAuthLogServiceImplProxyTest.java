/**
 * @FileName: SmsAuthLogServiceImplProxyTest.java
 * @Package com.ziroom.minsu.services.customer.test.api.inner
 * 
 * @author bushujie
 * @created 2016年4月22日 下午12:00:19
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.customer.test.api.inner;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.customer.SmsAuthLogEntity;
import com.ziroom.minsu.services.customer.dto.ContactAuthDto;
import com.ziroom.minsu.services.customer.dto.IdentityAuthDto;
import com.ziroom.minsu.services.customer.proxy.CustomerAuthServiceProxy;
import com.ziroom.minsu.services.customer.test.BaseTest;

/**
 * <p>TODO</p>
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
public class SmsAuthLogServiceImplProxyTest  extends BaseTest{
	
	@Resource(name="customer.customerAuthServiceProxy")
	private CustomerAuthServiceProxy customerAuthServiceProxy;
	
	
	@Test
	public void saveSmsAuthLogTest(){
		SmsAuthLogEntity smsAuthLogEntity=new SmsAuthLogEntity();
		smsAuthLogEntity.setMobileNo("15811236523");
		smsAuthLogEntity.setAuthCode("1234576");
		String resultJson=customerAuthServiceProxy.saveSmsAuthLog(JsonEntityTransform.Object2Json(smsAuthLogEntity));
		System.err.println(resultJson);
	}
	
	@Test
	public void customerMobileAuthTest(){
		ContactAuthDto contactAuthDto=new ContactAuthDto();
		contactAuthDto.setMobileNo("15811236524");
		contactAuthDto.setAuthCode("1234576");
		contactAuthDto.setUid("8a9e9a9e543d23f901543d23f9e90000");
		String resultJson=customerAuthServiceProxy.customerContactAuth(JsonEntityTransform.Object2Json(contactAuthDto));
		System.err.println(resultJson);
	}
	
	@Test
	public void customerIdentityAuthTest(){
		IdentityAuthDto identityAuthDto=new IdentityAuthDto();
		identityAuthDto.setRealName("王雷雷");
		identityAuthDto.setIdType(0);
		identityAuthDto.setIdNo("130525198310037288");
		identityAuthDto.setUid("8a9e9a9e543d23f901543d23f9e90000");
		String resultJson=customerAuthServiceProxy.customerIdentityAuth(JsonEntityTransform.Object2Json(identityAuthDto));
		System.err.println(resultJson);
	}
	
}
