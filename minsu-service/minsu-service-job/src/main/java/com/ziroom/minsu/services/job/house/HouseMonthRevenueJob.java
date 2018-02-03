package com.ziroom.minsu.services.job.house;

import com.asura.framework.base.context.ApplicationContext;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.quartz.job.AsuraJob;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.house.api.inner.HouseJobService;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * 
 * <p>房源月收益统计定时任务</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liujun
 * @since 1.0
 * @version 1.0
 */
public class HouseMonthRevenueJob extends AsuraJob {

    private static final Logger LOGGER = LoggerFactory.getLogger(HouseMonthRevenueJob.class);
    
    /**
     * 每月1号凌晨2点执行
     *
     * @author liujun
     * @param jobExecutionContext
     */
    @Override
    public void run(JobExecutionContext jobExecutionContext){
        LogUtil.info(LOGGER, "HouseMonthRevenueJob 开始执行.....");
        // 0 0 2 1 *
        try {
            String runDate = DateUtil.dateFormat(new Date(), "yyyy-MM-dd");
            //同步上月房源收益
            HouseJobService houseJobService = (HouseJobService) ApplicationContext.getContext().getBean("job.houseJobService");
            houseJobService.houseMonthRevenueStatistics(runDate);
            LogUtil.info(LOGGER, "HouseMonthRevenueJob执行结束");
        }catch (Exception e){
            LogUtil.error(LOGGER,"error:{}",e);
        }
    }

}
