package com.zra.task.business;

import com.zra.common.utils.TaskConcurrentControl;
import org.slf4j.Logger;
import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.zra.business.logic.BusinessLogic;

/**
 * 更新商机处理进度
 *
 * @author tianxf9
 */
@Component
public class UpdateBOHandStateTask {

    @Autowired
    private BusinessLogic logic;
    private static final Logger LOGGER = LoggerFactoryProxy.getLogger(UpdateBOHandStateTask.class);

    @Scheduled(cron = "0/30 * * * * ?")
    public void updateBOHandState() {
        if (TaskConcurrentControl.getLock() != null && TaskConcurrentControl.getLock().equals("1")) {
            LOGGER.info("===================更新商机处理进度任务开始=========================");
            logic.updateBusinessHandState();
            LOGGER.info("===================更新商机处理进度任务结束==========================");
        } else {
            LOGGER.info("updateBOHandState未获得任务锁，任务取消");
        }
    }
}
