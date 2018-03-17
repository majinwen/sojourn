package com.asura.amp.dubbo.search.controller;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.asura.amp.common.utils.PagingUtils;
import com.asura.amp.dubbo.common.service.ConsumerService;
import com.asura.amp.dubbo.common.service.ProviderService;
import com.asura.amp.dubbo.search.entity.SearchResult;
import com.asura.framework.base.paging.SearchCondition;
import com.asura.framework.base.paging.SearchModel;

@Controller
@RequestMapping("/servicecenter/search")
public class SearchController {
	
	@Autowired
    private ProviderService providerService;
    
    @Autowired
    private ConsumerService consumerService;
    
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
	public String init() {
		return "/servicecenter/search/init";
	}
    
	/**
	 * 登录页面初始化
	 *
	 * @author zhangshaobin
	 * @created 2012-12-25 下午3:36:52
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/service")
	public String service(SearchModel search, Model model) {
		List<SearchCondition> conditions = search.getSearchConditionList();

		// 查询全部提供者 + 消费者的服务接口信息
		List<String> providerServices = providerService.findServices();
		List<String> consumerServices = consumerService.findServices();

		// 去掉提供者、消费者相重复的接口信息（去重）
		Set<String> services = new TreeSet<String>();
		if (providerServices != null) {
			services.addAll(providerServices);
		}
		if (consumerServices != null) {
			services.addAll(consumerServices);
		}
		
		// 查询关键词
		String keyword = "";
		if (conditions != null && conditions.size() > 0) { // condition
			SearchCondition keywordCondition = conditions.get(0);
			if ("keyword".equals(keywordCondition.getName())) { // 判断是否为keyword，保证数据的正确
				keyword = (String) keywordCondition.getValue();
			}
		}

		// 使用LinkedHashSet，保证分页时数据的顺序一致
		Set<SearchResult> serviceResult = new LinkedHashSet<SearchResult>();
		for (String service : services) {
			// 服务名不匹配关键词，不添加到结果中
			if (StringUtils.isNotEmpty(keyword) && (service.toLowerCase().indexOf(keyword.toLowerCase()) == -1)) {
				continue;
			}

			SearchResult result = new SearchResult();
			result.setName(service);
			if (providerServices == null || !providerServices.contains(service)) {
				result.setDescription("没有提供者");
			} else if (consumerServices == null || !consumerServices.contains(service)) {
				result.setDescription("没有消费者");
			} else {
				result.setDescription("正常");
			}

			serviceResult.add(result);
		}
		
		model.addAttribute("PAGING_RESULT", PagingUtils.dealFullResult(search, new ArrayList<SearchResult>(serviceResult)));
		
		return "/servicecenter/search/service";
	}
	
	@RequestMapping(value="/allservice")
	public String allservice(SearchModel search, Model model) {
		service(search, model);
		return "/servicecenter/service/index";
	}
	
	/**
	 * 登录页面初始化
	 *
	 * @author zhangshaobin
	 * @created 2012-12-25 下午3:36:52
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/application")
	public String application(SearchModel search, Model model) {
		List<SearchCondition> conditions = search.getSearchConditionList();

		// 查询全部提供者 + 消费者的服务应用信息
		List<String> providerApplications = providerService.findApplications();
		List<String> consumerApplications = consumerService.findApplications();

		// 去掉提供者、消费者相重复的应用信息（去重）
		Set<String> applications = new TreeSet<String>();
		if (providerApplications != null) {
			applications.addAll(providerApplications);
		}
		if (consumerApplications != null) {
			applications.addAll(consumerApplications);
		}
		
		// 查询关键词
		String keyword = "";
		if (conditions != null && conditions.size() > 0) { // condition
			SearchCondition keywordCondition = conditions.get(0);
			if ("keyword".equals(keywordCondition.getName())) { // 判断是否为keyword，保证数据的正确
				keyword = (String) keywordCondition.getValue();
			}
		}

		// 使用LinkedHashSet，保证分页时数据的顺序一致
		Set<SearchResult> applicationResult = new LinkedHashSet<SearchResult>();
		for (String application : applications) {
			// 应用名不匹配关键词，不添加到结果中
			if (StringUtils.isNotEmpty(keyword) && (application.toLowerCase().indexOf(keyword.toLowerCase()) == -1)) {
				continue;
			}

			SearchResult result = new SearchResult();
			result.setName(application);
			if (providerApplications != null && providerApplications.contains(application)) {
				result.setDescription("提供者");
			} else if (consumerApplications != null && consumerApplications.contains(application)) {
				result.setDescription("消费者");
			} else {
				// s.setDescription("正常");
			}

			applicationResult.add(result);
		}
		
		model.addAttribute("PAGING_RESULT", PagingUtils.dealFullResult(search, new ArrayList<SearchResult>(applicationResult)));
		
		return "/servicecenter/search/application";
	}
	
	@RequestMapping(value="/allapplication")
	public String allapplication(SearchModel search, Model model) {
		application(search, model);
		return "/servicecenter/application/index";
	}
	
	/**
	 * 登录页面初始化
	 *
	 * @author zhangshaobin
	 * @created 2012-12-25 下午3:36:52
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/address")
	public String address(SearchModel search, Model model) {
		List<SearchCondition> conditions = search.getSearchConditionList();

		// 查询全部提供者 + 消费者的服务地址信息
		List<String> providerAddresses = providerService.findAddresses();
		List<String> consumerAddresses = consumerService.findAddresses();

		// 去掉提供者、消费者相重复的地址信息（去重）
		Set<String> addresses = new TreeSet<String>();
		if (providerAddresses != null) {
			addresses.addAll(providerAddresses);
		}
		if (consumerAddresses != null) {
			addresses.addAll(consumerAddresses);
		}
		
		// 查询关键词
		String keyword = "";
		if (conditions != null && conditions.size() > 0) { // condition
			SearchCondition keywordCondition = conditions.get(0);
			if ("keyword".equals(keywordCondition.getName())) { // 判断是否为keyword，保证数据的正确
				keyword = (String) keywordCondition.getValue();
			}
		}
		
		// 使用LinkedHashSet，保证分页时数据的顺序一致
		Set<SearchResult> addressResult = new LinkedHashSet<SearchResult>();
		for (String address : addresses) {
			// 地址不匹配关键词，不添加到结果中
			if (StringUtils.isNotEmpty(keyword) && (address.toLowerCase().indexOf(keyword.toLowerCase()) == -1)) {
				continue;
			}
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
		
		return "/servicecenter/search/address";
	}
	
	@RequestMapping(value="/alladdress")
	public String alladdress(SearchModel search, Model model) {
		address(search, model);
		return "/servicecenter/address/index";
	}
}
