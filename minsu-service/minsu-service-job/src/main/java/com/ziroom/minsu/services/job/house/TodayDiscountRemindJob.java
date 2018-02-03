/**
 * @FileName: TodayDiscountRemindJob.java
 * @Package com.ziroom.minsu.services.job.house
 * 
 * @author bushujie
 * @created 2017年5月16日 下午2:23:24
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.job.house;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asura.framework.base.context.ApplicationContext;
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.quartz.job.AsuraJob;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.entity.house.HouseBaseMsgEntity;
import com.ziroom.minsu.services.basedata.api.inner.SmsTemplateService;
import com.ziroom.minsu.services.basedata.dto.JpushRequest;
import com.ziroom.minsu.services.basedata.dto.SmsRequest;
import com.ziroom.minsu.services.common.constant.JpushConst;
import com.ziroom.minsu.services.common.jpush.base.MessageTypeEnum;
import com.ziroom.minsu.services.common.thread.pool.SendThreadPool;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.customer.api.inner.CustomerInfoService;
import com.ziroom.minsu.services.house.api.inner.HouseTonightDiscountService;
import com.ziroom.minsu.services.order.api.inner.HouseCommonService;
import com.ziroom.minsu.valenum.customer.JpushPersonType;
import com.ziroom.minsu.valenum.msg.MessageTemplateCodeEnum;


/**
 * <p>今夜特价设置提醒Job</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
public class TodayDiscountRemindJob extends AsuraJob{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TodayDiscountRemindJob.class);

	/* (non-Javadoc)
	 * @see com.asura.framework.quartz.job.AsuraJob#run(org.quartz.JobExecutionContext)
	 */
	@Override
	public void run(JobExecutionContext arg0) {
		try {
			LogUtil.info(LOGGER, "今夜特价设置提醒job执行开始");

            Thread task = new Thread(){
                @Override
                public void run() {
                    int limit = 150;
                    Map<String, Object> paramMap=new HashMap<>();
                    paramMap.put("page", 1);
                    paramMap.put("limit", limit);
                    String lockTime=DateUtil.dateFormat(new Date(), "yyyy-MM-dd")+" 00:00:00";
                    paramMap.put("lockTime", lockTime);
                    HouseCommonService houseCommonService= (HouseCommonService) ApplicationContext.getContext().getBean("order.houseCommonService");
                    String houseFidsJson=houseCommonService.getLockFidByLockTime(lockTime);
                    try {
						paramMap.put("houseFids", SOAResParseUtil.getListValueFromDataByKey(houseFidsJson, "houseFids", String.class));
						paramMap.put("roomFids", SOAResParseUtil.getListValueFromDataByKey(houseFidsJson, "roomFids", String.class));
					} catch (SOAParseException e) {
						LogUtil.error(LOGGER, "获取被锁定房源fid异常e:{}", e);
						e.printStackTrace();
					}
                    HouseTonightDiscountService houseTonightDiscountService=(HouseTonightDiscountService) ApplicationContext.getContext().getBean("house.houseTonightDiscountService");
                    String landlordUidJson=houseTonightDiscountService.findRemindLandlordUid(JsonEntityTransform.Object2Json(paramMap));
                    Long count=SOAResParseUtil.getLongFromDataByKey(landlordUidJson, "count");
                    LogUtil.info(LOGGER, ",条数:{}", count);
                    if (Check.NuNObj(count) || count == 0) {
                        return;
                    }
                    int pageAll = ValueUtil.getPage(count.intValue(), limit);
                    //得到customer服务
                    CustomerInfoService customerInfoService= (CustomerInfoService) ApplicationContext.getContext().getBean("customer.customerInfoService");
                    SmsTemplateService smsTemplateService=(SmsTemplateService) ApplicationContext.getContext().getBean("basedata.smsTemplateService");
            		ResourceBundle resource = ResourceBundle.getBundle("job");
            		String OPEN_MINSU_APP_MYHOUSE=resource.getString("OPEN_MINSU_APP_MYHOUSE");
                    for (int page = 1; page <= pageAll; page++) {
                    	paramMap.put("page", page);
                    	landlordUidJson=houseTonightDiscountService.findRemindLandlordUid(JsonEntityTransform.Object2Json(paramMap));
                    	List<HouseBaseMsgEntity> landlordUidList=new ArrayList<>();
						try {
							landlordUidList = SOAResParseUtil.getListValueFromDataByKey(landlordUidJson, "list", HouseBaseMsgEntity.class);
						} catch (SOAParseException e) {
							LogUtil.error(LOGGER, "分页获取uid异常e:{}", e);
							e.printStackTrace();
						}
                    	LogUtil.info(LOGGER,"今夜特价设置提醒job第一页房东uid数据：{}",landlordUidJson);
                    	//拼装uid
                    	List<String> uidList=new ArrayList<String>();
                    	for(HouseBaseMsgEntity uid:landlordUidList){
                    		uidList.add(uid.getLandlordUid());
                    	}
                    	//循环发短信消息
                    	String customerJson=customerInfoService.getCustomerListByUidList(JsonEntityTransform.Object2Json(uidList));
                    	List<CustomerBaseMsgEntity> customerList=new ArrayList<>();
                    	try {
							customerList=SOAResParseUtil.getListValueFromDataByKey(customerJson, "customerList", CustomerBaseMsgEntity.class);
						} catch (SOAParseException e) {
							LogUtil.error(LOGGER, "获取用户信息异常e:{}", e);
							e.printStackTrace();
						}
                    	for(CustomerBaseMsgEntity customer:customerList){
                    		Map<String, String> jpushMap=new HashMap<>();
            				JpushRequest jpushRequest = new JpushRequest();
            				jpushRequest.setParamsMap(jpushMap);
            				jpushRequest.setJpushPersonType(JpushPersonType.ONE.getCode());
            				jpushRequest.setMessageType(MessageTypeEnum.MESSAGE.getCode());
            				jpushRequest.setSmsCode(String.valueOf(MessageTemplateCodeEnum.HOUSE_TODAYDISCOUNT_LANDLORD_REMIND_MSG.getCode()));
            				jpushRequest.setTitle("您可以参加今夜特价啦");
            				jpushRequest.setUid(customer.getUid());
            				//自定义消息
            				Map<String, String> extrasMap = new HashMap<>();
            				extrasMap.put(JpushConst.MSG_BODY_TYPE_KEY, JpushConst.MSG_BODY_TYPE_VALUE);
            				extrasMap.put(JpushConst.MSG_SUB_TYPE_KEY, JpushConst.MSG_SUB_TYPE_VALULE_6);
            				extrasMap.put(JpushConst.MSG_HAS_RESPONSE,"1");
            				extrasMap.put(JpushConst.MSG_TAG_TYPE,JpushConst.MSG_TARGET_LAN);
            				extrasMap.put(JpushConst.MSG_PUSH_TIME,String.valueOf(System.currentTimeMillis()));
            				jpushRequest.setExtrasMap(extrasMap);
            				smsTemplateService.jpushByCode(JsonEntityTransform.Object2Json(jpushRequest));
            				//发短信
            				String msgCode = ValueUtil.getStrValue(MessageTemplateCodeEnum.HOUSE_TODAYDISCOUNT_LANDLORD_REMIND_SMS.getCode());
            				SmsRequest smsRequest  = new SmsRequest();
            				Map<String, String> paramsMap = new HashMap<String, String>();
            				paramsMap.put("{1}", OPEN_MINSU_APP_MYHOUSE); 
            				smsRequest.setParamsMap(paramsMap);
            				smsRequest.setMobile(customer.getCustomerMobile());
            				smsRequest.setSmsCode(msgCode);
            				LogUtil.info(LOGGER, "发短信参数{}",JsonEntityTransform.Object2Json(smsRequest));
            				smsTemplateService.sendSmsByCode(JsonEntityTransform.Object2Json(smsRequest));
                    	}
                    }
                }
            };
            SendThreadPool.execute(task);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "今夜特价设置提醒job异常e:{}", e);
		}
	}
}
