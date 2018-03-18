package com.zra.common.dto.kanban;

import java.util.Date;

/**
 * Created by lixn49 on 2016/12/26.
 */
public class AudContractQueryDto {

	private  String projectId;  // 项目id

	private String auditState;  //审核状态

	private Date notifyDate;  //房租催缴日期（当前日期+7天）
	
	private String userId;	// 用户id
	
	private String zoCode;	// ZO code

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getAuditState() {
		return auditState;
	}

	public void setAuditState(String auditState) {
		this.auditState = auditState;
	}

	public Date getNotifyDate() {
		return notifyDate;
	}

	public void setNotifyDate(Date notifyDate) {
		this.notifyDate = notifyDate;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getZoCode() {
		return zoCode;
	}

	public void setZoCode(String zoCode) {
		this.zoCode = zoCode;
	}

	@Override
	public String toString() {
		return "AudContractQueryDto [projectId=" + projectId + ", auditState=" + auditState + ", notifyDate="
				+ notifyDate + ", userId=" + userId + ", zoCode=" + zoCode + "]";
	}
}
