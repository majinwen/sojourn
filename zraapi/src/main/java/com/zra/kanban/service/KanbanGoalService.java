package com.zra.kanban.service;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zra.common.dto.kanban.DBSJResultDto;
import com.zra.common.utils.KeyGenUtils;
import com.zra.kanban.dao.KanbanGoalMapper;
import com.zra.kanban.entity.KanbanGoal;

/**
 * Author: cuiyh9
 * CreateDate: 2016/12/20.
 */
@Component
public class KanbanGoalService {
    
    private static final Logger LOGGER = LoggerFactoryProxy.getLogger(KanbanGoalService.class);
    
    @Autowired
    private KanbanGoalMapper kanbanGoalMapper;
    
    public List<KanbanGoal> selectAllGoals(String cityId, String goalStartDate, Integer goalType) {
        return kanbanGoalMapper.selectAllGoals(cityId, goalStartDate, goalType);
    }
    
    public boolean saveOrUpdateGoals(List<KanbanGoal> goalList) {
        boolean flag = false;
        if (goalList == null || goalList.size() == 0) {
            return flag;
        }
        //我不用批处理。。。
        for (KanbanGoal goal : goalList) {
            
            if (goal.getGoalId() == null || goal.getGoalId().trim().length() == 0) {
                goal.setGoalId(KeyGenUtils.genKey());
                goal.setIsDel(0);
                kanbanGoalMapper.insert(goal);
            } else {
                kanbanGoalMapper.update(goal);
            }
        } 
        return true;
    }

    /**
     * 管家工作台商机商机管理，待约看
     *
     * @Author: wangxm113
     * @CreateDate: 2016-12-23
     */
    public List<DBSJResultDto> listDYK(String projectId, String zoId) {
        return kanbanGoalMapper.listDYK(projectId, zoId);
    }

    /**
     * 管家工作台商机商机管理，待带看
     *
     * @Author: wangxm113
     * @CreateDate: 2016-12-23
     */
    public List<DBSJResultDto> listDDK(String projectId, String zoId) {
        return kanbanGoalMapper.listDDK(projectId, zoId);
    }

    /**
     * 管家工作台商机商机管理，待回访
     *
     * @Author: wangxm113
     * @CreateDate: 2016-12-23
     */
    public List<DBSJResultDto> listDHF(String projectId, String zoId) {
        return  kanbanGoalMapper.listDHF(projectId, zoId);
    }
}
