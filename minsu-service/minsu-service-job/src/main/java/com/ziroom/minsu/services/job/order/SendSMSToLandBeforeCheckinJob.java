/**
 * @FileName: SendSMSToLandBeforeCheckinJob.java
 * @Package com.ziroom.minsu.services.job.order
 * 
 * @author loushuai
 * @created 2017年7月28日 下午4:17:27
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.job.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javassist.expr.NewArray;

import org.codehaus.jackson.type.TypeReference;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asura.framework.base.context.ApplicationContext;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.quartz.job.AsuraJob;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.order.OrderEntity;
import com.ziroom.minsu.services.basedata.api.inner.SmsTemplateService;
import com.ziroom.minsu.services.basedata.dto.SmsRequest;
import com.ziroom.minsu.services.common.dto.PageRequest;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.order.api.inner.OrderTaskOrderService;
import com.ziroom.minsu.services.order.entity.OrderHouseVo;
import com.ziroom.minsu.valenum.msg.MessageTemplateCodeEnum;

/**
 * <p>订单入住前给房东发短信</p>
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
public class SendSMSToLandBeforeCheckinJob extends AsuraJob{

	private static Logger LOGGER =  LoggerFactory.getLogger(SendSMSToLandBeforeCheckinJob.class);
	
	/**
	 *  定时任务执行时间  0 00 18 * * ?
     *  每天下午18:00分执行
	 *  将第二天将要入住的订单，给订单房东发送短信
     *  @author loushuai
     *  @param jobExecutionContext
	 */
	@Override
	public void run(JobExecutionContext jobExecutionContext) {
		LogUtil.info(LOGGER, "SendSMSToLandBeforeCheckinJob   开始执行.....");
		SmsTemplateService smsTemplate = (SmsTemplateService) ApplicationContext.getContext().getBean("basedata.smsTemplateService");
		//短信code 
		String msgCode = ValueUtil.getStrValue(MessageTemplateCodeEnum.ORDER_WILL_CHECKIN_SEND_MESSAGE_TO_LAN.getCode());
		LogUtil.info(LOGGER, "msgCode={}",msgCode);
		OrderTaskOrderService orderTaskOrderService = (OrderTaskOrderService) ApplicationContext.getContext().getBean("job.orderTaskOrderService");
		
		//获取明天将要入住的订单，给房东发短信
		PageRequest pageRequest = new PageRequest();
		Map<String, String> map = new HashMap<String, String>();
		int page = 1;
		for(; ; ){
			pageRequest.setPage(page);
			String waitCheckinListJson = orderTaskOrderService.getWaitCheckinList(JsonEntityTransform.Object2Json(pageRequest));
			LogUtil.info(LOGGER, "SendSMSToLandBeforeCheckinJob 查询结果result={}", waitCheckinListJson);
			DataTransferObject waitCheckinListDto = JsonEntityTransform.json2DataTransferObject(waitCheckinListJson);
			if(waitCheckinListDto.getCode() == DataTransferObject.ERROR){
				break;
			}
			List<OrderHouseVo> waitCheckinList = waitCheckinListDto.parseData("list", new TypeReference<List<OrderHouseVo>>() {});
			if(Check.NuNCollection(waitCheckinList)){
				break;
			}
			page++;
			for (OrderHouseVo orderHouseVo : waitCheckinList) {
				String houseName = orderHouseVo.getHouseName();
				if( Check.NuNStr(orderHouseVo.getLandlordTel()) || Check.NuNStr(houseName) || Check.NuNStr(orderHouseVo.getUserName())){
					continue;
				}
				if(Check.NuNObj(orderHouseVo.getStartTime()) || Check.NuNObj(orderHouseVo.getEndTime())){
					continue;
				}
				houseName='"'+houseName+'"';
				int datebetweenOfDayNum = DateUtil.getDatebetweenOfDayNum(orderHouseVo.getStartTime(), orderHouseVo.getEndTime());
				SmsRequest smsRequest = new SmsRequest();
				smsRequest.setMobile(orderHouseVo.getLandlordTel());
				smsRequest.setSmsCode(msgCode);
				map.put("{1}", orderHouseVo.getUserName());
				map.put("{2}", houseName);
				map.put("{3}", String.valueOf(datebetweenOfDayNum));
				smsRequest.setParamsMap(map);
				smsTemplate.sendSmsByCode(JsonEntityTransform.Object2Json(smsRequest));
			}
		}
		
	}

	
}
