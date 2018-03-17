/**
 * @FileName: ResourceController.java
 * @Package com.ziroom.minsu.ups.controller
 * 
 * @author bushujie
 * @created 2016年12月5日 上午10:47:21
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.ups.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.sys.ResourceEntity;
import com.ziroom.minsu.services.basedata.dto.ResourceRequest;
import com.ziroom.minsu.services.basedata.entity.TreeNodeVo;
import com.ziroom.minsu.services.common.page.PageResult;
import com.ziroom.minsu.ups.service.IResourceService;

/**
 * <p>权限菜单controller</p>
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
@Controller
@RequestMapping("resource")
public class ResourceController {
	
	@Resource(name="ups.resourceService")
	private IResourceService resourceService;
	
	/**
	 * 
	 * 权限菜单管理列表
	 *
	 * @author bushujie
	 * @created 2016年12月5日 下午2:17:40
	 *
	 * @param request
	 */
	@RequestMapping("resourceList")
	public void resourceList(HttpServletRequest request){
		List<TreeNodeVo> resourceTree=resourceService.findMenuTreeNodeVos(null);
		request.setAttribute("treeview", JsonEntityTransform.Object2Json(resourceTree));
	}
	
	/**
	 * 
	 * 分页查询权限菜单列表
	 *
	 * @author bushujie
	 * @created 2016年12月5日 下午2:54:45
	 *
	 * @param paramRequest
	 * @param request
	 * @return
	 */
	@RequestMapping("menuPageList")
	@ResponseBody
	public PageResult menuPageList(ResourceRequest paramRequest,HttpServletRequest request){
		String parent_id = request.getParameter("fid");

		if (parent_id != null && parent_id != "") {
			paramRequest.setParent_fid(parent_id);
		}

		PagingResult<ResourceEntity> pagingResult=resourceService.findMenuOperPageList(paramRequest);
        //返回结果
		PageResult pageResult = new PageResult();
		pageResult.setRows(pagingResult.getRows());
		pageResult.setTotal(pagingResult.getTotal());
		return pageResult;
	}
	
	/**
	 * 
	 * 插入权限菜单
	 *
	 * @author bushujie
	 * @created 2016年12月5日 下午5:09:27
	 *
	 * @param menu
	 * @param request
	 * @return
	 */
	@RequestMapping("insertMenuResource")
	@ResponseBody
	public DataTransferObject insertMenuResource(ResourceEntity menu, HttpServletRequest request){
		DataTransferObject dto=new DataTransferObject();
		menu.setFid(UUIDGenerator.hexUUID());
		menu.setIsLeaf(1);
		resourceService.insertMenuResource(menu);
		List<TreeNodeVo> resourceTree=resourceService.findMenuTreeNodeVos(null);
		dto.putValue("treeview", JsonEntityTransform.Object2Json(resourceTree));
		return dto;
	}
	/**
	 * 
	 * fid查询权限菜单
	 *
	 * @author bushujie
	 * @created 2016年12月6日 下午3:18:19
	 *
	 * @param fid
	 * @return
	 */
	@RequestMapping("findResourceByFid")
	@ResponseBody
	public DataTransferObject findResourceByFid(String fid){
		DataTransferObject dto=new DataTransferObject();
		ResourceEntity resourceEntity=resourceService.findResourceByFid(fid);
		dto.putValue("resource", resourceEntity);
		dto.putValue("panrentName", resourceService.findResourceByFid(resourceEntity.getParentFid()).getResName());
		return dto;
	}
	
	/**
	 * 
	 * 更新权限菜单
	 *
	 * @author bushujie
	 * @created 2016年12月6日 下午3:47:16
	 *
	 * @param resourceEntity
	 * @return
	 */
	@RequestMapping("updateResource")
	@ResponseBody
	public DataTransferObject updateResource(ResourceEntity resourceEntity){
		DataTransferObject dto=new DataTransferObject();
		resourceService.updateResource(resourceEntity);
		return dto;
	}
	
	/**
	 * 
	 * 更新菜单状态
	 *
	 * @author bushujie
	 * @created 2016年12月6日 下午8:01:21
	 *
	 * @param selectedId
	 * @param resStatus
	 * @return
	 */
	@RequestMapping("changeMenuStatus")
	@ResponseBody
	public DataTransferObject changeMenuStatus(String selectedId,String resStatus){
		DataTransferObject dto=new DataTransferObject();
		ResourceEntity resourceEntity = new ResourceEntity();
		resourceEntity.setFid(selectedId);
		
		if(Integer.parseInt(resStatus) == 0){
			resourceEntity.setResState(1);
		}else{
			resourceEntity.setResState(0);
		}
		resourceService.updateResource(resourceEntity);
		return dto;
	}
	
}
