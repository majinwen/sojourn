package com.zra.zmconfig;

import com.zra.zmconfig.entity.CfZmConfig;

/**
 * @author cuiyh9
 * 配置工具类
 */
public class ConfigUtils {

	private static final String  prefix = "ZRA:CONFIG:";
	
	private ConfigUtils(){
		
	}

	public  static String wrapCfKey(CfZmConfig config){
		return  wrap(config.getSystemId()+":"+config.getCfKey());
	}
	public static String wrap(String key){
		return  prefix +key;
	}
	
}
