package com.ziroom.minsu.report.order.dto;

import com.ziroom.minsu.services.common.dto.PageRequest;

import java.util.List;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lishaochuan on 2017/3/7 17:22
 * @version 1.0
 * @since 1.0
 */
public class OrderInformationRequest extends PageRequest {
    private static final long serialVersionUID = 3584875377181266165L;

    private List<String> cityList;

    private String region;
    private String city;
    private String payStatus;
    private String rentWay;
    private String orderType;
    private String createStartTime;
    private String createEndTime;
    private String checkInStartTime;
    private String checkInEndTime;
    private String checkOutStartTime;
    private String checkOutEndTime;
    private String realCheckOutStartTime;
    private String realCheckOutEndTime;

    public List<String> getCityList() {
        return cityList;
    }

    public void setCityList(List<String> cityList) {
        this.cityList = cityList;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCreateStartTime() {
        return createStartTime;
    }

    public void setCreateStartTime(String createStartTime) {
        this.createStartTime = createStartTime;
    }

    public String getCreateEndTime() {
        return createEndTime;
    }

    public void setCreateEndTime(String createEndTime) {
        this.createEndTime = createEndTime;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getCheckInStartTime() {
        return checkInStartTime;
    }

    public void setCheckInStartTime(String checkInStartTime) {
        this.checkInStartTime = checkInStartTime;
    }

    public String getCheckInEndTime() {
        return checkInEndTime;
    }

    public void setCheckInEndTime(String checkInEndTime) {
        this.checkInEndTime = checkInEndTime;
    }

    public String getCheckOutStartTime() {
        return checkOutStartTime;
    }

    public void setCheckOutStartTime(String checkOutStartTime) {
        this.checkOutStartTime = checkOutStartTime;
    }

    public String getCheckOutEndTime() {
        return checkOutEndTime;
    }

    public void setCheckOutEndTime(String checkOutEndTime) {
        this.checkOutEndTime = checkOutEndTime;
    }

    public String getRentWay() {
        return rentWay;
    }

    public void setRentWay(String rentWay) {
        this.rentWay = rentWay;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getRealCheckOutStartTime() {
        return realCheckOutStartTime;
    }

    public void setRealCheckOutStartTime(String realCheckOutStartTime) {
        this.realCheckOutStartTime = realCheckOutStartTime;
    }

    public String getRealCheckOutEndTime() {
        return realCheckOutEndTime;
    }

    public void setRealCheckOutEndTime(String realCheckOutEndTime) {
        this.realCheckOutEndTime = realCheckOutEndTime;
    }
}
