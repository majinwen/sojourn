/**
 * @FileName: Trigger.java
 * @Package com.asura.amp.quartz.entity
 * 
 * @author zhangshaobin
 * @created 2012-12-11 下午5:04:06
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.amp.quartz.entity;

import java.util.Map;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>触发器实体</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zhangshaobin
 * @since 1.0
 * @version 1.0
 */
public class Trigger extends BaseEntity {
	
	private static final long serialVersionUID = -7514565709365627300L;

	/**
	 * 触发器名称
	 */
	private String name;
	
	/**
	 * 触发器分组
	 */
	private String group;
	
	/**
	 * 作业名称
	 */
	private String jobName;
	
	/**
	 * 作业分组
	 */
	private String jobGroup;
	
	
	/**
	 * 描述
	 */
	private String description;
	
	/**
	 * 下次执行时间
	 */
	private long nextFireTime;
	
	/**
	 * 前一次执行时间
	 */
	private long prevFireTime;
	
	/**
	 * 优先级
	 */
	private int priority;
	
	/**
	 * 状态
	 */
	private String state;
	
	/**
	 * 类型
	 */
	private String type;
	
	/**
	 * 启动时间
	 */
	private long startTime;
	
	/**
	 * 任务数据
	 */
	private Map<String, Object> jobData;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the group
	 */
	public String getGroup() {
		return group;
	}

	/**
	 * @param group the group to set
	 */
	public void setGroup(String group) {
		this.group = group;
	}

	/**
	 * @return the jobName
	 */
	public String getJobName() {
		return jobName;
	}

	/**
	 * @param jobName the jobName to set
	 */
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	/**
	 * @return the jobGroup
	 */
	public String getJobGroup() {
		return jobGroup;
	}

	/**
	 * @param jobGroup the jobGroup to set
	 */
	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the nextFireTime
	 */
	public long getNextFireTime() {
		return nextFireTime;
	}

	/**
	 * @param nextFireTime the nextFireTime to set
	 */
	public void setNextFireTime(long nextFireTime) {
		this.nextFireTime = nextFireTime;
	}

	/**
	 * @return the prevFireTime
	 */
	public long getPrevFireTime() {
		return prevFireTime;
	}

	/**
	 * @param prevFireTime the prevFireTime to set
	 */
	public void setPrevFireTime(long prevFireTime) {
		this.prevFireTime = prevFireTime;
	}

	/**
	 * @return the priority
	 */
	public int getPriority() {
		return priority;
	}

	/**
	 * @param priority the priority to set
	 */
	public void setPriority(int priority) {
		this.priority = priority;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the startTime
	 */
	public long getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the jobData
	 */
	public Map<String, Object> getJobData() {
		return jobData;
	}

	/**
	 * @param jobData the jobData to set
	 */
	public void setJobData(Map<String, Object> jobData) {
		this.jobData = jobData;
	}

}
