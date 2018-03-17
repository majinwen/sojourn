/**
 * @FileName: ResourceServiceImpl.java
 * @Package com.ziroom.minsu.ups.service.impl
 * 
 * @author bushujie
 * @created 2016年12月5日 上午11:35:35
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.ups.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import com.ziroom.minsu.ups.dao.CurrentuserRoleDao;
import org.springframework.stereotype.Service;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.ziroom.minsu.entity.sys.ResourceEntity;
import com.ziroom.minsu.entity.sys.RoleResourceEntity;
import com.ziroom.minsu.services.basedata.dto.ResourceRequest;
import com.ziroom.minsu.services.basedata.dto.RoleResourceRequest;
import com.ziroom.minsu.services.basedata.entity.ResourceVo;
import com.ziroom.minsu.services.basedata.entity.TreeNodeVo;
import com.ziroom.minsu.ups.dao.ResourceDao;
import com.ziroom.minsu.ups.dao.RoleResDao;
import com.ziroom.minsu.ups.service.IResourceService;
import com.ziroom.minsu.valenum.base.MenuAuthEnum;

/**
 * <p>权限菜单业务层接口实现</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
@Service("ups.resourceService")
public class ResourceServiceImpl implements IResourceService {
	
	@Resource(name="ups.resourceDao")
	private ResourceDao resourceDao;
	
	@Resource(name="ups.currentuserRoleDao")
	private CurrentuserRoleDao currentuserRoleDao;



	@Resource(name="ups.roleResDao")
	private RoleResDao roleResDao;
	/* (non-Javadoc)
	 * @see com.ziroom.minsu.ups.service.IResourceService#findMenuTreeNodeVos()
	 */
	@Override
	public List<TreeNodeVo> findMenuTreeNodeVos(String systemFid) {
		return resourceDao.findTreeNodeVoList(systemFid);
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.ups.service.IResourceService#findMenuOperPageList(com.ziroom.minsu.services.basedata.dto.ResourceRequest)
	 */
	@Override
	public PagingResult<ResourceEntity> findMenuOperPageList(ResourceRequest resourceRequest) {
		return resourceDao.findMenuOperPageList(resourceRequest);
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.ups.service.IResourceService#insertMenuResource(com.ziroom.minsu.entity.sys.ResourceEntity)
	 */
	@Override
	public void insertMenuResource(ResourceEntity resourceEntity) {
		ResourceEntity parentRe=resourceDao.getResourceByFid(resourceEntity.getParentFid());
		resourceEntity.setSystemsFid(parentRe.getSystemsFid());
		resourceDao.insertMenuResource(resourceEntity);
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.ups.service.IResourceService#findResourceByFid(java.lang.String)
	 */
	@Override
	public ResourceEntity findResourceByFid(String fid) {
		return resourceDao.getResourceByFid(fid);
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.ups.service.IResourceService#updateResource(com.ziroom.minsu.entity.sys.ResourceEntity)
	 */
	@Override
	public void updateResource(ResourceEntity resourceEntity) {
		resourceDao.updateMenuByFid(resourceEntity);
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.ups.service.IResourceService#findFnResourceSetByUid(java.lang.String, java.lang.String)
	 */
	@Override
	public Set<String> findFnResourceSetByUid(String systemFid, String uid) {
		Map<String, String> paramMap=new HashMap<String, String>();
		paramMap.put("systemFid", systemFid);
		paramMap.put("currentuserFid", uid);
		List<String> resourceList=resourceDao.findFnResourceSetByUid(paramMap);
		Set<String> resourceSet=new HashSet<String>();
		resourceSet.addAll(resourceList);
		return resourceSet;
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.ups.service.IResourceService#findResourceByCurrentuserId(java.lang.String, java.lang.String)
	 */
	@Override
	public List<ResourceVo> findResourceByCurrentuserId(String currentuserFid, String systemFid) {
		return resourceDao.findResourceByCurrentuserId(currentuserFid, systemFid);
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.ups.service.IResourceService#findTreeNodeResByRoleFid(java.lang.String)
	 */
	@Override
	public List<TreeNodeVo> findTreeNodeResByRoleFid(String roleFid,String systemFid) {
		List<TreeNodeVo> treeNodeList=resourceDao.findTreeNodeVoList(systemFid);
		RoleResourceRequest request = new RoleResourceRequest();
		request.setRoleFid(roleFid);
		List<RoleResourceEntity> roleResList=roleResDao.findRoleResources(request);
		if(!Check.NuNCollection(roleResList)){
			recursionSelected(treeNodeList, roleResList);
		}
		return treeNodeList;
	}
	
	/**
	 * 
	 * 拼装选中的权限
	 *
	 * @author bushujie
	 * @created 2016年12月8日 下午8:24:26
	 *
	 * @param treeNodeList
	 * @param roleResList
	 */
	private void recursionSelected(List<TreeNodeVo> treeNodeList, List<RoleResourceEntity> roleResList) {
		for (TreeNodeVo treeNodeVo : treeNodeList) {
			List<TreeNodeVo> treeNodeVoList = treeNodeVo.getNodes();
			if(!Check.NuNCollection(treeNodeVoList)){
				recursionSelected(treeNodeVoList,roleResList);
			}
			String id = treeNodeVo.getId();
			if (Check.NuNStr(id)) {
				continue;
			}
			for (RoleResourceEntity roleResource : roleResList) {
				String resFid = roleResource.getResourceFid();
				if (Check.NuNStr(resFid)) {
					continue;
				}
				if(resFid.equals(id)){
					Map<String, Object> map = treeNodeVo.getState();
					map.put("selected", true);
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.ups.service.IResourceService#isPrivilegeRes(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean isPrivilegeRes(String systemFid, String resUrl) {
		Map<String, Object> paramMap=new HashMap<String,Object>();
		paramMap.put("systemFid", systemFid);
		paramMap.put("resUrl", resUrl);
		List<ResourceEntity> list=resourceDao.findResourceByUrl(paramMap);
		for(ResourceEntity res:list){
			if(res.getMenuAuth()==MenuAuthEnum.PRIVILEGED_MENU.getCode()){
				return true;
			}
		}
		return false;
	}

    /**
     *
     * 查询用户菜单权限fid集合（前后端分离的权限格式：0：非功能点fid + 1：功能点菜单fid）
     *
     * @author zhangyl2
     * @created 2018年03月08日 17:33
     * @param
     * @return
     */
    @Override
    public Set<String> findMenuFidList(String systemFid, String currentuserFid) {
        List<String> resourceList = resourceDao.findMenuFidList(systemFid, currentuserFid);
        Set<String> resourceSet = new HashSet<>();
        resourceSet.addAll(resourceList);
        return resourceSet;
    }

    /**
     *
     * 查询用户功能点菜单权限树（前后端分离的权限格式：res_type=1:功能点菜单与其子权限）
     *
     * @author zhangyl2
     * @created 2018年03月08日 17:33
     * @param
     * @return
     */
    @Override
    public List<ResourceVo> findMenuChildTree(String systemFid, String currentuserFid) {
        return resourceDao.findMenuChildTree(systemFid, currentuserFid);
    }

	/**
	 * @param curFid
	 * @return
	 * @author yd
	 * @created
	 */
	@Override
	public List<String> queryRoleFidsByCurFid(String curFid) {
		return currentuserRoleDao.queryRoleFidsByCurFid(curFid);
	}
}
