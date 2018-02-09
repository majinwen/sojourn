/**
 * @FileName: SpecialPermissionInterceptor.java
 * @Package com.ziroom.minsu.troy.common.interceptor
 * 
 * @author bushujie
 * @created 2016年10月26日 下午2:59:25
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.troy.common.interceptor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.ziroom.minsu.entity.base.AuthMenuEntity;
import com.ziroom.minsu.entity.sys.CurrentuserCityEntity;
import com.ziroom.minsu.services.basedata.api.inner.LoginService;
import com.ziroom.minsu.services.basedata.api.inner.PermissionOperateService;
import com.ziroom.minsu.services.basedata.entity.CurrentuserVo;
import com.ziroom.minsu.troy.common.util.UserUtil;
import com.ziroom.minsu.valenum.base.RoleTypeEnum;

/**
 * <p>特殊权限拦截器
 * 
 * 说明：
 *  1. 校验当前请求的URL 是否是特权菜单
 *  2. 特权菜单，查当前用户的特权
 *  3. 非特权菜单，不论用户角色，用户拥有最高权限
 * 
 * </p>
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
public class SpecialPermissionInterceptor extends HandlerInterceptorAdapter{
	
	@Resource(name = "basedata.permissionOperateService")
	private PermissionOperateService permissionOperateService;
	
	@Resource(name="basedata.loginService")
	private LoginService loginService;
	
	
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	String contextPath = request.getContextPath();// contextPath=/minsu-web-troy
        String servletPath = request.getServletPath();
        
        int index = -1;
        if((index = servletPath.indexOf("?")) > -1){
        	servletPath = servletPath.substring(1, index);
        }else if((index = servletPath.indexOf(".")) > -1){
        	servletPath = servletPath.substring(1, index);
        }else {
        	servletPath = servletPath.substring(1);
		}
        CurrentuserVo user = UserUtil.getFullCurrentUser();
        //当前登录用户为空 session失效
        if(Check.NuNObj(user)){
        	response.sendRedirect(contextPath + "/noAuthority");
        	return false;
        }
        //设置当前角色的类型
        Map<String, String> paramMap=new HashMap<String, String>();
        paramMap.put("resUrl", servletPath);
        Integer roleType= RoleTypeEnum.ADMIN.getCode();
        List<CurrentuserCityEntity> userCityList= null;
        //校验当前菜单 是否是特权菜单
        paramMap.put("currenuserFid", user.getFid());
        if(checkAuthMenuByResurl(servletPath)){
        	 String resultJson=permissionOperateService.findRoleTypeByMenu(JsonEntityTransform.Object2Json(paramMap));
             roleType=SOAResParseUtil.getIntFromDataByKey(resultJson, "roleType");
             userCityList=SOAResParseUtil.getListValueFromDataByKey(resultJson, "userCityList", CurrentuserCityEntity.class);
        }
       
        request.setAttribute("roleType", roleType);
        request.setAttribute("userCityList", userCityList);
        request.setAttribute("empCode", user.getEmpCode());
        AuthMenuEntity authMenu = new AuthMenuEntity();
        authMenu.setEmpCode(user.getEmpCode());
        authMenu.setRoleType(roleType);
        authMenu.setUserCityList(userCityList);
        request.setAttribute("authMenu",authMenu);
        return super.preHandle(request, response, handler);
    }
    
    /**
     * 
     * 校验当前菜单是否是 特权菜单  如果resUrl 是空  则直接是普通菜单，最高权限（这里目的：不影响当前操作人的业务）
     *
     * @author yd
     * @created 2016年11月1日 上午10:20:14
     *
     * @param resUrl
     * @return
     */
    private boolean checkAuthMenuByResurl(String resUrl){
    	
    	boolean authBoo = false;
    	if(!Check.NuNStrStrict(resUrl)){
    		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.loginService.checkAuthMenuByResurl(resUrl));
    		if(dto.getCode() == DataTransferObject.SUCCESS){
    			Object menuAuth = dto.getData().get("menuAuth");
    			if(!Check.NuNObj(menuAuth)){
    				return (int)menuAuth == 0?false:true;
    			}
    		}
    	}
    	return authBoo;
    }
}
