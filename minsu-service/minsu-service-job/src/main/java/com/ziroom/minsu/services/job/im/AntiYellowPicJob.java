/**
 * @FileName: AntiYellowPicJob.java
 * @Package com.ziroom.minsu.services.job.im
 * 
 * @author loushuai
 * @created 2017年9月7日 下午8:35:58
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.job.im;

import java.util.List;

import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asura.framework.base.context.ApplicationContext;
import com.asura.framework.quartz.job.AsuraJob;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.message.api.inner.HuanxinImManagerService;

/**
 * <p>长租找找聊天-反黄定时任务</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author loushuai
 * @since 1.0
 * @version 1.0
 */
public class AntiYellowPicJob extends AsuraJob{

/**
     * 日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(DealHuanxinImGroupOpFailedJob.class);

    /**
	 * 半小时一次
	 * 0 0/30 * * * ?
	 */
	@Override
	public void run(JobExecutionContext arg0) {
		LogUtil.info(logger, "【反黄定时任务  AntiYellowPicJob】开始执行");
		HuanxinImManagerService huanxinImManagerService = (HuanxinImManagerService) ApplicationContext.getContext().getBean("job.huanxinImManagerService");
		try {
			huanxinImManagerService.dealImYellowPic();
		} catch (Exception e) {
			LogUtil.error(logger, "【处理黄色图片失败】e={}", e);
		}
	}
}
