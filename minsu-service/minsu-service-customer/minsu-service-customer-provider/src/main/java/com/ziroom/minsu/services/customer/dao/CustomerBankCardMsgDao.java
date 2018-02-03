/**
 * @FileName: CustomerBankCardMsgDao.java
 * @Package com.ziroom.minsu.services.customer.dao
 * 
 * @author bushujie
 * @created 2016年4月25日 下午3:22:43
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.customer.dao;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.customer.CustomerBankCardMsgEntity;

/**
 * <p>客户银行卡信息</p>
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
@Repository("customer.customerBankCardMsgDao")
public class CustomerBankCardMsgDao {
	
    private String SQLID="customer.customerBankCardMsgDao.";

    @Autowired
    @Qualifier("customer.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;
    
    /**
     * 
     * 插入客户银行卡信息
     *
     * @author bushujie
     * @created 2016年4月25日 下午3:27:49
     *
     * @param customerBankCardMsgEntity
     */
    public void insertCustomerBankCard(CustomerBankCardMsgEntity customerBankCardMsgEntity){
    	mybatisDaoContext.save(SQLID+"insertCustomerBankCard", customerBankCardMsgEntity);
    }
    
    /**
     * 
     * uid查询客户银行卡信息
     *
     * @author bushujie
     * @created 2016年4月25日 下午4:17:34
     *
     * @param uid
     */
    public CustomerBankCardMsgEntity getCustomerBankCardByUid(String uid){
    	return mybatisDaoContext.findOne(SQLID+"getCustomerBankCardByUid", CustomerBankCardMsgEntity.class, uid);
    }
    
    
    /**
     * 根据fid,uid查询客户银行卡信息
     * @author lishaochuan
     * @create 2016年8月16日下午7:01:16
     * @param fid
     * @param uid
     * @return
     */
    public CustomerBankCardMsgEntity getCustomerBankCardByFid(String fid, String uid){
    	Map<String, Object> map = new HashMap<String, Object>();
		map.put("fid", fid);
		map.put("uid", uid);
    	return mybatisDaoContext.findOne(SQLID+"getCustomerBankCardByFid", CustomerBankCardMsgEntity.class, map);
    }
    
    /**
     * 
     * 更新客户银行卡信息 
     *
     * @author bushujie
     * @created 2016年4月25日 下午5:02:39
     *
     * @param customerBankCardMsgEntity
     * @return
     */
    public int updateCustomerBankCardByUid(CustomerBankCardMsgEntity customerBankCardMsgEntity){
		return mybatisDaoContext.update(SQLID+"updateCustomerBankCard", customerBankCardMsgEntity);
    }
}
