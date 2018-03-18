package com.zra.pay.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by cuigh6 on 2016/12/30.
 */
public class FinReceiBillEntity {
    private String fid;
    private String contractId;
    private BigDecimal oughtTotalAmount;
    private Integer billState;
    private BigDecimal actualTotalAmount;
    private String cityId;
    private Date gatherDate;
    private Integer billType;

    public Integer getBillType() {
        return billType;
    }

    public void setBillType(Integer billType) {
        this.billType = billType;
    }

    public Date getGatherDate() {
        return gatherDate;
    }

    public void setGatherDate(Date gatherDate) {
        this.gatherDate = gatherDate;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public BigDecimal getActualTotalAmount() {
        return actualTotalAmount;
    }

    public void setActualTotalAmount(BigDecimal actualTotalAmount) {
        this.actualTotalAmount = actualTotalAmount;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public BigDecimal getOughtTotalAmount() {
        return oughtTotalAmount;
    }

    public void setOughtTotalAmount(BigDecimal oughtTotalAmount) {
        this.oughtTotalAmount = oughtTotalAmount;
    }

    public Integer getBillState() {
        return billState;
    }

    public void setBillState(Integer billState) {
        this.billState = billState;
    }
}
