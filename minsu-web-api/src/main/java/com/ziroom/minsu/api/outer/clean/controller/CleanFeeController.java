/**
 * @FileName: CleanFeeContrller.java
 * @Package com.ziroom.minsu.api.outer.smartlock.controller
 * 
 * @author yd
 * @created 2016年9月9日 下午1:35:07
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.api.outer.clean.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ziroom.minsu.api.common.dto.ResponseSecurityDto;

/**
 * <p>服务提供保洁费接口</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
@Controller
@RequestMapping("/clean")
public class CleanFeeController {
	/**
	 * 日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(CleanFeeController.class);
	
	@RequestMapping(value = "/${NO_LGIN_AUTH}/isLanlord", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> isLanlord(HttpServletRequest request){
		
		
		return null;
	}
}
