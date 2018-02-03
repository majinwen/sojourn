/**
 * @FileName: HuanxinUtils.java
 * @Package com.ziroom.minsu.services.message.utils
 * 
 * @author yd
 * @created 2017年4月8日 下午3:22:23
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.utils;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.cache.redisOne.RedisOperations;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.common.utils.CloseableHttpsUtil;
import com.ziroom.minsu.services.common.utils.RedisKeyConst;
import com.ziroom.minsu.services.message.utils.base.HuanxinConfig;

/**
 * <p>环信工具类</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
public class HuanxinUtils {
	
	/**
	 * 日志对象
	 */
	private final static Logger LOGGER = LoggerFactory.getLogger(HuanxinUtils.class);

	
	/**
	 * 
	 * 获取环信token
	 *
	 * @author yd
	 * @created 2016年9月10日 下午3:32:03
	 *
	 * @return
	 */
	public static String getHuanxinToken(RedisOperations redisOperations,HuanxinConfig huanxinConfig){

		String token = null;
		String huanxinTokenKey  = RedisKeyConst.getHuanxinTokenKey("huanxin_token");

		if(!Check.NuNObj(redisOperations)){
			try {
				token = redisOperations.get(huanxinTokenKey);
				LogUtil.info(LOGGER, "根据环信 tokenKey={},获取token={}", huanxinTokenKey,token);
				if(!Check.NuNStr(token)){
					return token;
				}

			} catch (Exception e) {
				LogUtil.error(LOGGER, "环信获取token，redis异常e={}", e);
			}
		}


		if(checkSysHuanxinConfig(huanxinConfig)){
			try {
				Map<String, String> paramsMap = new HashMap<String, String>();
				paramsMap.put("grant_type", "client_credentials");
				paramsMap.put("client_id", huanxinConfig.getHuanxinClientId());
				paramsMap.put("client_secret", huanxinConfig.getHuanxinClientSecret());
				Long t1 = System.currentTimeMillis();
				String huanxinTokenRes = CloseableHttpsUtil.sendPost(huanxinConfig.getHuanxinTokenUrl(),JsonEntityTransform.Object2Json(paramsMap));
				Long t2 = System.currentTimeMillis();
				LogUtil.info(LOGGER, "获取环信接口用户t2-t1={}ms,入参paramsMap={},请求url={},返回结果huanxinTokenRes={}", t2-t1,JsonEntityTransform.Object2Json(paramsMap),huanxinConfig.getHuanxinTokenUrl(),huanxinTokenRes);
				if(!Check.NuNStr(huanxinTokenRes)){
					LogUtil.info(LOGGER, "环信token获取huanxinTokenRes={}", huanxinTokenRes);
					paramsMap.clear();
					paramsMap = (Map<String, String>) JsonEntityTransform.json2Map(huanxinTokenRes);
					if(!Check.NuNMap(paramsMap)){
						token = paramsMap.get("access_token");
						if(!Check.NuNStr(token)){
							redisOperations.setex(huanxinTokenKey, RedisKeyConst.HUANXIN_TOKEN_CACHE_TIME, token);
						}
					}
				}
			} catch (Exception e) {
				LogUtil.error(LOGGER, "环信获取token接口异常e={},token={}", e,token);
			}
		}


		LogUtil.info(LOGGER, "返回结果toekn={}", token);	
		return token;
	}
	

	/**
	 * 
	 * 校验当前环信配置文件
	 *
	 * @author yd
	 * @created 2016年9月20日 上午10:21:52
	 *
	 * @param huanxinConfig
	 * @return
	 */
	private static boolean checkSysHuanxinConfig(HuanxinConfig huanxinConfig){

		if(Check.NuNObj(huanxinConfig)||Check.NuNStr(huanxinConfig.getHuanxinAppKey())
				||Check.NuNStr(huanxinConfig.getHuanxinClientId())
				||Check.NuNStr(huanxinConfig.getHuanxinClientSecret())
				||Check.NuNStr(huanxinConfig.getHuanxinDomain())
				||Check.NuNStr(huanxinConfig.getHuanxinTokenUrl())
				||Check.NuNStr(huanxinConfig.getImMinsuFlag())){

			return false;
		}

		return true;
	}
}
