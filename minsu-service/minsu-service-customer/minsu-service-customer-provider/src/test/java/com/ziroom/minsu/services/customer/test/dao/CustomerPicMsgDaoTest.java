/**
 * @FileName: CustomerPicMsgDaoTest.java
 * @Package com.ziroom.minsu.services.customer.test.dao
 * 
 * @author bushujie
 * @created 2016年4月23日 下午1:56:11
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.customer.test.dao;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.customer.CustomerPicMsgEntity;
import com.ziroom.minsu.services.customer.dao.CustomerPicMsgDao;
import com.ziroom.minsu.services.customer.test.BaseTest;

/**
 * <p>TODO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
public class CustomerPicMsgDaoTest extends BaseTest{
	
	@Resource(name="customer.customerPicMsgDao")
	private CustomerPicMsgDao customerPicMsgDao;




	@Test
	public void delCustomerPicMsgByType(){
		customerPicMsgDao.delCustomerPicMsgByType("111",3);
	}


	@Test
	public void insertCustomerPicMsgTest(){
		CustomerPicMsgEntity customerPicMsgEntity=new CustomerPicMsgEntity();
		customerPicMsgEntity.setFid(UUIDGenerator.hexUUID());
		customerPicMsgEntity.setUid("8a9e9a9e543d23f901543d23f9");
		customerPicMsgEntity.setPicBaseUrl("dddd");
		customerPicMsgEntity.setPicName("1405189183000ac7da9e5ff7e4a03f670480e546bd5fbbebdf6f807ee15ccbd6f0ef7d79b38fca2ae32b9159d0e69f23afcadeb6ce6c5378040c1a585195c7b911de8fc851ca4");
		customerPicMsgEntity.setPicServerUuid(UUIDGenerator.hexUUID());
		customerPicMsgEntity.setPicSuffix(".jpg");
		customerPicMsgEntity.setPicType(3);
		customerPicMsgDao.insertCustomerPicMsg(customerPicMsgEntity);
	}
	
	@Test
	public void getCustomerPicByTypeTest(){
		Map<String , Object> paramMap=new HashMap<String, Object>();
		paramMap.put("uid", "8a9e9a8b53d6da8b0153d6da8bae0000");
		paramMap.put("picType", 0);
		System.out.println(JsonEntityTransform.Object2Json(customerPicMsgDao.getCustomerPicByType(paramMap)));
	}
	
	@Test
	public void updateCustomerPicMsgTest(){
		CustomerPicMsgEntity customerPicMsgEntity=new CustomerPicMsgEntity();
		customerPicMsgEntity.setFid("8a9e9ac8613fb20c01613fb20c970000");
		customerPicMsgEntity.setUid("8a9e9a9e543d23f901543d23f9");
		customerPicMsgEntity.setPicBaseUrl("dddd");
		customerPicMsgEntity.setPicName("1405189183000ac7da9e5ff7e4a03f670480e546bd5fbbebdf6f807ee15ccbd6f0ef7d79b38fca2ae32b9159d0e69f23afcadeb6ce6c5378040c1a585195c7b911de8fc851ca4");
		customerPicMsgEntity.setPicServerUuid(UUIDGenerator.hexUUID());
		customerPicMsgEntity.setPicSuffix(".jpg");
		customerPicMsgEntity.setPicType(2);
		customerPicMsgDao.updateCustomerPicMsg(customerPicMsgEntity);
	}
}
