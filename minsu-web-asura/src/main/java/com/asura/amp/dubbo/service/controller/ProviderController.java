package com.asura.amp.dubbo.service.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.asura.amp.authority.entity.Operator;
import com.asura.amp.authority.logon.constant.LogonConstant;
import com.asura.amp.common.entity.BaseAjaxValue;
import com.asura.amp.common.entity.Success;
import com.asura.amp.dubbo.common.entity.DynamicConfig;
import com.asura.amp.dubbo.common.entity.Provider;
import com.asura.amp.dubbo.common.service.ConsumerService;
import com.asura.amp.dubbo.common.service.OverrideService;
import com.asura.amp.dubbo.common.service.ProviderService;
import com.asura.amp.dubbo.common.utils.OverrideUtils;
import com.asura.amp.dubbo.common.utils.PullTool;

@Controller
@RequestMapping("/servicecenter/service/provider")
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
    @RequestMapping(value="/addinit", params="service")
	public String providerAddInit(String service, Model model) {
    	
    	model.addAttribute("service", service);
    	if(StringUtils.isEmpty(service)) {
    		service = "com.xxx.XxxService";
    		
	    	List<String> serviceList = new ArrayList<String>();
	    	
	    	serviceList.addAll(providerService.findServices());
	        serviceList.addAll(consumerService.findServices());
	        
	        Map<String, String> serviceMap = new HashMap<String, String>();
	        for(String svc : serviceList) {
	        	if(svc != null) {
	        		int index = svc.lastIndexOf('.');
	        		String name = index >=0 ? svc.substring(index + 1) : svc;
	        		serviceMap.put(name, svc);
	        	}
	        }
	        
	        model.addAttribute("serviceMap", serviceMap);
    	}
    	model.addAttribute("service_address", "dubbo://0.0.0.0:20880/" + service + "?application=xxx");
		
		return "/servicecenter/service/provider/add";
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
	    	model.addAttribute("service", provider.getService());
		}
		
		return "/servicecenter/service/provider/add";
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
    @RequestMapping(value="/add")
    @ResponseBody
	public BaseAjaxValue providerAdd(Provider provider, HttpServletRequest request) {
    	Operator user = (Operator) request.getSession().getAttribute(LogonConstant.SESSION_OPERATOR);
    	
    	String url = provider.getUrl();
    	int indexOfSeparator = url.lastIndexOf("/");
    	int indexOfParamter = url.indexOf("?");
    	
    	provider.setService(url.substring(indexOfSeparator + 1, indexOfParamter));
    	provider.setOperator(user.getLoginName());
    	provider.setOperatorAddress(request.getRemoteHost());
    	provider.setUrl(url.substring(0, indexOfParamter));
    	provider.setParameters(url.substring(indexOfParamter + 1));
    	provider.setDynamic(false); // 自添加都是静态的，url即可动态，无需动态去配置参数。
    	provider.setCreated(new Date());
    	
    	providerService.create(provider);
		
		return new Success("提供者添加成功");
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
    @RequestMapping(value="/show")
	public String providerShow(Long providerId, Model model) {
    	
    	Provider provider = providerService.findProvider(providerId);
		if (provider != null && provider.isDynamic()) {
			List<DynamicConfig> overrides = overrideService.findByServiceAndAddress(provider.getService(), provider.getAddress());
			OverrideUtils.setProviderOverrides(provider, overrides);
		}
		model.addAttribute("provider", provider);
		model.addAttribute("parameterMap", PullTool.toParameterMap(provider.getParameters()));
		
		return "/servicecenter/service/provider/show";
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
		
		return "/servicecenter/service/provider/edit";
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
    @RequestMapping(value="/edit")
    @ResponseBody
	public BaseAjaxValue providerEdit(Provider newProvider, HttpServletRequest request) {
    	Operator user = (Operator) request.getSession().getAttribute(LogonConstant.SESSION_OPERATOR);
    	
    	Long id = newProvider.getId();
    	String parameters = newProvider.getParameters();
    	Provider provider = providerService.findProvider(id);
    	
		Map<String, String> oldMap = StringUtils.parseQueryString(provider.getParameters());
        Map<String, String> newMap = StringUtils.parseQueryString(parameters);
        for (Map.Entry<String, String> entry : oldMap.entrySet()) {
        	if (entry.getValue().equals(newMap.get(entry.getKey()))) {
        		newMap.remove(entry.getKey());
        	}
        }
		if (provider.isDynamic()) {
            String address = provider.getAddress();
            List<DynamicConfig> overrides = overrideService.findByServiceAndAddress(provider.getService(), provider.getAddress());
	        OverrideUtils.setProviderOverrides(provider, overrides);
	        DynamicConfig override = provider.getOverride();
            if (override != null) {
                if (newMap.size() > 0) {
                	override.setParams(StringUtils.toQueryString(newMap));
                    override.setEnabled(true);
                    provider.setOperator(user.getLoginName());
                	provider.setOperatorAddress(request.getRemoteHost());
                    overrideService.updateOverride(override);
                } else {
                	overrideService.deleteOverride(override.getId());
                }
            } else {
                override = new DynamicConfig();
                
                String url = newProvider.getUrl();
            	int indexOfSeparator = url.lastIndexOf("/");
            	int indexOfParamter = url.indexOf("?");
            	
            	override.setService(url.substring(indexOfSeparator + 1, indexOfParamter));
                override.setAddress(address);
                override.setParams(StringUtils.toQueryString(newMap));
                override.setEnabled(true);
                provider.setOperator(user.getLoginName());
            	provider.setOperatorAddress(request.getRemoteHost());
            	
                overrideService.saveOverride(override);
            }
		} else {
			provider.setParameters(parameters);
			providerService.updateProvider(provider);
		}
		
		return new Success("提供者更新成功");
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
    @RequestMapping(value="/delete")
	@ResponseBody
    public BaseAjaxValue providerDelete(Long providerId) {
		providerService.deleteStaticProvider(providerId);
		return new Success("提供者删除成功");
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
	@RequestMapping(value = "/deletebatch")
	@ResponseBody
	public BaseAjaxValue providerDeleteBatch(Long[] rows) {
		for (Long providerId : rows) {
			providerService.deleteStaticProvider(providerId);
		}
		
		return new Success("提供者批量删除成功");
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
    @RequestMapping(value="/enable")
	@ResponseBody
    public BaseAjaxValue providerEnable(Long providerId) {
		providerService.enableProvider(providerId);
		return new Success("提供者启用成功");
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
	@RequestMapping(value = "/enablebatch")
	@ResponseBody
	public BaseAjaxValue providerEnableBatch(Long[] rows) {
		for (Long providerId : rows) {
			providerService.enableProvider(providerId);
		}
		
		return new Success("提供者批量启用成功");
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
    @RequestMapping(value="/disable")
	@ResponseBody
    public BaseAjaxValue providerDisable(Long providerId) {
		providerService.disableProvider(providerId);
		return new Success("提供者禁用成功");
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
	@RequestMapping(value = "/disablebatch")
	@ResponseBody
	public BaseAjaxValue providerDisableBatch(Long[] rows) {
		for (Long providerId : rows) {
			providerService.disableProvider(providerId);
		}
		
		return new Success("提供者批量禁用成功");
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
    @RequestMapping(value="/doubling")
	@ResponseBody
    public BaseAjaxValue providerDoubling(Long providerId) {
		providerService.doublingProvider(providerId);
		return new Success("提供者倍权成功");
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
    @RequestMapping(value="/doublingbatch")
	@ResponseBody
    public BaseAjaxValue providerDoublingBatch(Long[] rows) {
    	for(Long providerId : rows) {
    		providerService.doublingProvider(providerId);
    	}
    	
		return new Success("提供者批量倍权成功");
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
    @RequestMapping(value="/halving")
	@ResponseBody
    public BaseAjaxValue providerHalving(Long providerId) {
		providerService.halvingProvider(providerId);
		return new Success("提供者半权成功");
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
    @RequestMapping(value="/halvingbatch")
	@ResponseBody
    public BaseAjaxValue providerHalvingBatch(Long[] rows) {
    	for(Long providerId : rows) {
    		providerService.halvingProvider(providerId);
    	}
    	
		return new Success("提供者批量半权成功");
    }
}
