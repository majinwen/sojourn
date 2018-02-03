package com.ziroom.minsu.services.order.entity;

import java.util.Date;

/**
 * 罚款单对应关系vo
 * @author jixd
 * @created 2017年05月16日 11:49:22
 * @param
 * @return
 */
public class PenaltyRelVo {
    /**
     * 罚款单编号
     */
    private String penaltySn;
    /**
     * 父编号
     */
    private String parentPvSn;

    /**
     * 付款单
     */
    private String pvSn;
    /**
     * 付款订单号
     */
    private String pvOrderSn;
    /**
     * 金额
     */
    private String totalFee;
    /**
     * 收入编号
     */
    private String incomeSn;
    /**
     * 创建时间
     */
    private Date createTime;

    public String getPenaltySn() {
        return penaltySn;
    }

    public void setPenaltySn(String penaltySn) {
        this.penaltySn = penaltySn;
    }

    public String getParentPvSn() {
        return parentPvSn;
    }

    public void setParentPvSn(String parentPvSn) {
        this.parentPvSn = parentPvSn;
    }

    public String getPvSn() {
        return pvSn;
    }

    public void setPvSn(String pvSn) {
        this.pvSn = pvSn;
    }

    public String getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }

    public String getIncomeSn() {
        return incomeSn;
    }

    public void setIncomeSn(String incomeSn) {
        this.incomeSn = incomeSn;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getPvOrderSn() {
        return pvOrderSn;
    }

    public void setPvOrderSn(String pvOrderSn) {
        this.pvOrderSn = pvOrderSn;
    }
}
