package com.zra.common.dto.business;
/**
 * 商机列表展示Dto
 * @author tianxf9
 *
 */
public class BusinessShowDto {
	
	private String businessBid;
	private String projectName;
	private String zoName;
	private String cusName;
	private String cusPhone;
	private Integer step;
	private Integer handState;
	private String createTime;
	private String endTime;
	private String projectId;
	private Integer source;
	
	public String getBusinessBid() {
		return businessBid;
	}
	public void setBusinessBid(String businessBid) {
		this.businessBid = businessBid;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getZoName() {
		return zoName;
	}
	public void setZoName(String zoName) {
		this.zoName = zoName;
	}
	public String getCusName() {
		return cusName;
	}
	public void setCusName(String cusName) {
		this.cusName = cusName;
	}
	public String getCusPhone() {
		return cusPhone;
	}
	public void setCusPhone(String cusPhone) {
		this.cusPhone = cusPhone;
	}
	public Integer getStep() {
		return step;
	}
	public void setStep(Integer step) {
		this.step = step;
	}
	public Integer getHandState() {
		return handState;
	}
	public void setHandState(Integer handState) {
		this.handState = handState;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public Integer getSource() {
		return source;
	}
	public void setSource(Integer source) {
		this.source = source;
	}
	
}
