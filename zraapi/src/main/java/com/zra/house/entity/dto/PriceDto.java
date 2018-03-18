package com.zra.house.entity.dto;

/**
 * Author: wangxm113
 * CreateDate: 2016/7/29.
 */
public class PriceDto {
    private Double minPrice = 0.0;
    private Double maxPrice = 0.0;

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }
}
