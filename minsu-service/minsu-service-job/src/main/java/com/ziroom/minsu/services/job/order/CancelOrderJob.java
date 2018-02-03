package com.ziroom.minsu.services.job.order;

import com.asura.framework.base.context.ApplicationContext;
import com.asura.framework.quartz.job.AsuraJob;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.order.api.inner.OrderTaskOrderService;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>取消超时订单</p>
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
public class CancelOrderJob extends AsuraJob {

    private static final Logger LOGGER = LoggerFactory.getLogger(CheckInOrderJob.class);


    /**
     * 超时30分钟，取消订单 （10分之执行一次）
     *
     * @author afi
     * @param jobExecutionContext
     */
    @Override
    public void run(JobExecutionContext jobExecutionContext) {
        LogUtil.info(LOGGER, "CancelOrderJob 开始执行.....");
        // 0 0/10 * * * ?
        try {
            //取消超时未支付的订单信息
            OrderTaskOrderService orderTaskOrderService = (OrderTaskOrderService) ApplicationContext.getContext().getBean("job.orderTaskOrderService");
            orderTaskOrderService.cancelOrderAndUnlockHouse();
            LogUtil.info(LOGGER, "CancelOrderJob 执行结束");
        }catch (Exception e){
            LogUtil.error(LOGGER, "e:{}", e);
        }
    }
}
