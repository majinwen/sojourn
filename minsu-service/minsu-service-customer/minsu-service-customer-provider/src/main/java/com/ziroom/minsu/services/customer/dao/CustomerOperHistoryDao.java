/**
 * @FileName: CustomerOperHistoryDao.java
 * @Package com.ziroom.minsu.services.customer.dao.map
 * 
 * @author jixd
 * @created 2016年4月23日 下午11:55:04
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.customer.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.customer.CustomerOperHistoryEntity;

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
@Repository("customer.customerOperHistoryDao")
public class CustomerOperHistoryDao {
	
    private String SQLID="customer.customerOperHistoryDao.";

    @Autowired
    @Qualifier("customer.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;
    /**
     * 
     * 创建审核历史记录
     *
     * @author jixd
     * @created 2016年4月24日 上午12:04:00
     *
     * @param customerOperHistoryEntity
     * @return
     */
    public int insertCustomerOperHistoryEntity(CustomerOperHistoryEntity customerOperHistoryEntity){
    	if(Check.NuNObj(customerOperHistoryEntity)){
    		return 0;
    	}
    	return mybatisDaoContext.save(SQLID+"insertSelective", customerOperHistoryEntity);
    }
    /**
     * 
     * 查询审核历史记录
     *
     * @author jixd
     * @created 2016年4月24日 上午12:05:28
     *
     * @param uid
     * @return
     */
    public List<CustomerOperHistoryEntity> queryHistoryList(String uid){
    	if(Check.NuNStr(uid)){
    		throw new BusinessException("uid is null");
    	}
    	return mybatisDaoContext.findAll(SQLID+"selectCustomerHistoryByUid", CustomerOperHistoryEntity.class, uid);
    }
	/**
	 * 否是在30天内审核通过的
	 * @author loushuai
	 * @created 2017年10月29日 下午1:17:22
	 *
	 * @param landlordUid
	 * @return
	 */
	public List<CustomerOperHistoryEntity> getIsAuditPassIn30Days(Map<String, Object> parmaMap) {
		return mybatisDaoContext.findAll(SQLID+"getIsAuditPassIn30Days", CustomerOperHistoryEntity.class, parmaMap);
	}
}
