package com.zra.pay.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by cuigh6 on 2016/12/30.
 */
public class FinReceiBillDetailEntity {
    private String fid;
    private String billFid;
    private String costItemId;
    private BigDecimal oughtAmount;
    private BigDecimal actualAmount;
    private String roomId;
    private String itemName;
    //added by wangxm113
    private Integer paymentId;//付款计划id',
    private String cityId;
    private String createId;
    private Date createTime;
    private String updateId;
    private Date updateTime;

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateId() {
        return updateId;
    }

    public void setUpdateId(String updateId) {
        this.updateId = updateId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getBillFid() {
        return billFid;
    }

    public void setBillFid(String billFid) {
        this.billFid = billFid;
    }

    public String getCostItemId() {
        return costItemId;
    }

    public void setCostItemId(String costItemId) {
        this.costItemId = costItemId;
    }

    public BigDecimal getOughtAmount() {
        return oughtAmount;
    }

    public void setOughtAmount(BigDecimal oughtAmount) {
        this.oughtAmount = oughtAmount;
    }

    public BigDecimal getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(BigDecimal actualAmount) {
        this.actualAmount = actualAmount;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
}
