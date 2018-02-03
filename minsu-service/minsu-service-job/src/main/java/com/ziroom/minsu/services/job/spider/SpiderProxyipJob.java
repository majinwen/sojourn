package com.ziroom.minsu.services.job.spider;

import java.util.ResourceBundle;

import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asura.framework.quartz.job.AsuraJob;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.common.utils.CloseableHttpUtil;

/**
 * 
 * <p>爬取代理ip</p>
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
public class SpiderProxyipJob extends AsuraJob {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpiderProxyipJob.class);

    private static final String logPreStr = "爬取代理ip SpiderProxyipJob-";

    @Override
    public void run(JobExecutionContext jobExecutionContext) {
    	LogUtil.info(LOGGER, logPreStr + "开始执行.....");
    	spider();
    	LogUtil.info(LOGGER, logPreStr + "执行结束");
    }
    
    /**
     * 
     * 爬取代理ip
     *
     * @author zhangyl
     * @created 2017年7月6日 下午2:43:36
     *
     */
    private void spider(){
        try {
        	ResourceBundle resource = ResourceBundle.getBundle("job");
        	String SPIDER_SYSTEM_DOMAIN = resource.getString("SPIDER_SYSTEM_DOMAIN");
        	String url = SPIDER_SYSTEM_DOMAIN + "/proxyip/spider";
        	LogUtil.info(LOGGER, logPreStr + "url={}", url);
        	CloseableHttpUtil.sendPost(url);
        }catch (Exception e){
            LogUtil.error(LOGGER, logPreStr + "error e:{}", e);
        }
    }
    
    
}
