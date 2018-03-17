package com.asura.amp.dubbo.service.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asura.amp.common.entity.BaseAjaxValue;
import com.asura.amp.common.entity.Success;
import com.asura.amp.dubbo.common.entity.Owner;
import com.asura.amp.dubbo.common.service.OwnerService;
import com.asura.amp.dubbo.common.service.ProviderService;

@Controller
@RequestMapping("/servicecenter/service/owners")
public class OwnersController {
	
	@Autowired
    private ProviderService providerService;

	@Autowired
	private OwnerService ownerService;
    
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
	public String ownersAddInit(String service, Model model) {
    	
    	if (service == null || service.length() == 0) {
//            List<String> serviceList = PullTool.sortSimpleName(new ArrayList<String>(providerService.findServices()));
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
    	
		return "/servicecenter/service/owners/add";
    }
    
    @RequestMapping(value="/add")
    @ResponseBody
	public BaseAjaxValue ownersAdd(Owner owner) throws Exception {
    	
		ownerService.saveOwner(owner);

		return new Success("负责人添加成功");
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
	public BaseAjaxValue ownersDelete(Owner owner) {
    	ownerService.deleteOwner(owner);

		return new Success("负责人删除成功");
	}
}
