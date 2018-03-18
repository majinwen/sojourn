package com.zra.report.entity.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class ReportDto {

	@ApiModelProperty("记录时间")
	@JsonProperty("recordDate")
    private String recordDate;
	
	@ApiModelProperty("报表户型列表")
	@JsonProperty("reportHouseTypeList")
    private List<ReportHouseTypeDto> reportHouseTypeList;

	public String getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(String recordDate) {
		this.recordDate = recordDate;
	}

	public List<ReportHouseTypeDto> getReportHouseTypeList() {
		return reportHouseTypeList;
	}

	public void setReportHouseTypeList(List<ReportHouseTypeDto> reportHouseTypeList) {
		this.reportHouseTypeList = reportHouseTypeList;
	}
}
