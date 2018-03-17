package com.ziroom.minsu.ups.dao;


import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.ziroom.minsu.entity.sys.RoleEntity;
import com.ziroom.minsu.services.basedata.dto.RoleRequest;
import com.ziroom.minsu.ups.dto.RoleListRequest;
import com.ziroom.minsu.ups.vo.RoleListVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
@Repository("ups.roleDao")
public class RoleDao {
	
	private String SQLID="ups.roleDao.";
	
	@Autowired
	@Qualifier("ups.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;
	
	public int insertRole(RoleEntity roleEntity) {
		return mybatisDaoContext.save(SQLID+"insertRole", roleEntity);
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
	public PagingResult<RoleListVo> findRolePageList(RoleListRequest roleRequest) {
		PageBounds pageBounds=new PageBounds();
		pageBounds.setLimit(roleRequest.getLimit());
		pageBounds.setPage(roleRequest.getPage());
		return mybatisDaoContext.findForPage(SQLID+"findRoleListByPage", RoleListVo.class, roleRequest, pageBounds);
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
	public int updateRole(RoleEntity role) {
		return mybatisDaoContext.update(SQLID+"updateRoleByFid", role);
	}
	
	/**
	 * 
	 * 查询拥有此角色用户和系统信息
	 *
	 * @author bushujie
	 * @created 2016年12月14日 下午3:35:33
	 *
	 * @param roleFid
	 * @return
	 */
	public List<Map> getSysUserByRole(String roleFid){
		return mybatisDaoContext.findAll(SQLID+"getSysUserByRole", Map.class, roleFid);
	}
	
	/**
	 * 
	 * 角色查询系统code
	 *
	 * @author bushujie
	 * @created 2017年3月8日 下午6:23:09
	 *
	 * @param roleFid
	 * @return
	 */
	public String getSysCodeByRole(String roleFid){
		return mybatisDaoContext.findOneSlave(SQLID+"getSysCodeByRole", String.class, roleFid);
	}
}
