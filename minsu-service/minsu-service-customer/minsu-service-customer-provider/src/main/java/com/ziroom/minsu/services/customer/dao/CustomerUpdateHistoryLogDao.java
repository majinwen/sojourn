package com.ziroom.minsu.services.customer.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.customer.CustomerRoleEntity;
import com.ziroom.minsu.entity.customer.CustomerUpdateHistoryLogEntity;
import com.ziroom.minsu.services.customer.dto.CustomerFieldAuditVo;

@Repository("customer.customerUpdateHistoryLogDao")
public class CustomerUpdateHistoryLogDao {
	
	private String SQLID="customer.customerUpdateHistoryLogDao.";

    @Autowired
    @Qualifier("customer.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;
	
    /**
     * 插入实体
     * @author loushuai
     * @param uid
     * @return
     */
    public int insertSelective(CustomerUpdateHistoryLogEntity customerUpdateHistoryLog) {
    	return mybatisDaoContext.save(SQLID+"insertSelective", customerUpdateHistoryLog);
	}

    /**
     * 获取当前用户的实体
     * @author loushuai
     * @param uid
     * @return
     */
    public CustomerUpdateHistoryLogEntity selectByUid(String uid) {
    	if(Check.NuNStr(uid)){
			return null;
		}
		return mybatisDaoContext.findOne(SQLID + "selectByUid", CustomerUpdateHistoryLogEntity.class, uid);
	}

	/**
	 * 根据条件获取所有需要审核的字段最新信息
	 *
	 * @author loushuai
	 * @created 2017年8月8日 下午3:48:47
	 *
	 * @param paramMap
	 * @return
	 */
	public List<CustomerFieldAuditVo> getFieldAuditNewLogByParam(Map<String, Object> paramMap) {
		return mybatisDaoContext.findAll(SQLID + "getFieldAuditNewLogByParam", CustomerFieldAuditVo.class, paramMap);
	}

}