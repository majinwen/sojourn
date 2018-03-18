package com.zra.system.logic;

import com.zra.system.logic.SysTimedTaskLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 定时任务公共抽象类
 * 
 * @author tianxf9 2016-05-19
 */
@Component
public class QuartzSettingJobForOldTask extends QuartzSettingJob {

    @Override
    public Map<String, String> doSomething() {
        return null;
    }
}
