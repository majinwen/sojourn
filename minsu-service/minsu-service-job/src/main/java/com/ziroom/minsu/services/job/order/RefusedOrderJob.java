package com.ziroom.minsu.services.job.order;

import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.asura.framework.base.context.ApplicationContext;
import com.asura.framework.quartz.job.AsuraJob;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.order.api.inner.OrderTaskOrderService;

/**
 * <p>
 * 下单后房东超时未确认订单，自动拒绝
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author lishaochuan on 2016年5月12日
 * @since 1.0
 * @version 1.0
 */
public class RefusedOrderJob extends AsuraJob {

	/**
	 * 日志对象
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(RefusedOrderJob.class);

	@Override
	public void run(JobExecutionContext context) {
		LogUtil.info(LOGGER, "RefusedOrderJob 开始执行.....");
		try {
			OrderTaskOrderService orderTaskOrderService = (OrderTaskOrderService) ApplicationContext.getContext().getBean("job.orderTaskOrderService");
			orderTaskOrderService.taskRefusedOrder();
            LogUtil.info(LOGGER, "RefusedOrderJob 执行完成");
		} catch (Exception e) {
			LogUtil.error(LOGGER, "e:{}", e);
		}
	}

}
