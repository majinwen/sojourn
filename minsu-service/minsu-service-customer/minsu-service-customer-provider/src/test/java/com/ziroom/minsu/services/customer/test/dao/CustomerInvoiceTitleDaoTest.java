/**
 * @FileName: CustomerInvoiceTitleDaoTest.java
 * @Package com.ziroom.minsu.services.customer.test.dao
 * 
 * @author bushujie
 * @created 2016年4月25日 下午2:39:10
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.customer.test.dao;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.customer.CustomerInvoiceTitleEntity;
import com.ziroom.minsu.services.customer.dao.CustomerInvoiceTitleDao;
import com.ziroom.minsu.services.customer.test.BaseTest;

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
public class CustomerInvoiceTitleDaoTest extends BaseTest{
	
	@Resource(name="customer.customerInvoiceTitleDao")
	private CustomerInvoiceTitleDao customerInvoiceTitleDao;
	
	@Test
	public void insertCustomerInvoiceTitle(){
		CustomerInvoiceTitleEntity customerInvoiceTitleEntity=new CustomerInvoiceTitleEntity();
		customerInvoiceTitleEntity.setFid(UUIDGenerator.hexUUID());
		customerInvoiceTitleEntity.setInvoiceTitle("北京链家经济有限公司");
		customerInvoiceTitleEntity.setUid("8a9e9a9e543d23f901543d23f9e90000");
		customerInvoiceTitleDao.insertCustomerInvoiceTitle(customerInvoiceTitleEntity);
	}
}
