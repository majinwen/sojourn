package com.ziroom.minsu.services.job.search;

import com.asura.framework.base.context.ApplicationContext;
import com.asura.framework.quartz.job.AsuraJob;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.search.api.inner.CmsSearchService;
import com.ziroom.minsu.services.search.api.inner.SearchService;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author zl
 * @version 1.0
 * @Date Created in 2017年08月02日 18:42
 * @since 1.0
 */
public class FreshCmsIndexJob extends AsuraJob {

    private static final Logger LOGGER = LoggerFactory.getLogger(FreshCmsIndexJob.class);


    @Override
    public void run(JobExecutionContext jobExecutionContext) {

        LogUtil.info(LOGGER, "FreshCmsIndexJob 开始执行.....");
        try {
            CmsSearchService cmsSearchService = (CmsSearchService) ApplicationContext.getContext().getBean("job.cmsSearchService");
            String resultJson = cmsSearchService.freshIndex();
            LogUtil.info(LOGGER, "FreshCmsIndexJob result={}",resultJson);
        }catch (Exception e){
            LogUtil.error(LOGGER,"FreshCmsIndexJob ERROR e={}",e);
        }



    }
}
