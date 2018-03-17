/**
 * @FileName: RoleServiceImpl.java
 * @Package com.asura.amp.authority.role.service.impl
 * 
 * @author zhangshaobin
 * @created 2013-1-27 上午10:45:57
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.amp.authority.role.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asura.amp.authority.entity.Role;
import com.asura.amp.authority.entity.RoleResource;
import com.asura.amp.authority.role.dao.RoleDao;
import com.asura.amp.authority.role.dao.RoleResourceDao;
import com.asura.amp.authority.role.service.RoleService;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchModel;

/**
 * <p>
 * 角色信息服务接口实现类
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
@Service("roleService")
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private RoleResourceDao roleResourceDao;

	@Override
	public void save(Role role) {
		this.roleDao.save(role);
	}

	@Override
	public void update(Role role) {
		this.roleDao.update(role);
	}

	@Override
	public PagingResult<Role> findForPage(SearchModel searchModel) {
		return this.roleDao.findForPage(searchModel);
	}

	@Override
	public int delete(Map<String, Object> map) {
		return this.roleDao.delete(map);
	}
	
	@Override
	public void allotResource(String roleId, String resIds){
		String []resIdArr = resIds.split(",");
		RoleResource roleResource = new RoleResource();
		for(String resId : resIdArr){
			roleResource.setRoleId(Integer.parseInt(roleId));
			roleResource.setResId(Integer.parseInt(resId));
			this.roleResourceDao.save(roleResource);
		}
	}
	
	@Override
	public int deleteByRoleId(String roleId){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("roleId", roleId);
		return this.roleResourceDao.deleteByRoleId(map);
	}
	
	@Override
	public List<Role> findAll(){
		return this.roleDao.findAll();
	}

}
