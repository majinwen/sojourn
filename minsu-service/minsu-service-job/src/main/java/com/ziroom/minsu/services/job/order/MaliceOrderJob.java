package com.ziroom.minsu.services.job.order;

import com.asura.framework.base.context.ApplicationContext;
import com.asura.framework.quartz.job.AsuraJob;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.basedata.api.inner.ZkSysService;
import com.ziroom.minsu.services.common.conf.EnumMinsuConfig;
import com.ziroom.minsu.services.order.api.inner.OrderTaskOrderService;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>恶意订单</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2016/10/27.
 * @version 1.0
 * @since 1.0
 */
public class MaliceOrderJob extends AsuraJob {


    private static final Logger LOGGER = LoggerFactory.getLogger(CheckInOrderJob.class);

    /**
     * 到入住时间，更新订单状态为已入住
     *
     * @author afi
     * @param jobExecutionContext
     */
    @Override
    public void run(JobExecutionContext jobExecutionContext) {
        LogUtil.info(LOGGER, "MaliceOrderJob 开始执行.....");
        // 一个小时执行一次
        try {
            ZkSysService zksysService =  (ZkSysService)ApplicationContext.getContext().getBean("basedata.zkSysService");
            //恶意订单数量
            Integer maliceOrderNum = Integer.valueOf(zksysService.getZkSysValue(EnumMinsuConfig.minsu_maliceOrdeNum.getType(), EnumMinsuConfig.minsu_maliceOrdeNum.getCode()));
            //恶意下单发送短信
            OrderTaskOrderService orderTaskOrderService = (OrderTaskOrderService) ApplicationContext.getContext().getBean("job.orderTaskOrderService");
            orderTaskOrderService.taskMaliceOrder(maliceOrderNum);
            LogUtil.info(LOGGER, "MaliceOrderJob 执行结束");
        }catch (Exception e){
            LogUtil.error(LOGGER, "e:{}", e);
        }


    }
}
