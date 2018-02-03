package com.ziroom.minsu.services.job.order;

import com.asura.framework.base.context.ApplicationContext;
import com.asura.framework.quartz.job.AsuraJob;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.order.api.inner.OrderTaskOrderService;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>查询一定时间内房东未确认额外消费的订单，自动确认</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lishaochuan on 2016年5月12日
 * @since 1.0
 * @version 1.0
 */
public class CheckOutConfirmLandlordJob extends AsuraJob {
	
	private static Logger LOGGER = LoggerFactory.getLogger(CheckOutConfirmLandlordJob.class);

	@Override
	public void run(JobExecutionContext context) {
		LogUtil.info(LOGGER, "CheckOutConfirmLandlordJob 开始执行.....");
		try {
			OrderTaskOrderService orderTaskOrderService = (OrderTaskOrderService) ApplicationContext.getContext().getBean("job.orderTaskOrderService");
			orderTaskOrderService.taskConfirmOtherFeeLandlord();
            LogUtil.info(LOGGER, "CheckOutConfirmLandlordJob 执行完成");
		} catch (Exception e) {
			LogUtil.error(LOGGER, "e:{}", e);
		}
	}

}
