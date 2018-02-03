package com.ziroom.minsu.services.evaluate.dto;

import com.ziroom.minsu.entity.evaluate.TenantEvaluateEntity;

/**
 * <p>评价请求</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @since 1.0
 * @version 1.0
 */
public class EvaluateShareDto {
    /**
     * 城市名称
     */
    private String cityName;

    /**
     * 房源照片地址
     */
    private String picUrl;

    /** 房源名称 */
    private String houseName;

    /** 房间名称  */
    private String roomName;

    private Integer rentWay;

    /**
     * 房客评价实体
     */
    private TenantEvaluateEntity tenantEvaluate;

    public Integer getRentWay() {
        return rentWay;
    }

    public void setRentWay(Integer rentWay) {
        this.rentWay = rentWay;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public TenantEvaluateEntity getTenantEvaluate() {
        return tenantEvaluate;
    }

    public void setTenantEvaluate(TenantEvaluateEntity tenantEvaluate) {
        this.tenantEvaluate = tenantEvaluate;
    }
}
