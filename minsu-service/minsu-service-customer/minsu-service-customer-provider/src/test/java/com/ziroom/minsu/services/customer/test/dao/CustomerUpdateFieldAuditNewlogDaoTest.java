/**
 * @FileName: CustomerUpdateHistoryLogDaoTest.java
 * @Package com.ziroom.minsu.services.customer.test.dao
 * 
 * @author loushuai
 * @created 2017年8月4日 下午1:14:47
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.customer.test.dao;


import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.MD5Util;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.customer.CustomerUpdateFieldAuditNewlogEntity;
import com.ziroom.minsu.entity.house.HouseBaseMsgEntity;
import com.ziroom.minsu.services.common.utils.ClassReflectUtils;
import com.ziroom.minsu.services.customer.dao.CustomerUpdateFieldAuditNewlogDao;
import com.ziroom.minsu.services.customer.test.BaseTest;
import com.ziroom.minsu.valenum.customer.AuditStatusEnum;
import com.ziroom.minsu.valenum.house.CreaterTypeEnum;
import com.ziroom.minsu.valenum.house.HouseUpdateLogEnum;
import com.ziroom.minsu.valenum.house.IsTextEnum;

/**
 * <p>TODO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author loushuai
 * @since 1.0
 * @version 1.0
 */
public class CustomerUpdateFieldAuditNewlogDaoTest extends BaseTest {
	
	    @Resource(name="customer.customerUpdateFieldAuditNewlogDao")
	    private CustomerUpdateFieldAuditNewlogDao customerUpdateFieldAuditNewlogDao;

	    @Test
	    public void testcustomerUpdateHistoryExtLogDao(){
	    	
	    	CustomerUpdateFieldAuditNewlogEntity customerUpdateFieldAuditNewlogEntity = new CustomerUpdateFieldAuditNewlogEntity();

	    	String houseFid = UUIDGenerator.hexUUID();
			String roomFid =UUIDGenerator.hexUUID();
			int rentWay = 1;
			customerUpdateFieldAuditNewlogEntity.setFieldPath(ClassReflectUtils.getFieldNamePath(HouseBaseMsgEntity.class,HouseUpdateLogEnum.House_Base_Msg_House_Name.getFieldName()));
			customerUpdateFieldAuditNewlogEntity.setFieldDesc(HouseUpdateLogEnum.House_Base_Msg_House_Name.getFieldDesc());
			customerUpdateFieldAuditNewlogEntity.setFid(MD5Util.MD5Encode(houseFid+roomFid+rentWay+customerUpdateFieldAuditNewlogEntity.getFieldPath(), "UTF-8"));
			customerUpdateFieldAuditNewlogEntity.setCreaterFid("001");
			customerUpdateFieldAuditNewlogEntity.setCreaterType(CreaterTypeEnum.OTHER.getCode());
			customerUpdateFieldAuditNewlogEntity.setFieldAuditStatu(AuditStatusEnum.SUBMITAUDIT.getCode());
			customerUpdateFieldAuditNewlogEntity.setUid("8a9e9aaf5dabc66a015dabc66a5d0000");
			customerUpdateFieldAuditNewlogDao.insertSelective(customerUpdateFieldAuditNewlogEntity);
	    }
	    
	    @Test
	    public void testsustomerUpdateHistoryExtLogDao(){
	    	CustomerUpdateFieldAuditNewlogEntity selectByUid = customerUpdateFieldAuditNewlogDao.selectByUid("8a9e9aaf5dabc66a015dabc66a5d0000");
	        System.out.println(selectByUid);
	    }
	    
	    @Test
	    public void testupdateCustomerUpdateFieldAuditNewlogById(){
	    	CustomerUpdateFieldAuditNewlogEntity customerUpdateFieldAuditNewlogEntity = new CustomerUpdateFieldAuditNewlogEntity();
			customerUpdateFieldAuditNewlogEntity.setFid("fdasfsdfsdfsdfsdf");
			customerUpdateFieldAuditNewlogEntity.setFieldAuditStatu(2);
	    	 int a = customerUpdateFieldAuditNewlogDao.updateCustomerUpdateFieldAuditNewlogByFid(customerUpdateFieldAuditNewlogEntity);
	        System.out.println(a);
	    }


}
