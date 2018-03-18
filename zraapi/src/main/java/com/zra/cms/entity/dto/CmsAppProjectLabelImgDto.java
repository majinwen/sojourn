package com.zra.cms.entity.dto;

import java.io.Serializable;

/**
 * 项目标签图片.
 * @author cuiyh9
 *
 */
public class CmsAppProjectLabelImgDto implements Serializable {
  
    /**
     * 图片URL.
     */
    private String imgUrl;

    /**
     * 图片顺序. 
     */
    private Integer imgOrder;
    
    public CmsAppProjectLabelImgDto() {
        
    }

    
    public CmsAppProjectLabelImgDto(String imgUrl, Integer imgOrder) {
        super();
        this.imgUrl = imgUrl;
        this.imgOrder = imgOrder;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Integer getImgOrder() {
        return imgOrder;
    }

    public void setImgOrder(Integer imgOrder) {
        this.imgOrder = imgOrder;
    }
    
    

}