package com.zra.common.dto.marketing;

/**
 * 项目分机号dto
 * @author tianxf9
 *
 */
public class ProjectTelDto {
	
	private String projectId;
	private String marketTel;
	private String cityId;
	//add by tianxf9渠道统计
	private String channelBid;
	
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getMarketTel() {
		return marketTel;
	}
	public void setMarketTel(String marketTel) {
		this.marketTel = marketTel;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getChannelBid() {
		return channelBid;
	}
	public void setChannelBid(String channelBid) {
		this.channelBid = channelBid;
	}

}
