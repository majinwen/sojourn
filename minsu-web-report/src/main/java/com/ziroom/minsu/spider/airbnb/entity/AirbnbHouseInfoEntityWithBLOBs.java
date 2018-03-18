package com.ziroom.minsu.spider.airbnb.entity;

public class AirbnbHouseInfoEntityWithBLOBs extends AirbnbHouseInfoEntity {
    /**
     * 简述
     */
    private String summary;

    /**
     * 描述信息
     */
    private String description;

    /**
     * 房源全部信息
     */
    private String houseJson;
    
    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary == null ? null : summary.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getHouseJson() {
        return houseJson;
    }

    public void setHouseJson(String houseJson) {
        this.houseJson = houseJson == null ? null : houseJson.trim();
    }
}