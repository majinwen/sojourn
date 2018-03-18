package com.zra.common.utils;

import com.ziroom.platform.tesla.config.ApplicationConfiguration;
import com.ziroom.platform.tesla.config.TeslaConfigurationFactory;

import javax.annotation.concurrent.ThreadSafe;

/**
 * @author cuiyuhui
 * 配置文件工具类
 * 简单封装
 * ApplicationConfiguration
 */
@ThreadSafe
public class PropUtils {

	private static final ApplicationConfiguration configuration = TeslaConfigurationFactory.getInstance();
	
	private PropUtils(){
		
	}
	
	
	/**
	 * 不存在返回NULL
	 * @return
	 */
	public static String getString(String key){
		return  configuration.getString(key, null);
	}
}
