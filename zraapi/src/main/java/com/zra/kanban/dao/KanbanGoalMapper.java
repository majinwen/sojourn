package com.zra.kanban.dao;

import java.util.Date;
import java.util.List;

import com.zra.common.dto.kanban.DBSJResultDto;
import com.zra.kanban.entity.KanbanGoal;
import org.apache.ibatis.annotations.Param;

public interface KanbanGoalMapper {

    /**
     *
     */
    int insert(KanbanGoal record);

    /**
     * 根据城市、目标开始时间，目标结束时间查询.
     * @param kanbanGoal
     * @return
     */
    List<KanbanGoal> selectAllGoals(@Param("cityId") String cityId, @Param("goalStartDate") String goalStartDate, @Param("goalType") Integer goalType);

    /**
     *
     */
    int update(KanbanGoal record);

    /**
     * 管家工作台商机商机管理，待约看
     *
     * @Author: wangxm113
     * @CreateDate: 2016-12-23
     */
    List<DBSJResultDto> listDYK(@Param("projectId") String projectId, @Param("zoId") String zoId);

    /**
     * 管家工作台商机商机管理，待带看
     *
     * @Author: wangxm113
     * @CreateDate: 2016-12-23
     */
    List<DBSJResultDto> listDDK(@Param("projectId") String projectId, @Param("zoId") String zoId);

    /**
     * 管家工作台商机商机管理，待回访
     *
     * @Author: wangxm113
     * @CreateDate: 2016-12-23
     */
    List<DBSJResultDto> listDHF(@Param("projectId")String projectId, @Param("zoId") String zoId);
}