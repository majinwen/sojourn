package com.zra.report.job;

import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import com.zra.report.logic.ReportOverviewLogic;
import com.zra.system.logic.QuartzSettingJob;

public class DailyReportOverviewJob extends QuartzSettingJob {
    
    private static final Logger LOGGER = LoggerFactoryProxy.getLogger(DailyReportOverviewJob.class);

    @Autowired
    private ReportOverviewLogic reportOverviewLogic;
    @Override
    public Map<String, String> doSomething() {
        try {
            reportOverviewLogic.dailyReportJob();
        } catch (Exception e) {
            LOGGER.error("自如寓业务每日总览任务执行失败", e);
        }
        return null;
    }

}
