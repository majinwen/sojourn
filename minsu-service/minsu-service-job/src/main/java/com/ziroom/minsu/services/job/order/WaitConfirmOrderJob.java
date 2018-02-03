package com.ziroom.minsu.services.job.order;

import java.util.List;


import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asura.framework.base.context.ApplicationContext;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.quartz.job.AsuraJob;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.basedata.api.inner.SmsTemplateService;
import com.ziroom.minsu.services.common.thread.pool.SendEmailThreadPool;
import com.ziroom.minsu.services.customer.api.inner.CustomerMsgManagerService;
import com.ziroom.minsu.services.order.api.inner.OrderTaskOrderService;
import com.ziroom.minsu.services.order.dto.SendOrderEmailRequest;

/**
 * <p>
 * 下单后订单，房东在【申请预定提醒时限】内未接受订单，发短信提醒 
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author lishaochuan on 2016年5月4日
 * @since 1.0
 * @version 1.0
 */
public class WaitConfirmOrderJob extends AsuraJob {

	/**
	 *
	 *
	 * 日志对象
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(WaitConfirmOrderJob.class);

	/**
	 * 5分钟执行1次
	 * 0 0/5 * * * ?
	 */
	@Override
	public void run(JobExecutionContext jobExecutionContext) {

        LogUtil.info(LOGGER, "WaitConfirmOrderJob 开始执行.....");
		try {
			OrderTaskOrderService orderTaskOrderService = (OrderTaskOrderService) ApplicationContext.getContext().getBean("job.orderTaskOrderService");
			CustomerMsgManagerService customerMsgManagerService=(CustomerMsgManagerService) ApplicationContext.getContext().getBean("customer.customerMsgManagerService");
			SmsTemplateService smsTemplateService=(SmsTemplateService) ApplicationContext.getContext().getBean("basedata.smsTemplateService");
			String resultJson=orderTaskOrderService.taskWatiConfimOrder();
			DataTransferObject dto=JsonEntityTransform.json2DataTransferObject(resultJson);
			if(dto.getCode()==DataTransferObject.SUCCESS){
				List<SendOrderEmailRequest> list=SOAResParseUtil.getListValueFromDataByKey(resultJson, "list", SendOrderEmailRequest.class);
				//调用线程发邮件
				if(!Check.NuNCollection(list)){
					for(SendOrderEmailRequest orderEmailRequest:list){
						SendEmailThreadPool.execute(new SendOrderEmailThread(orderEmailRequest,customerMsgManagerService,smsTemplateService));
					}
				}
			}
            LogUtil.info(LOGGER, "WaitConfirmOrderJob 执行结束");
		} catch (Exception e) {
			LogUtil.error(LOGGER, "e:{}", e);
		}

	}
}
