package com.zra.cms.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

/**
 * 项目标签实体.
 * @author cuiyh9
 *
 */
@ApiModel(value="")
public class CmsProjectLabel {
    
    /**
     * 
     * 表字段 : cms_project_label.id
     * 
     */
    @ApiModelProperty(value="")
    private Integer id;

    /**
     * 
     * 表字段 : cms_project_label.fid
     * 
     */
    @ApiModelProperty(value="")
    private String fid;

    /**
     * 项目id
     * 表字段 : cms_project_label.project_id
     * 
     */
    @ApiModelProperty(value="项目id")
    private String projectId;

    /**
     * 模块名称
     * 表字段 : cms_project_label.module_name
     * 
     */
    @ApiModelProperty(value="模块名称")
    private String moduleName;

    /**
     * 模块顺序
     * 表字段 : cms_project_label.module_order
     * 
     */
    @ApiModelProperty(value="模块顺序")
    private Integer moduleOrder;

    /**
     * 0：未删除；1：已删除
     * 表字段 : cms_project_label.is_del
     * 
     */
    @ApiModelProperty(value="0：未删除；1：已删除")
    private Integer isDel;

    /**
     * 0：无效；1：有效
     * 表字段 : cms_project_label.is_valid
     * 
     */
    @ApiModelProperty(value="0：无效；1：有效")
    private Integer isValid;

    /**
     * 
     * 表字段 : cms_project_label.create_time
     * 
     */
    @ApiModelProperty(value="")
    private Date createTime;

    /**
     * 
     * 表字段 : cms_project_label.create_id
     * 
     */
    @ApiModelProperty(value="")
    private String createId;

    /**
     * 
     * 表字段 : cms_project_label.update_time
     * 
     */
    @ApiModelProperty(value="")
    private Date updateTime;

    /**
     * 
     * 表字段 : cms_project_label.update_id
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

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName == null ? null : moduleName.trim();
    }

    public Integer getModuleOrder() {
        return moduleOrder;
    }

    public void setModuleOrder(Integer moduleOrder) {
        this.moduleOrder = moduleOrder;
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