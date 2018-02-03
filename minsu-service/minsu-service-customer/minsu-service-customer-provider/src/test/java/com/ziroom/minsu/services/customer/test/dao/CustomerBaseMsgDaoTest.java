/**
 * @FileName: CustomerBaseMsgDaoTest.java
 * @Package com.ziroom.minsu.services.customer.test.dao
 * 
 * @author bushujie
 * @created 2016年4月22日 下午4:27:47
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.customer.test.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.ziroom.minsu.services.customer.entity.CustomerDetailImageVo;

import org.junit.Test;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.services.customer.dao.CustomerBaseMsgDao;
import com.ziroom.minsu.services.customer.dto.CustomerBaseMsgDto;
import com.ziroom.minsu.services.customer.entity.CustomerAuthVo;
import com.ziroom.minsu.services.customer.test.BaseTest;
import com.ziroom.minsu.valenum.customer.CustomerSourceEnum;

/**
 * <p></p>
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
public class CustomerBaseMsgDaoTest extends BaseTest{

	@Resource(name="customer.customerBaseMsgDao")
	private CustomerBaseMsgDao customerBaseMsgDao;


	@Test
	public void getCustomerDetailImage(){
		CustomerDetailImageVo customerDetailImageVo = customerBaseMsgDao.getCustomerDetailImage("7cd36c83-db20-4996-b1fe-343f13066972");
		System.out.println(JsonEntityTransform.Object2Json(customerDetailImageVo));
	}




	@Test
	public void insertCustomerBaseMsgTest(){
		CustomerBaseMsgEntity customerBaseMsgEntity=new CustomerBaseMsgEntity();
		customerBaseMsgEntity.setRealName("大师哥12441f65dsf45d5");
		customerBaseMsgEntity.setNickName("冥愿1231231");
		customerBaseMsgEntity.setCustomerMobile("18800010103");
		customerBaseMsgEntity.setAuditStatus(1);
		customerBaseMsgEntity.setClearingCode("1");
		customerBaseMsgEntity.setCustomerSex(1);
		customerBaseMsgEntity.setCustomerEmail("18800010103@139.com");
		customerBaseMsgEntity.setIdType(1);
		customerBaseMsgEntity.setIsContactAuth(1);//是否认证
		customerBaseMsgEntity.setIsLandlord(1);//是否房东
		customerBaseMsgEntity.setIsUploadIcon(1);
		customerBaseMsgEntity.setLastModifyDate(new Date());
		customerBaseMsgEntity.setCreateDate(new Date());
		customerBaseMsgEntity.setNationCode("100000");
		customerBaseMsgEntity.setProvinceCode("110000");
		customerBaseMsgEntity.setCityCode("110100");
		customerBaseMsgEntity.setAreaCode("110105");
		customerBaseMsgEntity.setRentPayment(1);
		customerBaseMsgEntity.setResideAddr("朝阳区");
		customerBaseMsgEntity.setUid("6f326b24-ef30-4048-9dd9-9d4ea5757b0b");
		customerBaseMsgEntity.setCustomerSource(CustomerSourceEnum.Api.getCode());
		
		customerBaseMsgDao.insertCustomerBaseMsg(customerBaseMsgEntity);
	}

	@Test
	public void updateCustomerBaseMsgTest(){
		CustomerBaseMsgEntity customerBaseMsgEntity=new CustomerBaseMsgEntity();
		customerBaseMsgEntity.setUid("8a9e9cd954ec3ab60154ec3ab6360000");
		customerBaseMsgEntity.setCustomerMobile("15811236523");
		customerBaseMsgEntity.setIsContactAuth(1);
		customerBaseMsgEntity.setCustomerSource(CustomerSourceEnum.Mapp_Login_First.getCode());
		customerBaseMsgDao.updateCustomerBaseMsg(customerBaseMsgEntity);
	}

	@Test
	public void testQueryCustomerBaseMsg(){
		CustomerBaseMsgDto dto = new CustomerBaseMsgDto();
		dto.setPage(0);
		dto.setLimit(5);
		dto.setAuditStatus(0);
		dto.setIsLandlord(1);
		PagingResult<CustomerBaseMsgEntity> msg = customerBaseMsgDao.queryCustomerBaseMsg(dto);
		System.out.println(msg.getTotal());

	}

	@Test
	public void selectByConditionTest(){

		CustomerBaseMsgDto customerBaseDto = new CustomerBaseMsgDto();
		customerBaseDto.setRealName("大师哥");
		List<CustomerBaseMsgEntity> list = 	this.customerBaseMsgDao.selectByCondition(customerBaseDto);

		System.out.println(list);
	}
	
	
	@Test
	public void customerAuthVoTest(){
		CustomerAuthVo customerAuthVo=customerBaseMsgDao.getCustomerAuthData("f877a191-4434-423e-9de7-39816ffc6e54");
		System.err.println(JsonEntityTransform.Object2Json(customerAuthVo));
	}
	
	@Test
	public void queryCustomerListByUidListTest(){
		List<String> uidList = new ArrayList<>();
		uidList.add("8a9e9a9e543d23f901543d23f9e90000");
		uidList.add("7bbbf57f-6228-5e92-91dc-c9688d4398ce");
		uidList.add("4bf3aaf2-5f1f-4ff6-871c-1000de055665");
		List<CustomerBaseMsgEntity> queryCustomerBaseMsgByUidList = customerBaseMsgDao.queryCustomerListByUidList(uidList);
		System.err.println(JsonEntityTransform.Object2Json(queryCustomerBaseMsgByUidList));
	}
	
	@Test
	public void updateCustomerBaseMsgPortionTest(){
		
		CustomerBaseMsgEntity customerBaseMsgEntity=new CustomerBaseMsgEntity();
		customerBaseMsgEntity.setUid("9afeae98-56ff-0c35-77cf-8624b2e1efae");
		
		customerBaseMsgEntity.setNickName("冥愿1231231");
		customerBaseMsgEntity.setCustomerMobile("18800010103");
		customerBaseMsgEntity.setAuditStatus(0);
		customerBaseMsgEntity.setClearingCode("1");
		customerBaseMsgEntity.setCustomerSex(1);
		//customerBaseMsgEntity.setCustomerEmail("18800010103@139.com");

		customerBaseMsgEntity.setIsContactAuth(0);//是否认证
		customerBaseMsgEntity.setIsLandlord(0);//是否房东
		customerBaseMsgEntity.setIsUploadIcon(0);
		customerBaseMsgEntity.setLastModifyDate(new Date());
		customerBaseMsgEntity.setCreateDate(new Date());
		customerBaseMsgEntity.setNationCode("100000");
		customerBaseMsgEntity.setProvinceCode("110000");
		customerBaseMsgEntity.setCityCode("110100");
		customerBaseMsgEntity.setAreaCode("110105");
		customerBaseMsgEntity.setRentPayment(1);
		customerBaseMsgEntity.setResideAddr("朝阳区");
		customerBaseMsgEntity.setCustomerSource(CustomerSourceEnum.Api.getCode());
		
		customerBaseMsgEntity.setIsContactAuth(1);
		customerBaseMsgEntity.setRealName("大师哥12441f65dsf45d5");
		customerBaseMsgEntity.setIdType(2);
		customerBaseMsgEntity.setIdNo("420684199902111554");
		customerBaseMsgEntity.setCustomerBirthday(new Date());
	    int i = this.customerBaseMsgDao.updateCustomerBaseMsgPortion(customerBaseMsgEntity);
	    
	    System.out.println(i);
		
	}
	
	/*@Test
	public void testgetByCustomNameAndTel(){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("realName", "邓云霞");
		CustomerBaseMsgEntity customerListByUidList = customerBaseMsgDao.getByCustomNameAndTel(paramMap);
		System.err.println(customerListByUidList);
	}*/

}
