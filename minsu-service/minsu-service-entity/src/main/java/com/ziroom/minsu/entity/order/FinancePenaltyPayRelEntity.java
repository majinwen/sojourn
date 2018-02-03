package com.ziroom.minsu.entity.order;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;
/**
 * 罚款单与付款单关系
 * @author jixd
 * @created 2017年05月10日 10:36:37
 * @param
 * @return
 */
public class FinancePenaltyPayRelEntity extends BaseEntity{

    private static final long serialVersionUID = -6734632537551482711L;
    /**
     * 编号
     */
    private Integer id;

    /**
     * 罚款单编号
     */
    private String penaltySn;
    /**
     * 罚款单订单号
     */
    private String penaltyOrderSn;

    /**
     * 付款单编号
     */
    private String pvSn;
    /**
     * 付款单订单号
     */
    private String pvOrderSn;
    /**
     * 冲抵金额 分
     */
    private Integer offsetFee;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 是否删除  0：否，1：是
     */
    private Integer isDel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPenaltySn() {
        return penaltySn;
    }

    public void setPenaltySn(String penaltySn) {
        this.penaltySn = penaltySn == null ? null : penaltySn.trim();
    }

    public String getPvSn() {
        return pvSn;
    }

    public void setPvSn(String pvSn) {
        this.pvSn = pvSn == null ? null : pvSn.trim();
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

    public String getPenaltyOrderSn() {
        return penaltyOrderSn;
    }

    public void setPenaltyOrderSn(String penaltyOrderSn) {
        this.penaltyOrderSn = penaltyOrderSn;
    }

    public String getPvOrderSn() {
        return pvOrderSn;
    }

    public void setPvOrderSn(String pvOrderSn) {
        this.pvOrderSn = pvOrderSn;
    }

    public Integer getOffsetFee() {
        return offsetFee;
    }

    public void setOffsetFee(Integer offsetFee) {
        this.offsetFee = offsetFee;
    }

    @Override
    public String toString() {
        return "FinancePenaltyPayRelEntity{" +
                "id=" + id +
                ", penaltySn='" + penaltySn + '\'' +
                ", penaltyOrderSn='" + penaltyOrderSn + '\'' +
                ", pvSn='" + pvSn + '\'' +
                ", pvOrderSn='" + pvOrderSn + '\'' +
                ", offsetFee=" + offsetFee +
                ", createTime=" + createTime +
                ", isDel=" + isDel +
                '}';
    }
}