package com.zra.kanban.logic;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zra.common.dto.kanban.DBSJQueryDto;
import com.zra.common.dto.kanban.DBSJResultDto;
import org.slf4j.Logger;
import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zra.common.dto.house.ProjectDto;
import com.zra.common.dto.kanban.KanbanGoalDto;
import com.zra.common.utils.DateTool;
import com.zra.common.utils.DateUtilFormate;
import com.zra.house.logic.ProjectLogic;
import com.zra.kanban.entity.KanbanGoal;
import com.zra.kanban.service.KanbanGoalService;

/**
 * Author: cuiyh9
 * CreateDate: 2016/12/20.
 */
@Component
public class KanbanGoalLogic {

    private static final Logger LOGGER = LoggerFactoryProxy.getLogger(KanbanGoalLogic.class);

    @Autowired
    private ProjectLogic projectLogic;

    @Autowired
    private KanbanGoalService kanbanGoalService;

    
    /**
     * 查询目标列表.
     * 自已拼是季度还是月度还是年的的，我不管了。。
     * @param cityId 城市ID 非必填
     * @param goalStartDate 目标期间开始时间,只取到天 非必填，但如果goalEndDate为非空时，goalStartDate也必须为非空
     * @param goalType 目标类型.2:月报;3:季报;4年报
     * @return 目标列表
     */
    public List<KanbanGoalDto> listGoals(String cityId, Date goalStartDate, Integer goalType) {
        String tempGoalStartDateStr  =  DateUtilFormate.formatDateToString(goalStartDate, DateUtilFormate.DATEFORMAT_4);
        List<KanbanGoalDto> result = new ArrayList<>();
        LOGGER.debug("listGoals:{},{},{}", cityId, tempGoalStartDateStr, goalType);
        //TODO 需要调整成加类型的
        List<ProjectDto> projectList = projectLogic.getAllProjectList(cityId);
        
        //项目列表为空时不需要将多余的项目拼接到目标列表中
        if (projectList == null || projectList.size() == 0) {
            return result;
        }
        
        Map<String,String> projectMap = new HashMap<>();
        for (ProjectDto pro : projectList) {
            projectMap.put(pro.getId(), pro.getName());
        }
        
        List<KanbanGoal> kanbanGoalList =  kanbanGoalService.selectAllGoals(cityId, tempGoalStartDateStr, goalType);
        
        if (kanbanGoalList == null) {
            kanbanGoalList = new ArrayList<>();
        }
        
        
//        int goalType = getGoalType(goalStartDate, goalEndDate);
        try {
            Date goalEndDate = DateTool.getEndDate(goalStartDate, goalType);
            for (ProjectDto project: projectList) {
                if (!isInList(kanbanGoalList, project.getId())) {
                    KanbanGoal goal = new KanbanGoal("", project.getId(), goalStartDate,goalEndDate, goalType,
                            null, null, null, null);
                    kanbanGoalList.add(goal);
                }
            }
            for (KanbanGoal goal : kanbanGoalList) {
                String projectName = projectMap.get(goal.getProjectId());
                String goalStartDateStr = DateTool.tranDateToYYYYMMDD(goal.getGoalStartDate());
                String goalEndDateStr = DateTool.tranDateToYYYYMMDD(goal.getGoalEndDate());
                String rentalRate = goal.getRentalRate() == null ? null : goal.getRentalRate().toString();
                String rentalCycle = goal.getRentalCycle() == null ? null : goal.getRentalCycle().toString();
                String voucherRate = goal.getVoucherRate() == null ? null : goal.getVoucherRate().toString();
                String yuekanGjRate = goal.getYuekanGjRate() == null ? null : goal.getYuekanGjRate().toString();
                
                KanbanGoalDto goalDto = new KanbanGoalDto(goal.getGoalId(), goal.getProjectId(), projectName, goalStartDateStr,
                        goalEndDateStr, goalType, rentalRate, rentalCycle, voucherRate,
                        yuekanGjRate, goal.getCityid(), goal.getCreaterId(), goal.getUpdaterId());
                result.add(goalDto);
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        
        
        return result;
    }
    
    
    /**
     * 根据日期类型返回目标类型.目标类型.2:月报;3:季报;4年报.
     * 计算方式，根据两个日期时间差计算，
     * 月报: 31>=days>=28
     * 季报: 92>=days>31
     * 年报: days>=365
     * @param startDate
     * @param endDate
     * @return 目标类型,-1为输入日期格式有误，无法返回
     */
    public int getGoalType(Date startDate, Date endDate) {
        int valid = -1;
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);
        
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);
        
        int startYear = startCal.get(Calendar.YEAR);
        int endYear = startCal.get(Calendar.YEAR);
        
        if (startYear !=  endYear) {
            return valid;
        }
        
        int days = endCal.get(Calendar.DAY_OF_YEAR) - startCal.get(Calendar.DAY_OF_YEAR);
        
        //TODO 你好意思这么写代码么? 好意思
        if (days <= 31) {
            return 2;
        } else if ((days > 31) && (days <= 92)) {
            return 3;
        } else if (days >= 300) {
            return 4;
        } else {
            return valid;
        }
        
    }

    /**
     * 设置目标
     * @return
     */
    public boolean setGoals(List<KanbanGoal> goalList) {
        if ((goalList == null) || (goalList.size() == 0)) {
            return false;
        }
        for (KanbanGoal goal: goalList) {
            if (goal.getGoalType() != null) {
                continue;
            }
            int goalType = getGoalType(goal.getGoalStartDate(), goal.getGoalEndDate());
            goal.setGoalType(goalType);;
        }
        
        return kanbanGoalService.saveOrUpdateGoals(goalList);
    }
    
    private boolean isInList(List<KanbanGoal> list, String projectId) {
        boolean flag = false;
        for (KanbanGoal goal: list) {
            if (goal.getProjectId().equals(projectId)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * 管家工作台待办商机，根据type来判断查询什么阶段的商机(0：待约看；1：待带看；2：待回访)
     *
     * @Author: wangxm113
     * @CreateDate: 2016-12-23
     */
    public List<DBSJResultDto> listDBSJ(DBSJQueryDto dto) {
        Integer type = dto.getType();
        if (type == 0) {
            return kanbanGoalService.listDYK(dto.getProjectId(), dto.getZoId());
        } else if (type == 1) {
            return kanbanGoalService.listDDK(dto.getProjectId(), dto.getZoId());
        } else if (type == 2) {
            return kanbanGoalService.listDHF(dto.getProjectId(), dto.getZoId());
        }
        return new ArrayList<>();
    }
}
