package com.ziroom.minsu.api.test.order.controller;

import java.text.ParseException;

import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MD5Util;
import com.ziroom.minsu.api.common.encrypt.EncryptFactory;
import com.ziroom.minsu.api.common.encrypt.IEncrypt;
import com.ziroom.minsu.api.order.dto.CreateOrderApiRequest;
import com.ziroom.minsu.api.order.dto.NeedPayFeeApiRequest;
import com.ziroom.minsu.services.cms.dto.MobileCouponRequest;
import com.ziroom.minsu.services.order.dto.LandOrderListRequest;

public class LandOrderControllerTest {
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
	

	public static void showOrderList() {
		
		LandOrderListRequest request = new LandOrderListRequest();
		request.setLanOrderType(1);
		request.setLimit(50);
		request.setPage(1);
		request.setUid("664524c5-6e75-ee98-4e0d-667d38b9eee1");
		System.err.println(JsonEntityTransform.Object2Json(request));
		String paramJson=iEncrypt.encrypt(JsonEntityTransform.Object2Json(request));
		System.err.println("2y5QfvAy="+paramJson);
		System.err.println("hPtJ39Xs="+MD5Util.MD5Encode(JsonEntityTransform.Object2Json(request).toString(),"UTF-8"));
	}
	
    public static void achieveCoupon() {
    	MobileCouponRequest request =  new MobileCouponRequest();
    	request.setGroupSn(null);
    	request.setActSn("FYLQ01");
    	request.setUid("e4614125-1d7d-417b-849e-c1f7839281d3");
		System.err.println(JsonEntityTransform.Object2Json(request));
		String paramJson=iEncrypt.encrypt(JsonEntityTransform.Object2Json(request));
		System.err.println("2y5QfvAy="+paramJson);
		System.err.println("hPtJ39Xs="+MD5Util.MD5Encode(JsonEntityTransform.Object2Json(request).toString(),"UTF-8"));
	}
	
	
	public static void main(String[] args) {
		//showOrderList();
		achieveCoupon();
	}

}
