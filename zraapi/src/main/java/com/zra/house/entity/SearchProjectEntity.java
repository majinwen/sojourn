package com.zra.house.entity;

/**
 * Author: wangxm113
 * CreateDate: 2016/7/29.
 */
public class SearchProjectEntity {
    private String projectId;
    private String projectName;
    private String projectAddr;
    private String projectImgUrl;
    private String htId;
    private String htName;
    private String htImgUrl;
    private Double htArea;
    private Double htMinPrice;
    private Double htMaxPrice;
    private Integer htAvaRoomAcc;
    private String htAvaRoomState;
    //added for app optimize
    private String showImg;
    private Double lng;
    private Double lat;
    private String proCode;

    public String getShowImg() {
        return showImg;
    }

    public void setShowImg(String showImg) {
        this.showImg = showImg;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public String getProCode() {
        return proCode;
    }

    public void setProCode(String proCode) {
        this.proCode = proCode;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectAddr() {
        return projectAddr;
    }

    public void setProjectAddr(String projectAddr) {
        this.projectAddr = projectAddr;
    }

    public String getProjectImgUrl() {
        return projectImgUrl;
    }

    public void setProjectImgUrl(String projectImgUrl) {
        this.projectImgUrl = projectImgUrl;
    }

    public String getHtId() {
        return htId;
    }

    public void setHtId(String htId) {
        this.htId = htId;
    }

    public String getHtName() {
        return htName;
    }

    public void setHtName(String htName) {
        this.htName = htName;
    }

    public String getHtImgUrl() {
        return htImgUrl;
    }

    public void setHtImgUrl(String htImgUrl) {
        this.htImgUrl = htImgUrl;
    }

    public Double getHtArea() {
        return htArea;
    }

    public void setHtArea(Double htArea) {
        this.htArea = htArea;
    }

    public Double getHtMinPrice() {
        return htMinPrice;
    }

    public void setHtMinPrice(Double htMinPrice) {
        this.htMinPrice = htMinPrice;
    }

    public Double getHtMaxPrice() {
        return htMaxPrice;
    }

    public void setHtMaxPrice(Double htMaxPrice) {
        this.htMaxPrice = htMaxPrice;
    }

    public Integer getHtAvaRoomAcc() {
        return htAvaRoomAcc;
    }

    public void setHtAvaRoomAcc(Integer htAvaRoomAcc) {
        this.htAvaRoomAcc = htAvaRoomAcc;
    }

    public String getHtAvaRoomState() {
        return htAvaRoomState;
    }

    public void setHtAvaRoomState(String htAvaRoomState) {
        this.htAvaRoomState = htAvaRoomState;
    }
}
