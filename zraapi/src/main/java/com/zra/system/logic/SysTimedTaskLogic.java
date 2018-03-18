package com.zra.system.logic;

import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import com.zra.system.entity.QuartzSetting;
import com.zra.system.entity.TimerTaskConstant;
import com.zra.system.service.SysTimedTaskService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 定时任务逻辑类
 *
 * @author tianxf9 2016年5月24日
 */
@Component
public class SysTimedTaskLogic {
    private static final Logger LOGGER = LoggerFactoryProxy.getLogger("SysTimedTaskLogic");
    @Autowired
    private SysTimedTaskService taskService;

    public boolean canRunTask(String taskName, boolean updateTime) {
        QuartzSetting quartzSetting = taskService.getTaskEntityByName(taskName);
        if (quartzSetting == null) {
            LOGGER.info("[定时任务异常]sys_timed_task中未找到名字为:{}的定时任务！！！", taskName);
            return false;
        }
        if (quartzSetting.getValid() != TimerTaskConstant.TIMER_VALID) {
            return false;
        }
        if (quartzSetting.getIsRun() == TimerTaskConstant.TIMER_RUN) {
            if (updateTime) {
                //如果不可以运行，判断上次运行的时间，与当前时间，间隔超过配置小时，就再次运行，防止因错误导致的。
                Date lastRunTime = quartzSetting.getLastRunTime();
                Date currentTime = new Date();
                long lTime = lastRunTime.getTime();
                long cTime = currentTime.getTime();
                long hour = (cTime - lTime) / (1000L * 60L * 60L);
                //当前时间与上次运行时间相关timerJobSetting.getHours() 小时，则重新运行
                if (hour >= quartzSetting.getHours()) {
                    LOGGER.info("{}定时任务，由于上次执行时间过长，本次强制执行！！！", taskName);
                    taskService.updateLastRunTime(taskName);
                    return true;
                }
            } else {
                return false;
            }

        }
        int count = taskService.runTaskEntity(taskName);
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }

    public int updateTaskRunFlagStop(String taskName) {
        return taskService.stopTaskEntity(TimerTaskConstant.TIMER_NOT_RUN, taskName);
    }

}
