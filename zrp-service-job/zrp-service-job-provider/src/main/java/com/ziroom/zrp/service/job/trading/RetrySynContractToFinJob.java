package com.ziroom.zrp.service.job.trading;

import com.asura.framework.base.context.ApplicationContext;
import com.asura.framework.quartz.job.AsuraJob;
import com.asura.framework.utils.LogUtil;
import com.ziroom.zrp.service.trading.api.EnterpriseSigningService;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>同步合同信息到财务</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuiyh9
 * @version 1.0
 * @Date Created in 2017年11月21日 11:59
 * @since 1.0
 */
public class RetrySynContractToFinJob extends AsuraJob {

    public static final Logger LOGGER = LoggerFactory.getLogger(RetrySynContractToFinJob.class);

    private static final String beanName = "job.enterpriseSigningService";

    /**
     *
     * @param jobExecutionContext
     */
    // 0 0/15 * * * ?
    @Override
    public void run(JobExecutionContext jobExecutionContext) {
        try {
            LogUtil.info(LOGGER, "RetrySynContractToFinJob 开始执行.....");
            EnterpriseSigningService enterpriseSigningService = (EnterpriseSigningService) ApplicationContext.getContext().getBean(beanName);
            enterpriseSigningService.asyncRetrySyncEntSubContractToFin();
            LogUtil.info(LOGGER, "RetrySynContractToFinJob 执行完毕.....");
        } catch (Exception e) {
            LogUtil.error(LOGGER, "error:{}", e);
        }
    }
}
