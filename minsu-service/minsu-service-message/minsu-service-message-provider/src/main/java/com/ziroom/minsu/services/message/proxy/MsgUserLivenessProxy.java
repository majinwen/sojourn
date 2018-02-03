/**
 * @FileName: MsgUserLivenessProxy.java
 * @Package com.ziroom.minsu.services.message.proxy
 * 
 * @author loushuai
 * @created 2017年9月1日 上午11:48:27
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.proxy;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.message.MsgUserLivenessEntity;
import com.ziroom.minsu.services.message.api.inner.MsgUserLivenessService;
import com.ziroom.minsu.services.message.service.MsgUserLivenessImpl;

/**
 * <p>TODO</p>
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
@Component("message.msgUserLivenessProxy")
public class MsgUserLivenessProxy implements MsgUserLivenessService{

	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(MsgUserLivenessProxy.class);
	
	
	@Resource(name="message.msgUserLivenessImpl")
	private MsgUserLivenessImpl msgUserLivenessImpl; 
	
	/**
	 * 同步聊天用户活跃时间
	 *
	 * @author loushuai
	 * @created 2017年9月1日 上午11:57:38
	 *
	 * @param msgReplySetRequest
	 * @return
	 */
	@Override
	public String syncLivenessTime(String param) {
		DataTransferObject dto = new DataTransferObject();
		List<MsgUserLivenessEntity> msgUserLivenessList = JsonEntityTransform.json2List(param, MsgUserLivenessEntity.class);
		if(Check.NuNCollection(msgUserLivenessList)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("sycnData同步数据为空");
		}
		int countInsert = msgUserLivenessImpl.syncLivenessTime(msgUserLivenessList);
		dto.putValue("countInsert", countInsert);
		return dto.toJsonString();
	}

}
