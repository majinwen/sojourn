/**
 * @FileName: ReductionActivityRemindJob.java
 * @Package com.ziroom.minsu.services.job.cms
 * 
 * @author lusp
 * @created 2017年6月7日 上午9:23:24
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.job.cms;

import com.asura.framework.base.context.ApplicationContext;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.quartz.job.AsuraJob;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.cms.ActivityRemindLogEntity;
import com.ziroom.minsu.services.basedata.api.inner.SmsTemplateService;
import com.ziroom.minsu.services.basedata.dto.SmsRequest;
import com.ziroom.minsu.services.cms.api.inner.ActivityRemindLogService;
import com.ziroom.minsu.services.common.thread.pool.SendThreadPool;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.customer.api.inner.CustomerMsgManagerService;
import com.ziroom.minsu.services.customer.entity.CustomerVo;
import com.ziroom.minsu.services.order.api.inner.HouseCommonService;
import com.ziroom.minsu.valenum.msg.MessageTemplateCodeEnum;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>首单立减活动提醒使用Job</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author lusp
 * @since 1.0
 * @version 1.0
 */
public class ReductionActivityRemindJob extends AsuraJob{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ReductionActivityRemindJob.class);

	/* (non-Javadoc)
	 * @see com.asura.framework.quartz.job.AsuraJob#run(org.quartz.JobExecutionContext)
	 */
	@Override
	public void run(JobExecutionContext arg0) {
		LogUtil.info(LOGGER, "首单立减活动提醒使用job执行开始");
		final long start = System.currentTimeMillis();
		try {
            Thread task = new Thread(){
                @Override
                public void run() {

					HouseCommonService houseCommonService = (HouseCommonService) ApplicationContext.getContext().getBean("order.houseCommonService");

					ActivityRemindLogService activityRemindLogService = (ActivityRemindLogService)ApplicationContext.getContext().getBean("cms.activityRemindLogService");

                    SmsTemplateService smsTemplateService=(SmsTemplateService) ApplicationContext.getContext().getBean("basedata.smsTemplateService");

					CustomerMsgManagerService customerMsgManagerService = (CustomerMsgManagerService) ApplicationContext.getContext().getBean("customer.customerMsgManagerService");

					//先分页查询需要发送提醒信息的用户信息
					int limit = 100;
					Map<String, Object> paramMap = new HashMap<>();
					paramMap.put("page", 1);
					paramMap.put("limit", limit);
					String tenantInfoJson = activityRemindLogService.queryRemindUidInfoByPage(JsonEntityTransform.Object2Json(paramMap));
					DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(tenantInfoJson);
					if(dto.getCode() == DataTransferObject.ERROR){
						LogUtil.error(LOGGER, "首单立减活动通知job中获取已经触发通知的新用户信息失败, error:{}", dto.getMsg());
						return;
					}
					Long count = SOAResParseUtil.getLongFromDataByKey(tenantInfoJson,"count");
					LogUtil.info(LOGGER, "总条数:{}", count);
					if (Check.NuNObj(count) || count == 0) {
                        return;
                    }
					int pageAll = ValueUtil.getPage(count.intValue(), limit);
					for(int page = 1;page <= pageAll;page++){
						paramMap.put("page", page);
						tenantInfoJson = activityRemindLogService.queryRemindUidInfoByPage(JsonEntityTransform.Object2Json(paramMap));
						List<ActivityRemindLogEntity> list = new ArrayList<>();
						try {
							list = SOAResParseUtil.getListValueFromDataByKey(tenantInfoJson,"list",ActivityRemindLogEntity.class);
						} catch (SOAParseException e) {
							LogUtil.error(LOGGER, "首单立减活动通知job中获取已经触发通知的新用户信息失败,error:{}", e);
						}
						LogUtil.info(LOGGER,"首单立减活动通知job第 "+page+" 页房客信息数据：{}",tenantInfoJson);
						//循环发送系统消息和短信
						for(ActivityRemindLogEntity activityRemindLogEntity:list){

							//判断该用户现在是否满足首单立减条件
							String resultJson = houseCommonService.isNewUserByOrderForFirstPage(activityRemindLogEntity.getUid());
							DataTransferObject dtoTemp = JsonEntityTransform.json2DataTransferObject(resultJson);
							if(dtoTemp.getCode() == DataTransferObject.ERROR){
								LogUtil.error(LOGGER, "获取当前用户是否满足首单立减失败,uid:{},errorMsg:{}",activityRemindLogEntity.getUid(), dtoTemp.getMsg());
								continue;
							}
							Integer intFlag = SOAResParseUtil.getIntFromDataByKey(resultJson, "isNewUser");
							if(!intFlag.equals(0)){
								LogUtil.info(LOGGER,"当前用户已经不满足首单立减条件,uid:{}",activityRemindLogEntity.getUid());
								//从通知表（t_activity_remind_log）中逻辑删除该条记录
								activityRemindLogService.deleteActivityRemindLogByUid(JsonEntityTransform.Object2Json(activityRemindLogEntity));
								continue;
							}

							//开始发送系统消息和短信（发送系统消息先注掉）
//                            Map<String, String> jpushMap=new HashMap<>();
//            				JpushRequest jpushRequest = new JpushRequest();
//            				jpushRequest.setParamsMap(jpushMap);
//            				jpushRequest.setJpushPersonType(JpushPersonType.ONE.getCode());
//            				jpushRequest.setMessageType(MessageTypeEnum.MESSAGE.getCode());
////            				jpushRequest.setSmsCode(String.valueOf(MessageTemplateCodeEnum.CMS_FIRST_SINGLE_REDUCTION_REMIND_MSG.getCode()));
//            				jpushRequest.setTitle("首单立减活动提醒");
//            				jpushRequest.setUid(activityRemindLogEntity.getUid());
//            				//自定义消息
//            				Map<String, String> extrasMap = new HashMap<>();
//            				extrasMap.put(JpushConst.MSG_BODY_TYPE_KEY, JpushConst.MSG_BODY_TYPE_VALUE);
//            				extrasMap.put(JpushConst.MSG_SUB_TYPE_KEY, JpushConst.MSG_SUB_TYPE_VALULE_4);
//            				extrasMap.put(JpushConst.MSG_HAS_RESPONSE,"1");
//            				extrasMap.put(JpushConst.MSG_TAG_TYPE,JpushConst.MSG_TARGET_TENANT);
//            				extrasMap.put(JpushConst.MSG_PUSH_TIME,String.valueOf(System.currentTimeMillis()));
//            				jpushRequest.setExtrasMap(extrasMap);
//            				smsTemplateService.jpushByCode(JsonEntityTransform.Object2Json(jpushRequest));

							String uid = activityRemindLogEntity.getUid();
							String mobileNo = activityRemindLogEntity.getMobileNo();
							//发短信
							if(Check.NuNStr(mobileNo)&&!Check.NuNStr(uid)){
								//当前记录用户手机号为空，再去查询一次用户信息，获取用户手机号
								String customerResultJson = customerMsgManagerService.getCutomerVo(uid);
								DataTransferObject customerDto = JsonEntityTransform.json2DataTransferObject(customerResultJson);
								if(customerDto.getCode()==DataTransferObject.ERROR){
									LogUtil.error(LOGGER,"调用customerMsgManagerService.getCutomerVo(uid)失败, uid={}",uid);
								}else{
									CustomerVo customerVo = null;
									try {
										customerVo = SOAResParseUtil.getValueFromDataByKey(customerResultJson,"customerVo",CustomerVo.class);
									} catch (SOAParseException e) {
										LogUtil.error(LOGGER, "SOAResParseUtil 转换异常",e);
									}
									if(!Check.NuNObj(customerVo)){
										mobileNo = customerVo.getShowMobile();
									}
								}
								if(Check.NuNStr(mobileNo)){
									LogUtil.info(LOGGER,"当前用户手机号为空,uid:{}",activityRemindLogEntity.getUid());
									//从通知表（t_activity_remind_log）中逻辑删除该条记录
									activityRemindLogService.deleteActivityRemindLogByUid(JsonEntityTransform.Object2Json(activityRemindLogEntity));
									continue;
								}
							}
            				String msgCode = ValueUtil.getStrValue(MessageTemplateCodeEnum.CMS_FIRST_SINGLE_REDUCTION_REMIND_SMS.getCode());
            				SmsRequest smsRequest  = new SmsRequest();
            				Map<String, String> paramsMap = new HashMap<String, String>();
            				smsRequest.setParamsMap(paramsMap);
            				smsRequest.setMobile(mobileNo);
            				smsRequest.setSmsCode(msgCode);
            				LogUtil.info(LOGGER, "发短信参数{}",JsonEntityTransform.Object2Json(smsRequest));
            				smsTemplateService.sendSmsByCode(JsonEntityTransform.Object2Json(smsRequest));

            				//发送之后修改表（t_activity_remind_log）中的send_times + 1,run_time 增加一个月
							activityRemindLogService.updateSendTimesRunTimeByUid(JsonEntityTransform.Object2Json(activityRemindLogEntity));

						}
					}
					long end = System.currentTimeMillis();
					LogUtil.info(LOGGER, "ReductionActivityRemindJob 结束....耗时time={}",(end-start));
                }
            };
            SendThreadPool.execute(task);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "今夜特价设置提醒job异常e:{}", e);
		}
	}
}
