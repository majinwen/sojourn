/**
 * @FileName: LogonController.java
 * @Package com.asura.amp.authority.logon.controller
 * 
 * @author zhangshaobin
 * @created 2013-1-28 下午6:11:01
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.amp.authority.logon.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.asura.amp.authority.entity.Operator;
import com.asura.amp.authority.entity.Resource;
import com.asura.amp.authority.logon.constant.LogonConstant;
import com.asura.amp.authority.operator.service.OperatorService;
import com.asura.amp.authority.resource.service.ResourceService;

/**
 * <p>
 * 登录控制器
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
@RequestMapping("/")
public class LogonController {

	@Autowired
	private OperatorService operatorService;

	@Autowired
	private ResourceService resourceService;

	/**
	 * 
	 * 登录
	 * 
	 * @author zhangshaobin
	 * @created 2013-1-28 下午6:43:18
	 * 
	 * @param operator
	 * @param session
	 */
	@RequestMapping("login")
	public String logon(Operator operator, HttpSession session, HttpServletRequest request) {
		String loginName = operator.getLoginName();
		String loginPwd = operator.getLoginPwd();
		if (loginName == null || loginPwd == null) {
			return "login";
		}
		Operator oper = this.operatorService.logon(operator);
		if (oper != null) {
			List<Resource> menus = new ArrayList<Resource>();
			List<Resource> funs = new ArrayList<Resource>();

			if ("superadmin".equals(oper.getLoginName())) {
				// 超级管理员拥有所有的权限, 并且不需要验证
				menus = resourceService.findAllResourcesByResType("01");
				funs = resourceService.findAllResourcesByResType("02");
			} else {
				menus = resourceService.findResourceByLogon(oper.getOperatorId(), "01");
				funs = resourceService.findResourceByLogon(oper.getOperatorId(), "02");
			}

			Set<String> funsHash = new HashSet<String>();
			for (Resource res : funs) {
				funsHash.add(res.getResId().toString());
			}

			Set<String> menusHash = new HashSet<String>();
			for (Resource res : menus) {
				menusHash.add(res.getResId().toString());
			}

			List<Resource> projects = new ArrayList<Resource>();
			for (Resource res : menus) {
				if (res.getParentId() == 0) {
					projects.add(res);
				}
			}

			List<Resource> resources = new ArrayList<Resource>();
			resources.addAll(menus);
			resources.addAll(funs);
			session.setAttribute(LogonConstant.SESSION_OPERATOR, oper);
			session.setAttribute(LogonConstant.SESSION_MENUS_RESOURCE, menus);
			session.setAttribute(LogonConstant.SESSION_PROJECT_RESOURCE, projects);
			session.setAttribute(LogonConstant.SESSION_ALL_RESOURCE, resources);
			session.setAttribute(LogonConstant.SESSION_FUNS_RESOURCE, funs);

			return "redirect:/index.do";
		} else {
			request.setAttribute("error", "我靠, 抵制假货!");
			return "login";
		}
	}

	/**
	 * 
	 * 登录成功跳转此页面
	 *
	 * @author zhangshaobin
	 * @created 2013-1-29 下午1:21:14
	 *
	 */
	@RequestMapping("index")
	public void index() {
	}

	/**
	 * 
	 * 跳转到头部
	 *
	 * @author zhangshaobin
	 * @created 2013-1-29 上午10:58:36
	 *
	 * @return
	 */
	@RequestMapping("header")
	public String header(HttpSession session) {
		return "/include/header";
	}

	/**
	 * 
	 * 跳转到左边菜单
	 *
	 * @author zhangshaobin
	 * @created 2013-1-29 上午10:58:36
	 *
	 * @return
	 */
	@RequestMapping("leftside")
	public String leftSide(HttpServletRequest request, HttpSession session) {
		String pId = request.getParameter("projectid");
		if (pId == null) {
			@SuppressWarnings("unchecked")
			List<Resource> projects = (List<Resource>) session.getAttribute("SESSION_PROJECT_RESOURCE");
			pId = projects.get(0).getResId().toString();
		}
		request.setAttribute("pId", pId);
		return "/include/leftside";
	}

	/**
	 * 
	 * 跳转到
	 *
	 * @author zhangshaobin
	 * @created 2013-1-29 上午10:58:36
	 *
	 * @return
	 */
	@RequestMapping("container")
	public String container() {
		return "/include/container";
	}

	/**
	 * 
	 * 退出
	 *
	 * @author zhangshaobin
	 * @created 2013-2-2 下午5:47:57
	 *
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "logout")
	public String logout(HttpSession session) {
		// session失效
		session.invalidate();
		return "login";
	}

}
