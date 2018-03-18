package com.zra.common.dto.marketing;

import java.util.List;

/**
 * 传递给400的分机用户表信息
 * @author tianxf9
 *
 */
public class PhonePreserveDto {
	
	//分机号
	private String extNum;
	//渠道名称
	private String name;
	
	private List<SchedulePersonDto> employees;
	
	private String cityId;
	
	private String userId;

	public String getExtNum() {
		return extNum;
	}

	public void setExtNum(String extNum) {
		this.extNum = extNum;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<SchedulePersonDto> getEmployees() {
		return employees;
	}

	public void setEmployees(List<SchedulePersonDto> employees) {
		this.employees = employees;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}
