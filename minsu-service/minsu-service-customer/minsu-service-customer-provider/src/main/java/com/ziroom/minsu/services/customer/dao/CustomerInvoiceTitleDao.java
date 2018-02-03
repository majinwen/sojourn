/**
 * @FileName: CustomerInvoiceTitleDao.java
 * @Package com.ziroom.minsu.services.customer.dao
 * 
 * @author bushujie
 * @created 2016年4月25日 下午2:30:30
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.customer.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.customer.CustomerInvoiceTitleEntity;

/**
 * <p>客户发票抬头信息DAO</p>
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
@Repository("customer.customerInvoiceTitleDao")
public class CustomerInvoiceTitleDao {
	
	private String SQLID="customer.customerInvoiceTitleDao.";

    @Autowired
    @Qualifier("customer.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;
    
    /**
     * 
     * 插入用户发票抬头
     *
     * @author bushujie
     * @created 2016年4月25日 下午2:35:09
     *
     * @param customerInvoiceTitleEntity
     */
    public void insertCustomerInvoiceTitle(CustomerInvoiceTitleEntity customerInvoiceTitleEntity){
    	mybatisDaoContext.save(SQLID+"insertCustomerInvoiceTitle", customerInvoiceTitleEntity);
    }
    
}
