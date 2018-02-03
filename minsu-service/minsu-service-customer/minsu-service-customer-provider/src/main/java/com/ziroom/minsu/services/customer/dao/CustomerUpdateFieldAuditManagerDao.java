package com.ziroom.minsu.services.customer.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.customer.CustomerUpdateFieldAuditManagerEntity;
import com.ziroom.minsu.entity.house.HouseUpdateFieldAuditManagerEntity;

@Repository("customer.customerUpdateFieldAuditManagerDao")
public class CustomerUpdateFieldAuditManagerDao {

	String SQLID = "customer.customerUpdateFieldAuditManagerDao.";
	
	@Autowired
	@Qualifier("customer.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;
	
	/**
	 * 
	 * 插入实体
	 *
	 * @author loushuai
	 * @created 2017年8月4日 下午2:20:43
	 *
	 * @param record
	 * @return
	 */
    public int insertSelective(CustomerUpdateFieldAuditManagerEntity record) {
		return mybatisDaoContext.save(SQLID+"insertSelective", record);
	}

	/**
	 * 
	 * 根据fid获取实体
	 *
	 * @author loushuai
	 * @created 2017年8月4日 下午2:20:43
	 *
	 * @param record
	 * @return
	 */
    public CustomerUpdateFieldAuditManagerEntity selectByFid(String fid) {
    	if(Check.NuNStr(fid)){
			return null;
		}
		return mybatisDaoContext.findOne(SQLID + "selectByFid", CustomerUpdateFieldAuditManagerEntity.class, fid);
	}

    /**
	 * 
	 * 根据 fid查询 
	 *
	 * @author yd
	 * @created 2017年7月3日 下午3:20:36
	 *
	 * @param fid
	 * @return
	 */
	public CustomerUpdateFieldAuditManagerEntity findCustomerUpdateFieldAuditManagerByFid(String fid){

		return mybatisDaoContext.findOne(SQLID+"findCustomerUpdateFieldAuditManagerByFid", CustomerUpdateFieldAuditManagerEntity.class, fid);
	}
}
