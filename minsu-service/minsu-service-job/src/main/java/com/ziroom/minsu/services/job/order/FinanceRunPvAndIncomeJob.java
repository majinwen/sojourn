package com.ziroom.minsu.services.job.order;

import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asura.framework.base.context.ApplicationContext;
import com.asura.framework.quartz.job.AsuraJob;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.order.api.inner.OrderTaskFinanceService;

/**
 * <p>
 * 执行收入表、付款单表打款
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
public class FinanceRunPvAndIncomeJob extends AsuraJob {

	/**
	 * 日志对象
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(FinanceRunPvAndIncomeJob.class);

	/**
	 * 2小时一次
	 */
	@Override
	public void run(JobExecutionContext jobExecutionContext) {
        LogUtil.info(LOGGER, "FinanceRunPvAndIncomeJob 开始执行.....");
		// 0 */2 * * * ?
		try {
			OrderTaskFinanceService orderTaskFinanceService = (OrderTaskFinanceService) ApplicationContext.getContext().getBean("job.orderTaskFinanceService");
			orderTaskFinanceService.taskRunFinance();
            LogUtil.info(LOGGER, "FinanceRunPvAndIncomeJob 执行完成");
		} catch (Exception e) {
			LogUtil.error(LOGGER, "e:{}", e);
		}

	}

}
