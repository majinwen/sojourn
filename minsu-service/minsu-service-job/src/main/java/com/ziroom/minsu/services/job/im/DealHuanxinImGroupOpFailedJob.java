/**
 * @FileName: DealHuanxinImGroupOpFailedLog.java
 * @Package com.ziroom.minsu.services.job.im
 * 
 * @author yd
 * @created 2017年8月10日 下午4:14:56
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.job.im;

import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asura.framework.base.context.ApplicationContext;
import com.asura.framework.quartz.job.AsuraJob;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.message.api.inner.HuanxinImManagerService;

/**
 * <p>处理环信 失败记录</p>
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
public class DealHuanxinImGroupOpFailedJob extends AsuraJob{
	
	
    /**
     * 日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(DealHuanxinImGroupOpFailedJob.class);

	/* (non-Javadoc)
	 * @see com.asura.framework.quartz.job.AsuraJob#run(org.quartz.JobExecutionContext)
	 * 
	 */
	@Override
	public void run(JobExecutionContext context) {
		
		HuanxinImManagerService huanxinImManagerService = (HuanxinImManagerService) ApplicationContext.getContext().getBean("job.huanxinImManagerService");
		try {
			huanxinImManagerService.dealGroupOpfailed();
		} catch (Exception e) {
			LogUtil.error(logger, "【处理环信操作失败记录异常】e={}", e);
		}
	}

}
