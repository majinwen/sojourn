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

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MD5Util;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.customer.CustomerRoleEntity;
import com.ziroom.minsu.entity.customer.CustomerUpdateHistoryExtLogEntity;
import com.ziroom.minsu.entity.customer.CustomerUpdateHistoryLogEntity;
import com.ziroom.minsu.entity.house.HouseBaseMsgEntity;
import com.ziroom.minsu.services.common.utils.ClassReflectUtils;
import com.ziroom.minsu.services.customer.dao.CustomerUpdateHistoryExtLogDao;
import com.ziroom.minsu.services.customer.dao.CustomerUpdateHistoryLogDao;
import com.ziroom.minsu.services.customer.test.BaseTest;
import com.ziroom.minsu.valenum.house.CreaterTypeEnum;
import com.ziroom.minsu.valenum.house.HouseSourceEnum;
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
public class CustomerUpdateHistoryExtLogDaoTest extends BaseTest {
	
	    @Resource(name="customer.customerUpdateHistoryExtLogDao")
	    private CustomerUpdateHistoryExtLogDao customerUpdateHistoryExtLogDao;

	    @Test
	    public void testcustomerUpdateHistoryExtLogDao(){
	    	CustomerUpdateHistoryExtLogEntity customerUpdateHistoryLog = new CustomerUpdateHistoryExtLogEntity();
	    	customerUpdateHistoryLog.setFid(UUIDGenerator.hexUUID());
	    	customerUpdateHistoryLog.setNewValue("1");
	    	customerUpdateHistoryLog.setOldValue("2");
	    	int insertSelective = customerUpdateHistoryExtLogDao.insertSelective(customerUpdateHistoryLog);
	        System.out.println(insertSelective);
	    }
	    
	    @Test
	    public void testscustomerUpdateHistoryExtLogDao(){
	    	CustomerUpdateHistoryExtLogEntity customerUpdateHistoryLog = new CustomerUpdateHistoryExtLogEntity();
	    	customerUpdateHistoryLog.setFid(UUIDGenerator.hexUUID());
	    	customerUpdateHistoryLog.setNewValue("1");
	    	customerUpdateHistoryLog.setOldValue("2");
	    	CustomerUpdateHistoryExtLogEntity selectByPrimaryKey = customerUpdateHistoryExtLogDao.selectByFid("8a9e9aaf5dabc66a015dabc66a5d0000");
	        System.out.println(selectByPrimaryKey);
	    }


}
