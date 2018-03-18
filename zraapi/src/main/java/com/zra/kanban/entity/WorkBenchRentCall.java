package com.zra.kanban.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by lixn49 on 2016/12/23.
 * 管家工作台 房租催缴
 */
@ApiModel(value="")
public class WorkBenchRentCall {
	@ApiModelProperty(value="合同号Id")
	private String contractId;

	@ApiModelProperty(value="合同号")
	private String contractCode;
	
	@ApiModelProperty(value="房间Id")
	private String roomId; 

	@ApiModelProperty(value="房间号")
	private String roomCode;

	@ApiModelProperty(value="客户姓名")
	private String customerName;

	@ApiModelProperty(value="应收日期-yyyy-MM-dd")
	private Date oughtPaymentDate;

	@ApiModelProperty(value="应收金额（元）")
	private BigDecimal oughtTotalAmount;
	
	@ApiModelProperty(value="应收金额（元）")
	private String outghtTotalAmountDisplay;

	@ApiModelProperty(value="已收金额（元）")
	private BigDecimal actualTotalAmount;
	
	@ApiModelProperty(value="已收金额（元）")
	private String actualTotalAmountDisplay;

	@ApiModelProperty(value="剩余天数（天）")
	private Integer RemaininDays;

	@ApiModelProperty(value="应收日期-yyyy-MM-dd")
	private String oughtPaymentDateDisplay;

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public String getRoomCode() {
		return roomCode;
	}

	public void setRoomCode(String roomCode) {
		this.roomCode = roomCode;
	}

	public Date getOughtPaymentDate() {
		return oughtPaymentDate;
	}

	public void setOughtPaymentDate(Date oughtPaymentDate) {
		this.oughtPaymentDate = oughtPaymentDate;
	}

	public BigDecimal getOughtTotalAmount() {
		return oughtTotalAmount;
	}

	public void setOughtTotalAmount(BigDecimal oughtTotalAmount) {
		this.oughtTotalAmount = oughtTotalAmount;
	}

	public BigDecimal getActualTotalAmount() {
		return actualTotalAmount;
	}

	public void setActualTotalAmount(BigDecimal actualTotalAmount) {
		this.actualTotalAmount = actualTotalAmount;
	}

	public Integer getRemaininDays() {
		return RemaininDays;
	}

	public void setRemaininDays(Integer remaininDays) {
		RemaininDays = remaininDays;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getOughtPaymentDateDisplay() {
		return oughtPaymentDateDisplay;
	}

	public void setOughtPaymentDateDisplay(String oughtPaymentDateDisplay) {
		this.oughtPaymentDateDisplay = oughtPaymentDateDisplay;
	}

	public String getOutghtTotalAmountDisplay() {
		return outghtTotalAmountDisplay;
	}

	public void setOutghtTotalAmountDisplay(String outghtTotalAmountDisplay) {
		this.outghtTotalAmountDisplay = outghtTotalAmountDisplay;
	}

	public String getActualTotalAmountDisplay() {
		return actualTotalAmountDisplay;
	}

	public void setActualTotalAmountDisplay(String actualTotalAmountDisplay) {
		this.actualTotalAmountDisplay = actualTotalAmountDisplay;
	}
	
	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	@Override
	public String toString() {
		return "WorkBenchRentCall [contractId=" + contractId + ", contractCode=" + contractCode + ", roomId=" + roomId
				+ ", roomCode=" + roomCode + ", customerName=" + customerName + ", oughtPaymentDate=" + oughtPaymentDate
				+ ", oughtTotalAmount=" + oughtTotalAmount + ", outghtTotalAmountDisplay=" + outghtTotalAmountDisplay
				+ ", actualTotalAmount=" + actualTotalAmount + ", actualTotalAmountDisplay=" + actualTotalAmountDisplay
				+ ", RemaininDays=" + RemaininDays + ", oughtPaymentDateDisplay=" + oughtPaymentDateDisplay + "]";
	}
}
