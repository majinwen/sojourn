/**
 * @FileName: CheckInPreNoticeTenantJob.java
 * @Package com.ziroom.minsu.services.job.order
 * 
 * @author yd
 * @created 2017年9月8日 下午2:11:23
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
import com.ziroom.minsu.services.order.api.inner.OrderTaskOrderService;

/**
 * <p>入住前 通知房客</p>
 *   每天： 11点 通知房客  
 *   条件： 第二天带入住的订单
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
public class CheckInPreNoticeTenantJob extends AsuraJob{

	private static final Logger LOGGER = LoggerFactory.getLogger(CheckInPreNoticeTenantJob.class);

	/* (non-Javadoc)  0 0 11 * * ?
	 * @see com.asura.framework.quartz.job.AsuraJob#run(org.quartz.JobExecutionContext)
	 */
	@Override
	public void run(JobExecutionContext arg0) {

		try {
			LogUtil.info(LOGGER, "【定时任务:CheckInPreNoticeTenantJob】入住前通知房客开始..............");
			OrderTaskOrderService orderTaskOrderService = (OrderTaskOrderService) ApplicationContext.getContext().getBean("job.orderTaskOrderService");
			orderTaskOrderService.checkInPreNoticeTenant("");
			LogUtil.info(LOGGER, "【定时任务:CheckInPreNoticeTenantJob】入住前通知房客结束..............");
		} catch (Exception e) {
			LogUtil.error(LOGGER, "【定时任务:CheckInPreNoticeTenantJob】异常e={}",e);
		}
	
	}

}
