package com.ziroom.minsu.services.customer.test.proxy;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.cache.redisOne.RedisOperations;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgExtEntity;
import com.ziroom.minsu.entity.customer.CustomerPicMsgEntity;
import com.ziroom.minsu.services.customer.dto.CustomerExtDto;
import com.ziroom.minsu.services.customer.dto.CustomerInfoDto;
import com.ziroom.minsu.services.customer.entity.CustomerVo;
import com.ziroom.minsu.services.customer.proxy.CustomerMsgManagerServiceProxy;
import com.ziroom.minsu.services.customer.test.BaseTest;

import org.codehaus.jackson.type.TypeReference;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

public class CustomerMsgManagerServiceProxyTest extends BaseTest {

	@Resource(name = "customer.customerMsgManagerServiceProxy")
	private CustomerMsgManagerServiceProxy customerMsgManagerServiceProxy;

	@Autowired
	private RedisOperations redisOperations;

	@Test
	public void insertCustomerPicMsgListTest() {
		List<CustomerPicMsgEntity> picmsgEntityList = new ArrayList<CustomerPicMsgEntity>();
		CustomerPicMsgEntity picMsg = new CustomerPicMsgEntity();
		picMsg.setFid(UUIDGenerator.hexUUID());
		picMsg.setUid(UUIDGenerator.hexUUID());
		picMsg.setPicType(1);
		picmsgEntityList.add(picMsg);

		CustomerPicMsgEntity picMsg2 = new CustomerPicMsgEntity();
		picMsg2.setFid(UUIDGenerator.hexUUID());
		picMsg2.setUid(UUIDGenerator.hexUUID());
		picMsg2.setPicType(1);
		picmsgEntityList.add(picMsg2);

		String json = customerMsgManagerServiceProxy.insertCustomerPicMsgList(JsonEntityTransform.Object2Json(picmsgEntityList));
		System.err.println("json:" + json);
	}

	@Test
	public void testDelRedisKey(){
		redisOperations.del("minsu:redis:customervo:8a9e9a9e543d23f901543d23f9e90000");
	}

	@Test
	public void getCutomerVoFromRedisTest(){

		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.customerMsgManagerServiceProxy.getCutomerVoFromRedis("8a9e9a9e543d23f901543d23f9e90000"));
		System.out.println(dto);
	}

	@Test
	public void getCutomerVoTest(){

		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.customerMsgManagerServiceProxy.getCutomerVo("5f4f193b-07fd-a708-85f8-22907004fd6d"));
		System.out.println(JsonEntityTransform.Object2Json(dto));
	}



	@Test
	public void selectCustomerExtByUidTest() {
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(customerMsgManagerServiceProxy.selectCustomerExtByUid("50cc2cc3-49a1-79cc-fa23-fd0c4d881448"));
		System.out.println(dto);
	}

	@Test
	public void insertCustomerExtTest(){

		CustomerBaseMsgExtEntity customerBaseMsgExt = new CustomerBaseMsgExtEntity();
		customerBaseMsgExt.setUid("50cc2cc3-49a1-79cc-fa23-fd0c4d881448");
		customerBaseMsgExt.setCustomerIntroduce("自我介绍");
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(customerMsgManagerServiceProxy.insertCustomerExt(JsonEntityTransform.Object2Json(customerBaseMsgExt)));

		System.out.println(dto);

	}

	@Test
	public void updateCustomerExtByUidTest(){

		CustomerBaseMsgExtEntity customerBaseMsgExt = new CustomerBaseMsgExtEntity();
		customerBaseMsgExt.setUid("d925349a-5e9d-48a3-8adb-22f5271873de");
		customerBaseMsgExt.setCustomerIntroduce("2017-8-7测试添加修改日志2017-8-7测试添加修改日志2017-8-7测试添加修改日志2017-8-7测试添加修改日志2017-8-7测试添加修改日志2017-8-7测试添加修改日志2017-8-7测试添加修改日志2017-8-7测试添加修改日志2017-8-7测试添加修改日志2017-8-7测试添加修改日志2017-8-7测试添加修改日志2017-8-7测试添加修改日志2017-8-7测试添加修改日志");
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(customerMsgManagerServiceProxy.updateCustomerExtByUid(JsonEntityTransform.Object2Json(customerBaseMsgExt)));

		System.out.println(dto);
	}
	
	
	@Test
	public void testGetCustomerFormVo(){
		String cutomerVoFromDb = customerMsgManagerServiceProxy.getCutomerVoFromDb("95bb423c-fef2-4073-bb96-b87db57535fd");
		DataTransferObject customerDto = JsonEntityTransform.json2DataTransferObject(cutomerVoFromDb);
		CustomerVo parseData = customerDto.parseData("customerVo", new TypeReference<CustomerVo>() {
		});
		
	}
	
	@Test
	public void saveCustomerInfoTest(){
		
		CustomerInfoDto customerInfoDto =  new CustomerInfoDto();
		
		CustomerBaseMsgEntity customerBaseMsg = new CustomerBaseMsgEntity();
		
		customerBaseMsg.setUid("f8a4375c-7437-b078-6f4b-5e2b7d6dfcb6");
		customerInfoDto.setCustomerBaseMsg(customerBaseMsg);
		
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.customerMsgManagerServiceProxy.saveCustomerInfo(JsonEntityTransform.Object2Json(customerInfoDto)));
		
		System.out.println(dto.toJsonString());
	}
	
	
	@Test
	public void testupdateCustomerPicMsg(){
		CustomerPicMsgEntity picMsgEntity = new CustomerPicMsgEntity();
		picMsgEntity.setFid("8a9e9abb556e2c7701556e358c240003");
		picMsgEntity.setIsDel(1);
		String json = customerMsgManagerServiceProxy.updateCustomerPicMsg(JsonEntityTransform.Object2Json(picMsgEntity));
		System.out.println(json);
	}
	
	
	@Test
	public void queryCustomerRoleMsgTest(){
		
		//String str = "{\"page\":1,\"limit\":10,\"uid\":null,\"realName\":\"\",\"nickName\":\"\",\"customerMobile\":\"18612648507\",\"isLandlord\":null,\"auditStatus\":null,\"roleCode\":\"\",\"isSeed\":null}";
		CustomerExtDto customerExtDto = new CustomerExtDto();
		/*List<String> uidList = new ArrayList<>();
		uidList.add("52a4aea1-5527-7421-1b25-83fbca1c1856");
		uidList.add("66ae471d-29af-41d0-b4d3-c9c87e3e3309");
		uidList.add("d925349a-5e9d-48a3-8adb-22f5271873de");
		customerExtDto.setUidList(uidList);*/
		customerExtDto.setRealName("张琴");
		customerExtDto.setIsCanShow(1);
		String json = customerMsgManagerServiceProxy.queryCustomerRoleMsg(JsonEntityTransform.Object2Json(customerExtDto));
		System.err.println(json);
	}
	@Test
	public void getCustomerPicByUid(){
		String uid = "664524c5-6e75-ee98-4e0d-667d38b9eee1";
		String resultJson = customerMsgManagerServiceProxy.getCustomerPicByUid(uid);
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
		System.out.println(dto.toJsonString());


	}
	
	@Test
	public void saveNickNameAndIntroduceTest(){
		 CustomerVo customerVo = new CustomerVo();
         customerVo.setUid("664524c5-6e75-ee98-4e0d-667d38b9eee1");
         customerVo.setCustomerIntroduce("功能键你笔力扛鼎北京功能键你笔力扛鼎北京功能键你笔力扛鼎北京功能键你笔力扛鼎北京功能键你笔力扛鼎北京功能键你笔力扛鼎北京功能键你笔力扛鼎北京功能键你笔力扛鼎北京功能键你笔力扛鼎北京功能键你笔功能键你笔力扛鼎北京功能键你笔力扛鼎北京功能键你笔力扛鼎北京功能键你笔力扛鼎北京功能键你笔力扛鼎北地跟");
         customerVo.setNickName("冥愿冥愿");
         String customerJson = JsonEntityTransform.Object2Json(customerVo);
         String saveNickNameAndIntroduce = customerMsgManagerServiceProxy.saveNickNameAndIntroduce(customerJson);
		System.out.println(saveNickNameAndIntroduce);


	}
	
	@Test
	public void updateCustomerBaseMsgTest(){

		CustomerBaseMsgEntity customerBaseMsg = new CustomerBaseMsgEntity();
		customerBaseMsg.setUid("1a957599-cf9c-add2-3d87-9fe47b4cbd32");
		customerBaseMsg.setAuditStatus(0);
	String updateCustomerBaseMsg = customerMsgManagerServiceProxy.updateCustomerBaseMsg(JsonEntityTransform.Object2Json(customerBaseMsg));

		System.out.println(updateCustomerBaseMsg);
	}
	
	@Test
	public void updateCustomerExtNotAuditTest(){

		CustomerBaseMsgExtEntity customerBaseMsgExt = new CustomerBaseMsgExtEntity();
		customerBaseMsgExt.setUid("664524c5-6e75-ee98-4e0d-667d38b9eee1");
		customerBaseMsgExt.setCustomerIntroduce("dfddasf");
	String updateCustomerBaseMsg = customerMsgManagerServiceProxy.updateCustomerExtNotAudit(JsonEntityTransform.Object2Json(customerBaseMsgExt));

		System.out.println(updateCustomerBaseMsg);
	}
}
