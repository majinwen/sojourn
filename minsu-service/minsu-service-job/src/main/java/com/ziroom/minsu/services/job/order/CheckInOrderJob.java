package com.ziroom.minsu.services.job.order;

import com.asura.framework.base.context.ApplicationContext;
import com.asura.framework.quartz.job.AsuraJob;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.order.api.inner.OrderTaskOrderService;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>订单入住的定时任务</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/5/4.
 * @version 1.0
 * @since 1.0
 */
public class CheckInOrderJob extends AsuraJob {


    private static final Logger LOGGER = LoggerFactory.getLogger(CheckInOrderJob.class);

    /**
     * 到入住时间，更新订单状态为已入住
     *
     * @author afi
     * @param jobExecutionContext
     */
    @Override
    public void run(JobExecutionContext jobExecutionContext) {
        LogUtil.info(LOGGER, "CheckInOrderJob 开始执行.....");
        // 0 0/30 * * * ?
        try {
            //到入住时间，更新订单状态为已入住
            OrderTaskOrderService orderTaskOrderService = (OrderTaskOrderService) ApplicationContext.getContext().getBean("job.orderTaskOrderService");
            orderTaskOrderService.updateOrderStatus();
            LogUtil.info(LOGGER, "CheckInOrderJob 执行结束");
        }catch (Exception e){
            LogUtil.error(LOGGER, "e:{}", e);
        }


    }
}
