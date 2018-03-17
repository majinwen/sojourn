package com.asura.amp.dubbo.monitor.controllor;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.asura.amp.dubbo.common.service.ProviderService;
import com.asura.amp.dubbo.monitor.entity.Summary;
import com.asura.amp.dubbo.monitor.service.SysMonitorService;

@Controller("DubboMonitor")
@RequestMapping("/servicecenter/monitor")
public class MonitorController {
	
	@Autowired
    private ProviderService providerService;
    
	@Autowired
	private SysMonitorService sysMonitorService;
	
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
		return "/servicecenter/monitor/init";
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
	@RequestMapping(value="/charts")
	public String charts(String datetime, String service, String chartsmethod, Model model) {
		List<String> serviceList = new ArrayList<String>();
    	
    	serviceList.addAll(providerService.findServices());
        // serviceList.addAll(consumerService.findServices());
        
        Map<String, String> serviceMap = new HashMap<String, String>();
        for(String svc : serviceList) {
        	if(svc != null) {
        		int index = svc.lastIndexOf('.');
        		String name = index >=0 ? svc.substring(index + 1) : svc;
        		serviceMap.put(name, svc);
        	}
        }
        
        model.addAttribute("serviceMap", serviceMap);
        
        if(StringUtils.isNotEmpty(service)) {
        	model.addAttribute("methods", serviceMethods(service));
        }
        
		return "/servicecenter/monitor/charts";
	}
	
	
	
	
	
	@RequestMapping(value = "/json")
	@ResponseBody
	public String JsonData(String service, String method, String statisticstype, String strTime, String endTime) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		List<Summary> summarys = null;
		try {
			summarys = sysMonitorService.findSummary(service, method, statisticstype, format.parse(strTime), format.parse(endTime));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return Summary2String(summarys);
	}
	private String Summary2String(List<Summary> summarys) {
		StringBuffer sb = new StringBuffer();
		
		sb.append("{");
		sb.append("\"flot\":{");
		sb.append("\"label\":").append("\"访问量\"").append(",");
		sb.append("\"data\":").append("[");
		for(Summary summary : summarys) {
			// 时区 + 8 小时
			sb.append("[").append(summary.getTime().getTime() + 8 * 60 * 60 * 1000).append(",").append(summary.getSummary()).append("],");
		}
		if(summarys.size() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		sb.append("]");
		sb.append("},\"avg\":").append("1000");
		sb.append("}");
		
		return sb.toString();
	}
	
	@RequestMapping(value = "/charts/serviceMethods")
	@ResponseBody
	public List<String> serviceMethods(String service) {
		return CollectionUtils.sort(new ArrayList<String>(providerService.findMethodsByService(service)));
	}
}
