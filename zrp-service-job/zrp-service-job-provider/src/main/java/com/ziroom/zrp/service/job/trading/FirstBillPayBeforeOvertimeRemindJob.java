package com.ziroom.zrp.service.job.trading;

import com.asura.framework.base.context.ApplicationContext;
import com.asura.framework.quartz.job.AsuraJob;
import com.asura.framework.utils.LogUtil;
import com.ziroom.zrp.service.trading.api.TradingTaskService;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>首笔账单距离超时n小时提醒定时任务</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lusp
 * @version 1.0
 * @Date Created in 2017年12月12日 11:39
 * @since 1.0
 */

public class FirstBillPayBeforeOvertimeRemindJob extends AsuraJob{

    public static final Logger LOGGER = LoggerFactory.getLogger(FirstBillPayBeforeOvertimeRemindJob.class);

    /**
     * @description: 首笔账单距离超时n小时提醒定时任务
     * @author: lusp
     * @date: 2017/12/12 上午 11:41
     * @params: jobExecutionContext
     * @return:
     */
    @Override
    public void run(JobExecutionContext jobExecutionContext) {
        try {
            LogUtil.info(LOGGER, "FirstBillPayBeforeOvertimeRemindJob 开始执行.....");
            TradingTaskService tradingTaskService = (TradingTaskService) ApplicationContext.getContext().getBean("job.tradingTaskService");
            tradingTaskService.firstBillPayBeforeOvertimeRemind();
            LogUtil.info(LOGGER, "FirstBillPayBeforeOvertimeRemindJob 执行完毕.....");
        } catch (Exception e) {
            LogUtil.error(LOGGER, "error:{}", e);
        }
    }
}
