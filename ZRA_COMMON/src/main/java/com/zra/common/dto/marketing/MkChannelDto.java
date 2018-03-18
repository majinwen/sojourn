package com.zra.common.dto.marketing;

import io.swagger.annotations.ApiModelProperty;

/**
 * 渠道线上dto
 * @author tianxf9
 *
 */
public class MkChannelDto {
	
	
    /**
     * 渠道名称
     * 表字段 : mk_channel.channel_bid
     * 
     */
    @ApiModelProperty(value="渠道bid")
	private String channelBid;
	
    /**
     * 渠道名称
     * 表字段 : mk_channel.channel_name
     * 
     */
    @ApiModelProperty(value="渠道名称")
    private String channelName;

    /**
     * 渠道内容
     * 表字段 : mk_channel.channel_content
     * 
     */
    @ApiModelProperty(value="渠道内容")
    private String channelContent;

    /**
     * 渠道类型：1：线上渠道；2：线下渠道
     * 表字段 : mk_channel.channel_type
     * 
     */
    @ApiModelProperty(value="渠道类型：1：线上渠道；2：线下渠道")
    private Byte channelType;
    
    /**
     * 表字段 : mk_channel.city_id
     * 
     */
    @ApiModelProperty(value="城市id")
    private String cityId;
    
    private String userId;
    
	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getChannelContent() {
		return channelContent;
	}

	public void setChannelContent(String channelContent) {
		this.channelContent = channelContent;
	}

	public Byte getChannelType() {
		return channelType;
	}

	public void setChannelType(Byte channelType) {
		this.channelType = channelType;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
