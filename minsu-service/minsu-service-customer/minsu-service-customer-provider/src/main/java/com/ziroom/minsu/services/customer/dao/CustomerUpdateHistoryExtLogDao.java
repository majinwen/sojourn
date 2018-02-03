package com.ziroom.minsu.services.customer.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.customer.CustomerRoleEntity;
import com.ziroom.minsu.entity.customer.CustomerUpdateHistoryExtLogEntity;

@Repository("customer.customerUpdateHistoryExtLogDao")
public class CustomerUpdateHistoryExtLogDao {

	private String SQLID="customer.customerUpdateHistoryExtLogDao.";

	@Autowired
	@Qualifier("customer.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;

	/**
     * 插入实体
     * @author loushuai
     * @param uid
     * @return
     */
    public int insertSelective(CustomerUpdateHistoryExtLogEntity customerUpdateHistoryExtLogEntity) {
       return	mybatisDaoContext.save(SQLID+"insertSelective", customerUpdateHistoryExtLogEntity);
	}

    /**
     * 查询实体
     * @author loushuai
     * @param uid
     * @return
     */
    public CustomerUpdateHistoryExtLogEntity selectByPrimaryKey(Integer id) {
		return mybatisDaoContext.findOne(SQLID + "selectByPrimaryKey", CustomerUpdateHistoryExtLogEntity.class, id);
	}

    /**
     * 查询实体
     * @author loushuai
     * @param uid
     * @return
     */
    public CustomerUpdateHistoryExtLogEntity selectByFid(String fid) {
		return mybatisDaoContext.findOne(SQLID + "selectByFid", CustomerUpdateHistoryExtLogEntity.class, fid);
	}

}