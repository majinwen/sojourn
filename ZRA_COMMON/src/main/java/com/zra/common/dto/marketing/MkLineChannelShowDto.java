package com.zra.common.dto.marketing;
/**
 * 线下渠道展示Dto
 * @author tianxf9
 *
 */
public class MkLineChannelShowDto {
	//渠道序号
	private int sequenceNum;
	//渠道bid
	private String channelBid;
	//渠道名称
	private String channelName;
	
	//项目id
	private String projectId;
	
	//项目名称
	private String projectName;
	
	//400电话
	private String phoneNum;
	
	//是否显示渠道序号
	private boolean isShowNum;

	public int getSequenceNum() {
		return sequenceNum;
	}

	public void setSequenceNum(int sequenceNum) {
		this.sequenceNum = sequenceNum;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
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

	public boolean isShowNum() {
		return isShowNum;
	}

	public void setShowNum(boolean isShowNum) {
		this.isShowNum = isShowNum;
	}

	public String getChannelBid() {
		return channelBid;
	}

	public void setChannelBid(String channelBid) {
		this.channelBid = channelBid;
	}
	
	
}
