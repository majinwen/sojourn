package com.zra.common.dto.marketing;
/**
 * 线下渠道400维护Dto
 * @author tianxf9
 *
 */
public class MkNumberDto {
	
	private String channelBid;
	private String numberBid;
	private String projectId;
	private String projectName;
	private String phoneNum;
	public String getChannelBid() {
		return channelBid;
	}
	public void setChannelBid(String channelBid) {
		this.channelBid = channelBid;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getNumberBid() {
		return numberBid;
	}
	public void setNumberBid(String numberBid) {
		this.numberBid = numberBid;
	}
	
	

}
