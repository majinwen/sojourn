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
import com.asura.amp.dubbo.common.entity.Consumer;
import com.asura.amp.dubbo.common.entity.Provider;
import com.asura.amp.dubbo.common.entity.Route;
import com.asura.amp.dubbo.common.service.ConsumerService;
import com.asura.amp.dubbo.common.service.ProviderService;
import com.asura.amp.dubbo.common.service.RouteService;
import com.asura.amp.dubbo.common.utils.RouteRule;
import com.asura.amp.dubbo.common.utils.RouteUtils;

@Controller
@RequestMapping("/servicecenter/service/routes")
public class RoutesController {
	
	@Autowired
    private ConsumerService consumerService;
	
	@Autowired
    private ProviderService providerService;
    
    @Autowired
    private RouteService routeService;
    
    // 参数1表示dubbo存储字段，参数2为web传值参数名（匹配的情况），参数3为web传值参数名（不匹配的情况）
    // method为dwz关键词，改为 ==> routemethod
    static final String[][] when_names = {
        {"method", "routemethod", "unmethod"},
        {"consumer.application", "consumerApplication", "unconsumerApplication"},
        {"consumer.cluster", "consumerCluster", "unconsumerCluster"},
        {"consumer.host", "consumerHost", "unconsumerHost"},
        {"consumer.version", "consumerVersion", "unconsumerVersion"},
        {"consumer.group", "consumerGroup", "unconsumerGroup"},
    };

    static final String[][] then_names = {
    	{"provider.application", "providerApplication", "unproviderApplication"},
        {"provider.cluster", "providerCluster", "unproviderCluster"}, // 要校验Cluster是否存在
        {"provider.host", "providerHost", "unproviderHost"},
        {"provider.protocol", "providerProtocol", "unproviderProtocol"},
        {"provider.port", "providerPort", "unproviderPort"},
        {"provider.version", "providerVersion", "unproviderVersion"},
        {"provider.group", "providerGroup", "unproviderGroup"}
    };
    
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
	public String routesAddInit(String service, Model model) {
    	
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
    	model.addAttribute("methods", CollectionUtils.sort(new ArrayList<String>(providerService.findMethodsByService(service))));
		
		return "/servicecenter/service/routes/add";
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
    @RequestMapping(value="/addinit", params="routesId")
	public String routesAddInit(Long routesId, Model model) throws ParseException {
    	routesShow(routesId, model);
		return "/servicecenter/service/routes/add";
    }
    
    @RequestMapping(value="/add")
    @ResponseBody
	public BaseAjaxValue routesAdd(Route route, HttpServletRequest request) {
    	Operator user = (Operator) request.getSession().getAttribute(LogonConstant.SESSION_OPERATOR);

		Map<String, String> when_name2valueList = new HashMap<String, String>();
		Map<String, String> notWhen_name2valueList = new HashMap<String, String>();
		for (String[] names : when_names) {
			when_name2valueList.put(names[0], request.getParameter(names[1]));
			notWhen_name2valueList.put(names[0], request.getParameter(names[2])); // value不为null的情况，这里处理，后面会保证
		}

		Map<String, String> then_name2valueList = new HashMap<String, String>();
		Map<String, String> notThen_name2valueList = new HashMap<String, String>();
		for (String[] names : then_names) {
			then_name2valueList.put(names[0], request.getParameter(names[1]));
			notThen_name2valueList.put(names[0], request.getParameter(names[2]));
		}

		RouteRule routeRule = RouteRule.createFromNameAndValueListString(
				when_name2valueList, notWhen_name2valueList,
				then_name2valueList, notThen_name2valueList);

		route.setUsername(user.getLoginName());
		route.setOperator(request.getRemoteHost());
		route.setRule(routeRule.toString());

		routeService.createRoute(route);

		return new Success("路由规则添加成功");
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
    @RequestMapping(value="/show")
	public String routesShow(Long routesId, Model model) throws ParseException {
    	
    	Route route = routeService.findRoute(routesId);
        
        RouteRule routeRule= RouteRule.parse(route);
        
        @SuppressWarnings("unchecked")
        Map<String, RouteRule.MatchPair>[] paramArray = new Map[] {routeRule.getWhenCondition(), routeRule.getThenCondition()};
        String[][][] namesArray = new String[][][] {when_names, then_names };
        
        for(int i=0; i<paramArray.length; ++i) {
            Map<String, RouteRule.MatchPair> param = paramArray[i];
            String[][] names = namesArray[i];
            for(String[] name : names) {
                RouteRule.MatchPair matchPair = param.get(name[0]);
                if(matchPair == null) {
                    continue;
                }
                
                if(!matchPair.getMatches().isEmpty()) {
                    String m = RouteRule.join(matchPair.getMatches());
                    model.addAttribute(name[1], m);
                }
                if(!matchPair.getUnmatches().isEmpty()) {
                    String u = RouteRule.join(matchPair.getUnmatches());
                    model.addAttribute(name[2], u);
                }
            }
        }
        
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
        
        model.addAttribute("methods", CollectionUtils.sort(new ArrayList<String>(providerService.findMethodsByService(route.getService()))));
        model.addAttribute("route", route);
		
		return "/servicecenter/service/routes/show";
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
	public String routesEditInit(Long routesId, Model model) throws ParseException {
    	routesShow(routesId, model);
		
		return "/servicecenter/service/routes/edit";
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
	public BaseAjaxValue routesEdit(Route route, HttpServletRequest request) {
    	Operator user = (Operator) request.getSession().getAttribute(LogonConstant.SESSION_OPERATOR);
    	
        Route oldRoute = routeService.findRoute(route.getId());
        
        Map<String, String> when_name2valueList = new HashMap<String, String>();
        Map<String, String> notWhen_name2valueList = new HashMap<String, String>();
        for(String[] names : when_names) {
            when_name2valueList.put(names[0], request.getParameter(names[1]));
            notWhen_name2valueList.put(names[0], request.getParameter(names[2])); // value不为null的情况，这里处理，后面会保证
        }

        Map<String, String> then_name2valueList = new HashMap<String, String>();
        Map<String, String> notThen_name2valueList = new HashMap<String, String>();
        for(String[] names : then_names) {
            then_name2valueList.put(names[0], request.getParameter(names[1]));
            notThen_name2valueList.put(names[0], request.getParameter(names[2]));
        }

        RouteRule routeRule = RouteRule.createFromNameAndValueListString(
                when_name2valueList, notWhen_name2valueList,
                then_name2valueList, notThen_name2valueList);
        
        RouteRule result = null;
        
        if(result == null){
            result = routeRule;
        }
        
        route.setRule(result.toString());
        //route.setService(service);
        //route.setPriority(priority);
        //route.setName((String)context.get("name"));
        route.setUsername(user.getLoginName());
        route.setOperator(request.getRemoteHost());
        // route.setId(Long.valueOf(idStr));
        // route.setPriority(Integer.parseInt((String)context.get("priority")));
        route.setService(oldRoute.getService());
        route.setEnabled(oldRoute.isEnabled());
        routeService.updateRoute(route);
        
//        Set<String> usernames = new HashSet<String>();
//        usernames.add(user.getUsername());
//        usernames.add(route.getUsername());
        //RelateUserUtils.addOwnersOfService(usernames, route.getService(), ownerDAO);
        
    	return new Success("路由规则更新成功");
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
    @RequestMapping(value="/select")
	public String routesSelect(Long routesId, Model model) throws ParseException {
        Route route = routeService.findRoute(routesId);
        model.addAttribute("route", route);
        
        // 获取数据
        List<Consumer> consumers = consumerService.findByService(route.getService());
        model.addAttribute("consumers", consumers);
        
        Map<String, Boolean> matchRoute = new HashMap<String, Boolean>();
        for(Consumer c : consumers) {
            matchRoute.put(c.getAddress(), RouteUtils.matchRoute(c.getAddress(), null, route, null));
        }
        model.addAttribute("matchRoute", matchRoute);
		
		return "/servicecenter/service/routes/select";
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
    public BaseAjaxValue routesEnable(Long routesId) {
    	routeService.enableRoute(routesId);
    	
		return new Success("路由规则启用成功");
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
	public BaseAjaxValue routesEnableBatch(Long[] rows) {
		for (Long routesId : rows) {
			routeService.enableRoute(routesId);
		}
		
		return new Success("路由规则批量启用成功");
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
    public BaseAjaxValue routesDisable(Long routesId) {
    	routeService.disableRoute(routesId);
    	
		return new Success("路由规则禁用成功");
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
	public BaseAjaxValue routesDisableBatch(Long[] rows) {
		for (Long routesId : rows) {
			routeService.disableRoute(routesId);
		}
		
		return new Success("路由规则批量禁用成功");
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
    public BaseAjaxValue routesDelete(Long routesId) {
    	routeService.deleteRoute(routesId);
    	
		return new Success("路由规则删除成功");
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
	public BaseAjaxValue routesDeleteBatch(Long[] rows) {
		for (Long routesId : rows) {
			routeService.deleteRoute(routesId);
		}
		
		return new Success("路由规则批量删除成功");
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
    @RequestMapping(value="/preview")
	public String routesPreview(Long routesId, Long consumerId, String address, String service, Model model) throws Exception {
		Map<String, String> serviceUrls = new HashMap<String, String>();
		Route route = routeService.findRoute(routesId);
		List<Provider> providers = providerService.findByService(route.getService());
		if (providers != null) {
			for (Provider p : providers) {
				serviceUrls.put(p.getUrl(), p.getParameters());
			}
		}
		if (consumerId != null) {
			Consumer consumer = consumerService.findConsumer(consumerId);
			Map<String, String> result = RouteUtils.previewRoute(consumer.getService(), consumer.getAddress(), consumer.getParameters(), serviceUrls, route, null, null);
			
			model.addAttribute("route", route);
			model.addAttribute("consumer", consumer);
			model.addAttribute("result", result);
		} else {
			Map<String, String> result = RouteUtils.previewRoute(service, address, null, serviceUrls, route, null, null);
			model.addAttribute("route", route);

			Consumer consumer = new Consumer();
			consumer.setService(service);
			consumer.setAddress(address);
			
			model.addAttribute("consumer", consumer);
			model.addAttribute("result", result);
		}
		
		return "/servicecenter/service/routes/preview";
	}
}
