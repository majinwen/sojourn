package com.ziroom.minsu.services.job.report;

import java.util.ResourceBundle;

import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asura.framework.quartz.job.AsuraJob;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.common.utils.CloseableHttpUtil;

/**
 * 
 * <p>统计城市日可出租天数</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liujun
 * @since 1.0
 * @version 1.0
 */
public class CityDailyMsgJob extends AsuraJob {

    private static final Logger LOGGER = LoggerFactory.getLogger(CityDailyMsgJob.class);

    @Override
    public void run(JobExecutionContext jobExecutionContext) {
        LogUtil.info(LOGGER, "CityDailyMsgJob 开始执行.....");
        // 30 0 * * * ?
        try {
        	ResourceBundle resource = ResourceBundle.getBundle("job");
        	String REPORT_SYSTEM_DOMAIN = resource.getString("REPORT_SYSTEM_DOMAIN");
        	String CITY_DAILY_MSG_TASK_URL = resource.getString("CITY_DAILY_MSG_TASK_URL");
        	StringBuilder sb = new StringBuilder(REPORT_SYSTEM_DOMAIN);
        	sb.append(CITY_DAILY_MSG_TASK_URL);
        	LogUtil.info(LOGGER, "CityDailyMsgJob url={}", sb.toString());
        	CloseableHttpUtil.sendPost(sb.toString());
            LogUtil.info(LOGGER, "CityDailyMsgJob 执行结束");
        }catch (Exception e){
            LogUtil.error(LOGGER, "CityDailyMsgJob error e:{}", e);
        }
    }
    
    public static void main(String[] args) {
		System.err.println();
	}
}
