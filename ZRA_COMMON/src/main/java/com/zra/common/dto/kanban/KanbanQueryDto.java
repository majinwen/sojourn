package com.zra.common.dto.kanban;

import java.util.List;

/**
 * 目标看板查询dto
 * @author tianxf9
 *
 */
public class KanbanQueryDto {
	
	//项目id
	private List<String> projectIds;
	
	//开始时间
	private String startDate;
	
	//结束时间
	private String endDate;
	
	//周期类型
	private Byte cycleType;
	
	//当前用户id
	private String userId;
	//当前登录城市id
	private String cityId;
	

	public List<String> getProjectId() {
		return projectIds;
	}

	public void setProjectId(List<String> projectId) {
		this.projectIds = projectId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Byte getCycleType() {
		return cycleType;
	}

	public void setCycleType(Byte cycleType) {
		this.cycleType = cycleType;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	
	
}
