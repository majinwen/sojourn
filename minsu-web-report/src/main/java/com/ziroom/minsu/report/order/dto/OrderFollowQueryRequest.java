package com.ziroom.minsu.report.order.dto;

import com.ziroom.minsu.services.common.dto.PageRequest;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lishaochuan on 2016/12/19 17:06
 * @version 1.0
 * @since 1.0
 */
public class OrderFollowQueryRequest extends PageRequest {

    private static final long serialVersionUID = 6641721681331246351L;

    private String houseName;

    private String cityCode;

    private String userName;

    private String userTel;

    private String landlordName;

    private String orderStatus;

    private String orderSn;

    private String createStartTime;

    private String createEndTime;

    private String checkInStartTime;

    private String checkInEndTime;

    private String checkOutStartTime;

    private String checkOutEndTime;

    private Integer payStatus;

    private String empPushName;

    private String empGuardName;

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserTel() {
        return userTel;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel;
    }

    public String getLandlordName() {
        return landlordName;
    }

    public void setLandlordName(String landlordName) {
        this.landlordName = landlordName;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
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

    public String getCheckInStartTime() {
        return checkInStartTime;
    }

    public void setCheckInStartTime(String checkInStartTime) {
        this.checkInStartTime = checkInStartTime;
    }

    public String getCheckOutStartTime() {
        return checkOutStartTime;
    }

    public void setCheckOutStartTime(String checkOutStartTime) {
        this.checkOutStartTime = checkOutStartTime;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public String getEmpPushName() {
        return empPushName;
    }

    public void setEmpPushName(String empPushName) {
        this.empPushName = empPushName;
    }

    public String getEmpGuardName() {
        return empGuardName;
    }

    public void setEmpGuardName(String empGuardName) {
        this.empGuardName = empGuardName;
    }

    public String getCheckInEndTime() {
        return checkInEndTime;
    }

    public void setCheckInEndTime(String checkInEndTime) {
        this.checkInEndTime = checkInEndTime;
    }

    public String getCheckOutEndTime() {
        return checkOutEndTime;
    }

    public void setCheckOutEndTime(String checkOutEndTime) {
        this.checkOutEndTime = checkOutEndTime;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
}
