package com.ziroom.minsu.portal.fd.center.house.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
* @author jixd on 2016年7月18日
* @version 1.0
* @since 1.0
*/
@RequestMapping("/house")
@Controller
public class HouseController {
	
	@RequestMapping("/index")
	public String index(HttpServletRequest request){
	
		return "/index/index";
	}
	
}
