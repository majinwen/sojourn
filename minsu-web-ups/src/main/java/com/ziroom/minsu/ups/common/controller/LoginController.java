/*
 * Copyright (c) 2016. Copyright (c) 2016. ziroom.com.
 */
package com.ziroom.minsu.ups.common.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ziroom.minsu.services.common.utils.SentinelJedisUtil;
import com.ziroom.minsu.ups.common.constant.Constant;

/**
 * 
 * <p>登录controller</p>
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
@RequestMapping("/")
public class LoginController {

    @Value("#{'${ups.web.logout.url}'.trim()}")
    private String logoutUrl;
    
    @Resource(name="sentinelJedisClient")
    private SentinelJedisUtil sentinelJedisClient;

    @RequestMapping(value = "/login")
    public String userLogin(HttpServletRequest request, Model model) {
        return "login/login";
    }
    
    @RequestMapping(value = "/logout")
    public String userLogout(HttpServletRequest request) {
        request.getSession().invalidate();
        sentinelJedisClient.del(Constant.USER_NAME_CACHE_KEY);
        return "redirect:" + logoutUrl;
    }
    
    @RequestMapping(value = "/error")
    public String redirectError(HttpServletRequest request) {
    	request.getSession().invalidate();
    	
    	return "error/error";
    }
    
    @RequestMapping(value="/noAuthority")
    public String redirect403Error(HttpServletRequest request){
    	return "error/403";
    }
}
