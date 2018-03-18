package com.zra.common.dto.business;

/**
 * 报表查询dto
 * @author tianxf9
 *
 */
public class BoReportQueryParamDto {
	private String startDate;
	private String endDate;
	private String projectIdStr;
	private String projectZoId;
	private String userId;
	private String cityId;

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
	public String getProjectIdStr() {
		return projectIdStr;
	}
	public void setProjectIdStr(String projectIdStr) {
		this.projectIdStr = projectIdStr;
	}
	public String getProjectZoId() {
		return projectZoId;
	}
	public void setProjectZoId(String projectZoId) {
		this.projectZoId = projectZoId;
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
