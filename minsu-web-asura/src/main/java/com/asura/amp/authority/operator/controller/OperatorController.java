package com.asura.amp.authority.operator.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asura.amp.authority.entity.Operator;
import com.asura.amp.authority.operator.service.OperatorService;
import com.asura.amp.common.entity.BaseAjaxValue;
import com.asura.amp.common.entity.Failure;
import com.asura.amp.common.entity.Success;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchModel;

/**
 * 
 * <p>
 * 操作员对象控制器
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
@RequestMapping("authority/operator")
public class OperatorController {

	@Autowired
	private OperatorService operatorService;

	/**
	 * 
	 * 跳转到操作员添加页面
	 * 
	 * @author zhangshaobin
	 * @created 2013-1-26 下午2:24:23
	 * 
	 * @return
	 */
	@RequestMapping("toOperatorAdd")
	public String toOperatorAdd(Model model, HttpServletRequest request) {
		model.addAttribute("resId", request.getParameter("resId"));
		return "authority/operator/add";
	}

	/**
	 * 
	 * 保存操作员对象
	 * 
	 * @author zhangshaobin
	 * @created 2013-1-26 下午2:18:18
	 * 
	 * @param operator
	 * @return
	 */
	@RequestMapping("add")
	@ResponseBody
	public BaseAjaxValue save(Operator operator) {
		this.operatorService.save(operator);
		return new Success("保存成功");
	}

	/**
	 * 
	 * 跳转到操作员修改页面
	 * 
	 * @author zhangshaobin
	 * @created 2013-1-26 下午2:24:23
	 * 
	 * @return
	 */
	@RequestMapping("toOperatorEdit")
	public String toOperatorEdit(Operator operator) {
		return "authority/operator/edit";
	}

	/**
	 * 
	 * 修改操作员对象
	 * 
	 * @author zhangshaobin
	 * @created 2013-1-26 下午2:18:18
	 * 
	 * @param operator
	 * @return
	 */
	@RequestMapping("edit")
	@ResponseBody
	public BaseAjaxValue update(Operator operator) {
		this.operatorService.update(operator);
		return new Success("修改成功");
	}

	/**
	 * 
	 * 删除操作员对象
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
		Map<String, Object> map = new HashMap<String, Object>();
		String operatorId = sb.substring(0, sb.toString().length() - 1);
		map.put("ids", operatorId);
		// 删除对应的角色信息
		this.operatorService.deleteOperatorRoleByOperatorId(operatorId);
		int deleteCount = this.operatorService.delete(map);
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
	@RequestMapping("operatorList")
	public void operatorList(SearchModel searchModel, Model model, HttpServletRequest request) {
		String resId = request.getParameter("resId");
		if (searchModel.getPage() == 0) {
			searchModel.setPage(1);
		}
		PagingResult<Operator> pr = this.operatorService
				.findForPage(searchModel);
		model.addAttribute("PAGING_RESULT", pr);
		model.addAttribute("resId", resId);
	}

	/**
	 * 
	 * 分配角色
	 * 
	 * @author zhangshaobin
	 * @created 2013-1-28 下午2:00:22
	 * 
	 * @return
	 */
	@RequestMapping("allotRoles")
	@ResponseBody
	public BaseAjaxValue allotRoles(String operatorId, String roleIds) {
		// 删除对应的角色信息
		this.operatorService.deleteOperatorRoleByOperatorId(operatorId);
		if (!roleIds.isEmpty()) {
			this.operatorService.saveOperatorRole(operatorId, roleIds);
			return new Success("角色分配成功");
		} else {
			return new Failure("没有分配角色");
		}
	}

}
