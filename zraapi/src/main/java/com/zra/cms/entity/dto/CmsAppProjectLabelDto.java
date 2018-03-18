package com.zra.cms.entity.dto;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;

/**
 * 项目标签实体.
 * @author cuiyh9
 */
public class CmsAppProjectLabelDto implements Serializable {
    
    /**
     * 标签名称.
     */
    private String moduleName;
    
    /**
     * 标签顺序
     */
    private Integer moduleOrder;
    
    private List<CmsAppProjectLabelImgDto> projectLabelImgList;
    
    public  CmsAppProjectLabelDto() {
        
    }
    
    
    public CmsAppProjectLabelDto(String moduleName, Integer moduleOrder,
            List<CmsAppProjectLabelImgDto> projectLabelImgList) {
        this.moduleName = moduleName;
        this.moduleOrder = moduleOrder;
        this.projectLabelImgList = projectLabelImgList;
    }



    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public Integer getModuleOrder() {
        return moduleOrder;
    }

    public void setModuleOrder(Integer moduleOrder) {
        this.moduleOrder = moduleOrder;
    }

    public List<CmsAppProjectLabelImgDto> getProjectLabelImgList() {
        return projectLabelImgList;
    }

    public void setProjectLabelImgList(List<CmsAppProjectLabelImgDto> projectLabelImgList) {
        this.projectLabelImgList = projectLabelImgList;
    }
    
    
    

}