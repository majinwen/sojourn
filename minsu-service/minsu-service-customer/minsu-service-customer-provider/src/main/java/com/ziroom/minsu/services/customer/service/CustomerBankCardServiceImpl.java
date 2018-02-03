/**
 * @FileName: CustomerBankCardServiceImpl.java
 * @Package com.ziroom.minsu.services.customer.service
 * 
 * @author bushujie
 * @created 2016年4月25日 下午4:42:19
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.customer.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ziroom.minsu.entity.customer.CustomerBankCardMsgEntity;
import com.ziroom.minsu.services.customer.dao.CustomerBankCardMsgDao;

/**
 * <p>客户银行卡信息业务实现</p>
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
@Service("customer.customerBankCardServiceImpl")
public class CustomerBankCardServiceImpl {
	
	@Resource(name="customer.customerBankCardMsgDao")
	private CustomerBankCardMsgDao customerBankCardMsgDao;
	
	/**
	 * 
	 * 插入客户银行卡信息
	 *
	 * @author bushujie
	 * @created 2016年4月25日 下午4:47:08
	 *
	 * @param customerBankCardMsgEntity
	 */
	public void insertCustomerBankCard(CustomerBankCardMsgEntity customerBankCardMsgEntity){
    	customerBankCardMsgDao.insertCustomerBankCard(customerBankCardMsgEntity);
    }
	
	/**
	 * 
	 * 获取客户银行卡信息
	 *
	 * @author bushujie
	 * @created 2016年4月25日 下午4:48:46
	 *
	 * @param uid
	 * @return
	 */
    public CustomerBankCardMsgEntity getCustomerBankCardByUid(String uid){
    	return customerBankCardMsgDao.getCustomerBankCardByUid(uid);
    }
    
    /**
     * 根据fid,uid查询客户银行卡信息
     * @author lishaochuan
     * @create 2016年8月16日下午7:02:26
     * @param fid
     * @param uid
     * @return
     */
    public CustomerBankCardMsgEntity getCustomerBankCardByFid(String fid, String uid){
    	return customerBankCardMsgDao.getCustomerBankCardByFid(fid, uid);
    }
    
    /**
     * 
     * 更新客户银行卡信息
     *
     * @author bushujie
     * @created 2016年4月25日 下午5:10:56
     *
     * @param customerBankCardMsgEntity
     * @return
     */
    public int updateCustomerBankCardByUid(CustomerBankCardMsgEntity customerBankCardMsgEntity){
    	return customerBankCardMsgDao.updateCustomerBankCardByUid(customerBankCardMsgEntity);
    }
}
