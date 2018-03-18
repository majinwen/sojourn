package com.zra.common.dto.quartz;

import java.util.Date;

/**
 * Author: wangxm113
 * CreateDate: 2017/5/4.
 */
public class QuartzDto {
    private Integer id;//'主键',
    private String jobName;//'任务名称',
    private String jobCommont;//'任务描述',
    private String jobGroup;//'任务组',
    private String jobClass;//'任务类名',
    private String triggerName;//'触发器名称',
    private String triggerGroup;//'触发器组',
    private String cronExpress;//'cron表达式',
    private Date lastRunTime;//'上次运行时间',
    private Integer isRun;//'是否正在运行，1：正在运行；0：停止运行',
    private Integer intervalSecond;//'上次任务与当前时间相差多久需要重新调整定时任务状态',
    private Integer isValid;//'开： 1  关： 0',
    private Integer isDel;//'是否删除，0：未删除；1：已删除',
    private String appName;//'应用的名字'
    private String creatorName;//'创建人',
    private Date createTime;//'创建时间',

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobCommont() {
        return jobCommont;
    }

    public void setJobCommont(String jobCommont) {
        this.jobCommont = jobCommont;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getJobClass() {
        return jobClass;
    }

    public void setJobClass(String jobClass) {
        this.jobClass = jobClass;
    }

    public String getTriggerName() {
        return triggerName;
    }

    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName;
    }

    public String getTriggerGroup() {
        return triggerGroup;
    }

    public void setTriggerGroup(String triggerGroup) {
        this.triggerGroup = triggerGroup;
    }

    public String getCronExpress() {
        return cronExpress;
    }

    public void setCronExpress(String cronExpress) {
        this.cronExpress = cronExpress;
    }

    public Date getLastRunTime() {
        return lastRunTime;
    }

    public void setLastRunTime(Date lastRunTime) {
        this.lastRunTime = lastRunTime;
    }

    public Integer getIsRun() {
        return isRun;
    }

    public void setIsRun(Integer isRun) {
        this.isRun = isRun;
    }

    public Integer getIntervalSecond() {
        return intervalSecond;
    }

    public void setIntervalSecond(Integer intervalSecond) {
        this.intervalSecond = intervalSecond;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
