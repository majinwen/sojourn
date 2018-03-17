package com.asura.amp.authority.resource.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asura.amp.authority.entity.Resource;
import com.asura.amp.authority.resource.service.ResourceService;
import com.asura.amp.common.entity.BaseAjaxValue;
import com.asura.amp.common.entity.Success;

/**
 * <p>
 * 资源管理控制器
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
@RequestMapping("authority/resource")
public class ResourceController {
	@Autowired
	private ResourceService resourceService;

	/**
	 * 
	 * 查询所有的资源信息
	 * 
	 * @author zhangshaobin
	 * @created 2012-12-20 下午3:38:09
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("resourceTree")
	public void resourceTree(Model model, HttpServletRequest request) {
		String resId = request.getParameter("resId");
		List<Resource> resources = this.resourceService.findAll();
		model.addAttribute("resources", resources);
		model.addAttribute("resId", resId);
	}

	/**
	 * 
	 * 资源信息操作
	 * 
	 * @author zhangshaobin
	 * @created 2012-12-20 下午3:38:09
	 * @param bt
	 * @param executeType
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("execute")
	@ResponseBody
	public BaseAjaxValue executeResourceData(Resource resource,
			String executeTypeName) throws Exception {
		if ("add".equals(executeTypeName)) {
			this.resourceService.save(resource);
			return new Success("资源添加成功");
		} else {
			if ("update".equals(executeTypeName)) {
				this.resourceService.update(resource);
				return new Success("资源修改成功");
			} else {
				this.resourceService.delete(resource);
				return new Success("资源删除成功");
			}
		}
	}
	
	/**
	 * 
	 * 带有复选框的树信息(在角色列表中调转到分配资源页面)
	 * 
	 * @author zhangshaobin
	 * @created 2012-12-20 下午3:38:09
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("allotResourceTree")
	public void allotResourceTree(Model model,String roleId) {
		List<Resource> resources = this.resourceService.findAll();
		// 获取功能资源
//		StringBuffer funResIds = this.resourceService.findFunResourcesId(roleId);
		// 获取所有的资源
		StringBuffer funResIds = this.resourceService.findAllResourcesByRole(roleId);
		model.addAttribute("resources", resources);
		model.addAttribute("roleId", roleId);
		model.addAttribute("funResIds", funResIds.toString());
	}
	
}
