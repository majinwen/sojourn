/**
 * @FileName: EhrAccountSyncServiceTest.java
 * @Package com.ziroom.minsu.services.basedata.api.inner
 * 
 * @author jixd
 * @created 2016年5月19日 上午4:09:43
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.basedata.test.api.inner;

import com.ziroom.minsu.services.basedata.proxy.EhrAccountSyncServiceProxy;
import com.ziroom.minsu.services.basedata.test.base.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * <p>TODO</p>
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
public class EhrAccountSyncServiceTest extends BaseTest {
	
	 @Resource(name = "basedata.ehrAccountSyncServiceProxy")
	 private EhrAccountSyncServiceProxy ehrAccountSyncServiceProxy;
	 	
 	 @Test
	 public void testSync(){
		 ehrAccountSyncServiceProxy.syncEmployeeTask(1);
	 }
	 
 	 
// 	 @Test
// 	 public void doEmployeeSyncTest(){
// 		 ehrAccountSyncServiceProxy.doEmployeeSync("2017-02-07", "2017-02-08");
// 	 }
}
