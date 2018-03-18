package com.zra.marketing.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

@ApiModel(value="")
public class MkChannelEntity {
    /**
     * 主键
     * 表字段 : mk_channel.id
     * 
     */
    @ApiModelProperty(value="主键")
    private Integer id;

    /**
     * 业务主键
     * 表字段 : mk_channel.channel_bid
     * 
     */
    @ApiModelProperty(value="业务主键")
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
     * 是否删除   0：未删除；1：删除
     * 表字段 : mk_channel.is_del
     * 
     */
    @ApiModelProperty(value="是否删除   0：未删除；1：删除")
    private Byte isDel;

    /**
     * 创建时间
     * 表字段 : mk_channel.create_time
     * 
     */
    @ApiModelProperty(value="创建时间")
    private Date createTime;

    /**
     * 删除时间
     * 表字段 : mk_channel.delete_time
     * 
     */
    @ApiModelProperty(value="删除时间")
    private Date deleteTime;

    /**
     * 更新时间
     * 表字段 : mk_channel.update_time
     * 
     */
    @ApiModelProperty(value="更新时间")
    private Date updateTime;

    /**
     * 创建人id(employee的fid)
     * 表字段 : mk_channel.create_id
     * 
     */
    @ApiModelProperty(value="创建人id(employee的fid)")
    private String createId;

    /**
     * 更新人
     * 表字段 : mk_channel.update_id
     * 
     */
    @ApiModelProperty(value="更新人")
    private String updateId;

    /**
     * 删除人
     * 表字段 : mk_channel.delete_id
     * 
     */
    @ApiModelProperty(value="删除人")
    private String deleteId;
    
    
    /**
     * 城市id
     * 表字段 : mk_channel.cityId
     * 
     */
    @ApiModelProperty(value="城市id")
    private String cityId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getChannelBid() {
        return channelBid;
    }

    public void setChannelBid(String channelBid) {
        this.channelBid = channelBid == null ? null : channelBid.trim();
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName == null ? null : channelName.trim();
    }

    public String getChannelContent() {
        return channelContent;
    }

    public void setChannelContent(String channelContent) {
        this.channelContent = channelContent == null ? null : channelContent.trim();
    }

    public Byte getChannelType() {
		return channelType;
	}

	public void setChannelType(Byte channelType) {
		this.channelType = channelType;
	}

	public Byte getIsDel() {
		return isDel;
	}

	public void setIsDel(Byte isDel) {
		this.isDel = isDel;
	}

	public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId == null ? null : createId.trim();
    }

    public String getUpdateId() {
        return updateId;
    }

    public void setUpdateId(String updateId) {
        this.updateId = updateId == null ? null : updateId.trim();
    }

    public String getDeleteId() {
        return deleteId;
    }

    public void setDeleteId(String deleteId) {
        this.deleteId = deleteId == null ? null : deleteId.trim();
    }

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
    
    
}