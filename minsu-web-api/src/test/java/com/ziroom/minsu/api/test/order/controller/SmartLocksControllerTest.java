/**
 * @FileName: SmartLocksControllerTest.java
 * @Package com.ziroom.minsu.api.test.order.controller
 * 
 * @author yd
 * @created 2016年6月24日 下午4:49:49
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.api.test.order.controller;

import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MD5Util;
import com.ziroom.minsu.api.common.encrypt.EncryptFactory;
import com.ziroom.minsu.api.common.encrypt.IEncrypt;
import com.ziroom.minsu.api.order.dto.SmartLockApi;

/**
 * <p>智能锁接口测试</p>
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
public class SmartLocksControllerTest {

	private static 	IEncrypt iEncrypt=EncryptFactory.createEncryption("DES");

	/**
	 * 
	 * 获取用户智能锁
	 *
	 * @author yd
	 * @created 2016年6月24日 下午4:50:26
	 *
	 */
	public static void getUserSmartLocksTest() {

		
		SmartLockApi martLockApi = new SmartLockApi();
		String orderSn = "160511252A9834163856";
		martLockApi.setOrderSn(orderSn);
		System.err.println(JsonEntityTransform.Object2Json(martLockApi));
		String paramJson=iEncrypt.encrypt(JsonEntityTransform.Object2Json(martLockApi));
		System.err.println("2y5QfvAy="+paramJson);
		System.err.println("hPtJ39Xs="+MD5Util.MD5Encode(JsonEntityTransform.Object2Json(martLockApi).toString(),"UTF-8"));
	}
	
	public static void main(String[] args) {
		getUserSmartLocksTest();
		
	}

}
