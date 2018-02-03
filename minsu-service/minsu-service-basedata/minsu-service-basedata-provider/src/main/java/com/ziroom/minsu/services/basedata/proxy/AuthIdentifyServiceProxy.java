/**
 * @FileName: AuthIdentifyServiceProxy.java
 * @Package com.ziroom.minsu.services.basedata.proxy
 * 
 * @author lunan
 * @created 2017年8月31日 下午2:43:11
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.basedata.proxy;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.base.AuthIdentifyEntity;
import com.ziroom.minsu.services.basedata.api.inner.AuthIdentifyService;
import com.ziroom.minsu.services.basedata.service.AuthIdentifyServiceImpl;

/**
 * <p>自如网授权标识代理层</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author loushuai
 * @since 1.0
 * @version 1.0
 */
@Component("basedata.authIdentifyServiceProxy")
public class AuthIdentifyServiceProxy implements AuthIdentifyService{

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthIdentifyServiceProxy.class);
	
	
	@Resource(name="basedata.authIdentifyServiceImpl")
	private AuthIdentifyServiceImpl authIdentifyServiceImpl;
	
	
	/**
	 * 
	 * 获取自如网授权标示
	 *
	 * @author loushuai
	 * @created 2017年8月31日 下午2:43:48
	 *
	 * @param parmaJson
	 * @return
	 */
	@Override
	public String getAuthIdentifyByCode(String parmaJson) {
		LogUtil.debug(LOGGER, "getAuthIdentifyByCode方法入参，parma={}", parmaJson);
		DataTransferObject dto = new DataTransferObject();
		AuthIdentifyEntity authIdentify = JsonEntityTransform.json2Entity(parmaJson, AuthIdentifyEntity.class);
		if(Check.NuNObj(authIdentify) || Check.NuNStr(authIdentify.getCode())){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数为空");
		}
		AuthIdentifyEntity resultAuthIdentify = authIdentifyServiceImpl.getByCode(authIdentify);
		dto.putValue("object", resultAuthIdentify);
		return dto.toJsonString();
	}

}
