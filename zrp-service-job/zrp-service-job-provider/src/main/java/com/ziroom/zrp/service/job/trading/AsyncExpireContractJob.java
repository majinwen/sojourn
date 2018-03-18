package com.ziroom.zrp.service.job.trading;

import com.asura.framework.base.context.ApplicationContext;
import com.asura.framework.quartz.job.AsuraJob;
import com.asura.framework.utils.LogUtil;
import com.ziroom.zrp.service.trading.api.TradingTaskService;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>同步过期合同定时任务</p>
 *  每天凌晨10分触发
 *  0 10 0 ? * *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年12月07日 10:59
 * @since 1.0
 */
public class AsyncExpireContractJob extends AsuraJob{
    public static final Logger LOGGER = LoggerFactory.getLogger(AsyncExpireContractJob.class);

    @Override
    public void run(JobExecutionContext jobExecutionContext) {
        LogUtil.info(LOGGER, "AsyncExpireContractJob 开始执行.....");
        TradingTaskService tradingTaskService = (TradingTaskService) ApplicationContext.getContext().getBean("job.tradingTaskService");
        tradingTaskService.syncExpireContractToFin();
        LogUtil.info(LOGGER, "AsyncExpireContractJob 执行完毕.....");
    }
}
