/**
 * @FileName: SysHuanxinImMesJob.java
 * @Package com.ziroom.minsu.services.job.im
 * 
 * @author yd
 * @created 2016年9月19日 下午3:48:49
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.job.im;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asura.framework.base.context.ApplicationContext;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.quartz.job.AsuraJob;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.basedata.api.inner.SmsTemplateService;
import com.ziroom.minsu.services.basedata.dto.SmsRequest;
import com.ziroom.minsu.services.common.conf.EnumMinsuConfig;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.common.utils.ZkUtil;
import com.ziroom.minsu.services.house.api.inner.HouseJobService;
import com.ziroom.minsu.services.message.api.inner.HuanxinImRecordService;
import com.ziroom.minsu.valenum.msg.MessageTemplateCodeEnum;

/**
 * <p>
 * 
 * 定时任务同步环信IM消息  每天晚上12点，同步前一天的数据，即：同步前一天晚上到今天晚上12点的数据（例如：2016/09/09 24:00:00   到 2016/09/10 24:00:00）
 * 环信地址：http://docs.easemob.com/im/100serverintegration/30chatlog
 * 接口限流说明：同一个 APP 每分钟最多可调用10次，超过的部分会返回429或503错误。所以在调用程序中，如果碰到了这样的错误，需要稍微暂停一下并且重试。如果该限流控制不满足需求，请联系商务经理开放更高的权限。（一次最多返回1000条）
 * 算法：
 * 1. 获取环信token （保存当前redis中，失效时间6天，redis失效后去环信获取）
 * 2. 由于 msg_id 在环信返回中是唯一的，故为主键，并且入库时，以此值，校验重复，重复插入直接忽略
 * 3. 以当前时间往前推hours小时（25小时 这个时间做成可配置的，比定时任务时间长1个小时即可  例如：定时任务24小时，hours就是25，影响：理论上多取1个小时数据，好处：能保证数据不丢失）
 * 4. 对于接口调用次数限制问题处理：让接口去调用，出现429或503，让当前线程睡30s
 * 5. 接口循环去调用，直到接口获取完所以数据停止
 *
 * @author yd
 * 
 * @created 2016年9月10日 下午2:55:21
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
public class SysHuanxinImMesJob extends AsuraJob {


	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(SysHuanxinImMesJob.class);

	/**
	 * 从当前时间往前，同步的时间（单位小时 默认25小时）
	 */
	private final static String hours = "25";

	/**
	 * 同步异常，当前线程睡的秒数（单位 s 默认10s）
	 */
	private final static String sleepScends = "10";
	/**
	 * 每天晚上23:59:59点开始执行
	 * 
	 * 59 59 23 * * ? 
	 */
	@Override
	public void run(JobExecutionContext arg0) {

		SmsTemplateService smsTemplat = (SmsTemplateService)ApplicationContext.getContext().getBean("basedata.smsTemplateService");

		String msgCode = ValueUtil.getStrValue(MessageTemplateCodeEnum.JOB_NOTICE.getCode());
		Long t1 = 0L;
		Long t2 = 0L;
		try {
			HuanxinImRecordService huanxinImRecordService = (HuanxinImRecordService) ApplicationContext.getContext().getBean("job.huanxinImRecordService");
			 t1 = System.currentTimeMillis();
			 LogUtil.info(logger, "环信IM同步开始hours={},sleepScends={}", SysHuanxinImMesJob.hours,SysHuanxinImMesJob.sleepScends);
			 huanxinImRecordService.sysHuanxinImMes(SysHuanxinImMesJob.hours, SysHuanxinImMesJob.sleepScends);
			 t2 = System.currentTimeMillis();
			LogUtil.info(logger, "IM:同步环信IM聊天记录结束，用时t2-t1={}ms", t2-t1);
		} catch (Exception e) {
			LogUtil.error(logger, "同步环信聊天记录异常e={}", e);
		}

		/*try {
			SmsRequest smsRequest  = new SmsRequest();
			Map<String, String> paramsMap = new HashMap<String, String>();
			paramsMap.put("{1}", "同步环信IM聊天记录,用时"+((t2-t1)/1000)+"s");
			smsRequest.setParamsMap(paramsMap);
			smsRequest.setMobile(ZkUtil.getZkSysValue(EnumMinsuConfig.minsu_mobileList.getType(),EnumMinsuConfig.minsu_mobileList.getCode()));
			smsRequest.setSmsCode(msgCode);
			smsTemplat.sendSmsByCode(JsonEntityTransform.Object2Json(smsRequest));
		}catch (Exception e){
			LogUtil.error(logger,"SysHuanxinImMesJob:定时任务发送短信失败：e={}",e);
		}*/


	}

}
