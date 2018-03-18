package com.zra.zmconfig;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import com.zra.common.utils.RedisUtil;
import com.zra.zmconfig.entity.CfZmConfig;
import com.zra.zmconfig.logic.ZmConfigLogic;

/**
 * @author 目前为redis，考虑使用非redis的情况
 *
 */
@Component
public class ConfigClient {

	private static final Logger LOG =  LoggerFactoryProxy.getLogger(ConfigClient.class);
	
	@Autowired
	private  ZmConfigLogic zmConfigLogic;
	
	
	/**
	 * 获取config值
	 * @param key
	 * @param systemId
	 * @return
	 */
	public String get(String key,String systemId){
		CfZmConfig config = new CfZmConfig(systemId,key);
		String value = RedisUtil.getConfig(ConfigUtils.wrapCfKey(config));
		if(value == null){
			List<CfZmConfig>  configList = zmConfigLogic.queryAll(config);
			if(configList == null || configList.size() == 0){
				LOG.error("key:"+key+",systemId:"+systemId+" do not config");
				return null;
			}else{
				config = configList.get(0);
				RedisUtil.setConfig(config);;
			}
			value =  config.getCfValue();
			
		}
		return value;
	}
	
	
}
