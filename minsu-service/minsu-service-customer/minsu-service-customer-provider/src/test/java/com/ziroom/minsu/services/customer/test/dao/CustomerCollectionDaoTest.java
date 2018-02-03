package com.ziroom.minsu.services.customer.test.dao;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.customer.CustomerCollectionEntity;
import com.ziroom.minsu.services.customer.dao.CustomerCollectionDao;
import com.ziroom.minsu.services.customer.dto.CollectionConditionDto;
import com.ziroom.minsu.services.customer.dto.CustomerCollectionDto;
import com.ziroom.minsu.services.customer.entity.CustomerCollectionVo;
import com.ziroom.minsu.services.customer.test.BaseTest;

/**
 * 
 * <p>客户房源收藏测试dao</p>
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
public class CustomerCollectionDaoTest extends BaseTest{
	
	@Resource(name="customer.customerCollectionDao")
	private CustomerCollectionDao customerCollectionDao;
	
	@Test
	public void insertCustomerCollectionTest(){
		CustomerCollectionEntity entity=new CustomerCollectionEntity();
		entity.setFid(UUIDGenerator.hexUUID());
		entity.setUid("3a59968c-1eb9-4612-e325-5cc0a856ac34");
		entity.setHouseFid("8a9e9aae5419cc22015419cc250a0002");
		entity.setRentWay(0);
		entity.setHouseName("普天实业创新园");
		entity.setLandlordUid("9afeae98-56ff-0c35-77cf-8624b2e1efae");
		entity.setPicBaseUrl("group1/M00/00/1C/ChAiMFciHMqAYEkUAAM9985rOHU378");
		entity.setPicSuffix(".jpg");
		entity.setCreateDate(new Date());
		customerCollectionDao.insertCustomerCollection(entity);
	}
	
	@Test
	public void findCustomerCollectionEntityByFidTest(){
		String fid = "8a9e9a9b5644e090015644e0908b0000";
		CustomerCollectionEntity entity = customerCollectionDao.findCustomerCollectionEntityByFid(fid);
		System.err.println(JsonEntityTransform.Object2Json(entity));
	}
	
	@Test
	public void findCustomerCollectionEntityByConditionTest(){
		CollectionConditionDto conditionDto = new CollectionConditionDto();
		conditionDto.setHouseFid("8a9e9aae5419cc22015419cc250a0002");
		conditionDto.setRentWay(0);
		CustomerCollectionEntity entity = customerCollectionDao.findCustomerCollectionEntityByCondition(conditionDto);
		System.err.println(JsonEntityTransform.Object2Json(entity));
	}
	
	@Test
	public void findCustomerCollectionEntityListByUidTest(){
		CustomerCollectionDto dto = new CustomerCollectionDto();
		dto.setUid("3a59968c-1eb9-4612-e325-5cc0a856ac34");
		PagingResult<CustomerCollectionVo> list = customerCollectionDao.findCustomerCollectionVoListByUid(dto);
		System.err.println(JsonEntityTransform.Object2Json(list));
	}
	
	@Test
	public void updateCustomerCollectionByFidTest(){
		CustomerCollectionEntity entity=new CustomerCollectionEntity();
		entity.setFid("8a9e9a9b5644e090015644e0908b0000");
		entity.setIsDel(1);
		entity.setLastModifyDate(new Date());
		int num = customerCollectionDao.updateCustomerCollectionByFid(entity);
		System.err.println(JsonEntityTransform.Object2Json(num));
	}
	
	@Test
	public void countByUidTest(){
		String uid = "3a59968c-1eb9-4612-e325-5cc0a856ac34";
		long num = customerCollectionDao.countByUid(uid);
		System.err.println(JsonEntityTransform.Object2Json(num));
	}
		
}
