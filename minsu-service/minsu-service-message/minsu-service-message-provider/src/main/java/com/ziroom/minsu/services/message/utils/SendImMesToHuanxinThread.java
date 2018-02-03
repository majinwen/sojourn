/**
 * @FileName: SendImMesToHuanxinThread.java
 * @Package com.ziroom.minsu.services.message.utils
 * 
 * @author yd
 * @created 2017年4月8日 下午2:51:55
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
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.common.utils.CloseableHttpsUtil;
import com.ziroom.minsu.services.message.service.MsgFirstAdvisoryServiceImpl;
import com.ziroom.minsu.services.message.utils.base.HuanxinConfig;
import com.ziroom.minsu.services.message.utils.base.SendImMsgRequest;
import com.ziroom.minsu.valenum.msg.RunStatusEnum;

/**
 * <p>
 * 1. 5分钟未首次咨询IM消息未回复，给房东主动发消息（说明： 5分钟 配置文件配置  单位：分钟）
 * </p>
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
public class SendImMesToHuanxinThread implements Runnable {

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

	public SendImMesToHuanxinThread(){};

	/**
	 * 带参 构造
	 * @param huanxinConfig
	 */
	public SendImMesToHuanxinThread(HuanxinConfig huanxinConfig,RedisOperations redisOperations, List<SendImMsgRequest> listSendImMsgRequest,MsgFirstAdvisoryServiceImpl msgFirstAdvisoryServiceImpl){

		this.huanxinConfig = huanxinConfig;
		this.redisOperations = redisOperations;
		this.listSendImMsgRequest = listSendImMsgRequest;
		this.msgFirstAdvisoryServiceImpl = msgFirstAdvisoryServiceImpl;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
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

				List<MsgFirstAdvisoryEntity> lsitMsgFirstAdvisory = new LinkedList<MsgFirstAdvisoryEntity>();

				try {
					for (SendImMsgRequest sendImMsgRequest : listSendImMsgRequest) {
						Map<String, String > msgMap = new HashMap<String, String>();
						Map<String, Object> param = new HashMap<String, Object>();
						msgMap.put("type", "txt");
						msgMap.put("msg", sendImMsgRequest.getMsg());
						param.put("target_type", sendImMsgRequest.getTarget_type());
						param.put("target", sendImMsgRequest.getTarget());
						param.put("msg", msgMap);
						param.put("from", sendImMsgRequest.getFrom());
						if(!Check.NuNMap(sendImMsgRequest.getExtMap())){
							param.put("ext", sendImMsgRequest.getExtMap());
						}
						String result = CloseableHttpsUtil.sendPost(url, JsonEntityTransform.Object2Json(param), headerMap);
						Map<String, Object> huanxinImMap = new HashMap<String, Object>();
						huanxinImMap = (Map<String, Object>) JsonEntityTransform.json2Map(result);
						if(!Check.NuNMap(huanxinImMap)){
							Object entities = huanxinImMap.get("data");
							if(!Check.NuNObj(entities)){
								huanxinImMap = (Map<String, Object>) JsonEntityTransform.json2Map(JsonEntityTransform.Object2Json(entities));
								if(!Check.NuNMap(huanxinImMap)){
									for (String key:huanxinImMap.keySet()) {
										String val = (String) huanxinImMap.get(key);
										MsgFirstAdvisoryEntity msgFirstAdvisory = new MsgFirstAdvisoryEntity();
										msgFirstAdvisory.setToUid(sendImMsgRequest.getFrom().split(MessageConst.IM_UID_PRE)[1]);
										msgFirstAdvisory.setFromUid(key.split(MessageConst.IM_UID_PRE)[1]);
										msgFirstAdvisory.setStatus(RunStatusEnum.RUN_FAILED.getValue());
										if(!Check.NuNStr(val)&&MessageConst.SUCCESS_CODE.equals(val)){
											msgFirstAdvisory.setStatus(RunStatusEnum.RUN_SUCCESS.getValue());
										}
										lsitMsgFirstAdvisory.add(msgFirstAdvisory);
									}
								}
							}
						}
					}
				} catch (Exception e) {
					LogUtil.error(LOGGER, "【5分钟IM回复】IM消息发送异常e={}", e);
				}

				if(!Check.NuNCollection(lsitMsgFirstAdvisory)){
					int i = msgFirstAdvisoryServiceImpl.updateBathByUid(lsitMsgFirstAdvisory);
					LogUtil.info(LOGGER, "【5分钟IM回复】成功回复{}条", i);
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
