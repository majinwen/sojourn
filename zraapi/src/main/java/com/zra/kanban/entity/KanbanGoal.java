package com.zra.kanban.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel(value="")
public class KanbanGoal {
    /**
     * 自增id
     * 表字段 : kanban_goal.id
     * 
     */
    @ApiModelProperty(value="自增id")
    private Integer id;

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

    /**
     * 目标时间段开始时间
     * 表字段 : kanban_goal.goal_start_date
     * 
     */
    @ApiModelProperty(value="目标时间段开始时间")
    private Date goalStartDate;

    /**
     * 目标时间段结束时间
     * 表字段 : kanban_goal.goal_end_date
     * 
     */
    @ApiModelProperty(value="目标时间段结束时间")
    private Date goalEndDate;

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
    private BigDecimal rentalRate;

    /**
     * 出租周期
     * 表字段 : kanban_goal.rental_cycle
     * 
     */
    @ApiModelProperty(value="出租周期")
    private BigDecimal rentalCycle;

    /**
     * 回款率
     * 表字段 : kanban_goal.voucher_rate
     * 
     */
    @ApiModelProperty(value="回款率")
    private BigDecimal voucherRate;

    /**
     * 约看及时跟进率
     * 表字段 : kanban_goal.yuekan_gj_rate
     * 
     */
    @ApiModelProperty(value="约看及时跟进率")
    private BigDecimal yuekanGjRate;

    /**
     * 是否删除.0:未删除;1删除
     * 表字段 : kanban_goal.is_del
     * 
     */
    @ApiModelProperty(value="是否删除.0:未删除;1删除")
    private Integer isDel;

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
     * 创建时间
     * 表字段 : kanban_goal.created_time
     * 
     */
    @ApiModelProperty(value="创建时间")
    private Date createdTime;

    /**
     * 修改人
     * 表字段 : kanban_goal.updater_id
     * 
     */
    @ApiModelProperty(value="修改人")
    private String updaterId;

    /**
     * 修改时间
     * 表字段 : kanban_goal.updated_time
     * 
     */
    @ApiModelProperty(value="修改时间")
    private Date updatedTime;
    
    
    public KanbanGoal() {
        
    }
    
    
    public KanbanGoal(String cityid, Date goalStartDate, Integer goalType) {
        super();
        this.cityid = cityid;
        this.goalStartDate = goalStartDate;
        this.goalType = goalType;
    }


    public KanbanGoal(String goalId, String projectId, Date goalStartDate, Date goalEndDate, Integer goalType,
            BigDecimal rentalRate, BigDecimal rentalCycle, BigDecimal voucherRate, BigDecimal yuekanGjRate) {
        super();
        this.goalId = goalId;
        this.projectId = projectId;
        this.goalStartDate = goalStartDate;
        this.goalEndDate = goalEndDate;
        this.goalType = goalType;
        this.rentalRate = rentalRate;
        this.rentalCycle = rentalCycle;
        this.voucherRate = voucherRate;
        this.yuekanGjRate = yuekanGjRate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGoalId() {
        return goalId;
    }

    public void setGoalId(String goalId) {
        this.goalId = goalId == null ? null : goalId.trim();
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    public Date getGoalStartDate() {
        return goalStartDate;
    }

    public void setGoalStartDate(Date goalStartDate) {
        this.goalStartDate = goalStartDate;
    }

    public Date getGoalEndDate() {
        return goalEndDate;
    }

    public void setGoalEndDate(Date goalEndDate) {
        this.goalEndDate = goalEndDate;
    }

    public Integer getGoalType() {
        return goalType;
    }

    public void setGoalType(Integer goalType) {
        this.goalType = goalType;
    }

    public BigDecimal getRentalRate() {
        return rentalRate;
    }

    public void setRentalRate(BigDecimal rentalRate) {
        this.rentalRate = rentalRate;
    }

    public BigDecimal getRentalCycle() {
        return rentalCycle;
    }

    public void setRentalCycle(BigDecimal rentalCycle) {
        this.rentalCycle = rentalCycle;
    }

    public BigDecimal getVoucherRate() {
        return voucherRate;
    }

    public void setVoucherRate(BigDecimal voucherRate) {
        this.voucherRate = voucherRate;
    }

    public BigDecimal getYuekanGjRate() {
        return yuekanGjRate;
    }

    public void setYuekanGjRate(BigDecimal yuekanGjRate) {
        this.yuekanGjRate = yuekanGjRate;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid == null ? null : cityid.trim();
    }

    public String getCreaterId() {
        return createrId;
    }

    public void setCreaterId(String createrId) {
        this.createrId = createrId == null ? null : createrId.trim();
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdaterId() {
        return updaterId;
    }

    public void setUpdaterId(String updaterId) {
        this.updaterId = updaterId == null ? null : updaterId.trim();
    }


    public Date getUpdatedTime() {
        return updatedTime;
    }


    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    
}