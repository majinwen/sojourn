package com.zra.task.business;

import com.zra.common.utils.TaskConcurrentControl;
import org.slf4j.Logger;
import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.zra.business.logic.BusinessLogic;

/**
 * @author wangws21 2016年8月23日
 * 带看完成后客户对本次带看进行评价<br>
 * 早9到晚10之间每隔2小时推送
 * 
 *  2016-9-30废弃   评价短信定时任务取消，改为处理带看后触发
 */
@Component
@Deprecated
public class EvaluateRemindSmsTask {

    @Autowired
    private BusinessLogic businessLogic;

    private static final Logger LOGGER = LoggerFactoryProxy.getLogger(EvaluateRemindSmsTask.class);

//    @Scheduled(cron = "0 0/5 * * * ?")
//    @Scheduled(cron = "0 0 9-22/2 * * ?")
    public void sendYkRemindSms() {
        if (TaskConcurrentControl.getLock() != null && TaskConcurrentControl.getLock().equals("1")) {
            LOGGER.info("================评价提醒定时任务 start==========================");
            businessLogic.sendEvaluateRemindSms();
            LOGGER.info("===============评价提醒定时任务 end=============================");
        } else {
            LOGGER.info("EvaluateRemindSmsTask未获得任务锁，任务取消");
        }

    }
}