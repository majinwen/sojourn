/**
 * @FileName: AccessesController.java
 * @Package com.asura.management.servicecenter.service.controller
 * 
 * @author zhangshaobin
 * @created 2012-12-25 下午4:32:06
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.amp.dubbo.service.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Pattern;

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
import com.asura.amp.dubbo.common.entity.Route;
import com.asura.amp.dubbo.common.service.ProviderService;
import com.asura.amp.dubbo.common.service.RouteService;
import com.asura.amp.dubbo.common.utils.PullTool;
import com.asura.amp.dubbo.common.utils.RouteRule;
import com.asura.amp.dubbo.common.utils.RouteRule.MatchPair;

/**
 * <p>访问控制Controller</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zhangshaobin
 * @since 1.0
 * @version 1.0
 */
@Controller
@RequestMapping("/servicecenter/service/accesses")
public class AccessesController {
	
	@Autowired
	private ProviderService providerService;
    
    @Autowired
    private RouteService routeService;
    
    /**
     * 添加访问控制的初始化页面
     *
     * @author zhangshaobin
     * @created 2013-1-16 下午7:31:44
     *
     * @param service		当前Service
     * @param model			model
     * @return
     */
    @RequestMapping(value="/addinit")
	public String accessesAddInit(String service, Model model) {
    	List<String> serviceList = new ArrayList<String>();
    	
    	serviceList.addAll(providerService.findServices());
        // serviceList.addAll(consumerService.findServicesByApplication(application));
        
        Map<String, String> serviceMap = new HashMap<String, String>();
        for(String svc : serviceList) {
        	if(svc != null) {
        		int index = svc.lastIndexOf('.');
        		String name = index >=0 ? svc.substring(index + 1) : svc;
        		serviceMap.put(name, svc);
        	}
        }
        
        model.addAttribute("serviceMap", serviceMap);
        
		return "/servicecenter/service/accesses/add";
    }
    
    /**
     * 添加访问控制
     *
     * @author zhangshaobin
     * @created 2013-1-16 下午7:32:10
     *
     * @param service			Service
     * @param consumerAddress	消费者地址
     * @param allow				禁止/允许
     * @param request			
     * @return
     * @throws Exception		
     */
    @RequestMapping(value="/add")
    @ResponseBody
	public BaseAjaxValue accessesAdd(String service, String consumerAddress, boolean allow, HttpServletRequest request) throws Exception {
    	// 登录用户信息
    	Operator user = (Operator) request.getSession().getAttribute(LogonConstant.SESSION_OPERATOR);

		// 分割消费者地址 ==> Set
		Set<String> consumerAddresses = toAddr(consumerAddress);
		// 分割服务地址 ==> Set
		Set<String> aimServices = toService(service);
		for (String aimService : aimServices) {
			boolean isFirst = false;
			List<Route> routes = routeService.findForceRouteByService(aimService);
			Route route = null;
			if (routes == null || routes.size() == 0) {
				isFirst = true;
				route = new Route();
				route.setService(aimService);
				route.setForce(true);
				route.setName(aimService + " blackwhitelist");
				route.setFilterRule("false");
				route.setEnabled(true);
			} else {
				route = routes.get(0);
			}
			Map<String, MatchPair> when = null;
			MatchPair matchPair = null;
			if (isFirst) {
				when = new HashMap<String, MatchPair>();
				matchPair = new MatchPair(new HashSet<String>(), new HashSet<String>());
				when.put("consumer.host", matchPair);
			} else {
				when = RouteRule.parseRule(route.getMatchRule());
				matchPair = when.get("consumer.host");
			}
			for (String address : consumerAddresses) {
				if (allow) {
					matchPair.getUnmatches().add(PullTool.getIP(address));

				} else {
					matchPair.getMatches().add(PullTool.getIP(address));
				}
			}
			StringBuilder sb = new StringBuilder();
			RouteRule.contidionToString(sb, when);
			route.setMatchRule(sb.toString());
			route.setUsername(user.getLoginName());
			if (isFirst) {
				routeService.createRoute(route);
			} else {
				routeService.updateRoute(route);
			}

		}

		return new Success("访问控制添加成功");
	}
    
    /**
     * 删除访问控制
     *
     * @author zhangshaobin
     * @created 2013-1-16 下午7:34:23
     *
     * @param accesses				访问控制（service + address拼接）
     * @return
     * @throws ParseException
     */
    @RequestMapping(value="/delete")
	@ResponseBody
	public BaseAjaxValue accessesDelete(String[] accesses) throws ParseException {
		Map<String, Set<String>> prepareToDeleate = new HashMap<String, Set<String>>();
		for (String s : accesses) {
			String service = s.split("=")[0];
			String address = s.split("=")[1];
			Set<String> addresses = prepareToDeleate.get(service);
			if (addresses == null) {
				prepareToDeleate.put(service, new HashSet<String>());
				addresses = prepareToDeleate.get(service);
			}
			addresses.add(address);
		}
		for (Entry<String, Set<String>> entry : prepareToDeleate.entrySet()) {

			String service = entry.getKey();
			List<Route> routes = routeService.findForceRouteByService(service);
			if (routes == null || routes.size() == 0) {
				continue;
			}
			for (Route blackwhitelist : routes) {
				MatchPair pairs = RouteRule.parseRule(blackwhitelist.getMatchRule()).get("consumer.host");
				Set<String> matches = new HashSet<String>();
				matches.addAll(pairs.getMatches());
				Set<String> unmatches = new HashSet<String>();
				unmatches.addAll(pairs.getUnmatches());
				for (String pair : pairs.getMatches()) {
					for (String address : entry.getValue()) {
						if (pair.equals(address)) {
							matches.remove(pair);
							break;
						}
					}
				}
				for (String pair : pairs.getUnmatches()) {
					for (String address : entry.getValue()) {
						if (pair.equals(address)) {
							unmatches.remove(pair);
							break;
						}
					}
				}
				if (matches.size() == 0 && unmatches.size() == 0) {
					routeService.deleteRoute(blackwhitelist.getId());
				} else {
					Map<String, MatchPair> condition = new HashMap<String, MatchPair>();
					condition.put("consumer.host", new MatchPair(matches, unmatches));
					StringBuilder sb = new StringBuilder();
					RouteRule.contidionToString(sb, condition);
					blackwhitelist.setMatchRule(sb.toString());
					routeService.updateRoute(blackwhitelist);
				}
			}
		}

		return new Success("访问控制删除成功");
	}
	
    /**
     * IP匹配表达式
     */
	private static final Pattern IP_PATTERN       = Pattern.compile("\\d{1,3}(\\.\\d{1,3}){3}$");

	/**
	 * 本地IP匹配表达式
	 */
    private static final Pattern LOCAL_IP_PATTERN = Pattern.compile("127(\\.\\d{1,3}){3}$");
    /**
     * 所有IP匹配（0.0.0.0）
     */
    private static final Pattern ALL_IP_PATTERN   = Pattern.compile("0{1,3}(\\.0{1,3}){3}$");
	
    /**
     * 分割地址==>Set
     *
     * @author zhangshaobin
     * @created 2013-1-16 下午7:36:53
     *
     * @param addr				全部地址字符串
     * @return
     * @throws IOException
     */
	private Set<String> toAddr(String addr) throws IOException {
		Set<String> consumerAddresses = new HashSet<String>();
		BufferedReader reader = new BufferedReader(new StringReader(addr));
		while (true) {
			String line = reader.readLine();
			if (null == line)
				break;

			String[] split = line.split("[\\s,;]+");
			for (String s : split) {
				if (s.length() == 0)
					continue;
				if (!IP_PATTERN.matcher(s).matches()) {
					throw new IllegalStateException("illegal IP: " + s);
				}
				if (LOCAL_IP_PATTERN.matcher(s).matches() || ALL_IP_PATTERN.matcher(s).matches()) {
					throw new IllegalStateException("local IP or any host ip is illegal: " + s);
				}

				consumerAddresses.add(s);
			}
		}
		return consumerAddresses;
	}

	/**
	 * 分割服务地址==>Set
	 *
	 * @author zhangshaobin
	 * @created 2013-1-16 下午7:37:48
	 *
	 * @param services			全部服务字符串
	 * @return
	 * @throws IOException
	 */
	private Set<String> toService(String services) throws IOException {
		Set<String> aimServices = new HashSet<String>();
		BufferedReader reader = new BufferedReader(new StringReader(services));
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
		return aimServices;
	}
}
