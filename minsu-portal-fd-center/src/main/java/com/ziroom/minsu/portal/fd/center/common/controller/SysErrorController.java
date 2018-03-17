/**
 * @FileName: ConfigController.java
 * @Package com.ziroom.minsu.portal.front.common.controller
 * 
 * @author jixd
 * @created 2016年8月11日 下午2:33:06
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.portal.fd.center.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * <p>错误页跳转</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author busj
 * @since 1.0
 * @version 1.0
 */
@RequestMapping("/error")
@Controller
public class SysErrorController {
	
	@RequestMapping("/houseLimit")
	public void houseLimit(){
		
	}
	
	@RequestMapping("/500")
	public String error500(){
		return "error/500";
	}
	
	@RequestMapping("/404")
	public String error404(){
		return "error/404";
	}
}
