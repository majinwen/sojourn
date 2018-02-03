package com.ziroom.minsu.services.job.order;

import com.asura.framework.base.context.ApplicationContext;
import com.asura.framework.quartz.job.AsuraJob;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.order.api.inner.OrderTaskOrderService;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lishaochuan on 2016/10/20 20:38
 * @version 1.0
 * @since 1.0
 */
public class CheckOutRemindJob extends AsuraJob {

    private static Logger LOGGER = LoggerFactory.getLogger(CheckOutConfirmUserJob.class);

    @Override
    public void run(JobExecutionContext context) {
        LogUtil.info(LOGGER, "CheckOutRemindJob 开始执行.....");
        try {
            OrderTaskOrderService orderTaskOrderService = (OrderTaskOrderService) ApplicationContext.getContext().getBean("job.orderTaskOrderService");
            orderTaskOrderService.taskCheckOutRemind();
            LogUtil.info(LOGGER, "CheckOutRemindJob 执行完成");
        } catch (Exception e) {
            LogUtil.error(LOGGER, "e:{}", e);
        }
    }
}
