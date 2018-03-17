package com.asura.amp.dubbo.address.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.asura.amp.dubbo.common.entity.DynamicConfig;
import com.asura.amp.dubbo.common.entity.Provider;
import com.asura.amp.dubbo.common.service.ConsumerService;
import com.asura.amp.dubbo.common.service.OverrideService;
import com.asura.amp.dubbo.common.service.ProviderService;
import com.asura.amp.dubbo.common.utils.OverrideUtils;

@Controller("addressProvider")
@RequestMapping("/servicecenter/address/provider")
public class ProviderController {
	
	@Autowired
    private ProviderService providerService;
    
    @Autowired
    private ConsumerService consumerService;
	
	@Autowired
	private OverrideService overrideService;
    
    /**
     * 查询服务信息
     *
     * @author zhangshaobin
     * @created 2012-12-25 下午4:28:55
     *
     * @param search
     * @return
     */
    @RequestMapping(value="/addinit", params="address")
	public String providerAddInit(String address, Model model) {
		List<String> serviceList = new ArrayList<String>();
    	
    	serviceList.addAll(providerService.findServicesByAddress(address));
        serviceList.addAll(consumerService.findServicesByAddress(address));
        
        Map<String, String> serviceMap = new HashMap<String, String>();
        for(String service : serviceList) {
        	if(service != null) {
        		int index = service.lastIndexOf('.');
        		String name = index >=0 ? service.substring(index + 1) : service;
        		serviceMap.put(name, service);
        	}
        }
        
        model.addAttribute("serviceMap", serviceMap);
    	model.addAttribute("service_address", "dubbo://" + address + "/com.xxx.XxxService?application=xxx");
    	model.addAttribute("address", address);
		
		return "/servicecenter/address/provider/add";
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
    @RequestMapping(value="/addinit", params="providerId")
	public String providerAddInit(Long providerId, Model model) {
    	
    	Provider provider = providerService.findProvider(providerId);
    	if (provider != null) {
			String parameters = provider.getParameters();
			if (parameters != null && parameters.length() > 0) {
				Map<String, String> map = StringUtils.parseQueryString(parameters);
				map.put("timestamp", String.valueOf(System.currentTimeMillis()));
				map.remove("pid");
				provider.setParameters(StringUtils.toQueryString(map));
			}
	    	model.addAttribute("service_address", provider.getUrl() + "?" + provider.getParameters());
	    	model.addAttribute("address", provider.getAddress());
		}
    	

		List<String> serviceList = new ArrayList<String>();
    	
    	serviceList.addAll(providerService.findServicesByAddress(provider.getAddress()));
        serviceList.addAll(consumerService.findServicesByAddress(provider.getAddress()));
        
        Map<String, String> serviceMap = new HashMap<String, String>();
        for(String service : serviceList) {
        	if(service != null) {
        		int index = service.lastIndexOf('.');
        		String name = index >=0 ? service.substring(index + 1) : service;
        		serviceMap.put(name, service);
        	}
        }
        
        model.addAttribute("serviceMap", serviceMap);
		
		return "/servicecenter/address/provider/add";
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
    @RequestMapping(value="/editinit")
	public String providerEditInit(Long providerId, Model model) {
    	
    	Provider provider = providerService.findProvider(providerId);
		if (provider != null && provider.isDynamic()) {
			List<DynamicConfig> overrides = overrideService.findByServiceAndAddress(provider.getService(), provider.getAddress());
			OverrideUtils.setProviderOverrides(provider, overrides);
		}
		model.addAttribute("provider", provider);
		
		return "/servicecenter/address/provider/edit";
    }
}
