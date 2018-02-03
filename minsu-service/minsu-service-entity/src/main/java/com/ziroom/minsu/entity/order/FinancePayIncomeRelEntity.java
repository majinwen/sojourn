package com.ziroom.minsu.entity.order;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;

/**
 * 付款单与收入关系
 * @author jixd
 * @created 2017年05月10日 10:41:21
 * @param
 * @return
 */
public class FinancePayIncomeRelEntity extends BaseEntity{

    private static final long serialVersionUID = -4393473898793095601L;
    /**
     * 编号
     */
    private Integer id;

    /**
     * 付款单编号
     */
    private String pvSn;
    /**
     * 付款单订单号
     */
    private String pvOrderSn;
    /**
     * 收入编号
     */
    private String incomeSn;
    /**
     * 转换收入 分
     */
    private Integer tranFee;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 是否删除 0：否，1：是
     */
    private Integer isDel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPvSn() {
        return pvSn;
    }

    public void setPvSn(String pvSn) {
        this.pvSn = pvSn == null ? null : pvSn.trim();
    }

    public String getIncomeSn() {
        return incomeSn;
    }

    public void setIncomeSn(String incomeSn) {
        this.incomeSn = incomeSn == null ? null : incomeSn.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public String getPvOrderSn() {
        return pvOrderSn;
    }

    public void setPvOrderSn(String pvOrderSn) {
        this.pvOrderSn = pvOrderSn;
    }

    public Integer getTranFee() {
        return tranFee;
    }

    public void setTranFee(Integer tranFee) {
        this.tranFee = tranFee;
    }

    @Override
    public String toString() {
        return "FinancePayIncomeRelEntity{" +
                "id=" + id +
                ", pvSn='" + pvSn + '\'' +
                ", pvOrderSn='" + pvOrderSn + '\'' +
                ", incomeSn='" + incomeSn + '\'' +
                ", tranFee=" + tranFee +
                ", createTime=" + createTime +
                ", isDel=" + isDel +
                '}';
    }
}