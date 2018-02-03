package com.ziroom.minsu.services.message.dto;

/**
 * 扩展消息里面的内容
 */
public class ShareHouseMsg {

    public String city;
    public String fid;
    public String isTop50Online;
    public String name;
    public String picUrl;
    public String price;
    public String rentWay;
    public String rentWayName;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getIsTop50Online() {
        return isTop50Online;
    }

    public void setIsTop50Online(String isTop50Online) {
        this.isTop50Online = isTop50Online;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRentWay() {
        return rentWay;
    }

    public void setRentWay(String rentWay) {
        this.rentWay = rentWay;
    }

	public String getRentWayName() {
		return rentWayName;
	}

	public void setRentWayName(String rentWayName) {
		this.rentWayName = rentWayName;
	}
    
}
