package com.zra.common.dto.house;

/**
 * Author: wangxm113
 * CreateDate: 2016/8/24.
 */
public class BusinessListReturnDto {
    private String proId;
    private String proName;
    private Double minArea;
    private Double minPrice;
    private String proHeadImg;

    public String getProId() {
        return proId;
    }

    public void setProId(String proId) {
        this.proId = proId;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public Double getMinArea() {
        return minArea;
    }

    public void setMinArea(Double minArea) {
        this.minArea = minArea;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public String getProHeadImg() {
        return proHeadImg;
    }

    public void setProHeadImg(String proHeadImg) {
        this.proHeadImg = proHeadImg;
    }
}
