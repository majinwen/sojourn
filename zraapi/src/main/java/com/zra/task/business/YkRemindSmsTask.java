package com.zra.task.business;

import com.zra.common.utils.TaskConcurrentControl;
import org.slf4j.Logger;
import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.zra.business.logic.BusinessLogic;

/**
 * <p>约看提醒短信定时任务</p>
 *
 * @author liujun
 * @version 1.0
 * @since 1.0
 */
@Component
public class YkRemindSmsTask {

    @Autowired
    private BusinessLogic businessLogic;

    private static final Logger LOGGER = LoggerFactoryProxy.getLogger(YkRemindSmsTask.class);

    @Scheduled(cron = "30 30 0/2 * * ?")
//    @Scheduled(cron = "30 0/5 * * * ?")
    public void sendYkRemindSms() {
        if (TaskConcurrentControl.getLock() != null && TaskConcurrentControl.getLock().equals("1")) {
            LOGGER.info("================开始发送约看提醒短信=============================");
            businessLogic.sendYkRemindSms();
            LOGGER.info("===============发送约看提醒短信结束=============================");
        } else {
            LOGGER.info("sendYkRemindSms未获得任务锁，任务取消");
        }

    }
}