package com.asura.amp.authority.resource.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.asura.amp.authority.entity.Resource;
import com.asura.framework.dao.old.BaseIbatisDAO;
import com.ibatis.sqlmap.client.SqlMapClient;
/**
 * <p>
 * 		资源管理持久化层
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
@Repository("resourceDao")
public class ResourceDao extends BaseIbatisDAO{
	
	@Autowired
	public void setSystemSqlMapClient(SqlMapClient ampSqlMapClient) {
		super.setCurrentSqlMapClient(ampSqlMapClient);
	}
	
	/**
	 * 添加
	 * @author zhangshaobin
	 * @created 2012-12-6 下午12:58:54
	 * @param resource 资源信息实体对象
	 */
	public void save(Resource resource){
		this.save("com.asura.management.authority.resource.dao.save", resource);
	}
	
	/**
	 * 查询所有的资源信息
	 * @author zhangshaobin
	 * @created 2012-12-6 下午12:58:54
	 */
	public List<Resource> findAll(){
		return this.findAll("com.asura.management.authority.resource.dao.findAll");
	}
	
	/**
	 * 更新
	 * @author zhangshaobin
	 * @created 2012-12-6 下午12:58:54
	 * @param resource 资源信息实体对象
	 */
	public void update(Resource resource){
		this.update("com.asura.management.authority.resource.dao.update", resource);
	}
	
	/**
	 * 删除
	 * @author zhangshaobin
	 * @created 2012-12-6 下午12:58:54
	 * @param resource 资源信息实体对象
	 */
	public void delete(Resource resource){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("resId", resource.getResId());
		this.delete("com.asura.management.authority.resource.dao.delete", map);
	}
	
	/**
	 * 
	 * 根据角色id查询对应的资源信息
	 *
	 * @author zhangshaobin
	 * @created 2013-1-27 下午4:49:23
	 * 
	 * @param map 角色id
	 * @return
	 */
	public List<Resource> findResourcesByRole(Map<String, Object> map){
		return this.findAll("com.asura.management.authority.resource.dao.findResourcesByRole", Resource.class, map);
	}
	
	/**
	 * 
	 * 根据角色id查询对应的资源信息（菜单和功能）
	 *
	 * @author zhangshaobin
	 * @created 2013-1-27 下午4:49:23
	 * 
	 * @param map 角色id
	 * @return
	 */
	public List<Resource> findAllResourcesByRole(Map<String, Object> map){
		return this.findAll("com.asura.management.authority.resource.dao.findAllResourcesByRole", Resource.class, map);
	}
	
	/**
	 * 
	 * 查询所有的资源信息
	 *
	 * @author zhangshaobin
	 * @created 2013-1-27 下午5:10:40
	 *
	 * @param map 父id
	 * @return
	 */
	public List<Resource> findResourcesByParentId(Map<String, Object> map){
		return this.findAll("com.asura.management.authority.resource.dao.findResourcesByParentId", Resource.class, map);
		
	}
	
	/**
	 * 
	 * 登录查询资源信息
	 *
	 * @author zhangshaobin
	 * @created 2013-1-27 下午5:10:40
	 *
	 * @param map 用户id 、 资源类型
	 * @return
	 */
	public List<Resource> findResourceByLogon(Map<String, Object> map){
		return this.findAll("com.asura.management.authority.resource.dao.findResourceByLogon", Resource.class, map);
		
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
	public List<Resource> findAllResourcesByResType(Map<String, Object> map){
		return this.findAll("com.asura.management.authority.resource.dao.findAllResourcesByResType", Resource.class, map);
		
	}

}
