/**
 * @FileName: StatsHouseEvaTask.java
 * @Package com.ziroom.minsu.services.evaluate.utils
 * 
 * @author yd
 * @created 2016年4月9日 上午10:40:49
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.evaluate.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.ziroom.minsu.services.common.constant.JpushConst;
import com.ziroom.minsu.services.common.sms.MessageUtils;
import com.ziroom.minsu.services.common.sms.base.SmsMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asura.framework.base.util.Check;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.evaluate.EvaluateOrderEntity;
import com.ziroom.minsu.entity.evaluate.TenantEvaluateEntity;
import com.ziroom.minsu.services.common.jpush.JpushUtils;
import com.ziroom.minsu.services.common.jpush.base.JpushConfig;
import com.ziroom.minsu.services.common.jpush.base.MessageTypeEnum;
import com.ziroom.minsu.services.common.utils.SystemGlobalsUtils;
import com.ziroom.minsu.services.evaluate.constant.MessageConst;
import com.ziroom.minsu.services.evaluate.dao.StatsHouseEvaDao;
import com.ziroom.minsu.services.evaluate.service.EvaluateOrderServiceImpl;
import com.ziroom.minsu.valenum.evaluate.EvaluateStatuEnum;

/**
 * <p>统计房源信息的task
 *  前提：只有发布的成功的评价才能统计
 *  说明：A. 异步实现统计房源信息，这里的目的：把统计信息单独入库，不去放入保存其他信息的事物中（例如：添加评价订单关系表 和 房源评价基础表)
 *      B.向房东推送评价成功消息
 *  
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
public class StatsHouseEvaTask implements Runnable{


	/**
	 * 日志对象
	 */
	private static final Logger logger = LoggerFactory.getLogger(EvaluateOrderServiceImpl.class);

	/**
	 * 更新统计信息的dao实例
	 */
	private StatsHouseEvaDao statsHouseEvaDao;
	/**
	 * 房客评价实体
	 */
	private TenantEvaluateEntity tenantEvaluateEntity;
	/**
	 * 评价订单关系实体
	 */
	private EvaluateOrderEntity evaluateOrderEntity;

	/**
	 * 推送需要替换参数
	 */
	private Map<String, String> paramsMap;

	/**
	 * 消息内容
	 */
	private String content;
	/**
	 * 用户手机号
	 */
	private String mobile;





	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Map<String, String> getParamsMap() {
		return paramsMap;
	}

	public void setParamsMap(Map<String, String> paramsMap) {
		this.paramsMap = paramsMap;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public StatsHouseEvaDao getStatsHouseEvaDao() {
		return statsHouseEvaDao;
	}

	public void setStatsHouseEvaDao(StatsHouseEvaDao statsHouseEvaDao) {
		this.statsHouseEvaDao = statsHouseEvaDao;
	}

	public TenantEvaluateEntity getTenantEvaluateEntity() {
		return tenantEvaluateEntity;
	}

	public void setTenantEvaluateEntity(TenantEvaluateEntity tenantEvaluateEntity) {
		this.tenantEvaluateEntity = tenantEvaluateEntity;
	}

	public EvaluateOrderEntity getEvaluateOrderEntity() {
		return evaluateOrderEntity;
	}

	public void setEvaluateOrderEntity(EvaluateOrderEntity evaluateOrderEntity) {
		this.evaluateOrderEntity = evaluateOrderEntity;
	}

	public StatsHouseEvaTask(){};

	/**
	 *初始化实例
	 * @param statsHouseEvaDao
	 * @param tenantEvaluateEntity
	 * @param evaluateOrderEntity
	 */
	public StatsHouseEvaTask(StatsHouseEvaDao statsHouseEvaDao,TenantEvaluateEntity tenantEvaluateEntity,EvaluateOrderEntity evaluateOrderEntity){
		this.statsHouseEvaDao = statsHouseEvaDao;
		this.tenantEvaluateEntity = tenantEvaluateEntity;
		this.evaluateOrderEntity = evaluateOrderEntity;

	}

	/**
	 *初始化实例
	 * @param statsHouseEvaDao
	 * @param tenantEvaluateEntity
	 * @param evaluateOrderEntity
	 */
	public StatsHouseEvaTask(StatsHouseEvaDao statsHouseEvaDao,TenantEvaluateEntity tenantEvaluateEntity,EvaluateOrderEntity evaluateOrderEntity,Map<String, String> paramsMap){
		this.statsHouseEvaDao = statsHouseEvaDao;
		this.tenantEvaluateEntity = tenantEvaluateEntity;
		this.evaluateOrderEntity = evaluateOrderEntity;
		this.paramsMap = paramsMap;
		if(!Check.NuNMap(paramsMap)){
			this.content = paramsMap.get("content");
			this.mobile = paramsMap.get("mobile");
		}


	}
	@Override
	public void run() {

		try {
			if(!Check.NuNObj(statsHouseEvaDao)){
				this.statsHouseEvaDao.saveOrUpdateStatsHouseEva(tenantEvaluateEntity, evaluateOrderEntity);
				//统计信息完成：得向用户推送信息（房源统计 向房东推送消息）
				if(!Check.NuNObj(evaluateOrderEntity)&&!Check.NuNObj(evaluateOrderEntity.getEvaStatu())&&evaluateOrderEntity.getEvaStatu().intValue() == EvaluateStatuEnum.ONLINE.getEvaStatuCode()){
					LogUtil.info(logger, "房客uid={}评价成功，向房东lanlordUid={},推送消息开始,推送内容content={}",evaluateOrderEntity.getEvaUserUid(),evaluateOrderEntity.getRatedUserUid(),this.getContent());
					if(!Check.NuNStr(this.getContent())){
						JpushConfig  jpushConfig=  new JpushConfig();
						jpushConfig.setContent(this.getContent());
						jpushConfig.setMessageType(MessageTypeEnum.MESSAGE.getCode());
						Map<String, String> extrasMap = new HashMap<String, String>();
						extrasMap.put(JpushConst.MSG_BODY_TYPE_KEY, JpushConst.MSG_BODY_TYPE_VALUE);
						extrasMap.put(JpushConst.MSG_SUB_TYPE_KEY, JpushConst.MSG_SUB_TYPE_VALULE_1);
						extrasMap.put(JpushConst.MSG_HAS_RESPONSE,"1");
						extrasMap.put(JpushConst.MSG_TAG_TYPE,JpushConst.MSG_TARGET_LAN);
						extrasMap.put(JpushConst.MSG_PUSH_TIME,String.valueOf(System.currentTimeMillis()));
				    	extrasMap.put("url", SystemGlobalsUtils.getValue(MessageConst.LAN_EVA_URL)+evaluateOrderEntity.getOrderSn());
						jpushConfig.setExtrasMap(extrasMap);
						JpushUtils.sendPushOne(evaluateOrderEntity.getRatedUserUid(), jpushConfig, this.getParamsMap());
					}
				}
				//评论发布后 向房客发送消息
				//统计信息完成：得向用户推送信息（房源统计 向房东推送消息）
				if(!Check.NuNObj(evaluateOrderEntity)&&!Check.NuNObj(evaluateOrderEntity.getEvaStatu())&&evaluateOrderEntity.getEvaStatu().intValue() == EvaluateStatuEnum.ONLINE.getEvaStatuCode()){
					if(!Check.NuNStr(this.getContent())){
						LogUtil.info(logger, "房东:tel{},推送内容content={}",this.getMobile(),this.getContent());
						MessageUtils.sendSms(new SmsMessage(this.getMobile(), this.getContent()), this.getParamsMap());
					}
				}

			}
		} catch (Exception e) {
			LogUtil.info(logger, "保存统计信息失败{}", tenantEvaluateEntity);
		}


	}

	public static void main(String[] args) {
		ExecutorService pool = Executors.newFixedThreadPool(5);  
		pool.execute(new StatsHouseEvaTask());
	}


}
