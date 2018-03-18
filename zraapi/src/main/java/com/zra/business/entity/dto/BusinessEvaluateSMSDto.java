package com.zra.business.entity.dto;

/**
 * @author wangws21 2016年8月23日
 * 评价短信提醒实体
 */
public class BusinessEvaluateSMSDto {
	
	private Integer id;
	private String businessBid;
	private String businessResultBid;
	private String projectId;
	private String zoId;
	private String cusPhone;
	private String cusUuid;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getBusinessBid() {
		return businessBid;
	}
	public void setBusinessBid(String businessBid) {
		this.businessBid = businessBid;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getBusinessResultBid() {
		return businessResultBid;
	}
	public void setBusinessResultBid(String businessResultBid) {
		this.businessResultBid = businessResultBid;
	}
	public String getZoId() {
		return zoId;
	}
	public void setZoId(String zoId) {
		this.zoId = zoId;
	}
	public String getCusPhone() {
		return cusPhone;
	}
	public void setCusPhone(String cusPhone) {
		this.cusPhone = cusPhone;
	}
	public String getCusUuid() {
		return cusUuid;
	}
	public void setCusUuid(String cusUuid) {
		this.cusUuid = cusUuid;
	}
	
}
