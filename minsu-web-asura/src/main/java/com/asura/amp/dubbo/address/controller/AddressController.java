package com.asura.amp.dubbo.address.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.asura.amp.common.utils.PagingUtils;
import com.asura.amp.dubbo.common.entity.Access;
import com.asura.amp.dubbo.common.entity.Consumer;
import com.asura.amp.dubbo.common.entity.DynamicConfig;
import com.asura.amp.dubbo.common.entity.Provider;
import com.asura.amp.dubbo.common.entity.Route;
import com.asura.amp.dubbo.common.entity.Weight;
import com.asura.amp.dubbo.common.service.ConsumerService;
import com.asura.amp.dubbo.common.service.OverrideService;
import com.asura.amp.dubbo.common.service.ProviderService;
import com.asura.amp.dubbo.common.service.RouteService;
import com.asura.amp.dubbo.common.utils.OverrideUtils;
import com.asura.amp.dubbo.common.utils.PullTool;
import com.asura.amp.dubbo.common.utils.RouteRule;
import com.asura.amp.dubbo.common.utils.RouteRule.MatchPair;
import com.asura.amp.dubbo.common.utils.RouteUtils;
import com.asura.amp.dubbo.common.utils.ServiceUtils;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchCondition;
import com.asura.framework.base.paging.SearchModel;

@Controller("address")
@RequestMapping("/servicecenter/address")
public class AddressController {
	
	@Autowired
    private ProviderService providerService;
    
    @Autowired
    private ConsumerService consumerService;
    
    @Autowired
    private OverrideService overrideService;
    
    @Autowired
    private RouteService routeService;
    
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
	public String init(String search_address, Model model) {
		if(StringUtils.isEmpty(search_address) || "*".equals(search_address)) {
			return "forward:/servicecenter/search/alladdress.do";
		} else {
			return "/servicecenter/address/init";
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
    	String address = "";
		if (conditions != null && conditions.size() > 0) {
			SearchCondition keywordCondition = conditions.get(0);
			if ("address".equals(keywordCondition.getName())) {
				address = (String) keywordCondition.getValue();
			}
		}
		
    	if(StringUtils.isEmpty(address)) {
    		providerServices = new ArrayList<Provider>();
    	} else {
    		providerServices = providerService.findByAddress(address);
    	}
    	
    	PagingResult<Provider> pagingResult = PagingUtils.dealFullResult(search, providerServices);
    	ServiceUtils.providerProcesser(pagingResult.getRows());
    	model.addAttribute("PAGING_RESULT", pagingResult);
		
		return "/servicecenter/address/provider";
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
    	String address = "";
		if (conditions != null && conditions.size() > 0) {
			SearchCondition keywordCondition = conditions.get(0);
			if ("address".equals(keywordCondition.getName())) {
				address = (String) keywordCondition.getValue();
			}
		}
		
    	if(StringUtils.isEmpty(address)) {
    		consumerServices = new ArrayList<Consumer>();
    	} else {
    		consumerServices = consumerService.findByAddress(address);
    		overrides = overrideService.findByAddress(PullTool.getIP(address));
    	}
    	
    	PagingResult<Consumer> pagingResult = PagingUtils.dealFullResult(search, consumerServices);
    	
    	for (Consumer consumer : pagingResult.getRows()) {
    		providers = providerService.findByService(consumer.getService());
    		routes = routeService.findByService(consumer.getService());
    		
        	List<Route> routed = new ArrayList<Route>();
            consumer.setProviders(RouteUtils.route(consumer.getService(), consumer.getAddress(), consumer.getParameters(), providers, overrides, routes, null, routed));
        	consumer.setRoutes(routed);
        	OverrideUtils.setConsumerOverrides(consumer, overrides);
        }
    	
    	ServiceUtils.consumerProcesser(pagingResult.getRows());
    	model.addAttribute("PAGING_RESULT", pagingResult);
		
		return "/servicecenter/address/consumer";
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
    	String address = "";
		if (conditions != null && conditions.size() > 0) {
			SearchCondition keywordCondition = conditions.get(0);
			if ("address".equals(keywordCondition.getName())) {
				address = (String) keywordCondition.getValue();
			}
		}

		if (StringUtils.isNotEmpty(address)) {
			routes = routeService.findByAddress(address);
		}

		PagingResult<Route> pagingResult = PagingUtils.dealFullResult(search, routes);

		model.addAttribute("PAGING_RESULT", pagingResult);

		return "/servicecenter/address/routes";
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
		String address = "";
		if (conditions != null && conditions.size() > 0) {
			SearchCondition keywordCondition = conditions.get(0);
			if ("address".equals(keywordCondition.getName())) {
				address = (String) keywordCondition.getValue();
			}
		}
		
        address = PullTool.getIP(address);
        
        List<Route> routes = null;
        if (address != null && address.length() > 0) {
            routes = routeService.findForceRouteByAddress(address);
        } else {
            routes = routeService.findAllForceRoute();
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

		return "/servicecenter/address/accesses";
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
		String address = null;
		if (conditions != null && conditions.size() > 0) {
			SearchCondition keywordCondition = conditions.get(0);
			if ("address".equals(keywordCondition.getName())) {
				address = (String) keywordCondition.getValue();
			}
		}
		
        address = PullTool.getIP(address);
        List<Weight> weights;
        if (address != null && address.length() > 0) {
            weights = OverrideUtils.overridesToWeights(overrideService.findByAddress(address));
        } else {
            weights = OverrideUtils.overridesToWeights(overrideService.findAll());
        }
        
		PagingResult<Weight> pagingResult = PagingUtils.dealFullResult(search, weights);
		model.addAttribute("PAGING_RESULT", pagingResult);

		return "/servicecenter/address/weights";
	}
}
