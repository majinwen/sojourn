package com.zra.cms.entity.dto;

import java.io.Serializable;

/**
 * Z-SPACE实体.
 * @author cuiyh9
 *
 */
public class CmsAppProjectZspaceImgDto implements Serializable {
    
    /**
     * 图片URL
     * 
     */
    private String imgUrl;

    /**
     * 图片顺序
     * 
     */
    private Integer imgOrder;

    /**
     * 图片描述
     * 
     */
    private String imgDesc;
    
    
    public CmsAppProjectZspaceImgDto() {
        
    }

    public CmsAppProjectZspaceImgDto(String imgUrl, Integer imgOrder, String imgDesc) {
        super();
        this.imgUrl = imgUrl;
        this.imgOrder = imgOrder;
        this.imgDesc = imgDesc;
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

    public String getImgDesc() {
        return imgDesc;
    }

    public void setImgDesc(String imgDesc) {
        this.imgDesc = imgDesc;
    }

}