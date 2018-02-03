package com.ziroom.minsu.services.basedata.dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.ziroom.minsu.entity.sys.RoleEntity;
import com.ziroom.minsu.services.basedata.dto.RoleRequest;
import com.ziroom.minsu.services.basedata.entity.RoleVo;

/**
 * 
 * <p>后台角色数据库操作类</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liujun
 * @since 1.0
 * @version 1.0
 */
@Repository("basedata.roleDao")
public class RoleDao {
	
	private String SQLID="basedata.roleDao.";
	
	@Autowired
	@Qualifier("basedata.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;
	
	public void insertRole(RoleEntity roleEntity) {
		mybatisDaoContext.save(SQLID+"insertRole", roleEntity);
	}
	
	/**
	 * 分页查询角色列表
	 *
	 * @author liujun
	 * @created 2016-3-10 下午7:44:43
	 *
	 * @param roleRequest
	 * @return
	 */
	public PagingResult<RoleVo> findRolePageList(RoleRequest roleRequest) {
		PageBounds pageBounds=new PageBounds();
		pageBounds.setLimit(roleRequest.getLimit());
		pageBounds.setPage(roleRequest.getPage());
		return mybatisDaoContext.findForPage(SQLID+"findRoleListByPage", RoleVo.class, roleRequest, pageBounds);
	}


	/**
	 * 获取当前用户的角色信息
	 *
	 * @author afi
	 * @created 2016-3-12 下午4:42:24
	 *
	 * @param userFid
	 * @return
	 */
	public List<RoleEntity> findRoleListByUserFid(String userFid) {
		Map<String,Object> par = new HashMap<>();
		par.put("userFid",userFid);
		return mybatisDaoContext.findAll(SQLID + "findRoleListByUserFid", RoleEntity.class, par);
	}


	/**
	 * 根据角色逻辑id查询角色
	 *
	 * @author liujun
	 * @created 2016-3-12 下午4:42:24
	 *
	 * @param roleFid
	 * @return
	 */
	public RoleEntity findRoleByFid(String roleFid) {
		return mybatisDaoContext.findOne(SQLID+"findRoleByFid", RoleEntity.class, roleFid);
	}

	/**
	 * 根据角色逻辑id更新角色
	 *
	 * @author liujun
	 * @created 2016-3-12 下午5:09:57
	 *
	 * @param role
	 */
	public void updateRole(RoleEntity role) {
		mybatisDaoContext.update(SQLID+"updateRoleByFid", role);
	}

}
