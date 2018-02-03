/**
 * @FileName: CustomerAuthServiceProxyTest.java
 * @Package com.ziroom.minsu.services.customer.test.proxy
 * 
 * @author yd
 * @created 2016年5月10日 下午10:50:53
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.customer.test.proxy;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.customer.CustomerPicMsgEntity;
import com.ziroom.minsu.services.customer.dto.CustomerChatRequest;
import com.ziroom.minsu.services.customer.dto.SmsAuthLogDto;
import com.ziroom.minsu.services.customer.proxy.CustomerAuthServiceProxy;
import com.ziroom.minsu.services.customer.proxy.CustomerChatServiceProxy;
import com.ziroom.minsu.services.customer.test.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>auth 测试</p>
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
public class CustomerAuthServiceProxyTest  extends BaseTest{


	@Resource(name = "customer.customerAuthServiceProxy")
	private  CustomerAuthServiceProxy customerAuthServiceProxy;
	
	@Resource(name = "customer.customerChatServiceProxy")
	private  CustomerChatServiceProxy customerChatServiceProxy;
	
	@Test
	public void testFindMobileVerifyResult() {

		SmsAuthLogDto smsAuthLogDto = new SmsAuthLogDto();
		smsAuthLogDto.setAuthCode("123456");
		smsAuthLogDto.setMobileNo("15811361402");
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.customerAuthServiceProxy.findMobileVerifyResult(JsonEntityTransform.Object2Json(smsAuthLogDto)));

		System.out.println(dto);
	}
	

	
	@Test
	public void testcustomerIconAuth() {

		CustomerPicMsgEntity customerPicMsgEntity=new CustomerPicMsgEntity();
		customerPicMsgEntity.setFid("8a9e9aaf5dc68f7a015dc6919d940007");
		customerPicMsgEntity.setUid("52a4aea1-5527-7421-1b25-83fbca1c1856");
		customerPicMsgEntity.setPicBaseUrl("group3/M00/01/C9/ChAiKlmK5Y-AXDbpAABiObifoN4225");
		customerPicMsgEntity.setPicName("8a9e9aaf5dc68f7a015dc69185b70006.jpg");
		customerPicMsgEntity.setPicServerUuid("1edf9964-e542-4a9a-ba01-91a23e9af8f6");
		customerPicMsgEntity.setPicSuffix(".jpg");
		customerPicMsgEntity.setPicType(3);
		List<CustomerPicMsgEntity> picList=new ArrayList<CustomerPicMsgEntity>();
		picList.add(customerPicMsgEntity);
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.customerAuthServiceProxy.customerIconAuth(JsonEntityTransform.Object2Json(picList)));

		System.out.println(dto);
	}
	
	@Test
	public void test() {
		

		List<String> uidList = new ArrayList<String>();
		uidList.add("a4db5314-e232-a2df-b41b-d87d0a3ffa22");
		uidList.add("beffd00d-51ba-b777-38da-5f6c61d83e1a");
		uidList.add("3b052430-84aa-879e-8130-f0139edf1fa7");
		uidList.add("f0ffc931-b76d-fc87-0ac6-687cef1a3d3c");
		uidList.add("9e9d769e-e8d2-a81d-fe74-fb86903c4bd3");
		
		
		uidList.add("149b5295-b6e4-b3b3-b089-9199db408143");
		uidList.add("957dccd6-c321-eac6-4fb3-e4878f9eb1bd");
		uidList.add("af198900-8e05-f12b-8206-64a3010c7e79");
		uidList.add("42b34f54-847e-7059-4e2f-5d2aaf861fe2");
		uidList.add("efa7d865-9764-679c-e8af-c413d2cc4141");
		
		uidList.add("ea513d23-4a12-7ed3-4362-c62e9de5170d");
		uidList.add("d1d2b1a6-b3ce-49d5-b460-fe82e90321da");
		uidList.add("712b3a46-74a6-7281-0128-dd5a96d4465b");
		uidList.add("128acb54-123f-3d71-2479-d80649eb0add");
		uidList.add("e298335a-7e18-1702-b116-ceb9766d3f32");
		
		uidList.add("f2788f01-9a00-978d-a720-5c3504d9f225");
		uidList.add("f562fb75-35f4-722d-df28-e658944f0e8a");
		uidList.add("0b1a1077-f537-84ae-2bc1-85ae708c6bbd");
		uidList.add("6ac33ae9-54b3-246f-6c3c-88aeb7bfae01");
		uidList.add("29660f58-9081-668d-d788-5beae9ea8263");

		uidList.add("ca62af40-7bfe-865c-2265-3a964acce806");
		uidList.add("1debd9c6-8292-49ba-b9b3-269b8733002f");
		uidList.add("989d2232-4608-d0a6-a0f2-c0392c63d83b");
		uidList.add("f34165f1-2948-4c66-97bd-fe601af164a7");
		uidList.add("1a0651e8-ccb0-8014-4bb3-db0cf45c1d74");
		
		/*uidList.add("a4db5314-e232-a2df-b41b-d87d0a3ffa22");
		uidList.add("beffd00d-51ba-b777-38da-5f6c61d83e1a");
		uidList.add("3b052430-84aa-879e-8130-f0139edf1fa7");
		uidList.add("f0ffc931-b76d-fc87-0ac6-687cef1a3d3c");
		uidList.add("9e9d769e-e8d2-a81d-fe74-fb86903c4bd3");
		
		
		uidList.add("149b5295-b6e4-b3b3-b089-9199db408143");
		uidList.add("957dccd6-c321-eac6-4fb3-e4878f9eb1bd");
		uidList.add("af198900-8e05-f12b-8206-64a3010c7e79");
		uidList.add("42b34f54-847e-7059-4e2f-5d2aaf861fe2");
		uidList.add("efa7d865-9764-679c-e8af-c413d2cc4141");
		
		uidList.add("ea513d23-4a12-7ed3-4362-c62e9de5170d");
		uidList.add("d1d2b1a6-b3ce-49d5-b460-fe82e90321da");
		uidList.add("712b3a46-74a6-7281-0128-dd5a96d4465b");
		uidList.add("128acb54-123f-3d71-2479-d80649eb0add");
		uidList.add("e298335a-7e18-1702-b116-ceb9766d3f32");
		
		uidList.add("f2788f01-9a00-978d-a720-5c3504d9f225");
		uidList.add("f562fb75-35f4-722d-df28-e658944f0e8a");
		uidList.add("0b1a1077-f537-84ae-2bc1-85ae708c6bbd");
		uidList.add("6ac33ae9-54b3-246f-6c3c-88aeb7bfae01");
		uidList.add("29660f58-9081-668d-d788-5beae9ea8263");

		uidList.add("ca62af40-7bfe-865c-2265-3a964acce806");
		uidList.add("1debd9c6-8292-49ba-b9b3-269b8733002f");
		uidList.add("989d2232-4608-d0a6-a0f2-c0392c63d83b");
		uidList.add("f34165f1-2948-4c66-97bd-fe601af164a7");
		uidList.add("1a0651e8-ccb0-8014-4bb3-db0cf45c1d74");
		
		uidList.add("a4db5314-e232-a2df-b41b-d87d0a3ffa22");
		uidList.add("beffd00d-51ba-b777-38da-5f6c61d83e1a");
		uidList.add("3b052430-84aa-879e-8130-f0139edf1fa7");
		uidList.add("f0ffc931-b76d-fc87-0ac6-687cef1a3d3c");
		uidList.add("9e9d769e-e8d2-a81d-fe74-fb86903c4bd3");
		
		
		uidList.add("149b5295-b6e4-b3b3-b089-9199db408143");
		uidList.add("957dccd6-c321-eac6-4fb3-e4878f9eb1bd");
		uidList.add("af198900-8e05-f12b-8206-64a3010c7e79");
		uidList.add("42b34f54-847e-7059-4e2f-5d2aaf861fe2");
		uidList.add("efa7d865-9764-679c-e8af-c413d2cc4141");
		
		uidList.add("ea513d23-4a12-7ed3-4362-c62e9de5170d");
		uidList.add("d1d2b1a6-b3ce-49d5-b460-fe82e90321da");
		uidList.add("712b3a46-74a6-7281-0128-dd5a96d4465b");
		uidList.add("128acb54-123f-3d71-2479-d80649eb0add");
		uidList.add("e298335a-7e18-1702-b116-ceb9766d3f32");
		
		uidList.add("f2788f01-9a00-978d-a720-5c3504d9f225");
		uidList.add("f562fb75-35f4-722d-df28-e658944f0e8a");
		uidList.add("0b1a1077-f537-84ae-2bc1-85ae708c6bbd");
		uidList.add("6ac33ae9-54b3-246f-6c3c-88aeb7bfae01");
		uidList.add("29660f58-9081-668d-d788-5beae9ea8263");

		uidList.add("ca62af40-7bfe-865c-2265-3a964acce806");
		uidList.add("1debd9c6-8292-49ba-b9b3-269b8733002f");
		uidList.add("989d2232-4608-d0a6-a0f2-c0392c63d83b");
		uidList.add("f34165f1-2948-4c66-97bd-fe601af164a7");
		uidList.add("1a0651e8-ccb0-8014-4bb3-db0cf45c1d74");
		
		uidList.add("a4db5314-e232-a2df-b41b-d87d0a3ffa22");
		uidList.add("beffd00d-51ba-b777-38da-5f6c61d83e1a");
		uidList.add("3b052430-84aa-879e-8130-f0139edf1fa7");
		uidList.add("f0ffc931-b76d-fc87-0ac6-687cef1a3d3c");
		uidList.add("9e9d769e-e8d2-a81d-fe74-fb86903c4bd3");
		
		
		uidList.add("149b5295-b6e4-b3b3-b089-9199db408143");
		uidList.add("957dccd6-c321-eac6-4fb3-e4878f9eb1bd");
		uidList.add("af198900-8e05-f12b-8206-64a3010c7e79");
		uidList.add("42b34f54-847e-7059-4e2f-5d2aaf861fe2");
		uidList.add("efa7d865-9764-679c-e8af-c413d2cc4141");
		
		uidList.add("ea513d23-4a12-7ed3-4362-c62e9de5170d");
		uidList.add("d1d2b1a6-b3ce-49d5-b460-fe82e90321da");
		uidList.add("712b3a46-74a6-7281-0128-dd5a96d4465b");
		uidList.add("128acb54-123f-3d71-2479-d80649eb0add");
		uidList.add("e298335a-7e18-1702-b116-ceb9766d3f32");
		
		uidList.add("f2788f01-9a00-978d-a720-5c3504d9f225");
		uidList.add("f562fb75-35f4-722d-df28-e658944f0e8a");
		uidList.add("0b1a1077-f537-84ae-2bc1-85ae708c6bbd");
		uidList.add("6ac33ae9-54b3-246f-6c3c-88aeb7bfae01");
		uidList.add("29660f58-9081-668d-d788-5beae9ea8263");

		uidList.add("ca62af40-7bfe-865c-2265-3a964acce806");
		uidList.add("1debd9c6-8292-49ba-b9b3-269b8733002f");
		uidList.add("989d2232-4608-d0a6-a0f2-c0392c63d83b");
		uidList.add("f34165f1-2948-4c66-97bd-fe601af164a7");
		uidList.add("1a0651e8-ccb0-8014-4bb3-db0cf45c1d74");
		
		uidList.add("149b5295-b6e4-b3b3-b089-9199db408143");
		uidList.add("957dccd6-c321-eac6-4fb3-e4878f9eb1bd");
		uidList.add("af198900-8e05-f12b-8206-64a3010c7e79");
		uidList.add("42b34f54-847e-7059-4e2f-5d2aaf861fe2");
		uidList.add("efa7d865-9764-679c-e8af-c413d2cc4141");
		
		uidList.add("ea513d23-4a12-7ed3-4362-c62e9de5170d");
		uidList.add("d1d2b1a6-b3ce-49d5-b460-fe82e90321da");
		uidList.add("712b3a46-74a6-7281-0128-dd5a96d4465b");
		uidList.add("128acb54-123f-3d71-2479-d80649eb0add");
		uidList.add("e298335a-7e18-1702-b116-ceb9766d3f32");
		
		uidList.add("f2788f01-9a00-978d-a720-5c3504d9f225");
		uidList.add("f562fb75-35f4-722d-df28-e658944f0e8a");
		uidList.add("0b1a1077-f537-84ae-2bc1-85ae708c6bbd");
		uidList.add("6ac33ae9-54b3-246f-6c3c-88aeb7bfae01");
		uidList.add("29660f58-9081-668d-d788-5beae9ea8263");

		uidList.add("ca62af40-7bfe-865c-2265-3a964acce806");
		uidList.add("1debd9c6-8292-49ba-b9b3-269b8733002f");
		uidList.add("989d2232-4608-d0a6-a0f2-c0392c63d83b");
		uidList.add("f34165f1-2948-4c66-97bd-fe601af164a7");
		uidList.add("1a0651e8-ccb0-8014-4bb3-db0cf45c1d74");
		
		uidList.add("149b5295-b6e4-b3b3-b089-9199db408143");
		uidList.add("957dccd6-c321-eac6-4fb3-e4878f9eb1bd");
		uidList.add("af198900-8e05-f12b-8206-64a3010c7e79");
		uidList.add("42b34f54-847e-7059-4e2f-5d2aaf861fe2");
		uidList.add("efa7d865-9764-679c-e8af-c413d2cc4141");
		
		uidList.add("ea513d23-4a12-7ed3-4362-c62e9de5170d");
		uidList.add("d1d2b1a6-b3ce-49d5-b460-fe82e90321da");
		uidList.add("712b3a46-74a6-7281-0128-dd5a96d4465b");
		uidList.add("128acb54-123f-3d71-2479-d80649eb0add");
		uidList.add("e298335a-7e18-1702-b116-ceb9766d3f32");
		
		uidList.add("f2788f01-9a00-978d-a720-5c3504d9f225");
		uidList.add("f562fb75-35f4-722d-df28-e658944f0e8a");
		uidList.add("0b1a1077-f537-84ae-2bc1-85ae708c6bbd");
		uidList.add("6ac33ae9-54b3-246f-6c3c-88aeb7bfae01");
		uidList.add("29660f58-9081-668d-d788-5beae9ea8263");

		uidList.add("ca62af40-7bfe-865c-2265-3a964acce806");
		uidList.add("1debd9c6-8292-49ba-b9b3-269b8733002f");
		uidList.add("989d2232-4608-d0a6-a0f2-c0392c63d83b");
		uidList.add("f34165f1-2948-4c66-97bd-fe601af164a7");
		uidList.add("1a0651e8-ccb0-8014-4bb3-db0cf45c1d74");*/
		
		
		CustomerChatRequest chatRequest = new CustomerChatRequest();
		chatRequest.setUidList(uidList);
		//String olduserPicAndNickName = customerTestServiceProxy.getListUserPicAndNickName(JsonEntityTransform.Object2Json(chatRequest));
		String userPicAndNickName = customerChatServiceProxy.getListUserPicAndNickName(JsonEntityTransform.Object2Json(chatRequest));
		//System.out.println(olduserPicAndNickName);
		System.err.println(userPicAndNickName);
		
	}

}
