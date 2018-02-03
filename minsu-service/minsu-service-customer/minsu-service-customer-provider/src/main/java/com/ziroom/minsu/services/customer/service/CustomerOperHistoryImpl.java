/**
 * @FileName: CustomerOperHistoryImpl.java
 * @Package com.ziroom.minsu.services.customer.service
 * 
 * @author jixd
 * @created 2016年4月24日 上午12:12:07
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.customer.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ziroom.minsu.entity.customer.CustomerOperHistoryEntity;
import com.ziroom.minsu.entity.customer.CustomerUpdateFieldAuditNewlogEntity;
import com.ziroom.minsu.services.customer.dao.CustomerOperHistoryDao;
import com.ziroom.minsu.services.customer.dao.CustomerUpdateFieldAuditNewlogDao;
import com.ziroom.minsu.services.customer.dao.CustomerUpdateHistoryLogDao;
import com.ziroom.minsu.services.customer.dto.CustomerFieldAuditVo;

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
@Service("customer.customerOperHistoryImpl")
public class CustomerOperHistoryImpl {
	
	@Resource(name="customer.customerOperHistoryDao")
	private CustomerOperHistoryDao customerOperHistoryDao;
	
	@Resource(name="customer.customerUpdateHistoryLogDao")
	private CustomerUpdateHistoryLogDao customerUpdateHistoryLogDao;
	
	@Resource(name="customer.customerUpdateFieldAuditNewlogDao")
	private CustomerUpdateFieldAuditNewlogDao customerUpdateFieldAuditNewlogDao;
	
	
	/**
	 * 
	 * 插入操作历史记录
	 *
	 * @author jixd
	 * @created 2016年4月24日 上午12:15:06
	 *
	 * @param customerOperHistory
	 * @return
	 */
	public int insertCustomerOperHistory(CustomerOperHistoryEntity customerOperHistory ){
		return customerOperHistoryDao.insertCustomerOperHistoryEntity(customerOperHistory);
	}
	
	/**
	 * 
	 * 查询操作历史记录
	 *
	 * @author jixd
	 * @created 2016年4月24日 上午12:16:17
	 *
	 * @param uid
	 * @return
	 */
	public List<CustomerOperHistoryEntity> queryHistoryList(String uid){
		return customerOperHistoryDao.queryHistoryList(uid);
	}

	/**
	 * 根据条件获取所有需要审核的字段最新信息
	 *
	 * @author loushuai
	 * @created 2017年8月8日 下午3:47:17
	 *
	 * @param paramMap
	 * @return
	 */
	public List<CustomerFieldAuditVo> getFieldAuditNewLogByParam(Map<String, Object> paramMap) {
		return customerUpdateHistoryLogDao.getFieldAuditNewLogByParam(paramMap);
	}

	/**
	 * 获取所有需要审核的房东列表
	 *
	 * @author loushuai
	 * @created 2017年8月30日 上午10:07:01
	 *
	 * @return
	 */
	public List<CustomerUpdateFieldAuditNewlogEntity> getAllNeedAuditLand(Map<String, Object> paramMap) {
		return customerUpdateFieldAuditNewlogDao.getAllNeedAuditLand(paramMap);
	}

	/**
	 * 否是在30天内审核通过的
	 *
	 * @author loushuai
	 * @created 2017年10月29日 下午1:12:19
	 *
	 * @param landlordUid
	 * @return 
	 */
	public List<CustomerOperHistoryEntity> getIsAuditPassIn30Days(Map<String, Object> parmaMap ) {
		return customerOperHistoryDao.getIsAuditPassIn30Days(parmaMap);
	}
}
