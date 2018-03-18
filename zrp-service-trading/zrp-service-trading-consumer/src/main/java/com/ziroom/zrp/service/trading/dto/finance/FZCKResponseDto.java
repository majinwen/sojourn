package com.ziroom.zrp.service.trading.dto.finance;

import java.util.List;

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
 * @Date 2017年11月10日 16时17分
 * @Version 1.0
 * @Since 1.0
 */
public class FZCKResponseDto {
    /**
     * customerName : 黄佐材
     * telphone : 18617000680
     * overDate : 31
     * limitPayDate : 2017-06-30
     * contractCode : SZZZCWZ81608311664
     * billId : null
     * divideStatus : 2
     * roomCode :
     * curSumAmount : 4926.5
     * realSumAmount : null
     * shouldDedit : 152.83
     * realDedit : null
     * lastCollectionName : null
     * lastCollectionCode : null
     * lastCollectionTime : null
     */

    private String customerName;
    private String telphone;
    private Integer overDate;
    private String limitPayDate;
    private String contractCode;
    private Integer billId;
    private String divideStatus;
    private String roomCode;
    private Double curSumAmount;
    private Double realSumAmount;
    private Double shouldDedit;
    private Double realDedit;
    private String lastCollectionName;
    private String lastCollectionCode;
    private String lastCollectionTime;

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

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public Double getCurSumAmount() {
        return curSumAmount;
    }

    public void setCurSumAmount(Double curSumAmount) {
        this.curSumAmount = curSumAmount;
    }

    public Double getRealSumAmount() {
        return realSumAmount;
    }

    public void setRealSumAmount(Double realSumAmount) {
        this.realSumAmount = realSumAmount;
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

    public String getLastCollectionName() {
        return lastCollectionName;
    }

    public void setLastCollectionName(String lastCollectionName) {
        this.lastCollectionName = lastCollectionName;
    }

    public String getLastCollectionCode() {
        return lastCollectionCode;
    }

    public void setLastCollectionCode(String lastCollectionCode) {
        this.lastCollectionCode = lastCollectionCode;
    }

    public String getLastCollectionTime() {
        return lastCollectionTime;
    }

    public void setLastCollectionTime(String lastCollectionTime) {
        this.lastCollectionTime = lastCollectionTime;
    }
}
