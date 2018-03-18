package com.ziroom.zrp.service.job.trading;

import com.asura.framework.base.context.ApplicationContext;
import com.asura.framework.quartz.job.AsuraJob;
import com.asura.framework.utils.LogUtil;
import com.ziroom.zrp.service.trading.api.FinReceiBillService;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>同步应收到财务</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuiyh9
 * @version 1.0
 * @Date Created in 2017年11月21日 12:00
 * @since 1.0
 */
public class RetrySynReceiveBillToFinJob extends AsuraJob {

    public static final Logger LOGGER = LoggerFactory.getLogger(RetrySynReceiveBillToFinJob.class);

    private static final String beanName = "job.finReceiBillService";

    /**
     * 每隔15分钟
     * @param jobExecutionContext
     */
    // 0 10/15 * * * ?
    @Override
    public void run(JobExecutionContext jobExecutionContext) {
        try {
            LogUtil.info(LOGGER, "RetrySynReceiveBillToFinJob 开始执行.....");
            FinReceiBillService finReceiBillService = (FinReceiBillService) ApplicationContext.getContext().getBean(beanName);
            finReceiBillService.asyncRetrySyncReceiptBillToFin();
            LogUtil.info(LOGGER, "RetrySynReceiveBillToFinJob 执行完毕.....");
        } catch (Exception e) {
            LogUtil.error(LOGGER, "error:{}", e);
        }
    }
}
