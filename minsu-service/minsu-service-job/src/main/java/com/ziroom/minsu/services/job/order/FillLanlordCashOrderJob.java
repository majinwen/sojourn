/**
 * @FileName: FillLanlordCashOrderJob.java
 * @Package com.ziroom.minsu.services.job.order
 * 
 * @author yd
 * @created 2017年8月31日 下午6:09:25
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.job.order;

import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asura.framework.base.context.ApplicationContext;
import com.asura.framework.quartz.job.AsuraJob;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.order.api.inner.OrderTaskSyncService;

/**
 * <p>房东 进击活动 生产返现单</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
public class FillLanlordCashOrderJob extends AsuraJob {
	
	/**
	 * 日志对象
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(FillLanlordCashOrderJob.class);

	/* (non-Javadoc)
	 * @see com.asura.framework.quartz.job.AsuraJob#run(org.quartz.JobExecutionContext)
	 */
	@Override
	public void run(JobExecutionContext context) {
		OrderTaskSyncService orderTaskSyncService = (OrderTaskSyncService) ApplicationContext.getContext().getBean("job.orderTaskSyncService");
		try {
			String actSn = "8a9091a15e37ba52015e37ba528f0000";
			orderTaskSyncService.fillLanlordCashOrder(actSn);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "【房东进击计划生成返现单异常】e={}", e);
		}
		
		
	}

}
