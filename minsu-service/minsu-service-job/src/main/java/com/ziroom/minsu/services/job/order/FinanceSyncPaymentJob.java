package com.ziroom.minsu.services.job.order;

import com.asura.framework.base.context.ApplicationContext;
import com.asura.framework.quartz.job.AsuraJob;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.order.api.inner.OrderTaskSyncService;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>同步收款单</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author liyingjie on 2016/5/4.
 * @version 1.0
 * @since 1.0
 */
public class FinanceSyncPaymentJob extends AsuraJob {
	/**
	 * 日志对象
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(FinanceSyncPaymentJob.class);
	/**
     * 每个小时一次，同步收款单 数据，给财务系统
     * @author liyingjie
     * @param jobExecutionContext
     */
    @Override
    public void run(JobExecutionContext jobExecutionContext){
        LogUtil.info(LOGGER, "FinanceSyncPaymentJob 开始执行.....");
        // 0 0/60 * * * ?
        try {
            OrderTaskSyncService orderTaskSyncService = (OrderTaskSyncService) ApplicationContext.getContext().getBean("job.orderTaskSyncService");
            orderTaskSyncService.syncPaymentData();
            LogUtil.info(LOGGER, "FinanceSyncPaymentJob 执行结束");
        }catch (Exception e){
            LogUtil.error(LOGGER, "e:{}", e);
        }

    }

}
