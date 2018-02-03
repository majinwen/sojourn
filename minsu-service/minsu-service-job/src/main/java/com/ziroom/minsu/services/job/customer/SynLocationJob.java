package com.ziroom.minsu.services.job.customer;

import com.asura.framework.base.context.ApplicationContext;
import com.asura.framework.quartz.job.AsuraJob;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.customer.api.inner.CustomerLocationService;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>定时任务 同步当前的位置</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/12/4.
 * @version 1.0
 * @since 1.0
 */
public class SynLocationJob extends AsuraJob {


    private static final Logger LOGGER = LoggerFactory.getLogger(SynLocationJob.class);

    /**
     * 同步当前的位置
     *
     * @author afi
     * @param jobExecutionContext
     */
    @Override
    public void run(JobExecutionContext jobExecutionContext) {
        LogUtil.info(LOGGER, "SynLocationJob 开始执行.....");
        // 0 0/30 * * * ?
        try {
            //到入住时间，更新订单状态为已入住
            CustomerLocationService customerLocationService = (CustomerLocationService) ApplicationContext.getContext().getBean("job.CustomerLocationService");
            customerLocationService.taskFillUserLocation();
            LogUtil.info(LOGGER, "SynLocationJob 执行结束");
        }catch (Exception e){
            LogUtil.error(LOGGER, "e:{}", e);
        }


    }
}
