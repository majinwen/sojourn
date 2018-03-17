package com.asura.amp.dubbo.address.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.asura.amp.dubbo.common.entity.DynamicConfig;
import com.asura.amp.dubbo.common.entity.Weight;
import com.asura.amp.dubbo.common.service.OverrideService;
import com.asura.amp.dubbo.common.service.ProviderService;
import com.asura.amp.dubbo.common.utils.OverrideUtils;
import com.asura.amp.dubbo.common.utils.PullTool;

@Controller("addressweights")
@RequestMapping("/servicecenter/address/weights")
public class WeightsController {
	
	@Autowired
    private ProviderService providerService;
    
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
    @RequestMapping(value="/addinit")
	public String weightsAddInit(String address, Model model) {
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
    	
		return "/servicecenter/address/weights/add";
    }
    
    /**
     * 查询服务信息
     *
     * @author zhangshaobin
     * @created 2012-12-25 下午4:28:55
     *
     * @param search
     * @return
     * @throws ParseException 
     */
    @RequestMapping(value="/editinit")
	public String weightsEditInit(Long weightsId, Model model) {
    	// weightsAddInit(servcice, model);
    	
    	DynamicConfig override = overrideService.findById(weightsId);
    	Weight weight = OverrideUtils.overrideToWeight(override);

    	String address = override.getAddress();
    	
    	model.addAttribute("weight", weight);
    	model.addAttribute("address", address);
    	
    	weightsAddInit(override.getService(), model);
    	
    	
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
		
		return "/servicecenter/address/weights/edit";
    }
}
