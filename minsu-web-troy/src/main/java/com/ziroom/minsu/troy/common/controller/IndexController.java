/*
 * Copyright (c) 2016. Copyright (c) 2016. ziroom.com.
 */
package com.ziroom.minsu.troy.common.controller;


import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.asura.framework.base.util.Check;
import com.ziroom.minsu.services.basedata.api.inner.LoginService;
import com.ziroom.minsu.services.basedata.entity.UpsUserVo;
import com.ziroom.minsu.troy.common.util.UserUtil;

import javax.servlet.http.HttpServletResponse;


/**
 * 
 * <p>首页controller</p>
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
public class IndexController {

    @Value("#{'${minsu.static.resource.url}'.trim()}")
    private String staticResourceUrl;
    
    @Resource(name="basedata.loginService")
    private LoginService loginService;
    
    @Value("#{'${login.error.msg}'.trim()}")
    private String loginErrorMsg;

    @RequestMapping("index")
    public String index(HttpServletRequest request,HttpServletResponse response){
        HttpSession session = request.getSession();
        session.setAttribute("staticResourceUrl", staticResourceUrl);
        //判断用户是否存在
        if(StringUtils.isBlank(UserUtil.getCurrentUserFid())){
        	request.setAttribute("errorMsg",loginErrorMsg);
        	return "/error/loginerror";
        }
        UpsUserVo upsUserVo=UserUtil.getUpsUserMsg();
        if(Check.NuNObj(upsUserVo)){
			request.setAttribute("errorMsg",loginErrorMsg);
        	return "/error/loginerror";
        } else {
        	request.setAttribute("currentuserResList", upsUserVo.getResourceVoList());
		}
        return "/index/index";
    }
}
