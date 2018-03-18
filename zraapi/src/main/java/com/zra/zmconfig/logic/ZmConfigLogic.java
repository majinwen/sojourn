package com.zra.zmconfig.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zra.zmconfig.entity.CfZmConfig;
import com.zra.zmconfig.service.ZmConfigService;

@Component
public class ZmConfigLogic {
	
	@Autowired
	private ZmConfigService zmConfigService;
	
	public List<CfZmConfig> queryAll(CfZmConfig config){
		return zmConfigService.queryAll(config);
	}
	
	public boolean saveCfZmConfig(CfZmConfig config){
		return zmConfigService.saveCfZmConfig(config);
	}
	
	public boolean updateCfZmConfig(CfZmConfig config){
		return zmConfigService.updateCfZmConfig(config);
	}
	
	public boolean deleteCfZmConfig(CfZmConfig config){
		return zmConfigService.deleteCfZmConfig(config);
	}
	
}

