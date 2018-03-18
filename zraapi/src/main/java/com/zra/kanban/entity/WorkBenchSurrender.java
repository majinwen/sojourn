package com.zra.kanban.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * Created by lixn49 on 2016/12/23.
 * 管家工作台 解约协议审核中未通过的
 */
@ApiModel(value="")
public class WorkBenchSurrender {

	@ApiModelProperty(value="解约协议Id")
	private String surrenderId;

	@ApiModelProperty(value="解约协议编号")
	private String surrenderCode;

	@ApiModelProperty(value="合同号Id")
	private String contractId;

	@ApiModelProperty(value="合同号")
	private String contractCode;

	@ApiModelProperty(value="房间Id")
	private String roomId;

	@ApiModelProperty(value="房间号")
	private String roomCode;

	@ApiModelProperty(value="审核日期-yyyy-MM-dd")
	private Date rentauditdate;
	
	@ApiModelProperty(value="审核日期-yyyy-MM-dd")
	private String rentAuditDateDisplay;

	public String getSurrenderId() {
		return surrenderId;
	}

	public void setSurrenderId(String surrenderId) {
		this.surrenderId = surrenderId;
	}

	public String getSurrenderCode() {
		return surrenderCode;
	}

	public void setSurrenderCode(String surrenderCode) {
		this.surrenderCode = surrenderCode;
	}

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

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getRoomCode() {
		return roomCode;
	}

	public void setRoomCode(String roomCode) {
		this.roomCode = roomCode;
	}

	public Date getRentauditdate() {
		return rentauditdate;
	}

	public void setRentauditdate(Date rentauditdate) {
		this.rentauditdate = rentauditdate;
	}

	public String getRentAuditDateDisplay() {
		return rentAuditDateDisplay;
	}

	public void setRentAuditDateDisplay(String rentAuditDateDisplay) {
		this.rentAuditDateDisplay = rentAuditDateDisplay;
	}

	@Override
	public String toString() {
		return "WorkBenchSurrender [surrenderId=" + surrenderId + ", surrenderCode=" + surrenderCode + ", contractId="
				+ contractId + ", contractCode=" + contractCode + ", roomId=" + roomId + ", roomCode=" + roomCode
				+ ", rentauditdate=" + rentauditdate + ", rentAuditDateDisplay=" + rentAuditDateDisplay + "]";
	}
}
