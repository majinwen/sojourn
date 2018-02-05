/**
 * @FileName: IndexController.java
 * @Package com.ziroom.minsu.api.common.controller
 * 
 * @author bushujie
 * @created 2016年3月17日 下午5:57:12
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.api.common.controller;



import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ziroom.minsu.api.common.constant.ConstDef;
import com.ziroom.minsu.api.common.dto.ResponseSecurityDto;
import com.ziroom.minsu.services.basedata.api.inner.UserPermissionService;

/**
 * <p>加密测试</p>
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
@RequestMapping("/test")
public class IndexController {
	
	private UserPermissionService userPermissionService;
	
	@RequestMapping("/userlist")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> test(HttpServletRequest request){

        String paramValue = request.getParameter("2y5QfvAy");

        System.out.println(paramValue);
        String paramJson=(String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
        String resultJson=userPermissionService.searchCurrentuserList(paramJson);
        return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(resultJson), HttpStatus.OK);
	}

}
