/**
 * @FileName: IResourceService.java
 * @Package com.ziroom.minsu.ups.service
 * 
 * @author bushujie
 * @created 2016年12月5日 上午11:16:29
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.ups.service;

import java.util.List;
import java.util.Set;

import com.asura.framework.base.paging.PagingResult;
import com.ziroom.minsu.entity.sys.ResourceEntity;
import com.ziroom.minsu.services.basedata.dto.ResourceRequest;
import com.ziroom.minsu.services.basedata.entity.ResourceVo;
import com.ziroom.minsu.services.basedata.entity.TreeNodeVo;

/**
 * <p>权限菜单业务层接口</p>
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
public interface IResourceService {
	
	/**
	 * 
	 * 查询权限菜单树结构
	 *
	 * @author bushujie
	 * @created 2016年12月5日 上午11:21:58
	 *
	 * @return
	 */
	List<TreeNodeVo> findMenuTreeNodeVos(String systemFid);
	
	/**
	 * 
	 * 分页条件查询权限菜单列表
	 *
	 * @author bushujie
	 * @created 2016年12月5日 下午2:47:52
	 *
	 * @param resourceRequest
	 * @return
	 */
	PagingResult<ResourceEntity> findMenuOperPageList(ResourceRequest resourceRequest);
	
	/**
	 * 
	 * 插入权限菜单
	 *
	 * @author bushujie
	 * @created 2016年12月5日 下午5:48:43
	 *
	 * @param resourceEntity
	 */
	void insertMenuResource(ResourceEntity resourceEntity);
	
	/**
	 * 
	 * 根据fid查询权限菜单
	 *
	 * @author bushujie
	 * @created 2016年12月6日 下午2:53:09
	 *
	 * @param fid
	 * @return
	 */
	ResourceEntity findResourceByFid(String fid);
	
	/**
	 * 
	 * 更新权限菜单
	 *
	 * @author bushujie
	 * @created 2016年12月6日 下午3:44:18
	 *
	 * @param resourceEntity
	 * @return
	 */
	void updateResource(ResourceEntity resourceEntity);
	
	/**
	 * 
	 * 系统fid和用户fid查询权限集合
	 *
	 * @author bushujie
	 * @created 2016年12月7日 下午5:13:21
	 *
	 * @param systemFid
	 * @param uid
	 * @return
	 */
	Set<String> findFnResourceSetByUid(String systemFid,String uid);
	
	/**
	 * 
	 * 用户权限树查询
	 *
	 * @author bushujie
	 * @created 2016年12月7日 下午8:00:39
	 *
	 * @param currentuserFid
	 * @param systemFid
	 * @return
	 */
	List<ResourceVo> findResourceByCurrentuserId(String currentuserFid,String systemFid);
	
	/**
	 * 
	 * 角色fid查询权限树
	 *
	 * @author bushujie
	 * @created 2016年12月8日 下午8:16:42
	 *
	 * @param roleFid
	 * @return
	 */
	List<TreeNodeVo> findTreeNodeResByRoleFid(String roleFid,String systemFid);
	
	/**
	 * 
	 *  是否是特权菜单
	 *
	 * @author bushujie
	 * @created 2016年12月20日 下午5:37:16
	 *
	 * @param systemFid
	 * @param resUrl
	 * @return
	 */
	boolean isPrivilegeRes(String systemFid,String resUrl);

    /**
     *
     * 查询用户菜单权限fid集合（前后端分离的权限格式：0：非功能点fid + 1：功能点菜单fid）
     *
     * @author zhangyl2
     * @created 2018年03月08日 17:33
     * @param
     * @return
     */
    Set<String> findMenuFidList(String systemFid, String currentuserFid);

    /**
     *
     * 查询用户功能点菜单权限树（前后端分离的权限格式：res_type=1:功能点菜单与其子权限）
     *
     * @author zhangyl2
     * @created 2018年03月08日 17:33
     * @param
     * @return
     */
    List<ResourceVo> findMenuChildTree(String systemFid, String currentuserFid);

	/**
	 * 
	 * @author yd
	 * @created  
	 * @param 
	 * @return 
	 */
	List<String> queryRoleFidsByCurFid(String curFid);
}
