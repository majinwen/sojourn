package com.zra.common.dto.business;

import java.util.List;

/**
 * 计算接听率的查询条件Dto
 * @author tianxf9
 *
 */
public class AnswerRateQueryParamDto {
	
	//分机号或者管家电话
	private List<String> numbers;
	//开始时间
	private String startDate;
	//结束时间
	private String endDate;
	//是否接听
	private boolean isSuccess;
	
	
	public List<String> getNumbers() {
		return numbers;
	}
	public void setNumbers(List<String> numbers) {
		this.numbers = numbers;
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
	public boolean isSuccess() {
		return isSuccess;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
}
