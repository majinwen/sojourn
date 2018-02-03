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
import com.ziroom.minsu.entity.customer.CustomerUpdateFieldAuditManagerEntity;
import com.ziroom.minsu.entity.customer.CustomerUpdateFieldAuditNewlogEntity;
import com.ziroom.minsu.entity.house.HouseBaseExtEntity;
import com.ziroom.minsu.entity.house.HouseBaseMsgEntity;
import com.ziroom.minsu.entity.house.HouseUpdateFieldAuditManagerEntity;
import com.ziroom.minsu.services.common.utils.ClassReflectUtils;
import com.ziroom.minsu.services.customer.dao.CustomerUpdateFieldAuditManagerDao;
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
public class CustomerUpdateFieldAuditManagerDaoTest extends BaseTest {
	
	    @Resource(name="customer.customerUpdateFieldAuditManagerDao")
	    private CustomerUpdateFieldAuditManagerDao customerUpdateFieldAuditManagerDao;

	    @Test
	    public void testcustomerUpdateFieldAuditManagerDao(){
	

	    	CustomerUpdateFieldAuditManagerEntity customerUpdateFieldAuditManager = new CustomerUpdateFieldAuditManagerEntity();
	    	customerUpdateFieldAuditManager.setFieldPath(ClassReflectUtils.getFieldNamePath(HouseBaseExtEntity.class, HouseUpdateLogEnum.House_Base_Ext_Default_Pic_Fid.getFieldName()));
	    	customerUpdateFieldAuditManager.setFid(MD5Util.MD5Encode(customerUpdateFieldAuditManager.getFieldPath(), "UTF-8"));
	    	customerUpdateFieldAuditManager.setFieldDesc(HouseUpdateLogEnum.House_Base_Ext_Default_Pic_Fid.getFieldDesc());
	    	customerUpdateFieldAuditManager.setCreaterFid("001");
			int i = customerUpdateFieldAuditManagerDao.insertSelective(customerUpdateFieldAuditManager);
			System.out.println(i);

	    }
	    
	    @Test
	    public void testsustomerUpdateHistoryExtLogDao(){//
	    	CustomerUpdateFieldAuditManagerEntity  selectByFid= customerUpdateFieldAuditManagerDao.selectByFid("ab5d2e8abd68bdd7d6afd28fba8ad576");
	    	System.out.println(selectByFid);
	    }


}
