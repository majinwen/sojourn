package com.zra.cms.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

/**
 * Z-SPACE实体.
 * @author cuiyh9
 *
 */
@ApiModel(value="")
public class CmsProjectZspaceImg {
    
    /**
     * 
     * 表字段 : cms_zspace_img.id
     * 
     */
    @ApiModelProperty(value="")
    private Integer id;

    /**
     * 
     * 表字段 : cms_zspace_img.fid
     * 
     */
    @ApiModelProperty(value="")
    private String fid;

    /**
     * 项目配置信息表fid
     * 表字段 : cms_zspace_img.project_id
     * 
     */
    @ApiModelProperty(value="项目配置信息表fid")
    private String projectId;

    /**
     * 图片URL
     * 表字段 : cms_zspace_img.img_url
     * 
     */
    @ApiModelProperty(value="图片URL")
    private String imgUrl;

    /**
     * 图片顺序
     * 表字段 : cms_zspace_img.img_order
     * 
     */
    @ApiModelProperty(value="图片顺序")
    private Integer imgOrder;

    /**
     * 图片描述
     * 表字段 : cms_zspace_img.img_desc
     * 
     */
    @ApiModelProperty(value="图片描述")
    private String imgDesc;

    /**
     * 0：未删除；1：已删除
     * 表字段 : cms_zspace_img.is_del
     * 
     */
    @ApiModelProperty(value="0：未删除；1：已删除")
    private Integer isDel;

    /**
     * 0：无效；1：有效
     * 表字段 : cms_zspace_img.is_valid
     * 
     */
    @ApiModelProperty(value="0：无效；1：有效")
    private Integer isValid;

    /**
     * 
     * 表字段 : cms_zspace_img.create_time
     * 
     */
    @ApiModelProperty(value="")
    private Date createTime;

    /**
     * 
     * 表字段 : cms_zspace_img.create_id
     * 
     */
    @ApiModelProperty(value="")
    private String createId;

    /**
     * 
     * 表字段 : cms_zspace_img.update_time
     * 
     */
    @ApiModelProperty(value="")
    private Date updateTime;

    /**
     * 
     * 表字段 : cms_zspace_img.update_id
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
    
    
    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl == null ? null : imgUrl.trim();
    }

    public Integer getImgOrder() {
        return imgOrder;
    }

    public void setImgOrder(Integer imgOrder) {
        this.imgOrder = imgOrder;
    }

    public String getImgDesc() {
        return imgDesc;
    }

    public void setImgDesc(String imgDesc) {
        this.imgDesc = imgDesc == null ? null : imgDesc.trim();
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