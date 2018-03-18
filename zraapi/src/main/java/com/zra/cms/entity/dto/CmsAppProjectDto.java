package com.zra.cms.entity.dto;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 项目配置信息实体.
 * 主要用于APP端项目详情页显示信息
 * @author cuiyh9
 *
 */
/**
 * 
 * @author cuiyh9
 *
 */
public class CmsAppProjectDto implements Serializable {

    /**
     * 项目id
     * 
     */
    private String projectId;
    
    /**
     * 项目副标题
     */
    private String projectTitle;
    
    /**
     * 项目描述 
     */
    private String projectDescribe;

    /**
     * 风采展示图片
     * 
     */
    private String showImg;

    /**
     * 全景看房url
     * 
     */
    private String panoramicUrl;

    /**
     * 周边链接
     * 
     */
    private String peripheralUrl;

    /**
     * 分享链接
     * 
     */
    private String shareUrl;

    /**
     * 项目头图
     * 
     */
    private String headImg;

    /**
     * z-space介绍
     * 
     */
    private String zspaceDesc;

    /**
     * 专属zo介绍
     * 
     */
    private String zoDesc;

    /**
     * 专属zo图片URL
     * 
     */
    private String zoImgUrl;
    
    /**
     * zo专属服务描述
     * 
     */
    private String zoServiceDesc;
    
    
    /**
     * 项目标签实体列表.
     */
    private List<CmsAppProjectLabelDto> appProjectLabelDtoList;
    
    /**
     * 项目周边交通信息实体.
     * 
     */
    private List<CmsAppProjectTrafficDto> appProjectTrafficDtoList;
    
    /**
     * Z-SPACE实体列.
     * 
     */
    private List<CmsAppProjectZspaceImgDto> appProjectZspaceImgDtoList;
    
    public CmsAppProjectDto()
    {
        
    }
    

    public CmsAppProjectDto(String projectId,String projectTitle,String projectDescribe, String showImg, String panoramicUrl, String peripheralUrl,
            String shareUrl, String headImg, String zspaceDesc, String zoDesc, String zoImgUrl, String zoServiceDesc,
            List<CmsAppProjectLabelDto> appProjectLabelDtoList, List<CmsAppProjectTrafficDto> appProjectTrafficDtoList,
            List<CmsAppProjectZspaceImgDto> appProjectZspaceImgDtoList) {
        super();
        this.projectId = projectId;
        this.projectTitle = projectTitle;
        this.projectDescribe = projectDescribe;
        this.showImg = showImg;
        this.panoramicUrl = panoramicUrl;
        this.peripheralUrl = peripheralUrl;
        this.shareUrl = shareUrl;
        this.headImg = headImg;
        this.zspaceDesc = zspaceDesc;
        this.zoDesc = zoDesc;
        this.zoImgUrl = zoImgUrl;
        this.zoServiceDesc = zoServiceDesc;
        this.appProjectLabelDtoList = appProjectLabelDtoList;
        this.appProjectTrafficDtoList = appProjectTrafficDtoList;
        this.appProjectZspaceImgDtoList = appProjectZspaceImgDtoList;
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

    public List<CmsAppProjectLabelDto> getAppProjectLabelDtoList() {
        return appProjectLabelDtoList;
    }

    public void setAppProjectLabelDtoList(List<CmsAppProjectLabelDto> appProjectLabelDtoList) {
        this.appProjectLabelDtoList = appProjectLabelDtoList;
    }

    public List<CmsAppProjectTrafficDto> getAppProjectTrafficDtoList() {
        return appProjectTrafficDtoList;
    }


    public void setAppProjectTrafficDtoList(List<CmsAppProjectTrafficDto> appProjectTrafficDtoList) {
        this.appProjectTrafficDtoList = appProjectTrafficDtoList;
    }

    public List<CmsAppProjectZspaceImgDto> getAppProjectZspaceImgDtoList() {
        return appProjectZspaceImgDtoList;
    }

    public void setAppProjectZspaceImgDtoList(List<CmsAppProjectZspaceImgDto> appProjectZspaceImgDtoList) {
        this.appProjectZspaceImgDtoList = appProjectZspaceImgDtoList;
    }

}