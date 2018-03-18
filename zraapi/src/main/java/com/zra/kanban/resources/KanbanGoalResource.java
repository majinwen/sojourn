package com.zra.kanban.resources;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zra.common.dto.kanban.DBSJQueryDto;
import com.zra.common.dto.kanban.DBSJResultDto;
import com.zra.common.dto.kanban.KanbanGoalDto;
import com.zra.common.utils.DateUtilFormate;
import com.zra.common.utils.StrUtils;
import com.zra.kanban.entity.KanbanGoal;
import com.zra.kanban.logic.KanbanGoalLogic;
import com.zra.push.resources.PushResources;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * Author: cuiyh9
 * CreateDate: 2016/12/20.
 */
@Component
@Path("/kanbangoal")
@Api(value="/kanbangoal")
public class KanbanGoalResource {
    
    private static final Logger LOGGER = LoggerFactoryProxy.getLogger(PushResources.class);

    @Autowired
    private KanbanGoalLogic kanbanGoalLogic;
    
    @GET
    @Path("/list/v1")
    @Produces("application/json")
    @Consumes("application/json")
    @ApiOperation(value = "查询项目目标列表", notes = "查询项目目标列表", response = List.class)
    public List<KanbanGoalDto> listGoals(@ApiParam(name = "cityId", value = "城市Id") @QueryParam(value = "cityId") String cityId,
            @ApiParam(name = "startDate", value = "目标期间开始时间") @QueryParam(value = "startDate") String startDate,
            @ApiParam(name = "goalType", value = "类似") @QueryParam(value = "goalType") String goalType) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date goalStartDate = null;
        try {
            goalStartDate = df.parse(startDate);
        } catch (Exception e) {
            LOGGER.error("", e);
            return null;
        }

        return  kanbanGoalLogic.listGoals(cityId, goalStartDate, Integer.valueOf(goalType));
    }
    
    @POST
    @Path("/setGoals/v1")
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "保存或更新项目目标", notes = "保存或更新项目目标", response = Response.class)
    public Response setGoals(@ApiParam(name = "goals", value = "目标设置列表") @FormParam("goals") String goals,
            @ApiParam(name = "cityId", value = "城市Id") @FormParam("cityId") String cityId,
            @ApiParam(name = "employeId", value = "雇员ID") @FormParam("employeId") String employeId) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        List<KanbanGoal> goalList = new ArrayList<KanbanGoal>();
        List<KanbanGoalDto> kanbanGoalDtoList = gson.fromJson(goals, new TypeToken<List<KanbanGoalDto>>(){}.getType());
        if ((kanbanGoalDtoList != null) && (kanbanGoalDtoList.size() > 0)) {
            for (KanbanGoalDto kanbanGoalDto: kanbanGoalDtoList) {
                
                Date tempGoalStartDate = DateUtilFormate.formatStringToDate(kanbanGoalDto.getGoalStartDate(), DateUtilFormate.DATEFORMAT_4);
                Date tempGoalEndDate = DateUtilFormate.formatStringToDate(kanbanGoalDto.getGoalEndDate(), DateUtilFormate.DATEFORMAT_4);
                String tempRentalCycleStr = kanbanGoalDto.getRentalCycle();
                BigDecimal tempRentalCycleBig  = null;
                if (StrUtils.isNotNullOrBlank(tempRentalCycleStr)) {
                    tempRentalCycleBig =  new BigDecimal(tempRentalCycleStr);
                }
                BigDecimal rentalRateBig = null;
                if (StrUtils.isNotNullOrBlank(kanbanGoalDto.getRentalRate())) {
                    rentalRateBig =  new BigDecimal(kanbanGoalDto.getRentalRate());
                }
                BigDecimal voucherRateBig = null;
                if (StrUtils.isNotNullOrBlank(kanbanGoalDto.getVoucherRate())) {
                    voucherRateBig = new BigDecimal(kanbanGoalDto.getVoucherRate());
                }
                BigDecimal yuekanGjRateBig = null;
                if (StrUtils.isNotNullOrBlank(kanbanGoalDto.getYuekanGjRate())) {
                    yuekanGjRateBig = new BigDecimal(kanbanGoalDto.getYuekanGjRate());
                }
                KanbanGoal tempGoal = new KanbanGoal(kanbanGoalDto.getGoalId(), kanbanGoalDto.getProjectId(), 
                        tempGoalStartDate, tempGoalEndDate,
                        kanbanGoalDto.getGoalType(), rentalRateBig, tempRentalCycleBig,
                        voucherRateBig, yuekanGjRateBig);
                
                goalList.add(tempGoal);
            }
            
            for (KanbanGoal goal: goalList) {
                goal.setCityid(cityId);
                if (goal.getGoalId() == null || goal.getGoalId().trim().length() == 0) {
                    goal.setCreaterId(employeId);
                    goal.setCreatedTime(new Date());
                }
                goal.setIsDel(0);
                goal.setCityid(cityId);
                goal.setUpdaterId(employeId);
                goal.setUpdatedTime(new Date());

            }
        }
        boolean flag = kanbanGoalLogic.setGoals(goalList);
        if (flag) {
            return Response.ok("success").build();
        } else {
            return Response.ok("error").build();
        }

    }

    @POST
    @Path("/dbsj/v1")
    @Produces("application/json")
    @Consumes("application/json")
    @ApiOperation(value = "管家工作台待办商机", notes = "管家工作台待办商机", response = DBSJResultDto.class)
    public List<DBSJResultDto> listDBSJ(DBSJQueryDto dto) {
        List<DBSJResultDto> list = kanbanGoalLogic.listDBSJ(dto);
        return list;
    }
}
