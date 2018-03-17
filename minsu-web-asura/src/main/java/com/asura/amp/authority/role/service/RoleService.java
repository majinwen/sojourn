/**
 * @FileName: RoleService.java
 * @Package com.asura.amp.authority.role.service
 * 
 * @author zhangshaobin
 * @created 2013-1-27 上午10:45:20
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.amp.authority.role.service;

import java.util.List;
import java.util.Map;

import com.asura.amp.authority.entity.Role;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchModel;

/**
 * <p>
 * 角色信息服务接口
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
public interface RoleService {

	/**
	 * 
	 * 保存
	 * 
	 * @author zhangshaobin
	 * @created 2013-1-26 下午2:07:08
	 * 
	 * @param role
	 */
	public void save(Role role);

	/**
	 * 
	 * 更新
	 * 
	 * @author zhangshaobin
	 * @created 2013-1-26 下午3:10:12
	 * 
	 * @param role
	 */
	public void update(Role role);

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
	public PagingResult<Role> findForPage(SearchModel searchModel);

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
	public int delete(Map<String, Object> map);
	
	/**
	 * 
	 * 分配资源
	 *
	 * @author zhangshaobin
	 * @created 2013-1-27 下午4:07:31
	 *
	 * @param roleId 角色id
	 * @param resIds 资源id
	 */
	public void allotResource(String roleId, String resIds);
	
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
	public int deleteByRoleId(String roleId);

	/**
	 * 
	 * 查询所有的角色信息
	 *
	 * @author zhangshaobin
	 * @created 2013-1-28 下午1:18:19
	 *
	 * @return
	 */
	public List<Role> findAll();
}
