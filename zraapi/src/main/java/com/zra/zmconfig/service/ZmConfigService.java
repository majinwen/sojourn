package com.zra.zmconfig.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zra.common.utils.RedisUtil;
import com.zra.zmconfig.dao.CfZmConfigMapper;
import com.zra.zmconfig.entity.CfZmConfig;

@Component
public class ZmConfigService {
	
	@Autowired
	private CfZmConfigMapper cfZmconfigMapper;
	
	/**
	 * 
	 * @return
	 */
	public List<CfZmConfig> queryAll(CfZmConfig config){
		return cfZmconfigMapper.queryAll(config);
	}
	
	public boolean saveCfZmConfig(CfZmConfig config){
		int size =  cfZmconfigMapper.insert(config);
		RedisUtil.setConfig(config);
		return size > 0;
		
	}
	
	public boolean updateCfZmConfig(CfZmConfig config){
		int size = cfZmconfigMapper.update(config);
		RedisUtil.setConfig(config);
		return size > 0 ;
	}
	
	public boolean deleteCfZmConfig(CfZmConfig config){
		int size = cfZmconfigMapper.deleteByPrimaryKey(config);
		RedisUtil.deleteConfig(config);
		return size > 0;
	}
	
}

