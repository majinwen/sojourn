/**
 * @FileName: EhrAccountSyncServiceProxyTest.java
 * @Package com.ziroom.minsu.services.basedata.proxy
 * 
 * @author jixd
 * @created 2016年4月27日 下午1:38:11
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.basedata.test.proxy;

import com.ziroom.minsu.services.basedata.proxy.EhrAccountSyncServiceProxy;
import com.ziroom.minsu.services.basedata.test.base.BaseTest;

import org.junit.Test;

import javax.annotation.Resource;

/**
 * <p>账户同步测试</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author jixd
 * @since 1.0
 * @version 1.0
 */
public class EhrAccountSyncServiceProxyTest extends BaseTest {
	
	@Resource(name = "basedata.ehrAccountSyncServiceProxy")
	private EhrAccountSyncServiceProxy ehrAccountSyncServiceProxy;
	
	@Test
	public void testTaskSync() throws InterruptedException{
		
		ehrAccountSyncServiceProxy.syncEmployeeTask(20);
		Thread.sleep(100000000000l);
	}
	
	@Test
	public void testSingleSync(){
		ehrAccountSyncServiceProxy.syncSingleEmployee("20333715");
	}
	
}
