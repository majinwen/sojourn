/**
 * @FileName: SmartLockTest.java
 * @Package com.ziroom.minsu.services.house.test.smartlock
 * 
 * @author jixd
 * @created 2016年6月23日 下午2:22:07
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.test.smartlock;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.util.JsonEntityTransform;
import com.google.gson.JsonObject;
import com.ziroom.minsu.services.common.utils.CloseableHttpUtil;
import com.ziroom.minsu.services.house.smartlock.enumvalue.PasswordTypeEnum;
import com.ziroom.minsu.services.house.smartlock.param.AUSLParam;
import com.ziroom.minsu.services.house.smartlock.param.PermissionTimeParam;
import com.ziroom.minsu.services.house.smartlock.vo.AddPassSLVo;
import com.ziroom.minsu.services.house.test.base.BaseTest;

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
public class SmartLockTest extends BaseTest{
	
	@Test
	public void testAddPass(){
		String url = "http://10.16.24.78:3000/v1/add_password";
		/*AUSLParam param = new AUSLParam();
		param.setOp_name("姬晓东");
		param.setOp_phone("18811366591");
		param.setOp_userid("20233512");
		
		param.setUser_name("姬晓东");
		param.setUser_phone("18811366591");
		param.setCallback_url("http://www.baidu.com");
		param.setHouse_id("8a9e9aa8555711fd01555711fda20001");
		param.setRoom_id("8a9e9aa8555711fd01555711fda20001");
		param.setIs_send_sms(1);
		param.setPassword("111116");
		param.setPassword_type(2);
		param.setUser_identify("20233512");
		PermissionTimeParam perTime = new PermissionTimeParam();
		perTime.setBegin(System.currentTimeMillis()/1000);
		perTime.setEnd((System.currentTimeMillis()+20000)/1000);
		param.setPermission(perTime);*/
		AUSLParam param = new AUSLParam();
		param.setOp_name("李央");
		param.setOp_phone("15998685217");
		param.setOp_userid("f8a4375c-7437-b078-6f4b-5e2b7d6dfcb6");
		
		param.setUser_name("李央");
		param.setUser_phone("15998685217");
		param.setCallback_url("http://www.baidu.com");
		param.setHouse_id("8a9e9aa8555711fd01555711fda20001");
		param.setRoom_id("8a9e9aa8555711fd01555711fda20001");
		param.setIs_send_sms(1);
		param.setPassword("558566");
		param.setPassword_type(PasswordTypeEnum.USER_PWD.getCode());
		param.setUser_identify("1605117Y6VG0CF213442");
		PermissionTimeParam perTime = new PermissionTimeParam();
		/*perTime.setBegin(1462939200000L);
		perTime.setEnd(1467262800000L);*/
		perTime.setBegin(System.currentTimeMillis()/1000);
		perTime.setEnd((System.currentTimeMillis()+20000)/1000);
		param.setPermission(perTime);
		
		String sendPost = CloseableHttpUtil.sendPost(url, JsonEntityTransform.Object2Json(param));
		
		System.out.println(sendPost);
	}
	

	/**
	 * 
	 * 获取UTC时间
	 *
	 * @author yd
	 * @created 2016年6月24日 下午6:58:43
	 *
	 * @param time
	 * @return
	 */
	private Long getUtcTime(Date time){
		//1、取得本地时间：    
		Calendar cal = Calendar.getInstance();   
		cal.setTime(time);
		//2、取得时间偏移量：    
		final int zoneOffset = cal.get(Calendar.ZONE_OFFSET);   
		//3、取得夏令时差：    
		final int dstOffset = cal.get(Calendar.DST_OFFSET);    
		//4、从本地时间里扣除这些差量，即可以取得UTC时间：    
		cal.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));    
		return cal.getTimeInMillis();
	}
	
	@Test
	public void testUpdatePass(){
		String url = "http://10.16.24.78:3000/v1/update_password";
		AUSLParam param = new AUSLParam();
		param.setOp_name("姬晓东");
		param.setOp_phone("18811366591");
		param.setOp_userid("20233512");
		
		param.setUser_name("姬晓东");
		param.setUser_phone("18811366591");
		param.setCallback_url("http://www.baidu.com");
		param.setHouse_id("8a9e9aa8555711fd01555711fda20001");
		param.setRoom_id("8a9e9aa8555711fd01555711fda20001");
		param.setIs_send_sms(1);
		param.setPassword("858888");
		param.setPassword_type(2);
		param.setUser_identify("20233512");
		PermissionTimeParam perTime = new PermissionTimeParam();
		perTime.setBegin(System.currentTimeMillis()/1000);
		perTime.setEnd((System.currentTimeMillis()+20000)/1000);
		param.setPermission(perTime);
		
		String sendPost = CloseableHttpUtil.sendPost(url, JsonEntityTransform.Object2Json(param));
		
		System.out.println(sendPost);
	}
	
	public static void main(String[] args) {
		String str = "{\"ReqID\":\"781146359d\",\"ErrNo\":0,\"ErrMsg\":\"ADD_PASSWORD_SUCCESS\",\"password_id\":1018,\"serviceid\":\"RzhKd7peSfxJ1RAzlBsrQSWOh38Pfhe3\",\"password\":\"123456\"}";
		AddPassSLVo vo = JSONObject.parseObject(str, AddPassSLVo.class);
		System.out.println(vo.getReqID());
	}
	
}
