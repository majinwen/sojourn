/**
 * @FileName: LoginServiceProxyTest.java
 * @Package com.ziroom.minsu.services.basedata.test.proxy
 * 
 * @author yd
 * @created 2016年11月1日 上午10:04:32
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.basedata.test.proxy;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.services.basedata.proxy.LoginServiceProxy;
import com.ziroom.minsu.services.basedata.test.base.BaseTest;

/**
 * <p>登录代理 测试</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
public class LoginServiceProxyTest extends BaseTest{

	
	@Resource(name="basedata.loginServiceProxy")
	private LoginServiceProxy loginServiceProxy;
	
	@Test
	public void checkAuthMenuByResurlTest() {
		
		String resUrl = "www.baidu.com";
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.loginServiceProxy.checkAuthMenuByResurl(resUrl));
		System.out.println(dto);
	}

}
