/**
 * @FileName: EvaluateControllerTest.java
 * @Package com.ziroom.minsu.api.test.evaluate.controller
 * 
 * @author yd
 * @created 2017年1月20日 下午2:02:54
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.api.test.evaluate.controller;

import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MD5Util;
import com.ziroom.minsu.api.common.encrypt.DESEncrypt;
import com.ziroom.minsu.api.common.encrypt.EncryptFactory;
import com.ziroom.minsu.api.common.encrypt.IEncrypt;
import com.ziroom.minsu.api.evaluate.dto.LanEvaApiRequest;
import com.ziroom.minsu.services.order.dto.OrderEvalRequest;

/**
 * <p>TODO</p>
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
public class EvaluateControllerTest {

	private static 	IEncrypt iEncrypt=EncryptFactory.createEncryption("DES");
	
	public static void testQueryLanEvaInfo() {
		
		LanEvaApiRequest lanEvaApiRequest = new LanEvaApiRequest();
      
		lanEvaApiRequest.setOrderSn("160511252A9834163856");
		System.err.println(JsonEntityTransform.Object2Json(lanEvaApiRequest));
		String paramJson=iEncrypt.encrypt(JsonEntityTransform.Object2Json(lanEvaApiRequest));
		System.err.println("2y5QfvAy="+paramJson);
		System.err.println("hPtJ39Xs="+MD5Util.MD5Encode(JsonEntityTransform.Object2Json(lanEvaApiRequest).toString(),"UTF-8"));
	}

	public static void houseEvaluateDes() {
		DESEncrypt dESEncrypt  = new DESEncrypt();
		
		String content = "b29f8942196125a5c26968b712b9b71c72c4f6954f19f7ac18aa0ed6a51db9acc5d1b9961cee8f65a1303689428aefca5e74284e56581cf1aaa900e4b34d44b37d350d7d68b84c8908a4ca3a24dba88e6435f59e65b7a521785ff10e7f8aa88e8cf76b02b98174f81bf11a9e66c0630582ee36008c93df80e22139f0818e89d13a1f914d9b8584ae3a50260d683c05fd4102f873b394f8b9dbde8f8e4bc1304c257463bf400d6e6036861a8047d7cea7bbedb1afa6d1618c6afcaf20f3f022c7714ad0b5a919e76b7d66f12ed372ed048a71feb5af1cf0e4fde1f991926a97f41ca33c05846359c377e3a8bc520eb53195b9a1572a7f95a73b5382289d0b76f02f7e8f76523ef802106d3f8f67b03f52c9eb777187b5dcf0e790c12cef6b84a9d8295dd15dbaffa062f333716cd825b89ae654d06e03d5b4166dbd4592c251d3d1f3f07c13baaa7464309e1d883f1aee0b1ca921cde41044d71c50dd05075129c621689c953d531aff5f0e0e3d0ac66c3e75a8fbf12e3a2cd4af53a25448eabbd52f3c2b12078a2f7d05b5eaf6cfc3d59029005265e7f2b3c066fad523230c27cd46319b243700255b9e80c9ba34071c8d469bd50a9c1eff89b70c3e8dc8993d8e2a0bdb58cbc8644930eb3a295ff6850816aab65829441d34b63d0c4c23723269b027359ec8825714259a05e5bbb28baeaca0194f37b5f12a7c9f1e24fba998a0b103bf8ec108cbb9abd80cbd1c794a94f8000a6a5efa28722bbe4086ac777121fb4a787dbaef4aec96855355cf71ba3f983ca29889df21c044f9c1a16dd4e48ae488fca68fba2eda314b3293c152d5d60bf8f5c0ad0141c49adad2ddadcd7c0357fae35b957809e2a6545a75f84f6a91f23ab4462be2b46682531e45dec82fee903e2809dbf86cf3dffe045a04e634ffe619bc7908ae314e88d5b41bed82c015fc1ec196b0c7e405fc148ddc65f74cb90cc0b09db26ab7454c17f002da8fd929be7fc4e598b15100be26a29d886be809502548b94762666ed7aecb67c53a11a10c9bd294e6dd121ba390fd9435f8f2c529384941df7c3a974ec6047bfa3ca7b19426b0f712c307a2276dec7427511b23634b5dbc1242eeb94c31f04195123b37d5261ec81c672f526da51e7db132007d2763ee9c18b4a663cf27b1247b4cbddd8fa201d97e501d8daa3cce0b805620619202c1ae12e47c0cea09fa653aba47bff6b39ae7e8a3c4f54eff82b4cae8718a5d71cf41ef8a432de7735bb4cf0670385435ba5e455b0ac6af08574a993fed08cab3b88295108e9e2528ceef78faf699d03114cc0b3aa1b1681c7facade29df7cc2fd45e451831835355c9f973a82574add52bb8df48ec18cc963a62beb13286c43526d10600624ae8f6e6587f673bb44f25a63b4a2ea10ddd112e5b6bdcab1baf2f47409b6402eef16fb316cb0d323c451b1a31811b901676f1158726db84001b52e45538d157";
		content = dESEncrypt.decrypt(content);		
		System.out.println(content);
	}
	
	public static void queryEvaListTest(){
		OrderEvalRequest request = new OrderEvalRequest();
		request.setLimit(150);
		request.setOrderEvalType(1);//0：所有，1：待评价，2：已评价（房东不区分，默认全部）
		request.setPage(1);
		request.setUid("664524c5-6e75-ee98-4e0d-667d38b9eee1");
		request.setRequestType(1);//2：房客，1：房东
		System.err.println(JsonEntityTransform.Object2Json(request));
		String paramJson=iEncrypt.encrypt(JsonEntityTransform.Object2Json(request));
		System.err.println("2y5QfvAy="+paramJson);
		System.err.println("hPtJ39Xs="+MD5Util.MD5Encode(JsonEntityTransform.Object2Json(request).toString(),"UTF-8"));
	}
	public static void main(String[] args) {
		//testQueryLanEvaInfo();
		//houseEvaluateDes();
		queryEvaListTest();
	}
	
	
}
