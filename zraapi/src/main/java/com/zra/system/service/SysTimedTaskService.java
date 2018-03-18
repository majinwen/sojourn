package com.zra.system.service;

import com.zra.system.dao.SysTimedTaskMapper;
import com.zra.system.entity.QuartzSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 定时任务服务类
 *
 * @author tianxf9 2016年5月24日
 */
@Service
public class SysTimedTaskService {

    @Autowired
    private SysTimedTaskMapper taskMapper;

    /**
     * 根据任务名称查询任务
     *
     * @param name
     * @return
     * @author tianxf9
     */
    public QuartzSetting getTaskEntityByName(String name) {
        return this.taskMapper.getTaskEntityByName(name);
    }

    /**
     * 停止运行自动任务
     *
     * @param taskEntity
     * @return
     * @author tianxf9
     */
    public int stopTaskEntity(Integer isRun, String name) {
        return this.taskMapper.stopTimedTaskEntity(isRun, name);
    }

    /**
     * 启动运行自动任务
     *
     * @param taskEntity
     * @return
     * @author tianxf9
     */
    public int runTaskEntity(String name) {
        return this.taskMapper.runTimedTaskEntity(name);
    }


    /**
     * 启动运行自动任务
     *
     * @param taskEntity
     * @return
     * @author tianxf9
     */
    public int updateLastRunTime(String name) {
        return this.taskMapper.updateLastRunTime(name);
    }

}
