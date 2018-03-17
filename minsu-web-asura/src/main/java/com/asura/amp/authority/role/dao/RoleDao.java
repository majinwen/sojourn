/**
 * @FileName: RoleDao.java
 * @Package com.asura.amp.authority.role.dao
 * 
 * @author zhangshaobin
 * @created 2013-1-27 上午10:43:11
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.amp.authority.role.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.asura.amp.authority.entity.Role;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchModel;
import com.asura.framework.dao.old.BaseIbatisDAO;
import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * <p>
 * TODO
 * </p>
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
@Repository("roleDao")
public class RoleDao extends BaseIbatisDAO {
	@Autowired
	public void setSystemSqlMapClient(SqlMapClient ampSqlMapClient) {
		super.setCurrentSqlMapClient(ampSqlMapClient);
	}

	/**
	 * 
	 * 保存
	 * 
	 * @author zhangshaobin
	 * @created 2013-1-26 下午2:07:08
	 * 
	 * @param role
	 */
	public void save(Role role) {
		this.save("com.asura.management.authority.role.dao.save", role);
	}

	/**
	 * 
	 * 更新
	 * 
	 * @author zhangshaobin
	 * @created 2013-1-26 下午3:10:12
	 * 
	 * @param role
	 */
	public void update(Role role) {
		this.update("com.asura.management.authority.role.dao.update", role);
	}

	/**
	 * 
	 * 按照条件查询
	 * 
	 * @author zhangshaobin
	 * @created 2013-1-26 下午3:12:09
	 * 
	 * @param searchModel
	 * @return
	 */
	public PagingResult<Role> findForPage(SearchModel searchModel) {
		return this
				.findForPage(
						"com.asura.management.authority.role.dao.findCountBySearchCondition",// 查询记录数的sqlid
						"com.asura.management.authority.role.dao.findBySearchCondition",// 查询数据的sqlid
						searchModel, Role.class);
	}

	/**
	 * 
	 * 删除
	 * 
	 * @author zhangshaobin
	 * @created 2013-1-26 下午3:31:31
	 * 
	 * @param ids
	 * @return
	 */
	public int delete(Map<String, Object> map) {
		return this.delete("com.asura.management.authority.role.dao.delete", map);
	}
	
	/**
	 * 
	 * 查询所有的角色信息
	 *
	 * @author zhangshaobin
	 * @created 2013-1-28 下午1:18:19
	 *
	 * @return
	 */
	public List<Role> findAll(){
		return this.findAll("com.asura.management.authority.role.dao.findAll");
	}
	
}
