/**
 * @FileName: AccessesController.java
 * @Package com.asura.amp.dubbo.sysinfo.controller
 * 
 * @author zhangshaobin
 * @created 2012-12-25 下午4:32:06
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.amp.dubbo.sysinfo.controller;

import java.lang.management.ManagementFactory;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.alibaba.dubbo.common.status.Status;
import com.alibaba.dubbo.common.status.StatusChecker;
import com.alibaba.dubbo.common.utils.NetUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.asura.amp.common.utils.PagingUtils;
import com.asura.amp.dubbo.common.StatusManager;
import com.asura.amp.dubbo.common.entity.Consumer;
import com.asura.amp.dubbo.common.entity.Provider;
import com.asura.amp.dubbo.common.service.ConsumerService;
import com.asura.amp.dubbo.common.service.ProviderService;
import com.asura.amp.dubbo.sysinfo.entity.Version;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchModel;

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
@RequestMapping("/servicecenter/sysinfo")
public class SysInfoController {
	
	@Autowired
    private ProviderService providerService;
	
	@Autowired
    private ConsumerService consumerService;
    
    /**
     * 添加访问控制的初始化页面
     *
     * @author zhangshaobin
     * @created 2013-1-16 下午7:31:44
     *
     * @param search		搜索查询条件
     * @param model			model
     * @return
     */
    @RequestMapping(value="/versions")
	public String versions(SearchModel search, Model model) {
    	List<Provider> providers = providerService.findAll();
        List<Consumer> consumers = consumerService.findAll();
        Set<String> parametersSet = new HashSet<String>();
        for (Provider provider : providers) {
            parametersSet.add(provider.getParameters());
        }
        for (Consumer consumer : consumers) {
            parametersSet.add(consumer.getParameters());
        }
        Map<String, Set<String>> versionsMap = new HashMap<String, Set<String>>();
        Iterator<String> temp = parametersSet.iterator();
        while (temp.hasNext()) {
            Map<String, String> parameter = StringUtils.parseQueryString(temp.next());
            if (parameter != null) {
                String dubbo = parameter.get("dubbo");
                if(dubbo == null) dubbo = "0.0.0";
                String application = parameter.get("application");
                if (versionsMap.get(dubbo) == null) {
                    Set<String> apps = new HashSet<String>();
                    versionsMap.put(dubbo, apps);
                }
                versionsMap.get(dubbo).add(application);
            }
        }
        
        List<Version> versions = new ArrayList<Version>();
        for(Map.Entry<String, Set<String>> entry : versionsMap.entrySet()) {
        	versions.add(new Version(entry.getKey(), entry.getValue().size()));
        }
        
        PagingResult<Version> pagingResult = PagingUtils.dealFullResult(search, versions);
    	model.addAttribute("PAGING_RESULT", pagingResult);
    	
		return "/servicecenter/sysinfo/versions";
    }
    
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
    @RequestMapping(value="/versions/show")
	public String versionsShow(String version, Model model) {
    	List<Provider> providers = providerService.findAll();
        List<Consumer> consumers = consumerService.findAll();
        Set<String> parametersSet = new HashSet<String>();
        Set<String> applications = new HashSet<String>();
        for (Provider provider : providers) {
            parametersSet.add(provider.getParameters());
        }
        for (Consumer consumer : consumers) {
            parametersSet.add(consumer.getParameters());
        }
        Iterator<String> temp = parametersSet.iterator();
        while (temp.hasNext()) {
            Map<String, String> parameter = StringUtils.parseQueryString(temp.next());
            if (parameter != null) {
                String dubbo = parameter.get("dubbo");
                if(dubbo == null) dubbo = "0.0.0";
                String application = parameter.get("application");
                if (version.equals(dubbo)) {
                    applications.add(application);
                }
            }
        }
        model.addAttribute("applications", applications);
    	
		return "/servicecenter/sysinfo/versions/show";
    }

    
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
    @RequestMapping(value="/dumps")
	public String dumps(Model model) {
    	model.addAttribute("noProviderServicesCount", getNoProviders().size());
    	model.addAttribute("servicesCount", providerService.findServices().size());
    	model.addAttribute("providersCount", providerService.findAll().size());
    	model.addAttribute("consumersCount", consumerService.findAll().size());
    	
		return "/servicecenter/sysinfo/dumps";
    }
    
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
    @RequestMapping(value="/dumps/show")
	public String dumpsShow(String type, Model model) {
    	if("noProvider".equals(type)) {
    		List<String> sortedService = getNoProviders();
            Collections.sort(sortedService);
            
            model.addAttribute("dumpsTitle", sortedService.size() + "条服务没有提供者");
            model.addAttribute("dumpsInfo", sortedService);
    	} else if("services".equals(type)) {
    		List<String> sortedService = providerService.findServices();
            Collections.sort(sortedService);

            model.addAttribute("dumpsTitle", sortedService.size() + "条服务");
            model.addAttribute("dumpsInfo", sortedService);
    	} else if("providers".equals(type)) {
    		List<Provider> providers = providerService.findAll();
            List<String> sortedProviders = new ArrayList<String>();
            for (Provider provider : providers) {
                sortedProviders.add(provider.getUrl() + " " + provider.getService());
            }
            Collections.sort(sortedProviders);
            
            model.addAttribute("dumpsTitle", sortedProviders.size() + "条提供者");
            model.addAttribute("dumpsInfo", sortedProviders);
    	} else if("consumers".equals(type)) {
    		List<Consumer> consumers = consumerService.findAll();
            List<String> sortedConsumerss = new ArrayList<String>();
            for (Consumer consumer : consumers) {
                sortedConsumerss.add(consumer.getAddress() + " " + consumer.getService());
            }
            Collections.sort(sortedConsumerss);
            
            model.addAttribute("dumpsTitle", sortedConsumerss.size() + "条消费者");
            model.addAttribute("dumpsInfo", sortedConsumerss);
    	}
    	
		return "/servicecenter/sysinfo/dumps/show";
    }
    
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
    @RequestMapping(value="/dumps/show", params="type=versions")
	public String dumpsVersionShow(Model model) {
    	List<Provider> providers = providerService.findAll();
        List<Consumer> consumers = consumerService.findAll();
        Set<String> parametersSet = new HashSet<String>();
        Map<String, Set<String>> versions = new HashMap<String, Set<String>>();
        for (Provider provider : providers) {
            parametersSet.add(provider.getParameters());
        }
        for (Consumer consumer : consumers) {
            parametersSet.add(consumer.getParameters());
        }
        Iterator<String> temp = parametersSet.iterator();
        while (temp.hasNext()) {
            Map<String, String> parameter = StringUtils.parseQueryString(temp.next());
            if (parameter != null) {
                String dubboversion = parameter.get("dubbo");
                String app = parameter.get("application");
                if (versions.get(dubboversion) == null) {
                    Set<String> apps = new HashSet<String>();
                    versions.put(dubboversion, apps);
                }
                versions.get(dubboversion).add(app);
            }
        }
        
        model.addAttribute("versions", versions);
        
    	return "/servicecenter/sysinfo/dumps/version";
    }
    
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
    @RequestMapping(value="/statuses")
	public String statuses(Model model) {
		ExtensionLoader<StatusChecker> loader = ExtensionLoader.getExtensionLoader(StatusChecker.class);
		Map<String, Status> statusList = new LinkedHashMap<String, Status>();
		for (String name : loader.getSupportedExtensions()) {
			Status status = loader.getExtension(name).check();
			if (status.getLevel() != null && status.getLevel() != Status.Level.UNKNOWN) {
				statusList.put(name, status);
			}
		}
		statusList.put("summary", StatusManager.getStatusSummary(statusList));
		model.addAttribute("statusList", statusList);

		return "/servicecenter/sysinfo/statuses";
    }
    
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
    @RequestMapping(value="/envs")
	public String envs(Model model) {
    	Map<String, String> properties = new TreeMap<String, String>();
        StringBuilder msg = new StringBuilder();
        msg.append("Version: ");
        msg.append(com.alibaba.dubbo.common.Version.getVersion(this.getClass(), "2.2.0"));
        properties.put("Registry", msg.toString());
        String address = NetUtils.getLocalHost();
        properties.put("Host", NetUtils.getHostName(address) + "/" + address);
        properties.put("Java", System.getProperty("java.runtime.name") + " " + System.getProperty("java.runtime.version"));
        properties.put("OS", System.getProperty("os.name") + " "
                + System.getProperty("os.version"));
        properties.put("CPU", System.getProperty("os.arch", "") + ", "
                + String.valueOf(Runtime.getRuntime().availableProcessors()) + " cores");
        properties.put("Locale", Locale.getDefault().toString() + "/"
                + System.getProperty("file.encoding"));
        properties.put("Uptime", formatUptime(ManagementFactory.getRuntimeMXBean().getUptime()) 
                + " From " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS Z").format(new Date(ManagementFactory.getRuntimeMXBean().getStartTime())) 
                + " To " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS Z").format(new Date()));
        
        model.addAttribute("properties", properties);

		return "/servicecenter/sysinfo/envs";
    }
    
    
    
    
    
    
    
    
    
    
    private List<String> getNoProviders() {
        List<String> providerServices = providerService.findServices();
        List<String> consumerServices = consumerService.findServices();
        List<String> noProviderServices = new ArrayList<String>();
        if (consumerServices != null) {
            noProviderServices.addAll(consumerServices);
            noProviderServices.removeAll(providerServices);
        }
        return noProviderServices;
    }
    
private static final long SECOND = 1000;
    
    private static final long MINUTE = 60 * SECOND;
    
    private static final long HOUR = 60 * MINUTE;
    
    private static final long DAY = 24 * HOUR;
    
    private String formatUptime(long uptime) {
        StringBuilder buf = new StringBuilder();
        if (uptime > DAY) {
            long days = (uptime - uptime % DAY) / DAY;
            buf.append(days);
            buf.append(" Days");
            uptime = uptime % DAY;
        }
        if (uptime > HOUR) {
            long hours = (uptime - uptime % HOUR) / HOUR;
            if (buf.length() > 0) {
                buf.append(", ");
            }
            buf.append(hours);
            buf.append(" Hours");
            uptime = uptime % HOUR;
        }
        if (uptime > MINUTE) {
            long minutes = (uptime - uptime % MINUTE) / MINUTE;
            if (buf.length() > 0) {
                buf.append(", ");
            }
            buf.append(minutes);
            buf.append(" Minutes");
            uptime = uptime % MINUTE;
        }
        if (uptime > SECOND) {
            long seconds = (uptime - uptime % SECOND) / SECOND;
            if (buf.length() > 0) {
                buf.append(", ");
            }
            buf.append(seconds);
            buf.append(" Seconds");
            uptime = uptime % SECOND;
        }
        if (uptime > 0) {
            if (buf.length() > 0) {
                buf.append(", ");
            }
            buf.append(uptime);
            buf.append(" Milliseconds");
        }
        return buf.toString();
    }
}
