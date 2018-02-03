package com.ziroom.minsu.services.job.order;

import com.asura.framework.base.context.ApplicationContext;
import com.asura.framework.quartz.job.AsuraJob;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.order.api.inner.OrderTaskOrderService;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>自动退房的定时任务</p>
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
public class CheckOutOrderJob extends AsuraJob {


    private static final Logger LOGGER = LoggerFactory.getLogger(CheckOutOrderJob.class);

    /**
     * 自动退房
     *
     * @author afi
     * @param jobExecutionContext
     */
    @Override
    public void run(JobExecutionContext jobExecutionContext) {

        LogUtil.info(LOGGER, "CheckOutOrderJob 开始执行.....");
        // 0 0/30 * * * ?
        try {
            //自动退房
            OrderTaskOrderService orderTaskOrderService = (OrderTaskOrderService) ApplicationContext.getContext().getBean("job.orderTaskOrderService");
            orderTaskOrderService.taskCheckOut();
            LogUtil.info(LOGGER, "CheckOutOrderJob 执行结束");
        }catch (Exception e){
            LogUtil.error(LOGGER, "e:{}", e);
        }


    }
}
