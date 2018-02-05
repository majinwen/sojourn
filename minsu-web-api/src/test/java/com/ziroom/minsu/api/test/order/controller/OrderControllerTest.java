package com.ziroom.minsu.api.test.order.controller;

import java.text.ParseException;

import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MD5Util;
import com.ziroom.minsu.api.common.encrypt.EncryptFactory;
import com.ziroom.minsu.api.common.encrypt.IEncrypt;
import com.ziroom.minsu.api.order.dto.CreateOrderApiRequest;
import com.ziroom.minsu.api.order.dto.NeedPayFeeApiRequest;

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
public class OrderControllerTest {

	private static 	IEncrypt iEncrypt=EncryptFactory.createEncryption("DES");

	public static void checkCoupon() {
		
		NeedPayFeeApiRequest request = new NeedPayFeeApiRequest();
		request.setCouponSn("chuan2");
		request.setRentWay(0);
		request.setFid("8a90a2d4549ac7990154a3ee6eee0237");
		request.setUid("a06f82a2-423a-e4e3-4ea8-e98317540190");
		
		String startTime = "2016-07-25";
		String endTime = "2016-07-26";
		try {
			request.setStartTime(DateUtil.parseDate(startTime, "yyyy-MM-dd"));
			request.setEndTime(DateUtil.parseDate(endTime, "yyyy-MM-dd"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		System.err.println(JsonEntityTransform.Object2Json(request));
		String paramJson=iEncrypt.encrypt(JsonEntityTransform.Object2Json(request));
		System.err.println("2y5QfvAy="+paramJson);
		System.err.println("hPtJ39Xs="+MD5Util.MD5Encode(JsonEntityTransform.Object2Json(request).toString(),"UTF-8"));
	}
	
	
	
	public static void createOrder() {
		CreateOrderApiRequest request = new CreateOrderApiRequest();
		request.setCouponSn("146");
		request.setRentWay(0);
		request.setFid("8a9e9a9a548a061301548a67e6300029");
		request.setUid("f8a4375c-7437-b078-6f4b-5e2b7d6dfcb6");
		request.setSourceType(1);
		
		String startTime = "2016-08-14";
		String endTime = "2016-08-24";
		try {
			request.setStartTime(DateUtil.parseDate(startTime, "yyyy-MM-dd"));
			request.setEndTime(DateUtil.parseDate(endTime, "yyyy-MM-dd"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		System.err.println(JsonEntityTransform.Object2Json(request));
		String paramJson=iEncrypt.encrypt(JsonEntityTransform.Object2Json(request));
		System.err.println("2y5QfvAy="+paramJson);
		System.err.println("hPtJ39Xs="+MD5Util.MD5Encode(JsonEntityTransform.Object2Json(request).toString(),"UTF-8"));
	}
	
	public static void main(String[] args) {
		checkCoupon();
		
	}

}
