/**
 * @FileName: HouseFollowStatsJob.java
 * @Package com.ziroom.minsu.services.job.house
 * 
 * @author bushujie
 * @created 2017年2月23日 下午2:48:38
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.job.house;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.commons.lang3.time.DateUtils;
import org.codehaus.jackson.type.TypeReference;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asura.framework.base.context.ApplicationContext;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.quartz.job.AsuraJob;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.services.basedata.api.inner.CityTemplateService;
import com.ziroom.minsu.services.basedata.api.inner.SmsTemplateService;
import com.ziroom.minsu.services.basedata.dto.SmsRequest;
import com.ziroom.minsu.services.cms.api.inner.ShortChainMapService;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.customer.api.inner.CustomerInfoService;
import com.ziroom.minsu.services.house.api.inner.HouseJobService;
import com.ziroom.minsu.services.house.constant.HouseConstant;
import com.ziroom.minsu.valenum.houseaudit.HouseAuditEnum005;
import com.ziroom.minsu.valenum.msg.MessageTemplateCodeEnum;

/**
 * <p>房源未审核通过，未生成客服跟进记录的统计（条件：未通过原因非房源品质原因且审核未通过超过12小时）</p>
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
public class HouseServiceFollowStatsJob extends AsuraJob{
	
	 private static final Logger LOGGER = LoggerFactory.getLogger(HouseServiceFollowStatsJob.class);
	/* (non-Javadoc)
	 * @see com.asura.framework.quartz.job.AsuraJob#run(org.quartz.JobExecutionContext)
	 */
	@SuppressWarnings({"unchecked" })
	@Override
	public void run(JobExecutionContext arg0) {
		ResourceBundle resource = ResourceBundle.getBundle("job");
		String OPEN_MINSU_APP_MYHOUSE=resource.getString("OPEN_MINSU_APP_MYHOUSE");
		LogUtil.info(LOGGER, "HouseServiceFollowStatsJob 开始执行.....");
		HouseJobService houseJobService = (HouseJobService) ApplicationContext.getContext().getBean("job.houseJobService");
		CityTemplateService cityTemplateService =  (CityTemplateService) ApplicationContext.getContext().getBean("basedata.cityTemplateService");
        CustomerInfoService customerInfoService = (CustomerInfoService) ApplicationContext.getContext().getBean("job.customerInfoService");
        SmsTemplateService smsTemplateService = (SmsTemplateService) ApplicationContext.getContext().getBean("job.smsTemplateService");
        ShortChainMapService shortChainMapService =  (ShortChainMapService)ApplicationContext.getContext().getBean("job.shortChainMapService");
        //查询客服多少小时后开始跟进
		String resultJson=cityTemplateService.getTextValue(null,HouseAuditEnum005.HouseAuditEnum001.getValue());
		String value=SOAResParseUtil.getStrFromDataByKey(resultJson,"textValue");
		Integer interval=HouseConstant.SERVICE_FOLLOW_WAIT_INT;
		if(!Check.NuNStr(value)){
			interval=Integer.valueOf(value);
		}
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("beforeDate",DateUtil.dateFormat(DateUtils.addHours(new Date(), -interval), "yyyy-MM-dd HH:mm:ss"));
		paramMap.put("startDate",DateUtil.dateFormat(DateUtils.addHours(new Date(), -(interval+1)), "yyyy-MM-dd HH:mm:ss"));
		String auditJson=houseJobService.houseNotAuditFollowStats(JsonEntityTransform.Object2Json(paramMap));
		try {
			Set<String>landlordUidSet= SOAResParseUtil.getValueFromDataByKey(auditJson, "landlordUidSet", Set.class);
			//获取短信跳转连接
			String shortLink = null;
			String result = shortChainMapService.getMinsuHomeJump();
			DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(result);
			if(resultDto.getCode()==DataTransferObject.SUCCESS){
					shortLink = resultDto.parseData("shortLink", new TypeReference<String>() {});
			}
					
			//房东发送短信和消息
			LogUtil.info(LOGGER, "要发短信的房东{}",JsonEntityTransform.Object2Json(landlordUidSet));
			for(String uid:landlordUidSet){
				String customerJson=customerInfoService.getCustomerInfoByUid(uid);
				CustomerBaseMsgEntity customer=SOAResParseUtil.getValueFromDataByKey(customerJson, "customerBase", CustomerBaseMsgEntity.class);
				//发短信
				String msgCode = ValueUtil.getStrValue(MessageTemplateCodeEnum.HOUSE_QA_AUDIT_FAIL_OVERTIME_LANDLORD_SMS.getCode());
				SmsRequest smsRequest  = new SmsRequest();
				Map<String, String> paramsMap = new HashMap<String, String>();
				paramsMap.put("{1}", shortLink); 
				smsRequest.setParamsMap(paramsMap);
				smsRequest.setMobile(customer.getCustomerMobile());
				smsRequest.setSmsCode(msgCode);
				LogUtil.info(LOGGER, "发短信参数{}",JsonEntityTransform.Object2Json(smsRequest));
				smsTemplateService.sendSmsByCode(JsonEntityTransform.Object2Json(smsRequest));
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER,"HouseServiceFollowStatsJob 发短信error:{}",e);
		}
		LogUtil.info(LOGGER, "HouseServiceFollowStatsJob执行结束");
	}
}
