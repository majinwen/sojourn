package com.ziroom.minsu.services.job.cms;

import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asura.framework.base.context.ApplicationContext;
import com.asura.framework.quartz.job.AsuraJob;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.cms.api.inner.JobActService;

/**
 * <p>优惠券活动结束job</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lishaochuan on 2016年6月16日
 * @since 1.0
 * @version 1.0
 */
public class CouponExpireJob extends AsuraJob {
	
	/**
	 * 日志对象
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(CouponExpireJob.class);


	/**
	 * 优惠券活动结束job
	 * 10分钟一次
	 * 0 0/10 * * * ?
	 */
	@Override
	public void run(JobExecutionContext jobExecutionContext) {
        LogUtil.info(LOGGER, "CouponExpireJob 开始执行.....");
		try {
			JobActService jobActService = (JobActService) ApplicationContext.getContext().getBean("job.jobActService");
			jobActService.couponExpireStatus();
            LogUtil.info(LOGGER, "CouponExpireJob 执行结束");
		} catch (Exception e) {
			LogUtil.error(LOGGER, "e:{}", e);
		}

	}

}
