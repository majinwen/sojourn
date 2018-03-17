package com.asura.amp.authority.resource.service;

import java.util.List;

import com.asura.amp.authority.entity.Resource;

/**
 * <p>
 *     资源管理服务
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
public interface ResourceService {
	
	/**
	 * 添加
	 * @author zhangshaobin
	 * @created 2012-12-6 下午12:58:54
	 * @param resource 资源信息实体对象
	 */
	public void save(Resource resource);
	
	/**
	 * 查询所有的资源信息
	 * @author zhangshaobin
	 * @created 2012-12-6 下午12:58:54
	 */
	public List<Resource> findAll();
	
	/**
	 * 更新
	 * @author zhangshaobin
	 * @created 2012-12-6 下午12:58:54
	 * @param resource 资源信息实体对象
	 */
	public void update(Resource resource);
	
	/**
	 * 删除
	 * @author zhangshaobin
	 * @created 2012-12-6 下午12:58:54
	 * @param resource 资源信息实体对象
	 */
	public void delete(Resource resource);
	
	/**
	 * 
	 * 根据角色id查询对应的资源信息
	 *
	 * @author zhangshaobin
	 * @created 2013-1-27 下午4:49:23
	 *
	 */
	public List<Resource> findResourcesByRole(String roleId);
	
	
	/**
	 * 
	 * 根据角色id查询对应的功能资源信息
	 *
	 * @author zhangshaobin
	 * @created 2013-1-28 上午11:09:13
	 *
	 * @param roleId
	 * @return
	 */
	public StringBuffer findFunResourcesId(String roleId);
	
	/**
	 * 
	 * 登录查询资源信息
	 *
	 * @author zhangshaobin
	 * @created 2013-1-29 上午9:33:00
	 *
	 * @param operatorId 用户id
	 * @param resType 资源类型
	 * @return
	 */
	public List<Resource> findResourceByLogon(int operatorId, String resType);
	
	/**
	 * 
	 * 根据角色id查询对应的资源信息 todo ....
	 * 
	 * @author zhangshaobin
	 * @created 2013-1-27 下午4:49:23
	 * 
	 */
	public StringBuffer findAllResourcesByRole(String roleId) ;
	
	/**
	 * 
	 * 根据资源类型查询出所有的资源
	 *
	 * @author zhangshaobin
	 * @created 2013-3-14 下午7:21:40
	 *
	 * @param resType 资源类型
	 * @return
	 */
	public List<Resource> findAllResourcesByResType(String resType) ;
	
}
