/**
 * @FileName: CustomerInvoiceTitleServiceImpl.java
 * @Package com.ziroom.minsu.services.customer.service
 * 
 * @author bushujie
 * @created 2016年4月25日 下午2:47:06
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.customer.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ziroom.minsu.entity.customer.CustomerInvoiceTitleEntity;
import com.ziroom.minsu.services.customer.dao.CustomerInvoiceTitleDao;

/**
 * <p>客户发票抬头业务实现</p>
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
@Service("customer.customerInvoiceTitleServiceImpl")
public class CustomerInvoiceTitleServiceImpl {
	
	@Resource(name="customer.customerInvoiceTitleDao")
	private CustomerInvoiceTitleDao customerInvoiceTitleDao;
	
	/**
	 * 
	 * 插入客户发票抬头
	 *
	 * @author bushujie
	 * @created 2016年4月25日 下午2:50:29
	 *
	 * @param customerInvoiceTitleEntity
	 */
	public void insertCustomerInvoiceTitle(CustomerInvoiceTitleEntity customerInvoiceTitleEntity){
		customerInvoiceTitleDao.insertCustomerInvoiceTitle(customerInvoiceTitleEntity);
	}
}
