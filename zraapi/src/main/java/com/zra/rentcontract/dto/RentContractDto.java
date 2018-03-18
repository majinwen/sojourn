package com.zra.rentcontract.dto;

import java.util.Date;

/**
 * 合同信息
 * @author tianxf9
 *
 */
public class RentContractDto {
	
	//合同id
	private String contractId;
	//合同起租日期
	private Date conStartDate;
	//合同到期日期
	private Date conEndDate;
	//合同签约日期
	private Date conSignDate;
	//解约类型
	private String surType;
	//解约日期
	private Date releaseDate;
	//合同状态
	private String conStatusCode;
	
	public String getContractId() {
		return contractId;
	}
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	public Date getConStartDate() {
		return conStartDate;
	}
	public void setConStartDate(Date conStartDate) {
		this.conStartDate = conStartDate;
	}
	public Date getConEndDate() {
		return conEndDate;
	}
	public void setConEndDate(Date conEndDate) {
		this.conEndDate = conEndDate;
	}
	
	public String getSurType() {
		return surType;
	}
	public void setSurType(String surType) {
		this.surType = surType;
	}
	public Date getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	public Date getConSignDate() {
		return conSignDate;
	}
	public void setConSignDate(Date conSignDate) {
		this.conSignDate = conSignDate;
	}
	public String getConStatusCode() {
		return conStatusCode;
	}
	public void setConStatusCode(String conStatusCode) {
		this.conStatusCode = conStatusCode;
	}
}
