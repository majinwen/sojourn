package com.zra.house.entity.dto;

import com.zra.common.dto.appbase.AppBaseDto;

/**
 * 首页搜索条件
 * <p>
 * Author: wangxm113
 * CreateDate: 2016/7/29.
 */
public class SearchOfProjectReturnDto extends AppBaseDto {
    private String city;//城市
    private PriceDto price;//价格
    private String checkInTime;//入住时间

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public PriceDto getPrice() {
        return price;
    }

    public void setPrice(PriceDto price) {
        this.price = price;
    }

    public String getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(String checkInTime) {
        this.checkInTime = checkInTime;
    }
}
