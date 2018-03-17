/**
 * @FileName: LogonInterceptor.java
 * @Package com.asura.amp.authority.logon.interceptor
 * 
 * @author zhangshaobin
 * @created 2013-1-28 下午6:41:02
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.amp.authority.logon.interceptor;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.asura.amp.authority.entity.Log;
import com.asura.amp.authority.entity.Operator;
import com.asura.amp.authority.entity.Resource;
import com.asura.amp.authority.log.service.LogService;
import com.asura.amp.authority.logon.constant.LogonConstant;

/**
 * <p>
 * 权限拦截器
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
public class AuthorityInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private LogService logService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		Log log = new Log();
		boolean flag = false;
		HttpSession session = request.getSession();
		Operator oper = (Operator) session.getAttribute(LogonConstant.SESSION_OPERATOR);
		@SuppressWarnings("unchecked")
		List<Resource> resources = (List<Resource>) session.getAttribute(LogonConstant.SESSION_ALL_RESOURCE);// 获得用户所拥有的资源信息(菜单和功能)
		String uri = request.getRequestURI();
		if (uri != null && uri.endsWith(".jsp")) {
			return true;
		}
		if (uri.equals("/amp/sms/callback/upMsgGather.do") || uri.equals("/amp/sms/callback/stateOk.do")) {
			return true;
		}
		if (oper != null) {
			log.setClientIp(request.getRemoteAddr());
			log.setJobNum(oper.getJobNum());
			log.setLogonName(oper.getLoginName());
			log.setLogonDate(new Date().getTime());
			log.setOperationUrl(uri);
			if (superAdmin(oper, log)) {
				return true;
			}
			Integer isNormalOperation = 0;
			for (Resource res : resources) {
				String resUrl = res.getResUrl();
				if (resUrl != null && !resUrl.isEmpty() && uri.contains(resUrl)) {
					String resId = request.getParameter("resId");
					request.setAttribute("resId", resId);
					flag = true;
					isNormalOperation = 1;
					break;
				}
			}
			log.setIsNormalOperation(isNormalOperation);
			this.logService.save(log);
			if (!flag) {
				response.sendRedirect(request.getContextPath() + "/error/nopermission.jsp");
			}
			return flag;
		} else {
			response.sendRedirect(request.getContextPath() + "/error/nologin.jsp");
			return flag;
		}
	}

	/**
	 * 超级管理员不需要验证, 操作踪迹保存到日志表中
	 * @param oper
	 * @param log
	 * @return
	 */
	private boolean superAdmin(Operator oper, Log log) {
		String loginName = oper.getLoginName();
		// 超级管理员不需要验证
		if ("superadmin".equals(loginName)) {
			log.setIsNormalOperation(1);
			this.logService.save(log);
			return true;
		} else {
			return false;
		}
	}

}
