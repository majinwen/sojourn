/**
 * @FileName: CustomerOperHistoryTest.java
 * @Package com.ziroom.minsu.services.customer.test.dao
 * 
 * @author jixd
 * @created 2016年4月25日 上午11:03:43
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.customer.test.dao;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.customer.CustomerOperHistoryEntity;
import com.ziroom.minsu.services.customer.dao.CustomerOperHistoryDao;
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
 * @author jixd
 * @since 1.0
 * @version 1.0
 */
public class CustomerOperHistoryTest extends BaseTest{
	
	@Resource(name="customer.customerOperHistoryDao")
	private CustomerOperHistoryDao customerOperHistoryDao;
	
	@Test
	public void testInserHistory(){
		CustomerOperHistoryEntity entity = new CustomerOperHistoryEntity();
		entity.setAuditAfterStatus(0);
		entity.setAuditBeforeStatus(1);
		entity.setCreateTime(new Date());		
		entity.setFid(UUIDGenerator.hexUUID());
		entity.setOperRemark("审核通过");
		entity.setUid("8a9e9a9f544b372101544b3721de0000");
		entity.setOperUid("14122555");
		customerOperHistoryDao.insertCustomerOperHistoryEntity(entity);
	}
	
	@Test
	public void testQueryHistory(){
		List<CustomerOperHistoryEntity> queryHistoryList = customerOperHistoryDao.queryHistoryList("8a9e9a9f544b372101544b3721de0000");
		
		System.out.println(queryHistoryList.size());
	}
}
