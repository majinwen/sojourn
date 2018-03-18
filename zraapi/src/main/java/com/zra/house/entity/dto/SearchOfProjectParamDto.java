package com.zra.house.entity.dto;

import com.zra.common.dto.appbase.AppBaseDto;

/**
 * 首页搜索条件
 * <p>
 * Author: wangxm113
 * CreateDate: 2016/7/29.
 */
public class SearchOfProjectParamDto extends AppBaseDto {
    private String city;//城市
    private String checkInTime;//入住时间
    private Double minPrice = 0.0;
    private Double maxPrice = 0.0;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(String checkInTime) {
        this.checkInTime = checkInTime;
    }

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
