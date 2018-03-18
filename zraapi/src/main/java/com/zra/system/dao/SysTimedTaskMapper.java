package com.zra.system.dao;

import com.zra.system.entity.QuartzSetting;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 自动任务持久化
 *
 * @author tianxf9 2016-05-24
 */
@Repository
public interface SysTimedTaskMapper {
    /**
     * 跟据任务名称查询任务
     *
     * @param name
     * @return
     * @author tianxf9
     */
    public QuartzSetting getTaskEntityByName(String name);

    /**
     * 更新运行状态任务为停止运行状态
     *
     * @param taskEntity
     * @return
     * @author tianxf9
     */
    public int stopTimedTaskEntity(@Param("isRun") Integer isRun, @Param("name") String name);

    /**
     * 更新停止运行状态的任务为可运行状态
     *
     * @param taskEntity
     * @return
     * @author tianxf9
     */
    public int runTimedTaskEntity(String name);

    /**
     * 更新上次运行时间
     *
     * @param taskEntity
     * @return
     */
    public int updateLastRunTime(String name);
}