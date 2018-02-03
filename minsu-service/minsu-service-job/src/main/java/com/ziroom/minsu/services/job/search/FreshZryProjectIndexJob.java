package com.ziroom.minsu.services.job.search;

import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asura.framework.base.context.ApplicationContext;
import com.asura.framework.quartz.job.AsuraJob;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.search.api.inner.ZrySearchService;

/**
 * 
 * <p>刷新自如驿索引</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zhangyl
 * @since 1.0
 * @version 1.0
 */
public class FreshZryProjectIndexJob extends AsuraJob {

    private static final Logger LOGGER = LoggerFactory.getLogger(FreshZryProjectIndexJob.class);


    @Override
    public void run(JobExecutionContext jobExecutionContext) {

        LogUtil.info(LOGGER, "FreshZryProjectIndexJob 开始执行.....");
        try {
        	ZrySearchService zrySearchService = (ZrySearchService) ApplicationContext.getContext().getBean("job.zrySearchService");
            String resultJson = zrySearchService.freshIndex("");
            LogUtil.info(LOGGER, "FreshZryProjectIndexJob result={}",resultJson);
        }catch (Exception e){
            LogUtil.error(LOGGER,"FreshZryProjectIndexJob ERROR e={}",e);
        }



    }
}
