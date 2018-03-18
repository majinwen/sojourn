package com.zra.cms.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

/**
 * 项目配置信息实体.
 * @author cuiyh9
 *
 */
@ApiModel(value="")
public class CmsProject {
    
    /**
     * 主键
     * 表字段 : cms_project.id
     * 
     */
    @ApiModelProperty(value="主键")
    private Integer id;

    /**
     * 业务主键
     * 表字段 : cms_project.fid
     * 
     */
    @ApiModelProperty(value="业务主键")
    private String fid;

    /**
     * 项目id
     * 表字段 : cms_project.project_id
     * 
     */
    @ApiModelProperty(value="项目id")
    private String projectId;

    /**
     * 项目子标题
     */
    private String projectTitle;
    
    /**
     * 项目描述 
     */
    private String projectDescribe;
    
    /**
     * 风采展示图片
     * 表字段 : cms_project.show_img
     * 前期直接取project表中数据
     */
    @ApiModelProperty(value="风采展示图片")
    private String showImg;

    /**
     * 全景看房url
     * 表字段 : cms_project.panoramic_url
     * 前期直接取project表中数据
     */
    @ApiModelProperty(value="全景看房url")
    private String panoramicUrl;

    /**
     * 周边链接
     * 表字段 : cms_project.peripheral_url
     * 前期直接取project表中数据
     */
    @ApiModelProperty(value="周边链接")
    private String peripheralUrl;

    /**
     * 分享链接
     * 表字段 : cms_project.share_url
     * 前期直接取project表中数据
     */
    @ApiModelProperty(value="分享链接")
    private String shareUrl;

    /**
     * 项目头图
     * 表字段 : cms_project.head_img
     * 前期直接取project表中数据
     */
    @ApiModelProperty(value="项目头图")
    private String headImg;

    /**
     * z-space介绍
     * 表字段 : cms_project.zspace_desc
     * 
     */
    @ApiModelProperty(value="z-space介绍")
    private String zspaceDesc;

    /**
     * 专属zo介绍
     * 表字段 : cms_project.zo_desc
     * 
     */
    @ApiModelProperty(value="专属zo介绍")
    private String zoDesc;

    /**
     * 专属zo图片URL
     * 表字段 : cms_project.zo_img_url
     * 
     */
    @ApiModelProperty(value="专属zo图片URL")
    private String zoImgUrl;
    
    /**
     * zo专属服务描述
     * 表字段 : cms_project.zo_img_url
     * 
     */
    @ApiModelProperty(value="zo专属服务")
    private String zoServiceDesc;

    /**
     * 0：未删除；1：已删除
     * 表字段 : cms_project.is_del
     * 
     */
    @ApiModelProperty(value="0：未删除；1：已删除")
    private Integer isDel;

    /**
     * 0：无效；1：有效
     * 表字段 : cms_project.is_valid
     * 
     */
    @ApiModelProperty(value="0：无效；1：有效")
    private Integer isValid;

    /**
     * 
     * 表字段 : cms_project.create_time
     * 
     */
    @ApiModelProperty(value="")
    private Date createTime;

    /**
     * 
     * 表字段 : cms_project.create_id
     * 
     */
    @ApiModelProperty(value="")
    private String createId;

    /**
     * 
     * 表字段 : cms_project.update_time
     * 
     */
    @ApiModelProperty(value="")
    private Date updateTime;

    /**
     * 
     * 表字段 : cms_project.update_id
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
    
    

    public String getProjectTitle() {
        return projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    public String getProjectDescribe() {
        return projectDescribe;
    }

    public void setProjectDescribe(String projectDescribe) {
        this.projectDescribe = projectDescribe;
    }

    public String getShowImg() {
        return showImg;
    }

    public void setShowImg(String showImg) {
        this.showImg = showImg == null ? null : showImg.trim();
    }

    public String getPanoramicUrl() {
        return panoramicUrl;
    }

    public void setPanoramicUrl(String panoramicUrl) {
        this.panoramicUrl = panoramicUrl == null ? null : panoramicUrl.trim();
    }

    public String getPeripheralUrl() {
        return peripheralUrl;
    }

    public void setPeripheralUrl(String peripheralUrl) {
        this.peripheralUrl = peripheralUrl == null ? null : peripheralUrl.trim();
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl == null ? null : shareUrl.trim();
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg == null ? null : headImg.trim();
    }

    public String getZspaceDesc() {
        return zspaceDesc;
    }

    public void setZspaceDesc(String zspaceDesc) {
        this.zspaceDesc = zspaceDesc == null ? null : zspaceDesc.trim();
    }

    public String getZoDesc() {
        return zoDesc;
    }

    public void setZoDesc(String zoDesc) {
        this.zoDesc = zoDesc == null ? null : zoDesc.trim();
    }

    public String getZoImgUrl() {
        return zoImgUrl;
    }

    public void setZoImgUrl(String zoImgUrl) {
        this.zoImgUrl = zoImgUrl == null ? null : zoImgUrl.trim();
    }

    
    public String getZoServiceDesc() {
        return zoServiceDesc;
    }

    public void setZoServiceDesc(String zoServiceDesc) {
        this.zoServiceDesc = zoServiceDesc;
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