package com.asura.amp.authority.resource.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asura.amp.authority.entity.Resource;
import com.asura.amp.authority.resource.dao.ResourceDao;
import com.asura.amp.authority.resource.service.ResourceService;
import com.asura.amp.authority.role.dao.RoleResourceDao;

/**
 * <p>
 * 资源管理服务实现类
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
@Service("resourceService")
public class ResourceServiceImpl implements ResourceService {

	@Autowired
	private ResourceDao resourceDao;
	
	@Autowired
	private RoleResourceDao roleResourceDao;

	/**
	 * 添加
	 * 
	 * @author zhangshaobin
	 * @created 2012-12-6 下午12:58:54
	 * @param resource
	 *            资源信息实体对象
	 */
	@Override
	public void save(Resource resource) {
		this.resourceDao.save(resource);
	}

	/**
	 * 查询所有的资源信息
	 * 
	 * @author zhangshaobin
	 * @created 2012-12-6 下午12:58:54
	 */
	@Override
	public List<Resource> findAll() {
		return this.resourceDao.findAll();
	}

	/**
	 * 更新
	 * 
	 * @author zhangshaobin
	 * @created 2012-12-6 下午12:58:54
	 * @param resource
	 *            资源信息实体对象
	 */
	@Override
	public void update(Resource resource) {
		this.resourceDao.update(resource);
	}

	/**
	 * 删除
	 * 
	 * @author zhangshaobin
	 * @created 2012-12-6 下午12:58:54
	 * @param resource
	 *            资源信息实体对象
	 */
	@Override
	public void delete(Resource resource) {
		// 删除资源信息
		this.resourceDao.delete(resource);
		// 删除角色资源对照信息
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("resId", resource.getResId());
		this.roleResourceDao.deleteByResId(map);
	}

	/**
	 * 
	 * 根据角色id查询对应的资源信息 todo ....
	 * 
	 * @author zhangshaobin
	 * @created 2013-1-27 下午4:49:23
	 * 
	 */
	@Override
	public List<Resource> findResourcesByRole(String roleId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("roleId", roleId);
		List<Resource> resources = this.resourceDao.findResourcesByRole(map);
		return resources;
	}
	
	/**
	 * 
	 * 根据角色id查询对应的资源信息 todo ....
	 * 
	 * @author zhangshaobin
	 * @created 2013-1-27 下午4:49:23
	 * 
	 */
	@Override
	public StringBuffer findAllResourcesByRole(String roleId) {
		StringBuffer funResIds = new StringBuffer();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("roleId", roleId);
		List<Resource> resources = this.resourceDao.findAllResourcesByRole(map);
		for (Resource rs : resources) {
			funResIds.append(rs.getResId() + "|");
		}
		return funResIds;
	}

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
	@Override
	public StringBuffer findFunResourcesId(String roleId) {
		StringBuffer funResIds = new StringBuffer();
		List<Resource> resources = findResourcesByRole(roleId);
		for (Resource rs : resources) {
			funResIds.append(rs.getResId() + "|");
		}
		return funResIds;
	}

	/**
	 * 
	 * 登录查询资源信息
	 * 
	 * @author zhangshaobin
	 * @created 2013-1-29 上午9:33:00
	 * 
	 * @param operatorId
	 *            用户id
	 * @param resType
	 *            资源类型
	 * @return
	 */
	@Override
	public List<Resource> findResourceByLogon(int operatorId, String resType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("operatorId", operatorId);
		map.put("resType", resType);
		return this.resourceDao.findResourceByLogon(map);
	}
	
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
	@Override
	public List<Resource> findAllResourcesByResType(String resType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resType", resType);
		return this.resourceDao.findAllResourcesByResType(map);
	}

}
