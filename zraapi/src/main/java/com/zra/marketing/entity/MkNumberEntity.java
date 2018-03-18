package com.zra.marketing.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

@ApiModel(value="")
public class MkNumberEntity {
    /**
     * 主键
     * 表字段 : mk_number.id
     * 
     */
    @ApiModelProperty(value="主键")
    private Integer id;

    /**
     * 业务主键
     * 表字段 : mk_number.number_bid
     * 
     */
    @ApiModelProperty(value="业务主键")
    private String numberBid;

    /**
     * 分机号
     * 表字段 : mk_number.number
     * 
     */
    @ApiModelProperty(value="分机号")
    private String number;

    /**
     * 项目id
     * 表字段 : mk_number.project_id
     * 
     */
    @ApiModelProperty(value="项目id")
    private String projectId;

    /**
     * 渠道bid
     * 表字段 : mk_number.channel_bid
     * 
     */
    @ApiModelProperty(value="渠道bid")
    private String channelBid;

    /**
     * 是否删除   0：未删除；1：删除
     * 表字段 : mk_number.is_del
     * 
     */
    @ApiModelProperty(value="是否删除   0：未删除；1：删除")
    private Byte isDel;

    /**
     * 创建时间
     * 表字段 : mk_number.create_time
     * 
     */
    @ApiModelProperty(value="创建时间")
    private Date createTime;

    /**
     * 删除时间
     * 表字段 : mk_number.delete_time
     * 
     */
    @ApiModelProperty(value="删除时间")
    private Date deleteTime;

    /**
     * 更新时间
     * 表字段 : mk_number.update_time
     * 
     */
    @ApiModelProperty(value="更新时间")
    private Date updateTime;

    /**
     * 创建人id(employee的fid)
     * 表字段 : mk_number.create_id
     * 
     */
    @ApiModelProperty(value="创建人id(employee的fid)")
    private String createId;

    /**
     * 更新人
     * 表字段 : mk_number.update_id
     * 
     */
    @ApiModelProperty(value="更新人")
    private String updateId;

    /**
     * 删除人
     * 表字段 : mk_number.delete_id
     * 
     */
    @ApiModelProperty(value="删除人")
    private String deleteId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumberBid() {
        return numberBid;
    }

    public void setNumberBid(String numberBid) {
        this.numberBid = numberBid == null ? null : numberBid.trim();
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    public String getChannelBid() {
        return channelBid;
    }

    public void setChannelBid(String channelBid) {
        this.channelBid = channelBid == null ? null : channelBid.trim();
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
}