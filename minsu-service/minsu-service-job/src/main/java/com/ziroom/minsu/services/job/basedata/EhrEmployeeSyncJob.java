package com.ziroom.minsu.services.job.basedata;

import com.asura.framework.base.context.ApplicationContext;
import com.asura.framework.quartz.job.AsuraJob;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.basedata.api.inner.EhrAccountSyncService;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * <p>Ehr员工数据同步</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author jixd
 * @since 1.0
 * @version 1.0
 */
public class EhrEmployeeSyncJob extends AsuraJob {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EhrEmployeeSyncJob.class);
	/**
	 * 每天  00:30 执行，第一次执行导入全部数据 时间从 2015-05-01 开始
	 * 以后每天拉取前一天数据，参数是去拉取前几天的数据。
	 */
	@Override
	public void run(JobExecutionContext jobExecutionContext) {
        // 0 0 3 * * ?

		try {
			LogUtil.info(LOGGER, "EhrEmployeeSyncJob 开始执行.....");
			EhrAccountSyncService ehrAccountSyncService = (EhrAccountSyncService) ApplicationContext.getContext().getBean("job.ehrAccountSyncService");
			ehrAccountSyncService.syncEmployeeTask(1);
			LogUtil.info(LOGGER, "EhrEmployeeSyncJob 执行完毕.....");
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
		}
	}

}
