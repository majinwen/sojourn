package com.asura.amp.dubbo.service.controller;

import java.io.BufferedReader;
import java.io.StringReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asura.amp.authority.entity.Operator;
import com.asura.amp.authority.logon.constant.LogonConstant;
import com.asura.amp.common.entity.BaseAjaxValue;
import com.asura.amp.common.entity.Success;
import com.asura.amp.dubbo.common.entity.DynamicConfig;
import com.asura.amp.dubbo.common.entity.Provider;
import com.asura.amp.dubbo.common.entity.Weight;
import com.asura.amp.dubbo.common.service.OverrideService;
import com.asura.amp.dubbo.common.service.ProviderService;
import com.asura.amp.dubbo.common.utils.OverrideUtils;
import com.asura.amp.dubbo.common.utils.PullTool;

@Controller
@RequestMapping("/servicecenter/service/weights")
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
	public String weightsAddInit(String service, Model model) {
    	
    	// model.addAttribute("service", service);
    	if (service != null && service.length() > 0 && !service.contains("*")) {
            List<Provider> providerList = providerService.findByService(service);
            List<String> addressList = new ArrayList<String>();
            for(Provider provider : providerList){
                addressList.add(provider.getUrl().split("://")[1].split("/")[0]);
            }
            model.addAttribute("addressList", addressList);
            // model.addAttribute("service", service);
            // model.addAttribute("methods", CollectionUtils.sort(providerService.findMethodsByService(service)));
        } else {
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
    	
		return "/servicecenter/service/weights/add";
    }
    
    @RequestMapping(value="/add")
    @ResponseBody
	public BaseAjaxValue weightsAdd(String service, String address, Integer weight, HttpServletRequest request) throws Exception {
    	Operator user = (Operator) request.getSession().getAttribute(LogonConstant.SESSION_OPERATOR);

		Set<String> addresses = new HashSet<String>();
		BufferedReader reader = new BufferedReader(new StringReader(address));
		while (true) {
			String line = reader.readLine();
			if (null == line)
				break;

			String[] split = line.split("[\\s,;]+");
			for (String s : split) {
				if (s.length() == 0)
					continue;

//				String ip = s;
//				String port = null;
//				if (s.indexOf(":") != -1) {
//					ip = s.substring(0, s.indexOf(":"));
//					port = s.substring(s.indexOf(":") + 1, s.length());
//					if (port.trim().length() == 0)
//						port = null;
//				}
				addresses.add(s);
			}
		}

		Set<String> aimServices = new HashSet<String>();
		reader = new BufferedReader(new StringReader(service));
		while (true) {
			String line = reader.readLine();
			if (null == line)
				break;

			String[] split = line.split("[\\s,;]+");
			for (String s : split) {
				if (s.length() == 0)
					continue;
				aimServices.add(s);
			}
		}

		for (String aimService : aimServices) {
			for (String a : addresses) {
				Weight wt = new Weight();
				wt.setUsername(user.getLoginName());
				wt.setAddress(PullTool.getIP(a));
				wt.setService(aimService);
				wt.setWeight(weight);

				overrideService.saveOverride(OverrideUtils.weightToOverride(wt));
			}
		}

		return new Success("权重调节添加成功");
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
    	
    	model.addAttribute("weight", weight);
    	// model.addAttribute("service", override.getService());
    	
    	weightsAddInit(override.getService(), model);
		
		return "/servicecenter/service/weights/edit";
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
	public BaseAjaxValue weightsEdit(Weight weight, Model model) {
    	weight.setAddress(PullTool.getIP(weight.getAddress()));
    	overrideService.updateOverride(OverrideUtils.weightToOverride(weight));
    	
    	return new Success("权重调节更新成功");
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
	public BaseAjaxValue weightsDelete(Long weightsId) {
    	overrideService.deleteOverride(weightsId);

		return new Success("权重调节删除成功");
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
	public BaseAjaxValue weightsDeleteBatch(Long[] rows) {
		for (Long weightsId : rows) {
			overrideService.deleteOverride(weightsId);
		}
		
		return new Success("权重调节批量删除成功");
	}
	
	@RequestMapping(value = "/serviceAddresses")
	@ResponseBody
	public Set<String> serviceAddresses(String service) {
		Set<String> addressSet = new TreeSet<String>();
		
		for(String svc : service.split("\n")) {
	        for(Provider provider : providerService.findByService(svc.trim())){
	        	addressSet.add(provider.getUrl().split("://")[1].split("/")[0]);
	        }
		}
		return addressSet;
	}
}
