package com.ziroom.minsu.services.job.search;

import com.asura.framework.base.context.ApplicationContext;
import com.asura.framework.quartz.job.AsuraJob;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.search.api.inner.SearchService;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>刷新房源信息</p>
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
public class FreshHouseIndexJob extends AsuraJob {

    private static final Logger LOGGER = LoggerFactory.getLogger(FreshHouseIndexJob.class);
    /**
     * 每天凌晨3点执行一次
     * @author afi
     * @param jobExecutionContext
     */
    @Override
    public void run(JobExecutionContext jobExecutionContext){
        LogUtil.info(LOGGER, "FreshHouseIndexJob 开始执行.....");
        // 0 0 3 * * ?
        try {
            /**
             * 刷新房源信息
             */
            SearchService searchService = (SearchService) ApplicationContext.getContext().getBean("job.searchService");
            String rst = searchService.creatAllIndex();
            LogUtil.info(LOGGER, "FreshHouseIndexJob rst is :{}",rst);
        }catch (Exception e){
            LogUtil.error(LOGGER,"e:{}",e);
        }

    }

}
