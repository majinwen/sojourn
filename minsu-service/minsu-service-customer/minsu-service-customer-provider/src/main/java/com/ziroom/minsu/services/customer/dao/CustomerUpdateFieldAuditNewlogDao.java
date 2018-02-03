package com.ziroom.minsu.services.customer.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.customer.CustomerUpdateFieldAuditNewlogEntity;
import com.ziroom.minsu.entity.house.HouseUpdateFieldAuditNewlogEntity;

@Repository("customer.customerUpdateFieldAuditNewlogDao")
public class CustomerUpdateFieldAuditNewlogDao {

	private String SQLID="customer.customerUpdateFieldAuditNewlogDao.";

	@Autowired
	@Qualifier("customer.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;

	/**
	 * 
	 * 插入实体
	 *
	 * @author loushuai
	 * @created 2017年8月4日 下午2:20:09
	 *
	 * @param customerUpdateHistoryExtLogEntity
	 * @return
	 */
	public int insertSelective(CustomerUpdateFieldAuditNewlogEntity customerUpdateFieldAuditNewlogEntity) {
	    return	mybatisDaoContext.save(SQLID+"insertSelective", customerUpdateFieldAuditNewlogEntity);
	}

	/**
	 * 
	 * 根据uid获取实体
	 *
	 * @author loushuai
	 * @created 2017年8月4日 下午2:20:09
	 *
	 * @param customerUpdateHistoryExtLogEntity
	 * @return
	 */
    public CustomerUpdateFieldAuditNewlogEntity selectByUid(String uid) {
    	if(Check.NuNStr(uid)){
			return null;
		}
    	CustomerUpdateFieldAuditNewlogEntity findOne = mybatisDaoContext.findOne(SQLID + "selectByUid", CustomerUpdateFieldAuditNewlogEntity.class, uid);
		return findOne;
	}

    /**
	 * 
	 * 根据 fid查询 
	 *
	 * @author loushuai
	 * @created 2017年8月7日 下午3:20:36
	 *
	 * @param fid
	 * @return
	 */
	public CustomerUpdateFieldAuditNewlogEntity findCustomerUpdateFieldAuditNewlogByFid(String fid){

		return mybatisDaoContext.findOne(SQLID+"findCustomerUpdateFieldAuditNewlogByFid", CustomerUpdateFieldAuditNewlogEntity.class, fid);
	}

	/**
	 * 根据id跟新对象
	 *
	 * @author loushuai
	 * @created 2017年8月9日 上午11:27:05
	 *
	 * @param customerUpdateFieldAuditNewlog
	 * @return
	 */
	public int updateCustomerUpdateFieldAuditNewlogByFid(CustomerUpdateFieldAuditNewlogEntity customerUpdateFieldAuditNewlog) {
		return mybatisDaoContext.update(SQLID+"updateCustomerUpdateFieldAuditNewlogByFid", customerUpdateFieldAuditNewlog);
	}

	/**
	 * 获取所有需要审核的房东列表
	 *
	 * @author loushuai
	 * @created 2017年8月30日 上午10:09:35
	 *
	 * @param paramMap
	 * @return
	 */
	public List<CustomerUpdateFieldAuditNewlogEntity> getAllNeedAuditLand(Map<String, Object> paramMap) {
		return mybatisDaoContext.findAll(SQLID+"getAllNeedAuditLand", CustomerUpdateFieldAuditNewlogEntity.class, paramMap);
	}

}