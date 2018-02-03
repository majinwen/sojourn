package com.ziroom.minsu.services.job.order;

import com.asura.framework.base.context.ApplicationContext;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.quartz.job.AsuraJob;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.basedata.api.inner.SmsTemplateService;
import com.ziroom.minsu.services.basedata.dto.SmsRequest;
import com.ziroom.minsu.services.common.conf.EnumMinsuConfig;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.common.utils.ZkUtil;
import com.ziroom.minsu.services.order.api.inner.OrderTaskSyncService;
import com.ziroom.minsu.valenum.msg.MessageTemplateCodeEnum;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>每月同步一次收入</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author liyingjie on 2016/5/4.
 * @version 1.0
 * @since 1.0
 */
public class FinanceSyncIncomeJob extends AsuraJob {
	/**
	 * 日志对象
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(FinanceSyncIncomeJob.class);
	
	/**
     * 每月2号凌晨 取 上个月1号到下个月1号凌晨的所有数据，给财务系统
     * @author liyingjie
     * @param jobExecutionContext
     */
    @Override
    public void run(JobExecutionContext jobExecutionContext){
        LogUtil.info(LOGGER, "FinanceSyncIncomeJob 开始执行.....");

        SmsTemplateService smsTemplat = (SmsTemplateService)ApplicationContext.getContext().getBean("basedata.smsTemplateService");

        String msgCode = ValueUtil.getStrValue(MessageTemplateCodeEnum.JOB_NOTICE.getCode());
        //0 0 1 2 * ?
        try {
            OrderTaskSyncService orderTaskSyncService = (OrderTaskSyncService) ApplicationContext.getContext().getBean("job.orderTaskSyncService");
            orderTaskSyncService.syncIncomeData();
            LogUtil.info(LOGGER, "FinanceSyncIncomeJob 执行结束");
        }catch (Exception e){
            LogUtil.error(LOGGER, "e:{}", e);
            msgCode = ValueUtil.getStrValue(MessageTemplateCodeEnum.JOB_NOTICE_F.getCode());
        }

        try {
            SmsRequest smsRequest  = new SmsRequest();
            Map<String, String> paramsMap = new HashMap<String, String>();
            paramsMap.put("{1}", "同步收入到财务");
            smsRequest.setParamsMap(paramsMap);
            smsRequest.setMobile(ZkUtil.getZkSysValue(EnumMinsuConfig.minsu_mobileList.getType(),EnumMinsuConfig.minsu_mobileList.getCode()));
            smsRequest.setSmsCode(msgCode);
            smsTemplat.sendSmsByCode(JsonEntityTransform.Object2Json(smsRequest));
        }catch (Exception e){
            LogUtil.error(LOGGER,"定时任务发送短信失败：e：{}",e);
        }


    }

}
