package com.ziroom.minsu.api.test.evaluate.controller;

import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MD5Util;
import com.ziroom.minsu.api.common.constant.OrderConst;
import com.ziroom.minsu.api.common.encrypt.EncryptFactory;
import com.ziroom.minsu.api.common.encrypt.IEncrypt;
import com.ziroom.minsu.api.evaluate.dto.HouseSnapshotApiRequest;
import com.ziroom.minsu.api.evaluate.dto.TenantEvaApiRequest;
import com.ziroom.minsu.api.evaluate.enumvalue.EvaTypeEnum;
import com.ziroom.minsu.api.order.dto.CheckOutOrderApiRequest;
import com.ziroom.minsu.valenum.common.UserTypeEnum;

/**
 * <p>客户端的评价测试</p>
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
public class TenantEvaControllerTest {
	

	private static 	IEncrypt iEncrypt=EncryptFactory.createEncryption("DES");

	/**
	 * 
	 * 查询评价的测试
	 *
	 * @author yd
	 * @created 2016年5月3日 下午1:22:51
	 *
	 */
	public  static void queryEvaluateTest() {
		
		HouseSnapshotApiRequest houseSnapshotApiRe=  new HouseSnapshotApiRequest();
		
		houseSnapshotApiRe.setUid("8a9e9a9e543d23f901543d23f9e90000");
		houseSnapshotApiRe.setLoginToken("dfkdkfkdjf223j2kj32kj32j32j");
		houseSnapshotApiRe.setEvaType(EvaTypeEnum.WAITING_EVA.getCode());
		
		System.err.println(JsonEntityTransform.Object2Json(houseSnapshotApiRe));
		String paramJson=iEncrypt.encrypt(JsonEntityTransform.Object2Json(houseSnapshotApiRe));
		System.err.println("2y5QfvAy="+paramJson);
		System.err.println("hPtJ39Xs="+MD5Util.MD5Encode(JsonEntityTransform.Object2Json(houseSnapshotApiRe).toString(),"UTF-8"));
	}

	/**
	 * 
	 * 查询详情
	 *
	 * @author yd
	 * @created 2016年5月3日 下午1:39:34
	 *
	 */
	public static void queryEvaluateInfoTest(){
		
		TenantEvaApiRequest tenantEvaApiRequest=  new TenantEvaApiRequest();
		tenantEvaApiRequest.setOrderSn("1605022FDZ6352190804");
		tenantEvaApiRequest.setUid("8a9e9a9e543d23f901543d23f9e90000");
		tenantEvaApiRequest.setLoginToken("dfkdkfkdjf223j2kj32kj32j32j");
		System.err.println(JsonEntityTransform.Object2Json(tenantEvaApiRequest));
		String paramJson=iEncrypt.encrypt(JsonEntityTransform.Object2Json(tenantEvaApiRequest));
		System.err.println("2y5QfvAy="+paramJson);
		System.err.println("hPtJ39Xs="+MD5Util.MD5Encode(JsonEntityTransform.Object2Json(tenantEvaApiRequest).toString(),"UTF-8"));
	}
	
	/**
	 * 
	 * 测试点评
	 *
	 * @author yd
	 * @created 2016年5月3日 下午5:02:45
	 *
	 */
	public static void tenEvaluateTest(){
		
		TenantEvaApiRequest tenantEvaApiRequest   =  new TenantEvaApiRequest();
		
		tenantEvaApiRequest.setOrderSn("16050267DY79C7174903");
		tenantEvaApiRequest.setUid("8a9e9a9e543d23f901543d23f9e90000");
		tenantEvaApiRequest.setLoginToken("dfkdkfkdjf223j2kj32kj32j32j");
		tenantEvaApiRequest.setEvaUserType(UserTypeEnum.TENANT.getUserType());
		
		tenantEvaApiRequest.setContent("发的顺丰就偶是地方绝对是泼妇");
		tenantEvaApiRequest.setCostPerformance(2);
		tenantEvaApiRequest.setDescriptionMatch(2);
		tenantEvaApiRequest.setHouseClean(2);
		tenantEvaApiRequest.setSafetyDegree(2);
		tenantEvaApiRequest.setTrafficPosition(2);
		
		System.err.println(JsonEntityTransform.Object2Json(tenantEvaApiRequest));
		String paramJson=iEncrypt.encrypt(JsonEntityTransform.Object2Json(tenantEvaApiRequest));
		System.err.println("2y5QfvAy="+paramJson);
		System.err.println("hPtJ39Xs="+MD5Util.MD5Encode(JsonEntityTransform.Object2Json(tenantEvaApiRequest).toString(),"UTF-8"));
	}
	
	
	/**
	 * 初始化退房
	 * @author lishaochuan
	 * @create 2016年5月4日
	 */
	public static void checkOutOrderMsgTest(){
		CheckOutOrderApiRequest requestApi = new CheckOutOrderApiRequest();
//		requestApi.setUid("937d573a-4f25-638b-db9b-f97339e3e5ming-2");
//		requestApi.setOrderSn("160419V6621S1J172304");
		requestApi.setUid("uid");
		requestApi.setOrderSn("1605042NL8KYP8111215");
		
		System.err.println(JsonEntityTransform.Object2Json(requestApi));
		String paramJson=iEncrypt.encrypt(JsonEntityTransform.Object2Json(requestApi));
		System.err.println("2y5QfvAy="+paramJson);
		System.err.println("hPtJ39Xs="+MD5Util.MD5Encode(JsonEntityTransform.Object2Json(requestApi).toString(),"UTF-8"));
		
		
		String result = "b29f8942196125a5513afebbf051b09b8636262133d44330622a651d2f3a0935228ab992e3b104305ac5cb0d02099fb3bf0cb836f19218e35fb01ca6a9e6c7a5";
		System.err.println(iEncrypt.decrypt(result));
	}
	
	/**
	 * 退房
	 * @author lishaochuan
	 * @create 2016年5月4日
	 */
	public static void checkOutOrderTest(){
		CheckOutOrderApiRequest requestApi = new CheckOutOrderApiRequest();
		requestApi.setUid("uid");
		requestApi.setOrderSn("1605042NL8KYP8111215");
		
		System.err.println(JsonEntityTransform.Object2Json(requestApi));
		String paramJson=iEncrypt.encrypt(JsonEntityTransform.Object2Json(requestApi));
		System.err.println("2y5QfvAy="+paramJson);
		System.err.println("hPtJ39Xs="+MD5Util.MD5Encode(JsonEntityTransform.Object2Json(requestApi).toString(),"UTF-8"));
		
		
	}
	
	
	/**
	 * 测试
	 *
	 * @author yd
	 * @created 2016年5月3日 下午1:33:49
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		
		//queryEvaluateTest();
		
		//queryEvaluateInfoTest();
		
		// tenEvaluateTest();
		
		 checkOutOrderMsgTest();
		
//		checkOutOrderTest();
		
		
	}
}
