package com.zra.report.entity.dto;

import java.math.BigDecimal;

/**
 * 报表基础数据dto
 * @author tianxf9
 *
 */
public class BaseReportDto {
	
	//户型
	private String houseTypeId;
	
	//数量
	private int count;
	
	//签约周期
	private int conRentYear;
	
	//退租类型
	private String surType;
	
	//实际出房价
	private BigDecimal actualPrice;
	
	//房间的当前状态
	private String currentState;

	public String getHouseTypeId() {
		return houseTypeId;
	}

	public void setHouseTypeId(String houseTypeId) {
		this.houseTypeId = houseTypeId;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getConRentYear() {
		return conRentYear;
	}

	public void setConRentYear(int conRentYear) {
		this.conRentYear = conRentYear;
	}

	public String getSurType() {
		return surType;
	}

	public void setSurType(String surType) {
		this.surType = surType;
	}

	public BigDecimal getActualPrice() {
		return actualPrice;
	}

	public void setActualPrice(BigDecimal actualPrice) {
		this.actualPrice = actualPrice;
	}

	public String getCurrentState() {
		return currentState;
	}

	public void setCurrentState(String currentState) {
		this.currentState = currentState;
	}
	
}
