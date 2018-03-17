/*
 * Copyright (c) 2016. Copyright (c) 2016. ziroom.com.
 */
package com.ziroom.minsu.ups.common.controller;


import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.sys.EmployeeEntity;
import com.ziroom.minsu.entity.sys.SystemsEntity;
import com.ziroom.minsu.services.basedata.entity.CurrentuserVo;
import com.ziroom.minsu.ups.common.util.UserUtil;
import com.ziroom.minsu.ups.service.EmployeeService;
import com.ziroom.minsu.ups.service.ICurrentUserService;
import com.ziroom.minsu.ups.service.IRoleService;
import com.ziroom.minsu.ups.service.ISystemService;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;


/**
 * 
 * <p>跳转首页</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author jixd
 * @since 1.0
 * @version 1.0
 */
@Controller
@RequestMapping("/")
public class IndexController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);
    @Resource(name="ups.currentUserService")
    private ICurrentUserService currentUserService;

	@Resource(name="ups.employeeService")
	private EmployeeService employeeService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private ISystemService systemService;

    @RequestMapping("index")
    public String index(HttpServletRequest request,HttpServletResponse response){
        try {
            CurrentuserVo currentUser = UserUtil.getCurrentUser();
            //判断用户是否存在
            if(Check.NuNStr(UserUtil.getCurrentUserFid())){
                return "/error/loginerror";
            }
            CurrentuserVo currentuserVo=currentUserService.getCurrentuserByUserAccount(currentUser.getUserAccount());
            LogUtil.info(LOGGER,"用户信息："+JsonEntityTransform.Object2Json(currentuserVo));
            EmployeeEntity employeeEntity = employeeService.findEmployeeByFid(currentuserVo.getEmployeeFid());
            if (Check.NuNObj(employeeEntity)){
                return "/error/notExist";
            }

            LogUtil.info(LOGGER,"员工信息："+JsonEntityTransform.Object2Json(employeeEntity));
            currentuserVo.setEmpCode(employeeEntity.getEmpCode());
            currentuserVo.setFullName(employeeEntity.getEmpName());
            List<SystemsEntity> sysList = systemService.findHasSysByUid(currentuserVo.getFid());
            request.getSession().setAttribute("sysList",sysList);
            if (currentUser.getIsAdmin() == YesOrNoEnum.YES.getCode()){
                return "/index/index";
            }else{
                return "/index/index";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/index/index";
    }

    @RequestMapping("/portal")
    public String portal(){
        return "index/portal";
    }
}
