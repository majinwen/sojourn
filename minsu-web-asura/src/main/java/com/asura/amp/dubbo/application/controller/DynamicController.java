package com.asura.amp.dubbo.application.controller;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.asura.amp.common.entity.BaseAjaxValue;
import com.asura.amp.common.entity.Success;
import com.asura.amp.dubbo.common.entity.DynamicConfig;
import com.asura.amp.dubbo.common.service.ConsumerService;
import com.asura.amp.dubbo.common.service.OverrideService;
import com.asura.amp.dubbo.common.service.ProviderService;

@Controller("appdynamic")
@RequestMapping("/servicecenter/application/dynamic")
public class DynamicController {
	
	@Autowired
    private ProviderService providerService;
    
    @Autowired
    private ConsumerService consumerService;
	
	@Autowired
	private OverrideService overrideService;
    
    
    static final String DEFAULT_MOCK_JSON_KEY = "mock";
    static final String MOCK_JSON_KEY_POSTFIX = ".mock";
    
    static final String FORM_OVERRIDE_KEY = "overrideKey";
    static final String FORM_OVERRIDE_VALUE = "overrideValue";
    
    static final String FORM_DEFAULT_MOCK_METHOD_FORCE = "mockDefaultMethodForce";
    static final String FORM_DEFAULT_MOCK_METHOD_JSON = "mockDefaultMethodJson";
    
    static final String FORM_ORIGINAL_METHOD_FORCE_PREFIX = "mockMethodForce.";
    static final String FORM_ORIGINAL_METHOD_PREFIX = "mockMethod.";
    
    static final String FORM_DYNAMIC_METHOD_NAME_PREFIX = "mockMethodName";
    static final String FORM_DYNAMIC_METHOD_FORCE_PREFIX = "mockMethodForce";
    static final String FORM_DYNAMIC_METHOD_JSON_PREFIX = "mockMethodJson";
    
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
	public String dynamicAddInit(String application, Model model) {
    	List<String> serviceList = new ArrayList<String>();
    	
    	serviceList.addAll(providerService.findServicesByApplication(application));
        serviceList.addAll(consumerService.findServicesByApplication(application));
        
        Map<String, String> serviceMap = new HashMap<String, String>();
        for(String service : serviceList) {
        	if(service != null) {
        		int index = service.lastIndexOf('.');
        		String name = index >=0 ? service.substring(index + 1) : service;
        		serviceMap.put(name, service);
        	}
        }
        
        model.addAttribute("serviceMap", serviceMap);
        
		return "/servicecenter/application/dynamic/add";
    }
    
    @RequestMapping(value="/add")
    @ResponseBody
	public BaseAjaxValue dynamicAdd(DynamicConfig override, HttpServletRequest request) {
		String parameter = catchParams(request);
		override.setParams(parameter);
		override.setService(override.getService());
		
		overrideService.saveOverride(override);
		return new Success("动态配置添加成功");
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
    @RequestMapping(value="/editinit")
	public String dynamicEditInit(String application, Long dynamicId, Model model) {
    	
    	DynamicConfig override = overrideService.findById(dynamicId);
        
        Map<String, String> parameters = parseQueryString(override.getParams());
        
        if(parameters.get(DEFAULT_MOCK_JSON_KEY)!=null){
            String mock = URL.decode(parameters.get(DEFAULT_MOCK_JSON_KEY));
            String[] tokens = parseMock(mock);
            model.addAttribute(FORM_DEFAULT_MOCK_METHOD_FORCE, tokens[0]);
            model.addAttribute(FORM_DEFAULT_MOCK_METHOD_JSON, tokens[1]);
            parameters.remove(DEFAULT_MOCK_JSON_KEY);
        }
        
        Map<String, String> method2Force = new LinkedHashMap<String, String>();
        Map<String, String> method2Json = new LinkedHashMap<String, String>();
        
        List<String> methods = CollectionUtils.sort(new ArrayList<String>(providerService.findMethodsByService(override.getService())));
        if(methods != null && methods.isEmpty()) {
            for(String m : methods) {
                parseMock(m, parameters.get(m + MOCK_JSON_KEY_POSTFIX), method2Force, method2Json);
                parameters.remove(m + MOCK_JSON_KEY_POSTFIX);
            }
        }
        for (Iterator<Map.Entry<String, String>> iterator = parameters.entrySet().iterator(); iterator.hasNext();) {
            Map.Entry<String, String> e =  iterator.next();
            String key = e.getKey();
            
            if(key.endsWith(MOCK_JSON_KEY_POSTFIX)) {
                String m = key.substring(0, key.length() - MOCK_JSON_KEY_POSTFIX.length());
                parseMock(m, e.getValue(), method2Force, method2Json);
                iterator.remove();
            }
        }
        
        List<String> serviceList = new ArrayList<String>();
    	serviceList.addAll(providerService.findServicesByApplication(application));
        serviceList.addAll(consumerService.findServicesByApplication(application));
        
        Map<String, String> serviceMap = new HashMap<String, String>();
        for(String service : serviceList) {
        	if(service != null) {
        		int index = service.lastIndexOf('.');
        		String name = index >=0 ? service.substring(index + 1) : service;
        		serviceMap.put(name, service);
        	}
        }
        
        model.addAttribute("serviceMap", serviceMap);
        
        model.addAttribute("methods", methods);
        model.addAttribute("methodForces", method2Force);
        model.addAttribute("methodJsons", method2Json);
        model.addAttribute("parameters", parameters);
        model.addAttribute("override", override);
		
		return "/servicecenter/application/dynamic/edit";
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
	public BaseAjaxValue dynamicEdit(DynamicConfig override, HttpServletRequest request) {
    	
    	DynamicConfig o = overrideService.findById(override.getId());
        override.setService(o.getService());
        override.setAddress(o.getAddress());
        override.setApplication(o.getApplication());
        
        String parameter = catchParams(request);
        override.setParams(parameter);
        
        overrideService.updateOverride(override);
		
		return new Success("动态配置更新成功");
    }
    
	private String catchParams(HttpServletRequest request) {

		String defaultMockMethodForce = request.getParameter(FORM_DEFAULT_MOCK_METHOD_FORCE);
		String defaultMockMethodJson = request.getParameter(FORM_DEFAULT_MOCK_METHOD_JSON);

		Map<String, String> override2Value = new HashMap<String, String>();
		Map<String, String> method2Json = new HashMap<String, String>();

		Enumeration<?> paramNames = request.getParameterNames();
		while(paramNames.hasMoreElements()) {
			String key = (String) paramNames.nextElement();
			String value = request.getParameter(key);

			if (key.startsWith(FORM_OVERRIDE_KEY) && value != null && value.trim().length() > 0) {
				String index = key.substring(FORM_OVERRIDE_KEY.length());
				String overrideValue = request.getParameter(FORM_OVERRIDE_VALUE + index);
				if (overrideValue != null && overrideValue.trim().length() > 0) {
					override2Value.put(value.trim(), overrideValue.trim());
				}
			}

			if (key.startsWith(FORM_ORIGINAL_METHOD_PREFIX) && value != null && value.trim().length() > 0) {
				String method = key.substring(FORM_ORIGINAL_METHOD_PREFIX.length());
				String force = request.getParameter(FORM_ORIGINAL_METHOD_FORCE_PREFIX + method);
				method2Json.put(method, force + ":" + value.trim());
			}

			if (key.startsWith(FORM_DYNAMIC_METHOD_NAME_PREFIX) && value != null && value.trim().length() > 0) {
				String index = key.substring(FORM_DYNAMIC_METHOD_NAME_PREFIX.length());
				String force = request.getParameter(FORM_DYNAMIC_METHOD_FORCE_PREFIX + index);
				String json = request.getParameter(FORM_DYNAMIC_METHOD_JSON_PREFIX + index);

				if (json != null && json.trim().length() > 0) {
					method2Json.put(value.trim(), force + ":" + json.trim());
				}
			}
		}

		StringBuilder paramters = new StringBuilder();
		boolean isFirst = true;
		if (defaultMockMethodJson != null && defaultMockMethodJson.trim().length() > 0) {
			paramters.append("mock=").append(URL.encode(defaultMockMethodForce + ":" + defaultMockMethodJson.trim()));
			isFirst = false;
		}
		for (Map.Entry<String, String> e : method2Json.entrySet()) {
			if (isFirst)
				isFirst = false;
			else
				paramters.append("&");

			paramters.append(e.getKey()).append(MOCK_JSON_KEY_POSTFIX).append("=").append(URL.encode(e.getValue()));
		}
		for (Map.Entry<String, String> e : override2Value.entrySet()) {
			if (isFirst)
				isFirst = false;
			else
				paramters.append("&");

			paramters.append(e.getKey()).append("=").append(URL.encode(e.getValue()));
		}

		return paramters.toString();
	}
	
	private void parseMock(String m, String mock, Map<String, String> method2Force, Map<String, String> method2Json) {
        String[] tokens = parseMock(mock);
        method2Force.put(m, tokens[0]);
        method2Json.put(m, tokens[1]);
    }
	
	private String[] parseMock(String mock) {
		mock = URL.decode(mock);
		String force;
		if (mock.startsWith("force:")) {
			force = "force";
			mock = mock.substring("force:".length());
		} else if (mock.startsWith("fail:")) {
			force = "fail";
			mock = mock.substring("fail:".length());
		} else {
			force = "fail";
		}
		String[] tokens = new String[2];
		tokens[0] = force;
		tokens[1] = mock;
		return tokens;
	}
	
	static final Pattern AND = Pattern.compile("\\&");
    static final Pattern EQUAL = Pattern.compile("([^=\\s]*)\\s*=\\s*(\\S*)");
    
	static Map<String, String> parseQueryString(String query) {
		HashMap<String, String> ret = new HashMap<String, String>();
		if (query == null || (query = query.trim()).length() == 0)
			return ret;

		String[] kvs = AND.split(query);
		for (String kv : kvs) {
			Matcher matcher = EQUAL.matcher(kv);
			if (!matcher.matches())
				continue;
			String key = matcher.group(1);
			String value = matcher.group(2);
			ret.put(key, value);
		}

		return ret;
	}
}
