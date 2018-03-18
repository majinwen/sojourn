package com.ziroom.zrp.service.trading.dto.finance;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @Author wangxm113
 * @Date 2017年11月10日 10时34分
 * @Version 1.0
 * @Since 1.0
 */
public class FollowupRecordDto {

    /**
     * customerName : 安伟亮
     * telphone : 17326910132
     * overDate : 0
     * limitPayDate : 2017-04-24
     * contractCode : BJZYCW81611243283
     * billId : 4655
     * divideStatus : 已关闭
     * urgeType : 自如白条
     * address : 昌平天通西苑三区21号楼7单元2层201
     * shouldDedit : 0
     * realDedit : 0
     * realSumAmount : 2411.8
     * curSumAmount : 2411.8
     * roomCode : 03
     * hireServiceName : 王一涵
     * supervisorName : 甄学雷
     */

    private String customerName;
    private String telphone;
    private Integer overDate;
    private String limitPayDate;
    private String contractCode;
    private Integer billId;
    private String divideStatus;
    private String urgeType;
    private String address;
    private Double shouldDedit;
    private Double realDedit;
    private Double realSumAmount;
    private Double curSumAmount;
    private String roomCode;
    private String hireServiceName;
    private String supervisorName;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public Integer getOverDate() {
        return overDate;
    }

    public void setOverDate(Integer overDate) {
        this.overDate = overDate;
    }

    public String getLimitPayDate() {
        return limitPayDate;
    }

    public void setLimitPayDate(String limitPayDate) {
        this.limitPayDate = limitPayDate;
    }

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public Integer getBillId() {
        return billId;
    }

    public void setBillId(Integer billId) {
        this.billId = billId;
    }

    public String getDivideStatus() {
        return divideStatus;
    }

    public void setDivideStatus(String divideStatus) {
        this.divideStatus = divideStatus;
    }

    public String getUrgeType() {
        return urgeType;
    }

    public void setUrgeType(String urgeType) {
        this.urgeType = urgeType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getShouldDedit() {
        return shouldDedit;
    }

    public void setShouldDedit(Double shouldDedit) {
        this.shouldDedit = shouldDedit;
    }

    public Double getRealDedit() {
        return realDedit;
    }

    public void setRealDedit(Double realDedit) {
        this.realDedit = realDedit;
    }

    public Double getRealSumAmount() {
        return realSumAmount;
    }

    public void setRealSumAmount(Double realSumAmount) {
        this.realSumAmount = realSumAmount;
    }

    public Double getCurSumAmount() {
        return curSumAmount;
    }

    public void setCurSumAmount(Double curSumAmount) {
        this.curSumAmount = curSumAmount;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public String getHireServiceName() {
        return hireServiceName;
    }

    public void setHireServiceName(String hireServiceName) {
        this.hireServiceName = hireServiceName;
    }

    public String getSupervisorName() {
        return supervisorName;
    }

    public void setSupervisorName(String supervisorName) {
        this.supervisorName = supervisorName;
    }
}
