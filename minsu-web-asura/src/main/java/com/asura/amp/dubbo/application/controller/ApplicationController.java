package com.asura.amp.dubbo.application.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;

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
import com.asura.amp.common.entity.Success;
import com.asura.amp.common.utils.PagingUtils;
import com.asura.amp.dubbo.common.entity.DynamicConfig;
import com.asura.amp.dubbo.common.service.ConsumerService;
import com.asura.amp.dubbo.common.service.OverrideService;
import com.asura.amp.dubbo.common.service.ProviderService;
import com.asura.amp.dubbo.common.utils.OverrideUtils;
import com.asura.amp.dubbo.common.utils.ServiceUtils;
import com.asura.amp.dubbo.search.entity.SearchResult;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchCondition;
import com.asura.framework.base.paging.SearchModel;

@Controller("application")
@RequestMapping("/servicecenter/application")
public class ApplicationController {
	
	@Autowired
    private ProviderService providerService;
    
    @Autowired
    private ConsumerService consumerService;
    
    @Autowired
    private OverrideService overrideService;
    
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
	public String init(String search_application, Model model) {
		if(StringUtils.isEmpty(search_application) || "*".equals(search_application)) {
			return "forward:/servicecenter/search/allapplication.do";
		} else {
			return "/servicecenter/application/init";
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
    @RequestMapping(value="/service")
	public String service(SearchModel search, Model model) {
		List<SearchCondition> conditions = search.getSearchConditionList();

		String application = "";
		if (conditions != null && conditions.size() > 0) {
			SearchCondition keywordCondition = conditions.get(0);
			if ("application".equals(keywordCondition.getName())) {
				application = (String) keywordCondition.getValue();
			}
		}

		// 查询全部提供者 + 消费者的服务接口信息
		List<String> providerServices = providerService.findServicesByApplication(application);
		List<String> consumerServices = consumerService.findServicesByApplication(application);
		List<DynamicConfig> overrides = overrideService.findByApplication(application);

		// 去掉提供者、消费者相重复的接口信息（去重）
		Set<String> services = new TreeSet<String>();
		if (providerServices != null) {
			services.addAll(providerServices);
		}
		if (consumerServices != null) {
			services.addAll(consumerServices);
		}
		
		Map<String, List<DynamicConfig>> service2Overrides = new HashMap<String, List<DynamicConfig>>();
        if (overrides != null && overrides.size() > 0 
                && services != null && services.size() > 0) {
            for (String s : services) {
                if (overrides != null && overrides.size() > 0) {
                    for (DynamicConfig override : overrides) {
                    	List<DynamicConfig> serOverrides = new ArrayList<DynamicConfig>();
                    	if (override.isMatch(s, null, application)) {
                        	serOverrides.add(override);
                        }
                        Collections.sort(serOverrides, OverrideUtils.OVERRIDE_COMPARATOR);
                        service2Overrides.put(s, serOverrides);
                    }
                }
            }
        }

		// 使用LinkedHashSet，保证分页时数据的顺序一致
		Set<SearchResult> serviceResult = new LinkedHashSet<SearchResult>();
		for (String service : services) {
//			// 服务名不匹配关键词，不添加到结果中
//			if (StringUtils.isNotEmpty(application) && (service.toLowerCase().indexOf(application.toLowerCase()) == -1)) {
//				continue;
//			}

			SearchResult result = new SearchResult();
			result.setName(service);
			if (providerServices != null && providerServices.contains(service)) {
				result.setDescription("提供者");
			} else if (providerServices != null && consumerServices.contains(service)) {
				result.setDescription("消费者");
			}
			
			result.setMock(ServiceUtils.applicationMock(service2Overrides.get(service)));

			serviceResult.add(result);
		}
		
		model.addAttribute("PAGING_RESULT", PagingUtils.dealFullResult(search, new ArrayList<SearchResult>(serviceResult)));
		
		return "/servicecenter/application/service";
	}
    
    @RequestMapping(value="/address")
	public String address(SearchModel search, Model model) {
		List<SearchCondition> conditions = search.getSearchConditionList();
		
		// 查询关键词
		String application = null;
		if (conditions != null && conditions.size() > 0) { // condition
			SearchCondition keywordCondition = conditions.get(0);
			if("application".equals(keywordCondition.getName())) {
				application = (String) keywordCondition.getValue();
			}
		}

		// 查询全部提供者 + 消费者的服务地址信息
		List<String> providerAddresses = providerService.findAddressesByApplication(application);
		List<String> consumerAddresses = consumerService.findAddressesByApplication(application);

		// 去掉提供者、消费者相重复的地址信息（去重）
		Set<String> addresses = new TreeSet<String>();
		if (providerAddresses != null) {
			addresses.addAll(providerAddresses);
		}
		if (consumerAddresses != null) {
			addresses.addAll(consumerAddresses);
		}
		
		// 使用LinkedHashSet，保证分页时数据的顺序一致
		Set<SearchResult> addressResult = new LinkedHashSet<SearchResult>();
		for (String address : addresses) {
			// 地址不匹配关键词，不添加到结果中
//			if (StringUtils.isNotEmpty(keyword) && (address.toLowerCase().indexOf(keyword.toLowerCase()) == -1)) {
//				continue;
//			}
			SearchResult result = new SearchResult();
			result.setName(address);
			if (providerAddresses != null && providerAddresses.contains(address)) {
				result.setDescription("提供者");
			} else if (consumerAddresses != null && consumerAddresses.contains(address)) {
				result.setDescription("消费者");
			} else {
				// s.setDescription("正常");
			}

			addressResult.add(result);
		}

		model.addAttribute("PAGING_RESULT", PagingUtils.dealFullResult(search, new ArrayList<SearchResult>(addressResult)));
		
		return "/servicecenter/application/address";
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
		
		// 查询关键词
		String application = null;
		if (conditions != null && conditions.size() > 0) { // condition
			SearchCondition keywordCondition = conditions.get(0);
			if("application".equals(keywordCondition.getName())) {
				application = (String) keywordCondition.getValue();
			}
		}

		List<DynamicConfig> overrides = overrideService.findByApplication(application);
		PagingResult<DynamicConfig> pagingResult = PagingUtils.dealFullResult(search, overrides);
		
		Map<String, String> urlDecodeMap = new HashMap<String, String>();
		for(DynamicConfig dynamic : pagingResult.getRows()) {
			String params = dynamic.getParams();
			urlDecodeMap.put(params, URL.decode(params));
		}

		model.addAttribute("PAGING_RESULT", pagingResult);
		model.addAttribute("URL_DECODE_MAP", urlDecodeMap);

		return "/servicecenter/application/dynamic";
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
    public BaseAjaxValue consumerShield(String application, String service, HttpServletRequest request) {
    	Operator user = (Operator) request.getSession().getAttribute(LogonConstant.SESSION_OPERATOR);
    	try {
			mock(application, service, user.getLoginName(), request.getRemoteHost(), "force:return null");
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
    public BaseAjaxValue consumerShieldBatch(String[] rows, HttpServletRequest request) {
    	Operator user = (Operator) request.getSession().getAttribute(LogonConstant.SESSION_OPERATOR);
    	try {
    		for(String appService : rows) {
    			String[] appsve = appService.split(",");
    			String application = appsve[0];
    			String service = appsve[1];
    			
    			mock(application, service, user.getLoginName(), request.getRemoteHost(), "force:return null");
    		}
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
    public BaseAjaxValue consumerTolerant(String application, String service, HttpServletRequest request) {
    	Operator user = (Operator) request.getSession().getAttribute(LogonConstant.SESSION_OPERATOR);
    	try {
			mock(application, service, user.getLoginName(), request.getRemoteHost(), "fail:return null");
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
    public BaseAjaxValue consumerTolerantBatch(String[] rows, HttpServletRequest request) {
    	Operator user = (Operator) request.getSession().getAttribute(LogonConstant.SESSION_OPERATOR);
    	try {
    		for(String appService : rows) {
    			String[] appsve = appService.split(",");
    			String application = appsve[0];
    			String service = appsve[1];
    			
    			mock(application, service, user.getLoginName(), request.getRemoteHost(), "fail:return null");
    		}
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
    public BaseAjaxValue consumerRecover(String application, String service, HttpServletRequest request) {
    	Operator user = (Operator) request.getSession().getAttribute(LogonConstant.SESSION_OPERATOR);
    	try {
			mock(application, service, user.getLoginName(), request.getRemoteHost(), "");
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
    public BaseAjaxValue consumerRecoverBatch(String[] rows, HttpServletRequest request) {
    	Operator user = (Operator) request.getSession().getAttribute(LogonConstant.SESSION_OPERATOR);
    	try {
    		for(String appService : rows) {
    			String[] appsve = appService.split(",");
    			String application = appsve[0];
    			String service = appsve[1];
    			
    			mock(application, service, user.getLoginName(), request.getRemoteHost(), "");
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return new Success("消费者批量恢复成功");
    }
    
    protected static final Pattern SPACE_SPLIT_PATTERN = Pattern.compile("\\s+");

	private boolean mock(String service, String applications, String username, String remoteIp, String mock)
			throws Exception {
		for (String application : SPACE_SPLIT_PATTERN.split(applications)) {
			List<DynamicConfig> overrides = overrideService.findByServiceAndApplication(service, application);
			if (overrides != null && overrides.size() > 0) {
				for (DynamicConfig override : overrides) {
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
				override.setApplication(application);
				override.setParams("mock=" + URL.encode(mock));
				override.setEnabled(true);
				override.setOperator(username);
				override.setOperatorAddress(remoteIp);
				overrideService.saveOverride(override);
			}
		}
		return true;
	}
}
