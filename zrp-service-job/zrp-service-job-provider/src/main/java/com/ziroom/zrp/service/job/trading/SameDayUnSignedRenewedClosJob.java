package com.ziroom.zrp.service.job.trading;

import com.asura.framework.base.context.ApplicationContext;
import com.asura.framework.quartz.job.AsuraJob;
import com.asura.framework.utils.LogUtil;
import com.ziroom.zrp.service.trading.api.TradingTaskService;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>当天未签约和第二天未续约关闭合同定时任务</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lusp
 * @version 1.0
 * @Date Created in 2017年10月20日 21:39
 * @since 1.0
 */

public class SameDayUnSignedRenewedClosJob extends AsuraJob{

    public static final Logger LOGGER = LoggerFactory.getLogger(SameDayUnSignedRenewedClosJob.class);

    /**
     * @description: 1.关闭的当天未签约的合同定时任务
     *               2.关闭合同到期第二天未续约的合同（每天 00：05分执行）
     * @author: lusp
     * @date: 2017/10/21 上午 21:40
     * @params: jobExecutionContext
     * @return:
     */
    @Override
    public void run(JobExecutionContext jobExecutionContext) {
        try {
            LogUtil.info(LOGGER, "SameDayUnSignedRenewedClosJob 开始执行.....");
            TradingTaskService tradingTaskService = (TradingTaskService) ApplicationContext.getContext().getBean("job.tradingTaskService");
            tradingTaskService.sameDayUnsignedCloseContract();
            tradingTaskService.sameDayUnrenewedCloseContract();
            LogUtil.info(LOGGER, "SameDayUnSignedRenewedClosJob 执行完毕.....");
        } catch (Exception e) {
            LogUtil.error(LOGGER, "error:{}", e);
        }
    }
    
}
