package com.zra.common.vo.room;

import java.io.Serializable;

import com.zra.common.vo.contract.ProjectInfoVo;

public class SignRoomInfoVo implements Serializable{
	
	private static final long serialVersionUID = 4580524047381189554L;
	/**
	 * 合同ID
	 */
	private String contractId;
	/**
	 * 合同码
	 */
	private String contractCode;
	/**
	 * 管家手机号
	 */
	private String handleZOPhone;
	/**
	 * 签约项目信息
	 */
	private ProjectInfoVo projectInfo;
	/**
	 * 签约房间信息
	 */
	private RoomInfoVo roomInfo;
	/**
	 * 租期2017/02/25至2018/02/26
	 */
	private String rentTime;
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
	public String getHandleZOPhone() {
		return handleZOPhone;
	}
	public void setHandleZOPhone(String handleZOPhone) {
		this.handleZOPhone = handleZOPhone;
	}
	public ProjectInfoVo getProjectInfo() {
		return projectInfo;
	}
	public void setProjectInfo(ProjectInfoVo projectInfo) {
		this.projectInfo = projectInfo;
	}
	public RoomInfoVo getRoomInfo() {
		return roomInfo;
	}
	public void setRoomInfo(RoomInfoVo roomInfo) {
		this.roomInfo = roomInfo;
	}
	public String getRentTime() {
		return rentTime;
	}
	public void setRentTime(String rentTime) {
		this.rentTime = rentTime;
	}
	
}
