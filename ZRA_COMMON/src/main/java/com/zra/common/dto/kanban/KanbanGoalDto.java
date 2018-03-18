package com.zra.common.dto.kanban;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel(value="")
public class KanbanGoalDto {

    /**
     * 业务ID
     * 表字段 : kanban_goal.goal_id
     * 
     */
    @ApiModelProperty(value="业务ID")
    private String goalId;
    

    /**
     * 项目ID
     * 表字段 : kanban_goal.project_Id
     * 
     */
    @ApiModelProperty(value="项目ID")
    private String projectId;
    
    @ApiModelProperty(value="项目名称")
    private String porjectName;


    /**
     * 目标时间段开始时间
     * 表字段 : kanban_goal.goal_start_date
     * 
     */
    @ApiModelProperty(value="目标时间段开始时间")
    private String goalStartDate;

    /**
     * 目标时间段结束时间
     * 表字段 : kanban_goal.goal_end_date
     * 
     */
    @ApiModelProperty(value="目标时间段结束时间")
    private String goalEndDate;

    /**
     * 目标类型.2:月报;3:季报;4年报
     * 表字段 : kanban_goal.goal_type
     * 
     */
    @ApiModelProperty(value="目标类型.2:月报;3:季报;4年报")
    private Integer goalType;

    /**
     * 出租率
     * 表字段 : kanban_goal.rental_rate
     * 
     */
    @ApiModelProperty(value="出租率")
    private String rentalRate;

    /**
     * 出租周期
     * 表字段 : kanban_goal.rental_cycle
     * 
     */
    @ApiModelProperty(value="出租周期")
    private String rentalCycle;

    /**
     * 回款率
     * 表字段 : kanban_goal.voucher_rate
     * 
     */
    @ApiModelProperty(value="回款率")
    private String voucherRate;

    /**
     * 约看及时跟进率
     * 表字段 : kanban_goal.yuekan_gj_rate
     * 
     */
    @ApiModelProperty(value="约看及时跟进率")
    private String yuekanGjRate;


    /**
     * 
     * 表字段 : kanban_goal.cityid
     * 
     */
    @ApiModelProperty(value="")
    private String cityid;

    /**
     * 创建人
     * 表字段 : kanban_goal.creater_id
     * 
     */
    @ApiModelProperty(value="创建人")
    private String createrId;


    /**
     * 修改人
     * 表字段 : kanban_goal.updater_id
     * 
     */
    @ApiModelProperty(value="修改人")
    private String updaterId;

    
    public KanbanGoalDto() {
        
    }

    
    
    public KanbanGoalDto(String goalId, String projectId, String porjectName, String goalStartDate,
            String goalEndDate, Integer goalType, String rentalRate, String rentalCycle, String voucherRate,
            String yuekanGjRate, String cityid, String createrId,String updaterId) {
        super();
        this.goalId = goalId;
        this.projectId = projectId;
        this.porjectName = porjectName;
        this.goalStartDate = goalStartDate;
        this.goalEndDate = goalEndDate;
        this.goalType = goalType;
        this.rentalRate = rentalRate;
        this.rentalCycle = rentalCycle;
        this.voucherRate = voucherRate;
        this.yuekanGjRate = yuekanGjRate;
        this.cityid = cityid;
        this.createrId = createrId;
        this.updaterId = updaterId;
    }




    public String getGoalId() {
        return goalId;
    }


    public void setGoalId(String goalId) {
        this.goalId = goalId;
    }


    public String getProjectId() {
        return projectId;
    }


    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }


    public String getPorjectName() {
        return porjectName;
    }


    public void setPorjectName(String porjectName) {
        this.porjectName = porjectName;
    }


    public String getGoalStartDate() {
        return goalStartDate;
    }


    public void setGoalStartDate(String goalStartDate) {
        this.goalStartDate = goalStartDate;
    }


    public String getGoalEndDate() {
        return goalEndDate;
    }


    public void setGoalEndDate(String goalEndDate) {
        this.goalEndDate = goalEndDate;
    }


    public Integer getGoalType() {
        return goalType;
    }


    public void setGoalType(Integer goalType) {
        this.goalType = goalType;
    }


    public String getRentalRate() {
        return rentalRate;
    }


    public void setRentalRate(String rentalRate) {
        this.rentalRate = rentalRate;
    }


    public String getRentalCycle() {
        return rentalCycle;
    }


    public void setRentalCycle(String rentalCycle) {
        this.rentalCycle = rentalCycle;
    }


    public String getVoucherRate() {
        return voucherRate;
    }


    public void setVoucherRate(String voucherRate) {
        this.voucherRate = voucherRate;
    }


    public String getYuekanGjRate() {
        return yuekanGjRate;
    }


    public void setYuekanGjRate(String yuekanGjRate) {
        this.yuekanGjRate = yuekanGjRate;
    }


    public String getCityid() {
        return cityid;
    }


    public void setCityid(String cityid) {
        this.cityid = cityid;
    }


    public String getCreaterId() {
        return createrId;
    }


    public void setCreaterId(String createrId) {
        this.createrId = createrId;
    }


    public String getUpdaterId() {
        return updaterId;
    }


    public void setUpdaterId(String updaterId) {
        this.updaterId = updaterId;
    }

}