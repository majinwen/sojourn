package com.ziroom.minsu.services.job.house;

import com.asura.framework.base.context.ApplicationContext;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.quartz.job.AsuraJob;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.house.api.inner.HouseJobService;
import com.ziroom.minsu.services.order.api.inner.OrderFinanceService;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * <p>获取房源昨天的收益</p>
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
public class HouseDayRevenueJob extends AsuraJob {

    private static final Logger LOGGER = LoggerFactory.getLogger(HouseDayRevenueJob.class);
    /**
     * 每天凌晨一点执行一次
     * 0 4 * * * ?
     * @author afi
     * @param jobExecutionContext
     */
    @Override
    public void run(JobExecutionContext jobExecutionContext){

        LogUtil.info(LOGGER, "HouseDayRevenueJob 开始执行.....");

        // 0 0 4 * * ?
        try {
            String runDate = DateUtil.dateFormat(new Date(), "yyyy-MM-dd");
            //获取昨天的收益
            OrderFinanceService orderFinanceService = (OrderFinanceService) ApplicationContext.getContext().getBean("job.orderFinanceService");
            String resultJson = orderFinanceService.landlordDayRevenueList(runDate);
            DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
            if(dto.getCode() == DataTransferObject.SUCCESS){
                dto.putValue("runDate",runDate);
            }
            //将昨天的收益同步
            HouseJobService houseJobService = (HouseJobService) ApplicationContext.getContext().getBean("job.houseJobService");
            houseJobService.houseDayRevenueStatisticsByInfo(dto.toJsonString());
            LogUtil.info(LOGGER, "HouseDayRevenueJob 执行结束");
        }catch (Exception e){
            LogUtil.error(LOGGER,"error:{}",e);
        }

    }

}
