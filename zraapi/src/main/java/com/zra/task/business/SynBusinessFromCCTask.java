package com.zra.task.business;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.zra.common.utils.TaskConcurrentControl;
import org.slf4j.Logger;
import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.zra.syncc.logic.ZraZrCallDetailLogic;

/**
 * 同步400商机
 *
 * @author tianxf9
 */
@Component
public class SynBusinessFromCCTask {

    @Autowired
    private ZraZrCallDetailLogic logic;

    private static final Logger LOGGER = LoggerFactoryProxy.getLogger(SynBusinessFromCCTask.class);

    @Scheduled(cron = "0/30 * * * * ?")
    public void synBusinessFromCCTask() {
        if (TaskConcurrentControl.getLock() != null && TaskConcurrentControl.getLock().equals("1")) {
            LOGGER.info("================开始同步400商机=============================");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date endDate = new Date();
            long startTime = endDate.getTime() - 1000 * 60*10;
            Date startDate = new Date(startTime);

            String startStr = sdf.format(startDate);
            String endStr = sdf.format(endDate);

            logic.synBusinessFromCC(startStr, endStr);

            LOGGER.info("===============同步400商机结束=============================");
        } else {
            LOGGER.info("synBusinessFromCCTask未获得任务锁，任务取消");
        }
    }

}
