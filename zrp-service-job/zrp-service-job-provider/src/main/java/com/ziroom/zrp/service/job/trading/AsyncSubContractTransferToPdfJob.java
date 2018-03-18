package com.ziroom.zrp.service.job.trading;

import com.asura.framework.base.context.ApplicationContext;
import com.asura.framework.quartz.job.AsuraJob;
import com.asura.framework.utils.LogUtil;
import com.ziroom.zrp.service.trading.api.EnterpriseSigningService;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>异步生成合同文本</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuiyh9
 * @version 1.0
 * @Date Created in 2017年11月29日 12:03
 * @since 1.0
 */
public class AsyncSubContractTransferToPdfJob  extends AsuraJob {

    public static final Logger LOGGER = LoggerFactory.getLogger(AsyncSubContractTransferToPdfJob.class);

    private static final String beanName = "job.enterpriseSigningService";

    /**
     *  这个地方正常应该是调用trading服务，trading通过ZRAMS获取html文本.在trading服务中生成合同pdf
     *  但trading和zrams中的/apartment目录都是存在本地，导致测试人员无法在测试环境，准生产环境测试。
     *  改吧。改到在zrams服务中生成合同文本
     * @param jobExecutionContext
     */
    //0 0 0/1 * * ?
    @Override
    public void run(JobExecutionContext jobExecutionContext) {
        try {
            LogUtil.info(LOGGER, "asyncRetrySubContractTransferToPdf 开始执行.....");
            EnterpriseSigningService enterpriseSigningService = (EnterpriseSigningService) ApplicationContext.getContext().getBean(beanName);
            enterpriseSigningService.asyncRetrySubContractTransferToPdf();
            LogUtil.info(LOGGER, "asyncRetrySubContractTransferToPdf 执行完毕.....");
        } catch (Exception e) {
            LogUtil.error(LOGGER, "error:{}", e);
        }
    }
}
