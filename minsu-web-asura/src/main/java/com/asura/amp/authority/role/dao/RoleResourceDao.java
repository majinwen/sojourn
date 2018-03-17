/**
 * @FileName: RoleResourceDao.java
 * @Package com.asura.amp.authority.role.dao
 * 
 * @author zhangshaobin
 * @created 2013-1-27 下午4:17:21
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.amp.authority.role.dao;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.asura.amp.authority.entity.RoleResource;
import com.asura.framework.dao.old.BaseIbatisDAO;
import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * <p>TODO</p>
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
@Repository("roleResourceDao")
public class RoleResourceDao extends BaseIbatisDAO {
	
	@Autowired
	public void setSystemSqlMapClient(SqlMapClient ampSqlMapClient) {
		super.setCurrentSqlMapClient(ampSqlMapClient);
	}
	
	/**
	 * 
	 * 保存
	 *
	 * @author zhangshaobin
	 * @created 2013-1-27 下午4:22:37
	 *
	 * @param roleResource 角色资源信息
	 */
	public void save(RoleResource roleResource){
		this.save("com.asura.management.authority.roleResource.dao.save", roleResource);
	}
	
	/**
	 * 
	 * 根据角色id删除对应的资源信息
	 *
	 * @author zhangshaobin
	 * @created 2013-1-28 上午11:39:01
	 *
	 * @param map
	 * @return
	 */
	public int deleteByRoleId(Map<String, Object> map){
		return this.delete("com.asura.management.authority.roleResource.dao.deleteByRoleId", map);
	}
	
	/**
	 * 
	 * 根据资源id删除对应信息
	 *
	 * @author zhangshaobin
	 * @created 2013-1-28 上午11:39:01
	 *
	 * @param map
	 * @return
	 */
	public int deleteByResId(Map<String, Object> map){
		return this.delete("com.asura.management.authority.roleResource.dao.deleteByResId", map);
	}
	

}
