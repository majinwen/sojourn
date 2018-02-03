/**
 * @FileName: StatsTenantTvaTask.java
 * @Package com.ziroom.minsu.services.evaluate.utils
 * 
 * @author yd
 * @created 2016年4月9日 下午12:05:36
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.evaluate.utils;

import java.util.HashMap;
import java.util.Map;

import com.ziroom.minsu.services.common.constant.JpushConst;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asura.framework.base.util.Check;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.evaluate.EvaluateOrderEntity;
import com.ziroom.minsu.entity.evaluate.LandlordEvaluateEntity;
import com.ziroom.minsu.services.common.jpush.JpushUtils;
import com.ziroom.minsu.services.common.jpush.base.JpushConfig;
import com.ziroom.minsu.services.common.jpush.base.MessageTypeEnum;
import com.ziroom.minsu.services.common.sms.MessageUtils;
import com.ziroom.minsu.services.common.sms.base.SmsMessage;
import com.ziroom.minsu.services.common.utils.SystemGlobalsUtils;
import com.ziroom.minsu.services.evaluate.constant.MessageConst;
import com.ziroom.minsu.services.evaluate.dao.StatsTenantEvaDao;
import com.ziroom.minsu.valenum.evaluate.EvaluateStatuEnum;

/**
 * <p>统计房东对房客评价的信息  线程实现 防止嵌入其他事物中</p>
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
public class StatsTenantTvaTask implements Runnable{
	
	
	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(StatsTenantTvaTask.class);
	
	/**
	 * 统计房东对房客的评价 实例
	 */
	private StatsTenantEvaDao statsTenantEvaDao;
	/**
	 * 房东对房客评价实体
	 */
	private LandlordEvaluateEntity landlordEvaluateEntity;
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
	
	public StatsTenantTvaTask(){};
	/**
	 * 初始化相关对象
	 * @param statsTenantEvaDao
	 * @param landlordEvaluateEntity
	 * @param evaluateOrderEntity
	 */
    public StatsTenantTvaTask(StatsTenantEvaDao statsTenantEvaDao,LandlordEvaluateEntity landlordEvaluateEntity,EvaluateOrderEntity evaluateOrderEntity){
    	
    	this.statsTenantEvaDao = statsTenantEvaDao;
    	this.landlordEvaluateEntity = landlordEvaluateEntity;
    	this.evaluateOrderEntity = evaluateOrderEntity;
    			
    }
    
    /**
	 * 初始化相关对象
	 * @param statsTenantEvaDao
	 * @param landlordEvaluateEntity
	 * @param evaluateOrderEntity
	 */
    public StatsTenantTvaTask(StatsTenantEvaDao statsTenantEvaDao,LandlordEvaluateEntity landlordEvaluateEntity,EvaluateOrderEntity evaluateOrderEntity,Map<String, String> paramsMap){
    	
    	this.statsTenantEvaDao = statsTenantEvaDao;
    	this.landlordEvaluateEntity = landlordEvaluateEntity;
    	this.evaluateOrderEntity = evaluateOrderEntity;
    	this.paramsMap = paramsMap;
    	if(!Check.NuNMap(paramsMap)){
    		this.content = paramsMap.get("content");
        	this.mobile = paramsMap.get("mobile");;
    	}
    	
    			
    }
    
    

	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public StatsTenantEvaDao getStatsTenantEvaDao() {
		return statsTenantEvaDao;
	}



	public void setStatsTenantEvaDao(StatsTenantEvaDao statsTenantEvaDao) {
		this.statsTenantEvaDao = statsTenantEvaDao;
	}



	public LandlordEvaluateEntity getLandlordEvaluateEntity() {
		return landlordEvaluateEntity;
	}



	public void setLandlordEvaluateEntity(
			LandlordEvaluateEntity landlordEvaluateEntity) {
		this.landlordEvaluateEntity = landlordEvaluateEntity;
	}



	public EvaluateOrderEntity getEvaluateOrderEntity() {
		return evaluateOrderEntity;
	}



	public void setEvaluateOrderEntity(EvaluateOrderEntity evaluateOrderEntity) {
		this.evaluateOrderEntity = evaluateOrderEntity;
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
	@Override
	public void run() {
		if(!Check.NuNObj(this.statsTenantEvaDao)){
			this.statsTenantEvaDao.saveOrUpdateStatsTenantEva(landlordEvaluateEntity, evaluateOrderEntity);
			
			//评论发布后 向房客发送消息
			//统计信息完成：得向用户推送信息（房源统计 向房东推送消息）
			/*if(!Check.NuNObj(evaluateOrderEntity)&&!Check.NuNObj(evaluateOrderEntity.getEvaStatu())&&evaluateOrderEntity.getEvaStatu().intValue() == EvaluateStatuEnum.ONLINE.getEvaStatuCode()){
				LogUtil.info(logger, "房东uid={}评价成功，向房客userUid={},推送消息开始,推送内容content={}",evaluateOrderEntity.getEvaUserUid(),evaluateOrderEntity.getRatedUserUid(),this.getContent());
				if(!Check.NuNStr(this.getContent())){
					JpushConfig  jpushConfig=  new JpushConfig();
					jpushConfig.setContent(this.getContent());
					jpushConfig.setMessageType(MessageTypeEnum.MESSAGE.getCode());
					Map<String, String> extrasMap = new HashMap<String, String>();
					extrasMap.put(JpushConst.MSG_BODY_TYPE_KEY, JpushConst.MSG_BODY_TYPE_VALUE);
			    	extrasMap.put(JpushConst.MSG_SUB_TYPE_KEY, JpushConst.MSG_SUB_TYPE_VALULE_3);
					extrasMap.put(JpushConst.MSG_TAG_TYPE,JpushConst.MSG_TARGET_TENANT);
					extrasMap.put(JpushConst.MSG_HAS_RESPONSE,"1");
					extrasMap.put(JpushConst.MSG_PUSH_TIME,String.valueOf(System.currentTimeMillis()));

			    	int rentWay = evaluateOrderEntity.getRentWay();
			    	String fid = evaluateOrderEntity.getHouseFid();
			    	if(rentWay == 1){
			    		fid = evaluateOrderEntity.getRoomFid();
			    	}
			    	extrasMap.put("orderSn",evaluateOrderEntity.getOrderSn());
                    extrasMap.put("fid",fid);
                    extrasMap.put("rentWay", ValueUtil.getStrValue(rentWay));
					jpushConfig.setExtrasMap(extrasMap);
					JpushUtils.sendPushOne(evaluateOrderEntity.getRatedUserUid(), jpushConfig, this.getParamsMap());
					//去掉 短信 和张鹏鹏已确认
					MessageUtils.sendSms(new SmsMessage(this.getMobile(), this.getContent()), this.getParamsMap());
				}
				
			}*/
		}
		
	}

}
