package com.zra.house.entity.dto;

import com.zra.common.dto.appbase.AppBaseDto;

/**
 * Author: wangxm113
 * CreateDate: 2016/8/1.
 */
public class SearchOfHTReturnDto extends AppBaseDto {
    private String projectId;
    private PriceDto price;
    private AreaDto area;
    private String checkInTime;
    private String floor;
    private String direction;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public PriceDto getPrice() {
        return price;
    }

    public void setPrice(PriceDto price) {
        this.price = price;
    }

    public AreaDto getArea() {
        return area;
    }

    public void setArea(AreaDto area) {
        this.area = area;
    }

    public String getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(String checkInTime) {
        this.checkInTime = checkInTime;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
