package com.asura.amp.dubbo.service.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.asura.amp.common.utils.PagingUtils;
import com.asura.amp.dubbo.common.entity.Access;
import com.asura.amp.dubbo.common.entity.Consumer;
import com.asura.amp.dubbo.common.entity.DynamicConfig;
import com.asura.amp.dubbo.common.entity.LoadBalance;
import com.asura.amp.dubbo.common.entity.Owner;
import com.asura.amp.dubbo.common.entity.Provider;
import com.asura.amp.dubbo.common.entity.Route;
import com.asura.amp.dubbo.common.entity.Weight;
import com.asura.amp.dubbo.common.service.ConsumerService;
import com.asura.amp.dubbo.common.service.OverrideService;
import com.asura.amp.dubbo.common.service.OwnerService;
import com.asura.amp.dubbo.common.service.ProviderService;
import com.asura.amp.dubbo.common.service.RouteService;
import com.asura.amp.dubbo.common.utils.OverrideUtils;
import com.asura.amp.dubbo.common.utils.RouteRule;
import com.asura.amp.dubbo.common.utils.RouteRule.MatchPair;
import com.asura.amp.dubbo.common.utils.RouteUtils;
import com.asura.amp.dubbo.common.utils.ServiceUtils;
import com.asura.amp.dubbo.service.entity.Application;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchCondition;
import com.asura.framework.base.paging.SearchModel;

@Controller
@RequestMapping("/servicecenter/service")
public class ServiceController {
	
	@Autowired
    private ProviderService providerService;
    
    @Autowired
    private ConsumerService consumerService;
    
    @Autowired
    private OverrideService overrideService;
    
    @Autowired
    private RouteService routeService;

	@Autowired
	private OwnerService ownerService;
    
    /**
	 * 登录页面初始化
	 *
	 * @author zhangshaobin
	 * @created 2012-12-25 下午3:36:52
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/init")
	public String init(String search_service, String type, Model model) {
		if(StringUtils.isEmpty(type) && (StringUtils.isEmpty(search_service) || "*".equals(search_service))) {
			return "forward:/servicecenter/search/allservice.do";
		} else {
			return "/servicecenter/service/init";
		}
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
    @RequestMapping(value="/provider")
	public String provider(SearchModel search, Model model) {
    	List<SearchCondition> conditions = search.getSearchConditionList();
    	
    	// 查询提供者信息
    	List<Provider> providerServices = null;
    	
    	// 服务接口名称
    	String service = "";
		if (conditions != null && conditions.size() > 0) {
			SearchCondition keywordCondition = conditions.get(0);
			if ("service".equals(keywordCondition.getName())) {
				service = (String) keywordCondition.getValue();
			}
		}
		
    	if(StringUtils.isEmpty(service)) {
    		providerServices = providerService.findAll();
    	} else {
    		providerServices = providerService.findByService(service);
    	}
    	
    	// 排序无用啊~
//		Collections.sort(providerServices, new Comparator<Provider>() {
//			@Override
//			public int compare(Provider p1, Provider p2) {
////				if(p1.getCreated() == null && p2.getCreated() == null) {
////					return 0;
////				} if (p1.getCreated() == null) {
////					return -1;
////				} else if (p2.getCreated() == null) {
////					return 1;
////				} else {
////					return p1.getCreated().compareTo(p2.getCreated());
////				}
//				
//				return p1.getId().compareTo(p2.getId());
//			}
//		});
    	PagingResult<Provider> pagingResult = PagingUtils.dealFullResult(search, providerServices);
    	ServiceUtils.providerProcesser(pagingResult.getRows());
    	model.addAttribute("PAGING_RESULT", pagingResult);
		
		return "/servicecenter/service/provider";
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
    @RequestMapping(value="/consumer")
	public String consumer(SearchModel search, Model model) {
    	List<SearchCondition> conditions = search.getSearchConditionList();
    	
    	// 查询提供者信息
    	List<Consumer> consumerServices = null;
    	List<DynamicConfig> overrides = null;
    	List<Provider> providers = null;
    	List<Route> routes = null;
    	
    	// 服务接口名称
    	String service = "";
		if (conditions != null && conditions.size() > 0) {
			SearchCondition keywordCondition = conditions.get(0);
			if ("service".equals(keywordCondition.getName())) {
				service = (String) keywordCondition.getValue();
			}
		}
		
    	if(StringUtils.isEmpty(service)) {
    		consumerServices = consumerService.findAll();
            overrides = overrideService.findAll();
    	} else {
    		consumerServices = consumerService.findByService(service);
    		overrides = overrideService.findByService(service);
    		providers = providerService.findByService(service);
    		routes = routeService.findByService(service);
    	}
    	
    	PagingResult<Consumer> pagingResult = PagingUtils.dealFullResult(search, consumerServices);
    	
    	for (Consumer consumer : pagingResult.getRows()) {
    		if (StringUtils.isEmpty(service)) {
        		providers = providerService.findByService(consumer.getService());
        		routes = routeService.findByService(consumer.getService());
        	}
    		
        	List<Route> routed = new ArrayList<Route>();
            consumer.setProviders(RouteUtils.route(consumer.getService(), consumer.getAddress(), consumer.getParameters(), providers, overrides, routes, null, routed));
        	consumer.setRoutes(routed);
        	OverrideUtils.setConsumerOverrides(consumer, overrides);
        }
    	
    	ServiceUtils.consumerProcesser(pagingResult.getRows());
    	model.addAttribute("PAGING_RESULT", pagingResult);
		
		return "/servicecenter/service/consumer";
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
    @RequestMapping(value="/application")
	public String application(SearchModel search, Model model) {
    	List<SearchCondition> conditions = search.getSearchConditionList();
    	
    	// 查询提供者信息
    	List<Application> applicationServices = new ArrayList<Application>();
    	
    	List<String> providerApplications = null;
    	List<String> consumerApplications = null;
    	Set<String> applicationNames = new TreeSet<String>();
    	
    	// 服务接口名称
    	String service = "";
		if (conditions != null && conditions.size() > 0) {
			SearchCondition keywordCondition = conditions.get(0);
			if ("service".equals(keywordCondition.getName())) {
				service = (String) keywordCondition.getValue();
			}
		}
		
		if (StringUtils.isNotEmpty(service)) {
			providerApplications = providerService.findApplicationsByServiceName(service);
			consumerApplications = consumerService.findApplicationsByServiceName(service);
			
			if (providerApplications != null && providerApplications.size() > 0) {
				applicationNames.addAll(providerApplications);
			}
			if (consumerApplications != null && consumerApplications.size() > 0) {
				applicationNames.addAll(consumerApplications);
			}
			
			List<DynamicConfig> overrides = overrideService.findByService(service);
        	Map<String, List<DynamicConfig>> application2Overrides = new HashMap<String, List<DynamicConfig>>();
            if (overrides != null && overrides.size() > 0 
                    && applicationNames != null && applicationNames.size() > 0) {
                for (String name : applicationNames) {
                    if (overrides != null && overrides.size() > 0) {
                    	List<DynamicConfig> appOverrides = new ArrayList<DynamicConfig>();
                        for (DynamicConfig override : overrides) {
                            if (override.isMatch(service, null, name)) {
                            	appOverrides.add(override);
                            }
                        }
                        Collections.sort(appOverrides, OverrideUtils.OVERRIDE_COMPARATOR);
                        application2Overrides.put(name, appOverrides);
                    }
                }
            }
	    	
			for (String name : applicationNames) {
				Application application = new Application();
				application.setName(name);
				
				String status = "";
				if (providerApplications != null && providerApplications.contains(name)) {
					status += "提供者 ";
				}
				if (consumerApplications != null && consumerApplications.contains(name)) {
					status += "消费者";
					
					application.setComsumer(true);
				}
				application.setStatus(status);
				application.setMockType(ServiceUtils.applicationMock(application2Overrides.get(name)));
				
				applicationServices.add(application);
			}
		}
		
    	PagingResult<Application> pagingResult = PagingUtils.dealFullResult(search, applicationServices);
    	
    	model.addAttribute("PAGING_RESULT", pagingResult);
		
		return "/servicecenter/service/application";
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
    @RequestMapping(value="/routes")
	public String routes(SearchModel search, Model model) {
		List<SearchCondition> conditions = search.getSearchConditionList();

		// 查询提供者信息
		List<Route> routes = new ArrayList<Route>();

		// 服务接口名称
		String service = "";
		if (conditions != null && conditions.size() > 0) {
			SearchCondition keywordCondition = conditions.get(0);
			if ("service".equals(keywordCondition.getName())) {
				service = (String) keywordCondition.getValue();
			}
		}

		if (StringUtils.isEmpty(service)) {
			routes = routeService.findAll();
		} else {
			routes = routeService.findByService(service);
		}

		PagingResult<Route> pagingResult = PagingUtils.dealFullResult(search, routes);

		model.addAttribute("PAGING_RESULT", pagingResult);

		return "/servicecenter/service/routes";
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
    @RequestMapping(value="/dynamic")
	public String dynamic(SearchModel search, Model model) {
		List<SearchCondition> conditions = search.getSearchConditionList();

		// 服务接口名称
		String service = "";
		if (conditions != null && conditions.size() > 0) {
			SearchCondition keywordCondition = conditions.get(0);
			if ("service".equals(keywordCondition.getName())) {
				service = (String) keywordCondition.getValue();
			}
		}
		
		List<DynamicConfig> overrides = null;
		if (StringUtils.isEmpty(service)) {
			overrides = overrideService.findAll();
		} else {
			overrides = overrideService.findByService(service);
		}
		
		PagingResult<DynamicConfig> pagingResult = PagingUtils.dealFullResult(search, overrides);
		
		Map<String, String> urlDecodeMap = new HashMap<String, String>();
		for(DynamicConfig dynamic : pagingResult.getRows()) {
			String params = dynamic.getParams();
			urlDecodeMap.put(params, URL.decode(params));
		}

		model.addAttribute("PAGING_RESULT", pagingResult);
		model.addAttribute("URL_DECODE_MAP", urlDecodeMap);

		return "/servicecenter/service/dynamic";
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
    @RequestMapping(value="/accesses")
	public String accesses(SearchModel search, Model model) throws ParseException {
    	List<SearchCondition> conditions = search.getSearchConditionList();

		// 服务接口名称
		String service = "";
		if (conditions != null && conditions.size() > 0) {
			SearchCondition keywordCondition = conditions.get(0);
			if ("service".equals(keywordCondition.getName())) {
				service = (String) keywordCondition.getValue();
			}
		}
		
        List<Route> routes;
        if (StringUtils.isEmpty(service)) {
        	routes = routeService.findAllForceRoute();
        } else {
        	routes = routeService.findForceRouteByService(service);
        }
        
        List<Access> accesses = new ArrayList<Access>();
		for (Route route : routes) {
			Map<String, MatchPair> rule = RouteRule.parseRule(route.getMatchRule());
			MatchPair pair = rule.get("consumer.host");
			if (pair != null) {
				for (String host : pair.getMatches()) {
					Access access = new Access();
					access.setAddress(host);
					access.setService(route.getService());
					access.setAllow(false);
					accesses.add(access);
				}
				for (String host : pair.getUnmatches()) {
					Access access = new Access();
					access.setAddress(host);
					access.setService(route.getService());
					access.setAllow(true);
					accesses.add(access);
				}
			}
		}
        
        PagingResult<Access> pagingResult = PagingUtils.dealFullResult(search, accesses);
		model.addAttribute("PAGING_RESULT", pagingResult);

		return "/servicecenter/service/accesses";
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
    @RequestMapping(value="/weights")
	public String weights(SearchModel search, Model model) {
		List<SearchCondition> conditions = search.getSearchConditionList();

		// 服务接口名称
		String service = null;
		if (conditions != null && conditions.size() > 0) {
			SearchCondition keywordCondition = conditions.get(0);
			if ("service".equals(keywordCondition.getName())) {
				service = (String) keywordCondition.getValue();
			}
		}

		service = StringUtils.isEmpty(service) ? null : service;
		
        List<Weight> weights;
        if (StringUtils.isEmpty(service)) {
        	weights = OverrideUtils.overridesToWeights(overrideService.findAll());
        } else {
            weights = OverrideUtils.overridesToWeights(overrideService.findByService(service));
        }
        
		PagingResult<Weight> pagingResult = PagingUtils.dealFullResult(search, weights);
		model.addAttribute("PAGING_RESULT", pagingResult);

		return "/servicecenter/service/weights";
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
    @RequestMapping(value="/loadbalances")
	public String loadbalances(SearchModel search, Model model) {
		List<SearchCondition> conditions = search.getSearchConditionList();

		// 服务接口名称
		String service = null;
		if (conditions != null && conditions.size() > 0) {
			SearchCondition keywordCondition = conditions.get(0);
			if ("service".equals(keywordCondition.getName())) {
				service = (String) keywordCondition.getValue();
			}
		}

		List<LoadBalance> loadbalances;
		if (StringUtils.isEmpty(service)) {
			loadbalances = OverrideUtils.overridesToLoadBalances(overrideService.findAll());
        } else {
            loadbalances = OverrideUtils.overridesToLoadBalances(overrideService.findByService(service));
        }
        
        PagingResult<LoadBalance> pagingResult = PagingUtils.dealFullResult(search, loadbalances);
		model.addAttribute("PAGING_RESULT", pagingResult);

		return "/servicecenter/service/loadbalances";
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
    @RequestMapping(value="/owners")
	public String owners(SearchModel search, Model model) {
		List<SearchCondition> conditions = search.getSearchConditionList();

		// 服务接口名称
		String service = null;
		if (conditions != null && conditions.size() > 0) {
			SearchCondition keywordCondition = conditions.get(0);
			if ("service".equals(keywordCondition.getName())) {
				service = (String) keywordCondition.getValue();
			}
		}

		List<Owner> owners;
		if (StringUtils.isEmpty(service)) {
			owners = ownerService.findAll();
		} else {
			owners = ownerService.findByService(service);
		}
        
        PagingResult<Owner> pagingResult = PagingUtils.dealFullResult(search, owners);
		model.addAttribute("PAGING_RESULT", pagingResult);

		return "/servicecenter/service/owners";
	}
}
