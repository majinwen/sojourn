package com.zra.kanban.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * Created by lixn49 on 2016/12/23.
 * 管家工作台 合同审核中未通过的
 */
@ApiModel(value="")
public class WorkBenchAudContract {

	@ApiModelProperty(value="合同号Id")
	private String contractId;

	@ApiModelProperty(value="合同号")
	private String contractCode;

	@ApiModelProperty(value="房间Id")
	private String roomId;

	@ApiModelProperty(value="房间号")
	private String roomCode;

	@ApiModelProperty(value="审核日期-yyyy-MM-dd")
	private Date conAuditDate;
	
	@ApiModelProperty(value="审核日期-yyyy-MM-dd")
	private String conAuditDateDisplay;

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

	public Date getConAuditDate() {
		return conAuditDate;
	}

	public void setConAuditDate(Date conAuditDate) {
		this.conAuditDate = conAuditDate;
	}

	public String getConAuditDateDisplay() {
		return conAuditDateDisplay;
	}

	public void setConAuditDateDisplay(String conAuditDateDisplay) {
		this.conAuditDateDisplay = conAuditDateDisplay;
	}

	@Override
	public String toString() {
		return "WorkBenchAudContract [contractId=" + contractId + ", contractCode=" + contractCode + ", roomId="
				+ roomId + ", roomCode=" + roomCode + ", conAuditDate=" + conAuditDate + ", conAuditDateDisplay="
				+ conAuditDateDisplay + "]";
	}
}
