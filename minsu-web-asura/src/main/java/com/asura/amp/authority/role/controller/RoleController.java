/**
 * @FileName: RoleController.java
 * @Package com.asura.amp.authority.role.controller
 * 
 * @author zhangshaobin
 * @created 2013-1-27 上午10:46:47
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.amp.authority.role.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asura.amp.authority.entity.Role;
import com.asura.amp.authority.operator.service.OperatorService;
import com.asura.amp.authority.role.service.RoleService;
import com.asura.amp.common.entity.BaseAjaxValue;
import com.asura.amp.common.entity.Failure;
import com.asura.amp.common.entity.Success;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchModel;

/**
 * <p>
 * 角色信息后台控制器
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
@Controller
@RequestMapping("authority/role")
public class RoleController {

	@Autowired
	private RoleService roleService;
	
	@Autowired
	private OperatorService operatorService;
	

	/**
	 * 
	 * 跳转到角色添加页面
	 * 
	 * @author zhangshaobin
	 * @created 2013-1-26 下午2:24:23
	 * 
	 * @return
	 */
	@RequestMapping("toRoleAdd")
	public String toRoleAdd() {
		return "authority/role/add";
	}

	/**
	 * 
	 * 保存角色信息对象
	 * 
	 * @author zhangshaobin
	 * @created 2013-1-26 下午2:18:18
	 * 
	 * @param role
	 * @return
	 */
	@RequestMapping("add")
	@ResponseBody
	public BaseAjaxValue save(Role role) {
		this.roleService.save(role);
		return new Success("保存成功");
	}

	/**
	 * 
	 * 跳转到角色信息修改页面
	 * 
	 * @author zhangshaobin
	 * @created 2013-1-26 下午2:24:23
	 * 
	 * @return
	 */
	@RequestMapping("toRoleEdit")
	public String toRoleEdit(Role role) {
		return "authority/role/edit";
	}

	/**
	 * 
	 * 修改角色信息对象
	 * 
	 * @author zhangshaobin
	 * @created 2013-1-26 下午2:18:18
	 * 
	 * @param role
	 * @return
	 */
	@RequestMapping("edit")
	@ResponseBody
	public BaseAjaxValue update(Role role) {
		this.roleService.update(role);
		return new Success("修改成功");
	}

	/**
	 * 
	 * 删除角色信息对象
	 * 
	 * @author zhangshaobin
	 * @created 2013-1-26 下午3:38:25
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping("delete")
	@ResponseBody
	public BaseAjaxValue delete(String[] ids) {
		StringBuffer sb = new StringBuffer();
		for (String id : ids) {
			sb.append(id + ",");
		}
		Map<String, Object> map = new HashMap<String,Object>();
		String roleIds = sb.substring(0, sb.toString().length() - 1);
		map.put("ids", roleIds);
		// 根据角色id删除对应的资源信息
		this.roleService.deleteByRoleId(roleIds);
		// 删除角色信息
		int deleteCount = this.roleService.delete(map);
		if (deleteCount != 0) {
			return new Success("删除成功");
		} else {
			return new Success("没有删除数据");
		}
	}

	/**
	 * 
	 * 查询数据
	 * 
	 * @author zhangshaobin
	 * @created 2013-1-26 下午3:17:10
	 * 
	 * @param searchModel
	 * @param model
	 */
	@RequestMapping("roleList")
	public void roleList(SearchModel searchModel, Model model) {
		if (searchModel.getPage() == 0) {
			searchModel.setPage(1);
		}
		PagingResult<Role> pr = this.roleService.findForPage(searchModel);
		model.addAttribute("PAGING_RESULT", pr);
	}
	
	/**
	 * 
	 * 分配资源
	 *
	 * @author zhangshaobin
	 * @created 2013-1-27 下午12:12:40
	 *
	 * @return
	 */
	@RequestMapping("allotResource")
	@ResponseBody
	public BaseAjaxValue allotResource(String roleId,String resIds){
		// 根据角色id删除对应的资源信息
		this.roleService.deleteByRoleId(roleId);
		if(!resIds.isEmpty()){
			// 添加资源和角色对照关系
			this.roleService.allotResource(roleId, resIds);
			return new Success("资源分配成功");
		}else{
			return new Failure("没有分配资源");
		}
	}
	
	/**
	 * 
	 * 查询所有的角色信息
	 *
	 * @author zhangshaobin
	 * @created 2013-1-28 下午1:18:19
	 *
	 * @return
	 */
	@RequestMapping("allotRoleTree")
	public void allotRoleTree(Model model, String operatorId){
		List<Role> roles = this.roleService.findAll();
		String roleIds = this.operatorService.findAllRoleIdsByOperatorId(operatorId);
		model.addAttribute("roles", roles);
		model.addAttribute("roleIds", roleIds);
		model.addAttribute("operatorId", operatorId);
	}

}
