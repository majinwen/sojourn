package com.ziroom.minsu.services.job.order;

import com.asura.framework.base.context.ApplicationContext;
import com.asura.framework.quartz.job.AsuraJob;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.order.api.inner.OrderTaskFinanceService;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * 查询已入住订单，生成付款单，收入记录
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
public class FinanceCreateJob extends AsuraJob {

	/**
	 * 日志对象
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(FinanceCreateJob.class);

	/**
	 * 3小时一次
	 */
	@Override
	public void run(JobExecutionContext jobExecutionContext) {

        LogUtil.info(LOGGER, "FinanceCreateJob 开始执行.....");
		// 0 */3 * * * ?
		try {
			OrderTaskFinanceService orderTaskFinanceService = (OrderTaskFinanceService) ApplicationContext.getContext().getBean("job.orderTaskFinanceService");
			orderTaskFinanceService.taskCreateFinance();
            LogUtil.info(LOGGER, "FinanceCreateJob 执行结束");
		} catch (Exception e) {
			LogUtil.error(LOGGER, "e:{}", e);
		}

	}
}
