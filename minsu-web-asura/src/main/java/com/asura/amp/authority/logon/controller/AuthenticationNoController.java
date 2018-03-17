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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asura.amp.authority.entity.Operator;
import com.asura.amp.authority.logon.constant.LogonConstant;
import com.asura.amp.authority.operator.service.OperatorService;
import com.asura.amp.common.entity.BaseAjaxValue;
import com.asura.amp.common.entity.Failure;
import com.asura.amp.common.entity.Success;

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
public class AuthenticationNoController {

	@Autowired
	private OperatorService operatorService;
	
	/**
	 * 
	 * 跳转到修改密码页面
	 *
	 * @author zhangshaobin
	 * @created 2013-3-14 下午7:59:08
	 *
	 * @return
	 */
	@RequestMapping("changePassword")
	public String changePassword(){
		return "/authenticationNo/changePassword";
	}
	
	/**
	 * 
	 * 修改密码操作
	 *
	 * @author zhangshaobin
	 * @created 2013-3-14 下午8:31:59
	 *
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("updatePassword")
	@ResponseBody
	public BaseAjaxValue updatePassword(HttpServletRequest request, HttpSession session) {
		Operator operator = (Operator) session.getAttribute(LogonConstant.SESSION_OPERATOR);
		String oldPassword = request.getParameter("oldPassword");
		String oldPasswordMD5 = DigestUtils.md5DigestAsHex(oldPassword.getBytes());
		if(oldPasswordMD5.equalsIgnoreCase(operator.getLoginPwd())){
			String newPassword = request.getParameter("newPassword");
		    operator.setLoginPwd(newPassword);
			this.operatorService.updatePassword(operator);
			return new Success("修改成功");
		}else{
			return new Failure("旧密码错误!");
		}
	    
	}
}
