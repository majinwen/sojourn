package com.zra.task.fourooTelephoneTask;

import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import com.zra.common.utils.TaskConcurrentControl;
import com.zra.projectZO.ProjectZODto;
import com.zra.projectZO.logic.ProjectZOLogic;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * 400电话分配
 */
@Component
public class FourooTelephoneTask {

    @Autowired
    ProjectZOLogic logic;
    private static Logger LOGGER = LoggerFactoryProxy.getLogger(FourooTelephoneTask.class);

    
//    @Scheduled(cron = "0 20 13 * * ?")
    @Scheduled(cron = "0 03 03 * * ?")
    public void bindPhoneTask() {
       LOGGER.info("FourooTelephoneTask任务开始");
        if (TaskConcurrentControl.getLock() != null && TaskConcurrentControl.getLock().equals("1")) {
            List<ProjectZODto> list = logic.getProjectZOs();
            for (int i = 0; i < list.size(); i++) {
                logic.bindPhone(list.get(i));
            }
            LOGGER.info("400电话分配完成");
        } else {
            LOGGER.info("FourooTelephoneTask未获得任务锁，任务取消");
        }
    }
}
