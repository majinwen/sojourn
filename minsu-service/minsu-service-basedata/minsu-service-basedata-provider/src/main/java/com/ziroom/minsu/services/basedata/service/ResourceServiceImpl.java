/**
 * @FileName: MenuOperServiceImpl.java
 * @Package com.ziroom.minsu.services.basedata.service
 * 
 * @author liyingjie
 * @created 2016年3月9日 上午11:14:06
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.basedata.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.ziroom.minsu.entity.sys.ResourceEntity;
import com.ziroom.minsu.entity.sys.RoleEntity;
import com.ziroom.minsu.entity.sys.RoleResourceEntity;
import com.ziroom.minsu.services.basedata.dao.ResourceDao;
import com.ziroom.minsu.services.basedata.dao.RoleDao;
import com.ziroom.minsu.services.basedata.dao.RoleResDao;
import com.ziroom.minsu.services.basedata.dto.ResourceRequest;
import com.ziroom.minsu.services.basedata.dto.RoleResourceRequest;
import com.ziroom.minsu.services.basedata.entity.ResourceVo;
import com.ziroom.minsu.services.basedata.entity.TreeNodeVo;
import com.ziroom.minsu.valenum.base.MenuAuthEnum;

/**
 * <p>
 * 后台菜单操作业务层
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liyingjie
 * @since 1.0
 * @version 1.0
 */
@Service("basedata.resourceServiceImpl")
public class ResourceServiceImpl {

	@Resource(name = "basedata.resourceDao")
	private ResourceDao resourceDao;
	
	@Resource(name = "basedata.roleResDao")
	private RoleResDao roleResDao;
	
	@Resource(name = "basedata.roleDao")
	private RoleDao roleDao;

	/**
	 * 更新菜单信息
	 * 
	 * @param resourceEntity
	 */
	public void updateMenuByFid(ResourceEntity resourceEntity) {
		resourceDao.updateMenuByFid(resourceEntity);
	}

	/**
	 * 
	 * 后台菜单列表
	 *
	 * @author liyingjie
	 * @created 2016年3月9日 上午11:20:09
	 *
	 * @param resourceRequest
	 * @return
	 */
	public PagingResult<ResourceEntity> findMenuResList(ResourceRequest resourceRequest) {
		return resourceDao.findMenuOperPageList(resourceRequest);
	}

	/**
	 * 
	 * 保存 菜单
	 *
	 * @author liyingjie
	 * @created 2016年3月11日 下午8:43:58
	 *
	 * @param resourceEntity
	 */
	public void saveMenuResouce(ResourceEntity resourceEntity) {
		resourceDao.insertMenuResource(resourceEntity);
	}

	/**
	 * 查询所有的菜单和与其相关子节点
	 *
	 * @author liyingjie
	 * @created 2016年3月11日 下午10:53:16
	 *
	 * @return
	 */
	public List<ResourceEntity> findAllMenuChildList() {
		return resourceDao.findAllMenuClasterChildren();
	}

	/**
	 * 
	 * 资源树结构查询
	 *
	 * @author bushujie
	 * @created 2016年3月13日 下午7:09:05
	 *
	 * @return
	 */
	public List<TreeNodeVo> findMenuTreeNodeVos() {
		return resourceDao.findTreeNodeVoList();
	}

    /**
     * 重新定义角色的资源系信息
     * @param roleFid
     * @param resSet
     */
    public void  saveRoleResources(String roleFid,String[] resFidArray,Integer roleType){
    	RoleEntity roleEntity=new RoleEntity();
    	roleEntity.setFid(roleFid);
    	roleEntity.setRoleType(roleType);
    	//更新角色类型
    	roleDao.updateRole(roleEntity);
        //清洗当前的角色资源信息
        roleResDao.delRoleResourcesByFid(roleFid);
        //重新定义当前的资源信息
        roleResDao.saveRoleResources(roleFid,resFidArray);
    }
    
	public void saveRoleAndRoleResources(RoleEntity role, String resFids) {
		//新增保存角色
		roleDao.insertRole(role);
		
		String[] resFidArray = resFids.split(",");
		//保存角色资源关系
		roleResDao.saveRoleResources(role.getFid(), resFidArray);
	}

	/**
	 * 查询角色资源关系
	 *
	 * @author liujun
	 * @created 2016-3-16 上午10:38:42
	 *
	 * @param roleFid
	 * @return
	 */
	public List<RoleResourceEntity> findRoleResourceList(String roleFid) {
		RoleResourceRequest request = new RoleResourceRequest();
		request.setRoleFid(roleFid);
		return roleResDao.findRoleResources(request);
	}
	/**
	 * 
	 * 用户id获取用户权限树
	 *
	 * @author bushujie
	 * @created 2016年3月16日 下午10:41:12
	 *
	 * @param currentuserId
	 * @return
	 */
	public List<ResourceVo> findCurrentuserResList(String currentuserId){
		return resourceDao.findResourceByCurrentuserId(currentuserId);
	}
	
	/**
	 * 
	 * 根据url 校验当前菜单是否是特权菜单
	 * 异常情况：一个url对应多个菜单，只要有一个是特权菜单，就默认所有都是特权菜单（此情况，在前台添加菜单时候没做校验，故后期得加上）
	 *
	 *  0=不是特权菜单  1=是特权菜单
	 * @author yd
	 * @created 2016年11月1日 上午9:21:41
	 *
	 * @param resurl
	 * @return
	 */
	public int  checkAuthMenuByResurl(String resUrl){
		
		List<ResourceEntity> list = this.resourceDao.findResourceByUrl(resUrl);
		if(!Check.NuNCollection(list)){
			for (ResourceEntity resourceEntity : list) {
				if(resourceEntity.getMenuAuth().intValue() == MenuAuthEnum.PRIVILEGED_MENU.getCode() ){
					return 1;
				}
			}
		}
		return 0;
	}

}
