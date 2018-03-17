package com.asura.amp.dubbo.common.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.asura.amp.dubbo.common.entity.DynamicConfig;
import com.asura.amp.dubbo.common.entity.Owner;
import com.asura.amp.dubbo.common.entity.Provider;
import com.asura.amp.dubbo.common.service.OverrideService;
import com.asura.amp.dubbo.common.service.OwnerService;
import com.asura.amp.dubbo.common.service.ProviderService;

@Service("ownerService")
public class OwnerServiceImpl extends AbstractService implements OwnerService {
    
    @Autowired
    ProviderService providerService;
    
    @Autowired
    OverrideService overrideService;

    public List<String> findAllServiceNames() {
        return null;
    }

    public List<String> findServiceNamesByUsername(String username) {
        return null;
    }

    public List<String> findUsernamesByServiceName(String serviceName) {
        return null;
    }

    public List<Owner> findByService(String serviceName) {
        List<Provider> pList = providerService.findByService(serviceName);
        List<DynamicConfig> cList = overrideService.findByServiceAndAddress(serviceName, Constants.ANYHOST_VALUE);
        return toOverrideLiset(pList,cList);
    }

    public List<Owner> findAll() {
        List<Provider> pList = providerService.findAll();
        List<DynamicConfig> cList = overrideService.findAll();
        return toOverrideLiset(pList,cList);
    }

    public Owner findById(Long id) {
        return null;
    }
    
    private List<Owner> toOverrideLiset(List<Provider> pList, List<DynamicConfig> cList){
        Map<String, Owner> oList = new HashMap<String, Owner>();
        for(Provider p : pList){
            if(p.getUsername() != null){
            	for (String username : Constants.COMMA_SPLIT_PATTERN.split(p.getUsername())) {
	                Owner o = new Owner();
	                o.setService(p.getService());
	                o.setUsername(username);
	                oList.put(o.getService() + "/" + o.getUsername(), o);
            	}
            }
        }
        for(DynamicConfig c : cList){
        	Map<String, String> params = StringUtils.parseQueryString(c.getParams());
        	String usernames = params.get("owner");
            if(usernames != null && usernames.length() > 0){
            	for (String username : Constants.COMMA_SPLIT_PATTERN.split(usernames)) {
            		Owner o = new Owner();
                    o.setService(c.getService());
                    o.setUsername(username);
                    oList.put(o.getService() + "/" + o.getUsername(), o);
            	}
            }
        }
        return new ArrayList<Owner>(oList.values());
    }

	public void saveOwner(Owner owner) {
		List<DynamicConfig> overrides = overrideService.findByServiceAndAddress(owner.getService(), Constants.ANYHOST_VALUE);
        if (overrides == null || overrides.size() == 0) {
        	DynamicConfig override = new DynamicConfig();
        	override.setAddress(Constants.ANYHOST_VALUE);
        	override.setService(owner.getService());
        	override.setEnabled(true);
        	override.setParams("owner=" + owner.getUsername());
        	overrideService.saveOverride(override);
        } else {
	        for(DynamicConfig override : overrides){
	        	Map<String, String> params = StringUtils.parseQueryString(override.getParams());
	        	String usernames = params.get("owner");
	        	if (usernames == null || usernames.length() == 0) {
	        		usernames = owner.getUsername();
	        	} else {
	        		usernames = usernames + "," + owner.getUsername();
	        	}
	        	params.put("owner", usernames);
	        	override.setParams(StringUtils.toQueryString(params));
        		overrideService.updateOverride(override);
	        }
        }
	}

	public void deleteOwner(Owner owner) {
		List<DynamicConfig> overrides = overrideService.findByServiceAndAddress(owner.getService(), Constants.ANYHOST_VALUE);
        if (overrides == null || overrides.size() == 0) {
        	DynamicConfig override = new DynamicConfig();
        	override.setAddress(Constants.ANYHOST_VALUE);
        	override.setService(owner.getService());
        	override.setEnabled(true);
        	override.setParams("owner=" + owner.getUsername());
        	overrideService.saveOverride(override);
        } else {
	        for(DynamicConfig override : overrides){
	        	Map<String, String> params = StringUtils.parseQueryString(override.getParams());
	        	String usernames = params.get("owner");
	        	if (usernames != null && usernames.length() > 0) {
	        		if (usernames.equals(owner.getUsername())) {
	        			params.remove("owner");
	        		} else {
	        			usernames = usernames.replace(owner.getUsername() + ",", "").replace("," + owner.getUsername(), "");
	        			params.put("owner", usernames);
	        		}
	        		if (params.size() > 0) {
		        		override.setParams(StringUtils.toQueryString(params));
		        		overrideService.updateOverride(override);
		        	} else {
		        		overrideService.deleteOverride(override.getId());
		        	}
	        	}
	        }
        }
	}
}
