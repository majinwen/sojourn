package com.zra.report.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class ReportHouseTypeDto {

	/**
	 * 户型id
	 */
	@JsonIgnore
    private String houseTypeId;
	
	@ApiModelProperty("户型名称")
	@JsonProperty("houseTypeName")
    private String houseTypeName;
	
	@ApiModelProperty("报表商机数据")
	@JsonProperty("reportBo")
    private ReportBoDto reportBo;
	
	@ApiModelProperty("报表库存数据")
	@JsonProperty("reportStock")
    private ReportStockDto reportStock;
	
	@ApiModelProperty("报表续约数据")
	@JsonProperty("reportRenew")
    private ReportRenewDto reportRenew;
	
	@ApiModelProperty("报表回款数据")
	@JsonProperty("reportPayment")
    private ReportPaymentDto reportPayment;

	public String getHouseTypeId() {
		return houseTypeId;
	}

	public void setHouseTypeId(String houseTypeId) {
		this.houseTypeId = houseTypeId;
	}

	public String getHouseTypeName() {
		return houseTypeName;
	}

	public void setHouseTypeName(String houseTypeName) {
		if ("ALL".equals(houseTypeId)) {
			this.houseTypeName = "所有户型";
		} else {
			this.houseTypeName = houseTypeName;
		}
	}

	public ReportBoDto getReportBo() {
		return reportBo;
	}

	public void setReportBo(ReportBoDto reportBo) {
		this.reportBo = reportBo;
	}

	public ReportStockDto getReportStock() {
		return reportStock;
	}

	public void setReportStock(ReportStockDto reportStock) {
		this.reportStock = reportStock;
	}

	public ReportRenewDto getReportRenew() {
		return reportRenew;
	}

	public void setReportRenew(ReportRenewDto reportRenew) {
		this.reportRenew = reportRenew;
	}

	public ReportPaymentDto getReportPayment() {
		return reportPayment;
	}

	public void setReportPayment(ReportPaymentDto reportPayment) {
		this.reportPayment = reportPayment;
	}

	@Override
	public String toString() {
		return "ReportHouseTypeDto [houseTypeId=" + houseTypeId + ", houseTypeName=" + houseTypeName + ", reportBo="
				+ reportBo + ", reportStock=" + reportStock + ", reportRenew=" + reportRenew + ", reportPayment="
				+ reportPayment + "]";
	}
}
