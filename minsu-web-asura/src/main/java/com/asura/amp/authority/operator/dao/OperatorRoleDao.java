/**
 * @FileName: OperatorRoleDao.java
 * @Package com.asura.amp.authority.operator.dao
 * 
 * @author zhangshaobin
 * @created 2013-1-28 下午1:49:04
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.amp.authority.operator.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.asura.amp.authority.entity.OperatorRole;
import com.asura.framework.dao.old.BaseIbatisDAO;
import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * <p>操作员角色对应关系持久化层</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zhangshaobin
 * @since 1.0
 * @version 1.0
 */
@Repository("operatorRoleDao")
public class OperatorRoleDao extends BaseIbatisDAO{
	
	@Autowired
	public void setSystemSqlMapClient(SqlMapClient ampSqlMapClient) {
		super.setCurrentSqlMapClient(ampSqlMapClient);
	}
	
	/**
	 * 
	 * 保存
	 *
	 * @author zhangshaobin
	 * @created 2013-1-28 下午1:51:18
	 *
	 * @param operatorRole
	 */
	public void save(OperatorRole operatorRole){
		this.save("com.asura.management.authority.operatorRole.dao.save", operatorRole);
	}
	
	/**
	 * 
	 * 根据操作员id删除对应的角色关系信息
	 *
	 * @author zhangshaobin
	 * @created 2013-1-28 下午1:52:29
	 *
	 * @param map
	 * @return
	 */
	public int deleteByOperatorId(Map<String, Object> map){
		return this.delete("com.asura.management.authority.operatorRole.dao.deleteByOperatorId", map);
	}
	
	/**
	 * 
	 * 根据操作员id删除对应的角色关系信息
	 *
	 * @author zhangshaobin
	 * @created 2013-1-28 下午2:22:10
	 *
	 * @param map
	 * @return
	 */
	public List<OperatorRole> findAllByOperatorId(Map<String , Object> map){
		return this.findAll("com.asura.management.authority.operatorRole.dao.findAllByOperatorId", map);
	}

}
