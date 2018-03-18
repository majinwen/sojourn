package com.ziroom.zrp.service.job.trading;

import com.asura.framework.base.context.ApplicationContext;
import com.asura.framework.quartz.job.AsuraJob;
import com.asura.framework.utils.LogUtil;
import com.ziroom.zrp.service.trading.api.TradingTaskService;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>作废智能电费应收账单定时任务</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author xiangbin
 * @version 1.0
 * @Date Created in 2018年02月08日 13:52
 * @since 1.0
 */
public class InvalidSmartReceiptBillJob extends AsuraJob {
    public static final Logger LOGGER = LoggerFactory.getLogger(InvalidSmartReceiptBillJob.class);
    /**
     * @description: 作废智能电费应收账单定时任务(20分钟执行一次)
     * @author: xiangb
     * @date: 2018年2月8日13:55:26
     * @params: jobExecutionContext
     * @return:
     */
    @Override
    public void run(JobExecutionContext jobExecutionContext) {
        try {
            LogUtil.info(LOGGER, "InvalidSmartReceiptBillJob 开始执行.....");
            TradingTaskService tradingTaskService = (TradingTaskService) ApplicationContext.getContext().getBean("job.tradingTaskService");
            tradingTaskService.invalidMeterFinReceiBill();
            LogUtil.info(LOGGER, "InvalidSmartReceiptBillJob 执行完毕.....");
        } catch (Exception e) {
            LogUtil.error(LOGGER, "error:{}", e);
        }
    }
}
