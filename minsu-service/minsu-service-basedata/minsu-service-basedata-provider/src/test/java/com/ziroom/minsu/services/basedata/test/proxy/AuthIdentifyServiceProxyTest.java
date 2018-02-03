/**
 * @FileName: AuthIdentifyServiceProxyTest.java
 * @Package com.ziroom.minsu.services.basedata.test.proxy
 * 
 * @author lunan
 * @created 2017年8月31日 下午3:04:58
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.basedata.test.proxy;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.base.AuthIdentifyEntity;
import com.ziroom.minsu.services.basedata.proxy.AuthIdentifyServiceProxy;
import com.ziroom.minsu.services.basedata.test.base.BaseTest;

/**
 * <p>TODO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author lunan
 * @since 1.0
 * @version 1.0
 */
public class AuthIdentifyServiceProxyTest extends BaseTest{

	@Resource(name="basedata.authIdentifyServiceProxy")
	private AuthIdentifyServiceProxy authIdentifyServiceProxy;
	
	@Test
	public void testgetAuthIdentifyByCode(){
		AuthIdentifyEntity authIdentify = new AuthIdentifyEntity();
		authIdentify.setCode("ZIROOM_CHANGZU_IM");
		String authIdentifyByCode = authIdentifyServiceProxy.getAuthIdentifyByCode(JsonEntityTransform.Object2Json(authIdentify));
		System.out.println(authIdentifyByCode);
	}
}
