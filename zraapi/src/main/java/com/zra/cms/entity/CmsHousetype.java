package com.zra.cms.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
/**
 * cms户型配置实体
 * @author tianxf9
 */
@ApiModel(value="")
public class CmsHousetype implements Serializable{
    /**
     * 
     * 表字段 : cms_housetype.id
     * 
     */
    @ApiModelProperty(value="")
    private Integer id;

    /**
     * 
     * 表字段 : cms_housetype.fid
     * 
     */
    @ApiModelProperty(value="")
    private String fid;

    /**
     * 项目id
     * 表字段 : cms_housetype.project_id
     * 
     */
    @ApiModelProperty(value="项目id")
    private String projectId;

    /**
     * 房型id
     * 表字段 : cms_housetype.houseType_id
     * 
     */
    @ApiModelProperty(value="房型id")
    private String housetypeId;

    /**
     * 房型介绍
     * 表字段 : cms_housetype.introduction
     * 
     */
    @ApiModelProperty(value="房型介绍")
    private String introduction;

    /**
     * 0：未删除；1：已删除
     * 表字段 : cms_housetype.is_del
     * 
     */
    @ApiModelProperty(value="0：未删除；1：已删除")
    private Integer isDel;

    /**
     * 0：无效；1：有效
     * 表字段 : cms_housetype.is_valid
     * 
     */
    @ApiModelProperty(value="0：无效；1：有效")
    private Integer isValid;

    /**
     * 
     * 表字段 : cms_housetype.create_time
     * 
     */
    @ApiModelProperty(value="")
    private Date createTime;

    /**
     * 
     * 表字段 : cms_housetype.create_id
     * 
     */
    @ApiModelProperty(value="")
    private String createId;

    /**
     * 
     * 表字段 : cms_housetype.update_time
     * 
     */
    @ApiModelProperty(value="")
    private Date updateTime;

    /**
     * 
     * 表字段 : cms_housetype.update_id
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
        this.projectId = projectId == null ? null : projectId.trim();
    }

    public String getHousetypeId() {
        return housetypeId;
    }

    public void setHousetypeId(String housetypeId) {
        this.housetypeId = housetypeId == null ? null : housetypeId.trim();
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction == null ? null : introduction.trim();
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