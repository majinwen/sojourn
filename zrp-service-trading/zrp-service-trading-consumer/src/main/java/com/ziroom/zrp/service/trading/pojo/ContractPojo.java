package com.ziroom.zrp.service.trading.pojo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>合同</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuigh6
 * @version 1.0
 * @Date Created in 2017年09月13日 18:52
 * @since 1.0
 */
public class ContractPojo {
	private String conType; // 合同类型 1-年租 2-月租 3-日租
	private Double roomSalesPrice; //出房价格
	private Double depositPrice;// 押金价格
	private String conCycleCode;// 1 月付 3 季付 6 半年付 12 年付 9 一次性付清
	private String roomId;// 房间标识
	private String projectId;// 项目标识
	private Integer rentType; //出租类型 1按房间2按床位
	private BigDecimal discount;// 折扣
	private Date conStartDate;// 合同开始日期
	private Date conEndDate;// 合同结束日期
	private Integer conRentYear; // 租期
	private String signType;// 签约类型 0:新签.1:续约.2:换租
	private Date firstSignStartDate;// 第一次签约的开始日期 用于续租
	private Date lastSignEndDate;// 最后一次签约的结束日期 用于续租
	private Integer customerType;// 客户类型
	private String uid;//客户uid
	private String contractId;// 合同标识
	private String preConType;// 续约合同前合同类型

	public String getPreConType() {
		return preConType;
	}

	public void setPreConType(String preConType) {
		this.preConType = preConType;
	}

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Integer getCustomerType() {
		return customerType;
	}

	public void setCustomerType(Integer customerType) {
		this.customerType = customerType;
	}

	public Double getDepositPrice() {
		return depositPrice;
	}

	public void setDepositPrice(Double depositPrice) {
		this.depositPrice = depositPrice;
	}
	public Date getLastSignEndDate() {
		return lastSignEndDate;
	}

	public void setLastSignEndDate(Date lastSignEndDate) {
		this.lastSignEndDate = lastSignEndDate;
	}

	public Date getFirstSignStartDate() {
		return firstSignStartDate;
	}

	public void setFirstSignStartDate(Date firstSignStartDate) {
		this.firstSignStartDate = firstSignStartDate;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public Integer getConRentYear() {
		return conRentYear;
	}

	public void setConRentYear(Integer conRentYear) {
		this.conRentYear = conRentYear;
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

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public Integer getRentType() {
		return rentType;
	}

	public void setRentType(Integer rentType) {
		this.rentType = rentType;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public Double getRoomSalesPrice() {
		return roomSalesPrice;
	}

	public void setRoomSalesPrice(Double roomSalesPrice) {
		this.roomSalesPrice = roomSalesPrice;
	}

	public String getConCycleCode() {
		return conCycleCode;
	}

	public void setConCycleCode(String conCycleCode) {
		this.conCycleCode = conCycleCode;
	}

	public String getConType() {
		return conType;
	}

	public void setConType(String conType) {
		this.conType = conType;
	}
}
