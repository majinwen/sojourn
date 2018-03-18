package com.zra.cms.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

/**
 * 项目周边交通信息实体.
 * @author cuiyh9
 *
 */
@ApiModel(value="")
public class CmsProjectTraffic {
    
    /**
     * 
     * 表字段 : cms_project_traffic.id
     * 
     */
    @ApiModelProperty(value="")
    private Integer id;

    /**
     * 
     * 表字段 : cms_project_traffic.fid
     * 
     */
    @ApiModelProperty(value="")
    private String fid;

    /**
     * 
     * 表字段 : cms_project_traffic.project_id
     * 
     */
    @ApiModelProperty(value="")
    private String projectId;

    /**
     * 具体交通信息
     * 表字段 : cms_project_traffic.traffic_des
     * 
     */
    @ApiModelProperty(value="具体交通信息")
    private String trafficDes;
    
    private Integer trafficOrder;

    /**
     * 0：未删除；1：已删除
     * 表字段 : cms_project_traffic.is_del
     * 
     */
    @ApiModelProperty(value="0：未删除；1：已删除")
    private Integer isDel;

    /**
     * 0：无效；1：有效
     * 表字段 : cms_project_traffic.is_valid
     * 
     */
    @ApiModelProperty(value="0：无效；1：有效")
    private Integer isValid;

    /**
     * 
     * 表字段 : cms_project_traffic.create_time
     * 
     */
    @ApiModelProperty(value="")
    private Date createTime;

    /**
     * 
     * 表字段 : cms_project_traffic.create_id
     * 
     */
    @ApiModelProperty(value="")
    private String createId;

    /**
     * 
     * 表字段 : cms_project_traffic.update_time
     * 
     */
    @ApiModelProperty(value="")
    private Date updateTime;

    /**
     * 
     * 表字段 : cms_project_traffic.update_id
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

    public String getTrafficDes() {
        return trafficDes;
    }

    public void setTrafficDes(String trafficDes) {
        this.trafficDes = trafficDes == null ? null : trafficDes.trim();
    }

    
    public Integer getTrafficOrder() {
        return trafficOrder;
    }

    public void setTrafficOrder(Integer trafficOrder) {
        this.trafficOrder = trafficOrder;
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