/**
 * @FileName: ApplicationController.java
 * @Package com.asura.management.servicecenter.service.controller
 * 
 * @author zhangshaobin
 * @created 2012-12-25 下午4:32:06
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.amp.dubbo.service.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.asura.amp.authority.entity.Operator;
import com.asura.amp.authority.logon.constant.LogonConstant;
import com.asura.amp.common.entity.BaseAjaxValue;
import com.asura.amp.common.entity.Success;
import com.asura.amp.dubbo.common.entity.DynamicConfig;
import com.asura.amp.dubbo.common.service.OverrideService;

/**
 * <p>应用Controller</p>
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
@RequestMapping("/servicecenter/service/application")
public class ApplicationController {
	
	@Autowired
	private OverrideService overrideService;
    
	/**
	 * 屏蔽应用服务
	 *
	 * @author zhangshaobin
	 * @created 2013-1-16 下午7:39:14
	 *
	 * @param service		服务
	 * @param appName		应用名
	 * @param request
	 * @return
	 */
    @RequestMapping(value="/shield")
	@ResponseBody
    public BaseAjaxValue consumerShield(String service, String appName, HttpServletRequest request) {
    	Operator user = (Operator) request.getSession().getAttribute(LogonConstant.SESSION_OPERATOR);
    	try {
			mock(service, new String[]{appName}, user.getLoginName(), request.getRemoteHost(), "force:return null");
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return new Success("消费者屏蔽成功");
    }
    
    /**
     * 批量屏蔽应用服务
     *
     * @author zhangshaobin
     * @created 2013-1-16 下午7:40:41
     *
     * @param service		服务
     * @param rows			需屏蔽的应用列表
     * @param request
     * @return
     */
    @RequestMapping(value="/shieldbatch")
	@ResponseBody
    public BaseAjaxValue consumerShieldBatch(String service, String[] rows, HttpServletRequest request) {
    	Operator user = (Operator) request.getSession().getAttribute(LogonConstant.SESSION_OPERATOR);
    	try {
			mock(service, rows, user.getLoginName(), request.getRemoteHost(), "force:return null");
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return new Success("消费者批量屏蔽成功");
    }
    
    /**
     * 应用服务容错
     *
     * @author zhangshaobin
     * @created 2013-1-16 下午7:41:32
     *
     * @param service		服务
	 * @param appName		应用名
	 * @param request
     * @return
     */
    @RequestMapping(value="/tolerant")
	@ResponseBody
    public BaseAjaxValue consumerTolerant(String service, String appName, HttpServletRequest request) {
    	Operator user = (Operator) request.getSession().getAttribute(LogonConstant.SESSION_OPERATOR);
    	try {
			mock(service, new String[]{appName}, user.getLoginName(), request.getRemoteHost(), "fail:return null");
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return new Success("消费者容错成功");
    }
    
    /**
     * 批量容错应用服务
     *
     * @author zhangshaobin
     * @created 2013-1-16 下午7:42:17
     *
     * @param service		服务
     * @param rows			需容错的应用列表
     * @param request
     * @return
     */
    @RequestMapping(value="/tolerantbatch")
	@ResponseBody
    public BaseAjaxValue consumerTolerantBatch(String service, String[] rows, HttpServletRequest request) {
    	Operator user = (Operator) request.getSession().getAttribute(LogonConstant.SESSION_OPERATOR);
    	try {
			mock(service, rows, user.getLoginName(), request.getRemoteHost(), "fail:return null");
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return new Success("消费者批量容错成功");
    }
    
    /**
     * 应用服务恢复
     *
     * @author zhangshaobin
     * @created 2013-1-16 下午7:42:54
     *
     * @param service		服务
	 * @param appName		应用名
	 * @param request
     * @return
     */
    @RequestMapping(value="/recover")
	@ResponseBody
    public BaseAjaxValue consumerRecover(String service, String appName, HttpServletRequest request) {
    	Operator user = (Operator) request.getSession().getAttribute(LogonConstant.SESSION_OPERATOR);
    	try {
			mock(service, new String[]{appName}, user.getLoginName(), request.getRemoteHost(), "");
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return new Success("消费者恢复成功");
    }
    
    /**
     * 应用服务批量恢复
     *
     * @author zhangshaobin
     * @created 2013-1-16 下午7:43:22
     *
     * @param service		服务
     * @param rows			需恢复的应用列表
     * @param request
     * @return
     */
    @RequestMapping(value="/recoverbatch")
	@ResponseBody
	public BaseAjaxValue consumerRecoverBatch(String service, String[] rows, HttpServletRequest request) {
    	Operator user = (Operator) request.getSession().getAttribute(LogonConstant.SESSION_OPERATOR);
		try {
			mock(service, rows, user.getLoginName(), request.getRemoteHost(), "");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new Success("消费者批量恢复成功");
	}
    
    /**
     * 查询服务信息
     *
     * @author zhangshaobin
     * @created 2012-12-25 下午4:28:55
     *
     * @param search
     * @return
     */
    @RequestMapping(value="/shieldall")
	@ResponseBody
	public BaseAjaxValue consumerShieldAll(String service, HttpServletRequest request) {
    	Operator user = (Operator) request.getSession().getAttribute(LogonConstant.SESSION_OPERATOR);
		try {
			allmock(service, user.getLoginName(), request.getRemoteHost(), "force:return null");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Success("服务缺省屏蔽成功");
	}
    
    @RequestMapping(value="/tolerantall")
	@ResponseBody
    public BaseAjaxValue consumerTolerantAll(String service, HttpServletRequest request) {
    	Operator user = (Operator) request.getSession().getAttribute(LogonConstant.SESSION_OPERATOR);
    	try {
			allmock(service, user.getLoginName(), request.getRemoteHost(), "fail:return null");
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return new Success("服务缺省容错成功");
    }
    
    @RequestMapping(value="/recoverall")
	@ResponseBody
    public BaseAjaxValue consumerRecoverAll(String service, HttpServletRequest request) {
    	Operator user = (Operator) request.getSession().getAttribute(LogonConstant.SESSION_OPERATOR);
    	try {
			allmock(service, user.getLoginName(), request.getRemoteHost(), "");
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return new Success("服务缺省容错成功");
    }
    
    private boolean mock(String service, String[] applications, String username, String remoteIp, String mock) throws Exception {
        for (String application : applications) {
	        List<DynamicConfig> overrides = overrideService.findByServiceAndApplication(service, application);
	        if (overrides != null && overrides.size() > 0) {
	            for (DynamicConfig override: overrides) {
	                Map<String, String> map = StringUtils.parseQueryString(override.getParams());
	                if (mock == null || mock.length() == 0) {
	                    map.remove("mock");
	                } else {
	                    map.put("mock", URL.encode(mock));
	                }
	                if (map.size() > 0) {
	                	override.setParams(StringUtils.toQueryString(map));
	                    override.setEnabled(true);
	                    override.setOperator(username);
	                    override.setOperatorAddress(remoteIp);
	                    overrideService.updateOverride(override);
	                } else {
	                	overrideService.deleteOverride(override.getId());
	                }
	            }
	        } else if (mock != null && mock.length() > 0) {
	        	DynamicConfig override = new DynamicConfig();
	            override.setService(service);
	            override.setApplication(application);
	            override.setParams("mock=" + URL.encode(mock));
	            override.setEnabled(true);
	            override.setOperator(username);
	            override.setOperatorAddress(remoteIp);
	            overrideService.saveOverride(override);
	        }
        }
        return true;
    }
    
    private boolean allmock(String service, String username, String remoteIp, String mock) throws Exception {
        List<DynamicConfig> overrides = overrideService.findByService(service);
        DynamicConfig allOverride = null;
        if (overrides != null && overrides.size() > 0) {
            for (DynamicConfig override: overrides) {
            	if (override.isDefault()) {
            		allOverride = override;
            		break;
            	}
            }
        }
        if (allOverride != null) {
        	Map<String, String> map = StringUtils.parseQueryString(allOverride.getParams());
            if (mock == null || mock.length() == 0) {
                map.remove("mock");
            } else {
                map.put("mock", URL.encode(mock));
            }
            if (map.size() > 0) {
            	allOverride.setParams(StringUtils.toQueryString(map));
            	allOverride.setEnabled(true);
            	allOverride.setOperator(username);
            	allOverride.setOperatorAddress(remoteIp);
                overrideService.updateOverride(allOverride);
            } else {
            	overrideService.deleteOverride(allOverride.getId());
            }
        } else if (mock != null && mock.length() > 0) {
        	DynamicConfig override = new DynamicConfig();
            override.setService(service);
            override.setParams("mock=" + URL.encode(mock));
            override.setEnabled(true);
            override.setOperator(username);
            override.setOperatorAddress(remoteIp);
            overrideService.saveOverride(override);
        }
        return true;
    }
}
