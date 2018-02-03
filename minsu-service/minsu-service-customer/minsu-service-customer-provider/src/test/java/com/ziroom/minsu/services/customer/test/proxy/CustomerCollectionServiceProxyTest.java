package com.ziroom.minsu.services.customer.test.proxy;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.customer.CustomerCollectionEntity;
import com.ziroom.minsu.services.customer.dto.CollectionConditionDto;
import com.ziroom.minsu.services.customer.dto.CustomerCollectionDto;
import com.ziroom.minsu.services.customer.proxy.CustomerCollectionServiceProxy;
import com.ziroom.minsu.services.customer.test.BaseTest;

/**
 * 
 * <p>客户房源收藏代理测试类</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liujun
 * @since 1.0
 * @version 1.0
 */
public class CustomerCollectionServiceProxyTest  extends BaseTest{


	@Resource(name = "customer.customerCollectionServiceProxy")
	private  CustomerCollectionServiceProxy customerCollectionServiceProxy;
	
	@Test
	public void saveCustomerCollectionEntityTest() {
		CustomerCollectionEntity entity=new CustomerCollectionEntity();
		entity.setFid(UUIDGenerator.hexUUID());
		entity.setUid("3a59968c-1eb9-4612-e325-5cc0a856ac34");
		entity.setHouseFid("8a9e9aae5419d73b015419d73ddb0001");
		entity.setRentWay(1);
		entity.setHouseName("幸福小屋");
		entity.setLandlordUid("9afeae98-56ff-0c35-77cf-8624b2e1efae");
		entity.setPicBaseUrl("group1/M00/00/1C/ChAiMFciHMqAYEkUAAM9985rOHU378");
		entity.setPicSuffix(".jpg");
		entity.setCreateDate(new Date());
		String resultJson = customerCollectionServiceProxy.saveCustomerCollectionEntity(JsonEntityTransform.Object2Json(entity));
		System.err.println(resultJson);
	}
	
	@Test
	public void findCustomerCollectionEntityByFidTest() {
		String fid = "8a9e9a9b564524100156452412990001";
		String resultJson = customerCollectionServiceProxy.findCustomerCollectionEntityByFid(fid);
		System.err.println(resultJson);
	}

	@Test
	public void findCustomerCollectionEntityByConditionTest(){
		CollectionConditionDto conditionDto = new CollectionConditionDto();
		conditionDto.setHouseFid("8a9e9aae5419cc22015419cc250a0002");
		conditionDto.setRentWay(0);
		String resultJson = customerCollectionServiceProxy
				.findCustomerCollectionEntityByCondition(JsonEntityTransform.Object2Json(conditionDto));
		System.err.println(JsonEntityTransform.Object2Json(resultJson));
	}
	
	@Test
	public void findCustomerCollectionEntityListByUidTest() {
		CustomerCollectionDto dto = new CustomerCollectionDto();
		dto.setUid("3a59968c-1eb9-4612-e325-5cc0a856ac34");
		String resultJson = customerCollectionServiceProxy
				.findCustomerCollectionVoListByUid(JsonEntityTransform.Object2Json(dto));
		System.err.println(resultJson);
	}
	
	@Test
	public void updateCustomerCollectionByFidTest() {
		CustomerCollectionEntity entity=new CustomerCollectionEntity();
		entity.setFid("8a9e9a9b564524100156452412990001");
		entity.setHouseName("幸福小屋");
		entity.setIsDel(1);
		entity.setLastModifyDate(new Date());
		String resultJson = customerCollectionServiceProxy.updateCustomerCollectionByFid(JsonEntityTransform.Object2Json(entity));
		System.err.println(resultJson);
	}
	

	@Test
	public void countByUidTest(){
		String uid = "3a59968c-1eb9-4612-e325-5cc0a856ac34";
		String resultJson = customerCollectionServiceProxy.countByUid(uid);
		System.err.println(resultJson);
	}

}
