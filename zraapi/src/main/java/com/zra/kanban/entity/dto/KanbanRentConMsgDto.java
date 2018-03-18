package com.zra.kanban.entity.dto;

import java.util.Date;

public class KanbanRentConMsgDto {
	
	//项目id
	private String projectId;
	//数量
	private String roomId;
	//合同起租日期
	private Date conStartDate;
	//起租之前的空置天数
	private int emptyNum;
	
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
	public Date getConStartDate() {
		return conStartDate;
	}
	public void setConStartDate(Date conStartDate) {
		this.conStartDate = conStartDate;
	}
	public int getEmptyNum() {
		return emptyNum;
	}
	public void setEmptyNum(int emptyNum) {
		this.emptyNum = emptyNum;
	}
}
