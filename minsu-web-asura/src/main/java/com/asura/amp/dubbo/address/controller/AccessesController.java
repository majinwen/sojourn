/**
 * @FileName: AccessesController.java
 * @Package com.asura.amp.dubbo.address.controller
 * 
 * @author zhangshaobin
 * @created 2012-12-25 下午4:32:06
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.amp.dubbo.address.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.asura.amp.dubbo.common.service.ProviderService;
import com.asura.amp.dubbo.common.utils.PullTool;

/**
 * <p>访问控制Controller</p>
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
@Controller("addressaccesses")
@RequestMapping("/servicecenter/address/accesses")
public class AccessesController {
	
	@Autowired
	private ProviderService providerService;
    
    /**
     * 添加访问控制的初始化页面
     *
     * @author zhangshaobin
     * @created 2013-1-16 下午7:31:44
     *
     * @param service		当前Service
     * @param model			model
     * @return
     */
    @RequestMapping(value="/addinit")
	public String accessesAddInit(String address, Model model) {
    	List<String> serviceList = new ArrayList<String>();
    	
    	address = PullTool.getIP(address);
    	
    	serviceList.addAll(providerService.findServicesByAddress(address));
        // serviceList.addAll(consumerService.findServicesByAddress(address));
        
        Map<String, String> serviceMap = new HashMap<String, String>();
        for(String service : serviceList) {
        	if(service != null) {
        		int index = service.lastIndexOf('.');
        		String name = index >=0 ? service.substring(index + 1) : service;
        		serviceMap.put(name, service);
        	}
        }
        
        model.addAttribute("serviceMap", serviceMap);
        model.addAttribute("address", address);
        
		return "/servicecenter/address/accesses/add";
    }
}
