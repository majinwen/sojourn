/**
 * @FileName: SendHuanxinForChangzuThread.java
 * @Package com.ziroom.minsu.services.message.utils
 * 
 * @author loushuai
 * @created 2017年9月7日 下午5:34:22
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.utils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.cache.redisOne.RedisOperations;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.message.MsgFirstAdvisoryEntity;
import com.ziroom.minsu.services.common.utils.CloseableHttpsUtil;
import com.ziroom.minsu.services.message.service.MsgFirstAdvisoryServiceImpl;
import com.ziroom.minsu.services.message.utils.base.HuanxinConfig;
import com.ziroom.minsu.services.message.utils.base.SendImMsgRequest;

/**
 * <p></p>
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
public class SendHuanxinForChangzuThread implements Runnable{

	private final static Logger LOGGER = LoggerFactory.getLogger(SendImMesToHuanxinThread.class);

	/**
	 * 请求参数
	 */
	private List<SendImMsgRequest> listSendImMsgRequest;

	/**
	 * 环信配置信息
	 */
	private HuanxinConfig huanxinConfig;

	/**
	 * redis 对象
	 */
	private RedisOperations redisOperations;

	private MsgFirstAdvisoryServiceImpl msgFirstAdvisoryServiceImpl;

	public SendHuanxinForChangzuThread(){};

	/**
	 * 带参 构造
	 * @param huanxinConfig
	 */
	public SendHuanxinForChangzuThread(HuanxinConfig huanxinConfig,RedisOperations redisOperations, List<SendImMsgRequest> listSendImMsgRequest,MsgFirstAdvisoryServiceImpl msgFirstAdvisoryServiceImpl){

		this.huanxinConfig = huanxinConfig;
		this.redisOperations = redisOperations;
		this.listSendImMsgRequest = listSendImMsgRequest;
		this.msgFirstAdvisoryServiceImpl = msgFirstAdvisoryServiceImpl;
	}

	@Override
	public void run() {

		String token = HuanxinUtils.getHuanxinToken(redisOperations, huanxinConfig);

		if(!Check.NuNStr(token)){
			List<SendImMsgRequest> listSendImMsgRequest = this.getListSendImMsgRequest();
			String url = huanxinConfig.getHuanxinSendImMsgUrl();

			if(!Check.NuNCollection(listSendImMsgRequest)
					&&!Check.NuNStr(url)){

				Map<String, String> headerMap = new HashMap<String, String>();
				headerMap.put("Content-Type", "application/json");
				headerMap.put("Authorization", "Bearer "+token);
				LogUtil.info(LOGGER, "【向环信发消息】url={},headerMap={}", url,headerMap);

				try {
					for (SendImMsgRequest sendImMsgRequest : listSendImMsgRequest) {
						Map<String, String > msgMap = new HashMap<String, String>();
						Map<String, Object> param = new HashMap<String, Object>();
						msgMap.put("type", "txt");
						//msgMap.put("msg", sendImMsgRequest.getMsg());
						param.put("target_type", sendImMsgRequest.getTarget_type());
						param.put("target", sendImMsgRequest.getTarget());
						param.put("msg", msgMap);
						param.put("from", sendImMsgRequest.getFrom());
						if(!Check.NuNMap(sendImMsgRequest.getExtMap())){
							param.put("ext", sendImMsgRequest.getExtMap());
						}
						CloseableHttpsUtil.sendPost(url, JsonEntityTransform.Object2Json(param), headerMap);
					}
				} catch (Exception e) {
					LogUtil.error(LOGGER, "发送IM消息到环信 异步(长租)e={}", e);
				}

			}
		}

	}


	/**
	 * @return the msgFirstAdvisoryServiceImpl
	 */
	public MsgFirstAdvisoryServiceImpl getMsgFirstAdvisoryServiceImpl() {
		return msgFirstAdvisoryServiceImpl;
	}

	/**
	 * @param msgFirstAdvisoryServiceImpl the msgFirstAdvisoryServiceImpl to set
	 */
	public void setMsgFirstAdvisoryServiceImpl(
			MsgFirstAdvisoryServiceImpl msgFirstAdvisoryServiceImpl) {
		this.msgFirstAdvisoryServiceImpl = msgFirstAdvisoryServiceImpl;
	}

	/**
	 * @return the listSendImMsgRequest
	 */
	public List<SendImMsgRequest> getListSendImMsgRequest() {
		return listSendImMsgRequest;
	}

	/**
	 * @param listSendImMsgRequest the listSendImMsgRequest to set
	 */
	public void setListSendImMsgRequest(List<SendImMsgRequest> listSendImMsgRequest) {
		this.listSendImMsgRequest = listSendImMsgRequest;
	}

	/**
	 * @return the huanxinConfig
	 */
	public HuanxinConfig getHuanxinConfig() {
		return huanxinConfig;
	}


	/**
	 * @param huanxinConfig the huanxinConfig to set
	 */
	public void setHuanxinConfig(HuanxinConfig huanxinConfig) {
		this.huanxinConfig = huanxinConfig;
	}


	/**
	 * @return the redisOperations
	 */
	public RedisOperations getRedisOperations() {
		return redisOperations;
	}


	/**
	 * @param redisOperations the redisOperations to set
	 */
	public void setRedisOperations(RedisOperations redisOperations) {
		this.redisOperations = redisOperations;
	}



}
