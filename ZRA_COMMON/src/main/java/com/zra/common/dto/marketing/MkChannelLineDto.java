package com.zra.common.dto.marketing;

import java.util.List;

/**
 * 线下渠道维护Dto
 * @author tianxf9
 *
 */
public class MkChannelLineDto {
	
	private String channelBid;
	private String channelName;
	List<MkNumberDto>  channelNumber;
	private String userId;
	private String cityId;
	public String getChannelBid() {
		return channelBid;
	}
	public void setChannelBid(String channelBid) {
		this.channelBid = channelBid;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public List<MkNumberDto> getChannelNumber() {
		return channelNumber;
	}
	public void setChannelNumber(List<MkNumberDto> channelNumber) {
		this.channelNumber = channelNumber;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	
	

}
