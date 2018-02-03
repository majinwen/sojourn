package com.ziroom.minsu.services.job.order;

import com.asura.framework.base.context.ApplicationContext;
import com.asura.framework.quartz.job.AsuraJob;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.order.api.inner.OrderTaskAccountService;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * 对账户充值失败的单子进行重新充值
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
public class AccountRepeatFillJob extends AsuraJob {

	/**
	 * 日志对象
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(AccountRepeatFillJob.class);


	/**
	 * 30分钟一次
	 */
	@Override
	public void run(JobExecutionContext jobExecutionContext) {
        LogUtil.info(LOGGER, "AccountRepeatFillJob 开始执行.....");
		// 0 0/30 * * * ?
		try {
			OrderTaskAccountService orderTaskAccountService = (OrderTaskAccountService) ApplicationContext.getContext().getBean("job.orderTaskAccountService");
			orderTaskAccountService.repeatAccountFillFailed();
            LogUtil.info(LOGGER, "AccountRepeatFillJob 执行结束");
		} catch (Exception e) {
			LogUtil.error(LOGGER, "e:{}", e);
		}

	}
}
