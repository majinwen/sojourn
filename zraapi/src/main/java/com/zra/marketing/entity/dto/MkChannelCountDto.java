package com.zra.marketing.entity.dto;
/**
 * 渠道数据统计Dto
 * @author tianxf9
 *
 */
public class MkChannelCountDto {
	
	//渠道bid
	private String channelBid;
	//渠道名称
	private String channelName;
	//数量
	private Integer count;
	
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
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
}
