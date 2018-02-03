package com.ziroom.minsu.services.cms.test.proxy;

import javax.annotation.Resource;

import org.junit.Test;

import com.ziroom.minsu.services.cms.proxy.JobActProxy;
import com.ziroom.minsu.services.cms.test.base.BaseTest;

public class JobActProxyTest extends BaseTest {
	
	@Resource(name = "cms.jobActProxy")
	private JobActProxy jobActProxy;
	
	
	@Test
	public void couponExpireStatusTest(){
		jobActProxy.couponExpireStatus();
	}

}
