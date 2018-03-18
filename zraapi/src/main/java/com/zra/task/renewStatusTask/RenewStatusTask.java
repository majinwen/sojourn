package com.zra.task.renewStatusTask;

import java.util.Calendar;
import java.util.Date;

import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.zra.common.utils.TaskConcurrentControl;
import com.zra.house.logic.RoomInfoLogic;

/**
 * 续约定时任务
 */
@Component
public class RenewStatusTask {
    
    @Autowired
    private RoomInfoLogic roomInfoLogic;
    
    private static Logger LOGGER = LoggerFactoryProxy.getLogger(RenewStatusTask.class);



    /**
     * 将房间状态计整为可预订.
     *  每两个小时跑一次定时任务
     */
    @Scheduled(cron = "0 0 0/2 * * ?")
    public void modifyPreStatus() {
        LOGGER.info("begin run  RenewStatusTask modifyPreStatus");
        if (TaskConcurrentControl.getLock() != null && TaskConcurrentControl.getLock().equals("1")) {
            LOGGER.info("begin run  RenewStatusTask modifyPreStatus 任务锁");
            roomInfoLogic.modifyPreStatus();
            LOGGER.info("修改为可预订完成");
        } else {
            LOGGER.info("modifyPreStatus未获得任务锁，任务取消");
        }
    }
}



