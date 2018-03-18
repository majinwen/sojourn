package com.zra.cms.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
/**
 * 户型标签实体
 * @author tianxf9
 *
 */
@ApiModel(value="")
public class CmsHousetypeLabel {
    /**
     * 
     * 表字段 : cms_housetype_label.id
     * 
     */
    @ApiModelProperty(value="")
    private Integer id;

    /**
     * 
     * 表字段 : cms_housetype_label.fid
     * 
     */
    @ApiModelProperty(value="")
    private String fid;

    /**
     * 房型id
     * 表字段 : cms_housetype_label.houseType_id
     * 
     */
    @ApiModelProperty(value="房型id")
    private String housetypeId;

    /**
     * 标签名称
     * 表字段 : cms_housetype_label.label_name
     * 
     */
    @ApiModelProperty(value="标签名称")
    private String labelName;

    /**
     * 标签顺序
     * 表字段 : cms_housetype_label.label_order
     * 
     */
    @ApiModelProperty(value="标签顺序")
    private Integer labelOrder;

    /**
     * 标签顺序
     * 表字段 : cms_housetype_label.label_icon
     * 
     */
    @ApiModelProperty(value="标签顺序")
    private String labelIcon;

    /**
     * 标签类型 1-基本标签；2-活动标签;3-核心标签
     * 表字段 : cms_housetype_label.label_type
     * 
     */
    @ApiModelProperty(value="标签类型 1-基本标签；2-活动标签;3-核心标签")
    private Integer labelType;

    /**
     * 0：未删除；1：已删除
     * 表字段 : cms_housetype_label.is_del
     * 
     */
    @ApiModelProperty(value="0：未删除；1：已删除")
    private Integer isDel;

    /**
     * 0：无效；1：有效
     * 表字段 : cms_housetype_label.is_valid
     * 
     */
    @ApiModelProperty(value="0：无效；1：有效")
    private Integer isValid;

    /**
     * 
     * 表字段 : cms_housetype_label.create_time
     * 
     */
    @ApiModelProperty(value="")
    private Date createTime;

    /**
     * 
     * 表字段 : cms_housetype_label.create_id
     * 
     */
    @ApiModelProperty(value="")
    private String createId;

    /**
     * 
     * 表字段 : cms_housetype_label.update_time
     * 
     */
    @ApiModelProperty(value="")
    private Date updateTime;

    /**
     * 
     * 表字段 : cms_housetype_label.update_id
     * 
     */
    @ApiModelProperty(value="")
    private String updateId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid == null ? null : fid.trim();
    }

    public String getHousetypeId() {
        return housetypeId;
    }

    public void setHousetypeId(String housetypeId) {
        this.housetypeId = housetypeId == null ? null : housetypeId.trim();
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName == null ? null : labelName.trim();
    }

    public Integer getLabelOrder() {
        return labelOrder;
    }

    public void setLabelOrder(Integer labelOrder) {
        this.labelOrder = labelOrder;
    }

    public String getLabelIcon() {
        return labelIcon;
    }

    public void setLabelIcon(String labelIcon) {
        this.labelIcon = labelIcon == null ? null : labelIcon.trim();
    }

    public Integer getLabelType() {
        return labelType;
    }

    public void setLabelType(Integer labelType) {
        this.labelType = labelType;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId == null ? null : createId.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateId() {
        return updateId;
    }

    public void setUpdateId(String updateId) {
        this.updateId = updateId == null ? null : updateId.trim();
    }
}