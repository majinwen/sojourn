package com.asura.amp.dubbo.service.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.asura.amp.authority.entity.Operator;
import com.asura.amp.authority.logon.constant.LogonConstant;
import com.asura.amp.common.entity.BaseAjaxValue;
import com.asura.amp.common.entity.Success;
import com.asura.amp.dubbo.common.entity.LoadBalance;
import com.asura.amp.dubbo.common.entity.Provider;
import com.asura.amp.dubbo.common.service.OverrideService;
import com.asura.amp.dubbo.common.service.ProviderService;
import com.asura.amp.dubbo.common.utils.OverrideUtils;

@Controller
@RequestMapping("/servicecenter/service/loadbalances")
public class LoadbalancesController {
	
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
	public String loadbalancesAddInit(String service, Model model) {
    	
    	// model.addAttribute("service", service);
    	if (service != null && service.length() > 0 && !service.contains("*")) {
            List<Provider> providerList = providerService.findByService(service);
            List<String> addressList = new ArrayList<String>();
            for(Provider provider : providerList){
                addressList.add(provider.getUrl().split("://")[1].split("/")[0]);
            }
            model.addAttribute("addressList", addressList);
            // model.addAttribute("service", service);
            model.addAttribute("methods", CollectionUtils.sort(providerService.findMethodsByService(service)));
        } else {
//            List<String> serviceList = PullTool.sortSimpleName(providerService.findServices());
//            model.addAttribute("serviceList", serviceList);
            
            List<String> serviceList = new ArrayList<String>();
        	
        	serviceList.addAll(providerService.findServices());
            //serviceList.addAll(consumerService.findServices());
            
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
    	
		return "/servicecenter/service/loadbalances/add";
    }
    
    @RequestMapping(value="/add")
    @ResponseBody
	public BaseAjaxValue loadbalancesAdd(LoadBalance loadBalance, String balance_method, HttpServletRequest request) throws Exception {
    	Operator user = (Operator) request.getSession().getAttribute(LogonConstant.SESSION_OPERATOR);
		
		loadBalance.setMethod(balance_method);
		loadBalance.setUsername(user.getLoginName());
    	overrideService.saveOverride(OverrideUtils.loadBalanceToOverride(loadBalance));

		return new Success("负载均衡添加成功");
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
	public String loadbalancesEditInit(Long loadbalancesId, Model model) {
    	
    	LoadBalance loadbalance = OverrideUtils.overrideToLoadBalance(overrideService.findById(loadbalancesId));
    	loadbalancesAddInit(loadbalance.getService(), model);
    	
    	model.addAttribute("loadbalance", loadbalance);
		
		return "/servicecenter/service/loadbalances/edit";
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
    @RequestMapping(value="/edit")
    @ResponseBody
	public BaseAjaxValue loadbalancesEdit(LoadBalance loadBalance, String balance_method) {
    	loadBalance.setMethod(balance_method);
    	overrideService.updateOverride(OverrideUtils.loadBalanceToOverride(loadBalance));
    	
    	return new Success("负载均衡更新成功");
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
    @RequestMapping(value="/delete")
	@ResponseBody
	public BaseAjaxValue loadbalancesDelete(Long loadbalancesId) {
    	overrideService.deleteOverride(loadbalancesId);

		return new Success("负载均衡删除成功");
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
	public BaseAjaxValue loadbalancesDeleteBatch(Long[] rows) {
		for (Long loadbalancesId : rows) {
			overrideService.deleteOverride(loadbalancesId);
		}
		
		return new Success("负载均衡批量删除成功");
	}
}
