package com.asura.amp.dubbo.service.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.asura.amp.authority.entity.Operator;
import com.asura.amp.authority.logon.constant.LogonConstant;
import com.asura.amp.common.entity.BaseAjaxValue;
import com.asura.amp.common.entity.Failure;
import com.asura.amp.common.entity.Success;
import com.asura.amp.dubbo.common.entity.Consumer;
import com.asura.amp.dubbo.common.entity.DynamicConfig;
import com.asura.amp.dubbo.common.entity.Provider;
import com.asura.amp.dubbo.common.entity.Route;
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

@Controller
@RequestMapping("/servicecenter/service/consumer")
public class ConsumerController {
	
	@Autowired
    private ProviderService providerService;
	
	@Autowired
    private ConsumerService consumerService;
	
	@Autowired
	private OverrideService overrideService;
    
    @Autowired
    private RouteService routeService;
    
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
	public String consumerEditInit(Long consumerId, Model model) {
    	
    	Consumer consumer = consumerService.findConsumer(consumerId);
    	List<Provider> providers = providerService.findByService(consumer.getService());
    	List<Route> routes = routeService.findByService(consumer.getService());
    	List<DynamicConfig> overrides = overrideService.findByService(consumer.getService());
    	List<Route> routed = new ArrayList<Route>();
        consumer.setProviders(RouteUtils.route(consumer.getService(), consumer.getAddress(), consumer.getParameters(), providers, overrides, routes, null, routed));
    	consumer.setRoutes(routed);
    	OverrideUtils.setConsumerOverrides(consumer, overrides);
    	
		model.addAttribute("consumer", ServiceUtils.consumerProcesser(consumer));
		
		return "/servicecenter/service/consumer/edit";
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
	public String consumerShow(Long consumerId, Model model) {
    	
    	Consumer consumer = consumerService.findConsumer(consumerId);
    	List<Provider> providers = providerService.findByService(consumer.getService());
    	List<Route> routes = routeService.findByService(consumer.getService());
    	List<DynamicConfig> overrides = overrideService.findByService(consumer.getService());
    	List<Route> routed = new ArrayList<Route>();
        consumer.setProviders(RouteUtils.route(consumer.getService(), consumer.getAddress(), consumer.getParameters(), providers, overrides, routes, null, routed));
    	consumer.setRoutes(routed);
    	OverrideUtils.setConsumerOverrides(consumer, overrides);
    	
		model.addAttribute("consumer", ServiceUtils.consumerProcesser(consumer));
		model.addAttribute("parameterMap", PullTool.toParameterMap(consumer.getParameters()));
		
		return "/servicecenter/service/consumer/show";
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
    @RequestMapping(value="/notify")
	public String consumerNotyfy(Long consumerId, Model model) {
    	
    	consumerShow(consumerId, model);
		
		return "/servicecenter/service/consumer/notify";
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
    @RequestMapping(value="/route")
	public String consumerRoute(Long consumerId, Model model) {
    	
    	consumerShow(consumerId, model);
		
		return "/servicecenter/service/consumer/route";
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
    @RequestMapping(value="/override")
	public String consumerOverride(Long consumerId, Model model) {
    	
    	consumerShow(consumerId, model);
		
		return "/servicecenter/service/consumer/override";
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
	public BaseAjaxValue consumerEdit(Consumer newConsumer, HttpServletRequest request) {
    	Operator user = (Operator) request.getSession().getAttribute(LogonConstant.SESSION_OPERATOR);
    	
    	Long id = newConsumer.getId();
    	String parameters = newConsumer.getParameters();
    	Consumer consumer = consumerService.findConsumer(id);
    	
        String service = consumer.getService();
        
        Map<String, String> oldMap = StringUtils.parseQueryString(consumer.getParameters());
        Map<String, String> newMap = StringUtils.parseQueryString(parameters);
        for (Map.Entry<String, String> entry : oldMap.entrySet()) {
        	if (entry.getValue().equals(newMap.get(entry.getKey()))) {
        		newMap.remove(entry.getKey());
        	}
        }
        String address = consumer.getAddress();
        List<DynamicConfig> overrides = overrideService.findByServiceAndAddress(consumer.getService(), consumer.getAddress());
        OverrideUtils.setConsumerOverrides(consumer, overrides);
        DynamicConfig override = consumer.getOverride();
        if (override != null) {
            if (newMap.size() > 0) {
            	override.setParams(StringUtils.toQueryString(newMap));
                override.setEnabled(true);
                override.setOperator(user.getLoginName());
                override.setOperatorAddress(request.getRemoteHost());
                overrideService.updateOverride(override);
            } else {
            	overrideService.deleteOverride(override.getId());
            }
        } else {
            override = new DynamicConfig();
            override.setService(service);
            override.setAddress(address);
            override.setParams(StringUtils.toQueryString(newMap));
            override.setEnabled(true);
            override.setOperator(user.getLoginName());
            override.setOperatorAddress(request.getRemoteHost());
            overrideService.saveOverride(override);
        }
        
		return new Success("消费者更新成功");
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
    @RequestMapping(value="/forbid")
	@ResponseBody
    public BaseAjaxValue consumerForbid(Long consumerId, HttpServletRequest request) {
    	Operator user = (Operator) request.getSession().getAttribute(LogonConstant.SESSION_OPERATOR);
    	
    	try {
			access(new Long[] {consumerId}, user.getLoginName(), false, false);
		} catch (Exception e) {
			e.printStackTrace();
			return new Failure("消费者禁用失败");
		}
    	
		return new Success("消费者禁用成功");
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
    @RequestMapping(value="/allow")
	@ResponseBody
    public BaseAjaxValue consumerAllow(Long consumerId, HttpServletRequest request) {
    	Operator user = (Operator) request.getSession().getAttribute(LogonConstant.SESSION_OPERATOR);
    	
    	try {
			access(new Long[] {consumerId}, user.getLoginName(), true, false);
		} catch (Exception e) {
			e.printStackTrace();
			return new Failure("消费者允许失败");
		}
    	
		return new Success("消费者允许成功");
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
    @RequestMapping(value="/forbidbatch")
	@ResponseBody
    public BaseAjaxValue consumerForbidBatch(Long[] rows, HttpServletRequest request) {
    	Operator user = (Operator) request.getSession().getAttribute(LogonConstant.SESSION_OPERATOR);
    	
    	try {
			access(rows, user.getLoginName(), false, false);
		} catch (Exception e) {
			e.printStackTrace();
			return new Failure("消费者批量禁用失败");
		}
    	
		return new Success("消费者批量禁用成功");
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
    @RequestMapping(value="/allowbatch")
	@ResponseBody
    public BaseAjaxValue consumerAllowBatch(Long[] rows, HttpServletRequest request) {
    	Operator user = (Operator) request.getSession().getAttribute(LogonConstant.SESSION_OPERATOR);
    	
    	try {
			access(rows, user.getLoginName(), true, false);
		} catch (Exception e) {
			e.printStackTrace();
			return new Failure("消费者允许失败");
		}
    	
		return new Success("消费者允许成功");
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
    @RequestMapping(value="/allowonly")
	@ResponseBody
    public BaseAjaxValue consumerAllowOnly(Long[] rows, HttpServletRequest request) {
    	Operator user = (Operator) request.getSession().getAttribute(LogonConstant.SESSION_OPERATOR);
    	
    	try {
			access(rows, user.getLoginName(), true, true);
		} catch (Exception e) {
			e.printStackTrace();
			return new Failure("消费者只允许失败");
		}
    	
		return new Success("消费者只允许成功");
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
    @RequestMapping(value="/forbidonly")
	@ResponseBody
    public BaseAjaxValue consumerForbidOnly(Long[] rows, HttpServletRequest request) {
    	Operator user = (Operator) request.getSession().getAttribute(LogonConstant.SESSION_OPERATOR);
    	
    	try {
			access(rows, user.getLoginName(), false, true);
		} catch (Exception e) {
			e.printStackTrace();
			return new Failure("消费者只禁止失败");
		}
    	
		return new Success("消费者只禁止成功");
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
    @RequestMapping(value="/shield")
	@ResponseBody
    public BaseAjaxValue consumerShield(Long consumerId, HttpServletRequest request) {
    	Operator user = (Operator) request.getSession().getAttribute(LogonConstant.SESSION_OPERATOR);
    	try {
			mock(new Long[]{consumerId}, user.getLoginName(), request.getRemoteHost(), "force:return null");
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return new Success("消费者屏蔽成功");
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
    @RequestMapping(value="/shieldbatch")
	@ResponseBody
    public BaseAjaxValue consumerShieldBatch(Long[] rows, HttpServletRequest request) {
    	Operator user = (Operator) request.getSession().getAttribute(LogonConstant.SESSION_OPERATOR);
    	try {
			mock(rows, user.getLoginName(), request.getRemoteHost(), "force:return null");
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return new Success("消费者批量屏蔽成功");
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
    @RequestMapping(value="/tolerant")
	@ResponseBody
    public BaseAjaxValue consumerTolerant(Long consumerId, HttpServletRequest request) {
    	Operator user = (Operator) request.getSession().getAttribute(LogonConstant.SESSION_OPERATOR);
    	try {
			mock(new Long[]{consumerId}, user.getLoginName(), request.getRemoteHost(), "fail:return null");
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return new Success("消费者容错成功");
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
    @RequestMapping(value="/tolerantbatch")
	@ResponseBody
    public BaseAjaxValue consumerTolerantBatch(Long[] rows, HttpServletRequest request) {
    	Operator user = (Operator) request.getSession().getAttribute(LogonConstant.SESSION_OPERATOR);
    	try {
			mock(rows, user.getLoginName(), request.getRemoteHost(), "fail:return null");
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return new Success("消费者批量容错成功");
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
    @RequestMapping(value="/recover")
	@ResponseBody
    public BaseAjaxValue consumerRecover(Long consumerId, HttpServletRequest request) {
    	Operator user = (Operator) request.getSession().getAttribute(LogonConstant.SESSION_OPERATOR);
    	try {
			mock(new Long[]{consumerId}, user.getLoginName(), request.getRemoteHost(), "");
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return new Success("消费者恢复成功");
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
    @RequestMapping(value="/recoverbatch")
	@ResponseBody
    public BaseAjaxValue consumerRecoverBatch(Long[] rows, HttpServletRequest request) {
    	Operator user = (Operator) request.getSession().getAttribute(LogonConstant.SESSION_OPERATOR);
    	try {
			mock(rows, user.getLoginName(), request.getRemoteHost(), "");
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return new Success("消费者批量恢复成功");
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
    @RequestMapping(value="/shieldall")
	@ResponseBody
    public BaseAjaxValue consumerShieldAll(String service, HttpServletRequest request) {
    	Operator user = (Operator) request.getSession().getAttribute(LogonConstant.SESSION_OPERATOR);
    	try {
			allmock(service, user.getLoginName(), request.getRemoteHost(), "force:return null");
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return new Success("服务缺省屏蔽成功");
    }
    
    @RequestMapping(value="/tolerantall")
	@ResponseBody
    public BaseAjaxValue consumerTolerantAll(String service, HttpServletRequest request) {
    	Operator user = (Operator) request.getSession().getAttribute(LogonConstant.SESSION_OPERATOR);
    	try {
			allmock(service, user.getLoginName(), request.getRemoteHost(), "fail:return null");
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return new Success("服务缺省容错成功");
    }
    
    @RequestMapping(value="/recoverall")
	@ResponseBody
    public BaseAjaxValue consumerRecoverAll(String service, HttpServletRequest request) {
    	Operator user = (Operator) request.getSession().getAttribute(LogonConstant.SESSION_OPERATOR);
    	try {
			allmock(service, user.getLoginName(), request.getRemoteHost(), "");
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return new Success("服务缺省容错成功");
    }
    
    private boolean access(Long[] ids, String username, boolean allow, boolean only) throws Exception {
    	List<Consumer> consumers = new ArrayList<Consumer>();

    	for (Long id : ids) {
            Consumer c = consumerService.findConsumer(id);
            if(c != null){
                consumers.add(c);
            }
        }
    	
    	Map<String, Set<String>> serviceAddresses = new HashMap<String, Set<String>>();
        for(Consumer consumer : consumers) {
        	String service = consumer.getService();
        	String address = PullTool.getIP(consumer.getAddress());
        	Set<String> addresses = serviceAddresses.get(service);
        	if (addresses == null) {
        		addresses = new HashSet<String>();
        		serviceAddresses.put(service, addresses);
        	}
        	addresses.add(address);
        }
        for(Map.Entry<String, Set<String>> entry : serviceAddresses.entrySet()) {
            String service = entry.getKey();
            boolean isFirst = false;
            List<Route> routes = routeService.findForceRouteByService(service);
            Route route = null;
            if(routes == null || routes.size() == 0){
                isFirst = true;
                route  = new Route();
                route.setService(service);
                route.setForce(true);
                route.setName(service+" blackwhitelist");
                route.setFilterRule("false");
                route.setEnabled(true);
            } else {
                route = routes.get(0);
            }
            Map<String, MatchPair> when = null;
            MatchPair matchPair = null;
            if(isFirst){
                when = new HashMap<String, MatchPair>();
                matchPair = new MatchPair(new HashSet<String>(),new HashSet<String>());
                when.put("consumer.host", matchPair);
            }else{
                when = RouteRule.parseRule(route.getMatchRule());
                matchPair = when.get("consumer.host");
            }
            if (only) {
            	matchPair.getUnmatches().clear();
            	matchPair.getMatches().clear();
            	if (allow) {
            		matchPair.getUnmatches().addAll(entry.getValue());
            	} else {
            		matchPair.getMatches().addAll(entry.getValue());
            	}
            } else {
            	for (String consumerAddress : entry.getValue()) {
                	if(matchPair.getUnmatches().size() > 0) { // 白名单优先
                		matchPair.getMatches().remove(consumerAddress); // 去掉黑名单中相同数据
                		if (allow) { // 如果允许访问
                			matchPair.getUnmatches().add(consumerAddress); // 加入白名单
                		} else { // 如果禁止访问
                			matchPair.getUnmatches().remove(consumerAddress); // 从白名单中去除
                		}
                    } else { // 黑名单生效
                    	if (allow) { // 如果允许访问
                    		matchPair.getMatches().remove(consumerAddress); // 从黑名单中去除
                    	} else { // 如果禁止访问
                    		matchPair.getMatches().add(consumerAddress); // 加入黑名单
                    	}
                    }
                }
            }
            StringBuilder sb = new StringBuilder();
            RouteRule.contidionToString(sb,when);
            route.setMatchRule(sb.toString());
            route.setUsername(username);
            if (matchPair.getMatches().size() > 0 || matchPair.getUnmatches().size() > 0) {
            	if(isFirst) {
                	routeService.createRoute(route);
                } else {
                	routeService.updateRoute(route);
                }
            } else if (! isFirst) {
        		routeService.deleteRoute(route.getId());
        	}
        }
        return true;
    }
    
    private boolean mock(Long[] ids, String username, String remoteIp, String mock) throws Exception {
        List<Consumer> consumers = new ArrayList<Consumer>();
        for (Long id : ids) {
            Consumer c = consumerService.findConsumer(id);
            if(c != null){
                consumers.add(c);
            }
        }
        for(Consumer consumer : consumers) {
            String service = consumer.getService();
            String address = PullTool.getIP(consumer.getAddress());
            List<DynamicConfig> overrides = overrideService.findByServiceAndAddress(service, address);
            if (overrides != null && overrides.size() > 0) {
                for (DynamicConfig override: overrides) {
                    Map<String, String> map = StringUtils.parseQueryString(override.getParams());
                    if (mock == null || mock.length() == 0) {
                        map.remove("mock");
                    } else {
                        map.put("mock", URL.encode(mock));
                    }
                    if (map.size() > 0) {
                    	override.setParams(StringUtils.toQueryString(map));
                        override.setEnabled(true);
                        override.setOperator(username);
                        override.setOperatorAddress(remoteIp);
                        overrideService.updateOverride(override);
                    } else {
                    	overrideService.deleteOverride(override.getId());
                    }
                }
            } else if (mock != null && mock.length() > 0) {
            	DynamicConfig override = new DynamicConfig();
                override.setService(service);
                override.setAddress(address);
                override.setParams("mock=" + URL.encode(mock));
                override.setEnabled(true);
                override.setOperator(username);
                override.setOperatorAddress(remoteIp);
                overrideService.saveOverride(override);
            }
        }
        return true;
    }
    
    private boolean allmock(String service, String username, String remoteIp, String mock) throws Exception {
        List<DynamicConfig> overrides = overrideService.findByService(service);
        DynamicConfig allOverride = null;
        if (overrides != null && overrides.size() > 0) {
            for (DynamicConfig override: overrides) {
            	if (override.isDefault()) {
            		allOverride = override;
            		break;
            	}
            }
        }
        if (allOverride != null) {
        	Map<String, String> map = StringUtils.parseQueryString(allOverride.getParams());
            if (mock == null || mock.length() == 0) {
                map.remove("mock");
            } else {
                map.put("mock", URL.encode(mock));
            }
            if (map.size() > 0) {
            	allOverride.setParams(StringUtils.toQueryString(map));
            	allOverride.setEnabled(true);
            	allOverride.setOperator(username);
            	allOverride.setOperatorAddress(remoteIp);
                overrideService.updateOverride(allOverride);
            } else {
            	overrideService.deleteOverride(allOverride.getId());
            }
        } else if (mock != null && mock.length() > 0) {
        	DynamicConfig override = new DynamicConfig();
            override.setService(service);
            override.setParams("mock=" + URL.encode(mock));
            override.setEnabled(true);
            override.setOperator(username);
            override.setOperatorAddress(remoteIp);
            overrideService.saveOverride(override);
        }
        return true;
    }
}
