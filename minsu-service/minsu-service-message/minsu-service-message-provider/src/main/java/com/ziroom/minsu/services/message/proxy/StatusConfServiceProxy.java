/**
 * @FileName: StatusConfServiceProxy.java
 * @Package com.ziroom.minsu.services.message.proxy
 * 
 * @author jixd
 * @created 2016年9月27日 下午9:17:02
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.proxy;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.cache.redisOne.RedisOperations;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.base.StatusConfEntity;
import com.ziroom.minsu.services.common.utils.RedisKeyConst;
import com.ziroom.minsu.services.message.api.inner.StatusConfService;
import com.ziroom.minsu.services.message.service.StatusConfServiceImpl;

/**
 * <p>配置参数</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author jixd
 * @since 1.0
 * @version 1.0
 */
@Component("message.statusConfServiceProxy")
public class StatusConfServiceProxy implements StatusConfService {
	
	
	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(StatusConfServiceProxy.class);

	@Resource(name = "message.statusConfServiceImpl")
	private StatusConfServiceImpl statusConfServiceImpl;
	

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.message.api.inner.StatusConfService#getStatusConfByKey(java.lang.String)
	 */
	@Override
	public String getStatusConfByKey(String key) {
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(key)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("配置键为空");
			return dto.toJsonString();
		}
		StatusConfEntity confEntity =statusConfServiceImpl.queryStatusConfByKey(key);
		if(Check.NuNObj(confEntity)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("配置不存在");
			return dto.toJsonString();
		}
		String staVal = confEntity.getStaVal();
			
		LogUtil.info(logger, "【配置取值标志】val={}", staVal);
		dto.putValue("value", staVal);
		return dto.toJsonString();
	}

}
