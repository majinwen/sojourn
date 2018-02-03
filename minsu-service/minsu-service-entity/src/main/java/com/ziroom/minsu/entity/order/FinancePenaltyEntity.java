package com.ziroom.minsu.entity.order;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;
/**
 * 罚款单实体
 * @author jixd
 * @created 2017年05月10日 10:35:55
 * @param
 * @return
 */
public class FinancePenaltyEntity extends BaseEntity{

    private static final long serialVersionUID = 7261501678758665004L;
    /**
     * 编号
     */
    private Integer id;

    /**
     * 罚款单编号
     */
    private String penaltySn;

    /**
     * 订单编号
     */
    private String orderSn;

    /**
     * 罚款人uid
     */
    private String penaltyUid;

    /**
     * 罚款人类型 1=房东 2=房客
     */
    private Integer penaltyUserType;

    /**
     * 罚款金额
     */
    private Integer penaltyFee;

    /**
     * 罚款剩余金额
     */
    private Integer penaltyLastFee;

    /**
     * 罚款状态 0=待处理 1=处理中 2=已完成 3=已废除
     */
    private Integer penaltyStatus;
    /**
     * 老罚款状态 不更新
     */
    private transient Integer oldPenaltyStatus;

    /**
     * 罚款类型 10=房东强制取消扣罚100元 11=房东强制取消扣罚首晚房费
     */
    private Integer penaltyType;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后修改时间
     */
    private Date lastModifyDate;

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

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn == null ? null : orderSn.trim();
    }

    public String getPenaltyUid() {
        return penaltyUid;
    }

    public void setPenaltyUid(String penaltyUid) {
        this.penaltyUid = penaltyUid == null ? null : penaltyUid.trim();
    }

    public Integer getPenaltyUserType() {
        return penaltyUserType;
    }

    public void setPenaltyUserType(Integer penaltyUserType) {
        this.penaltyUserType = penaltyUserType;
    }

    public Integer getPenaltyFee() {
        return penaltyFee;
    }

    public void setPenaltyFee(Integer penaltyFee) {
        this.penaltyFee = penaltyFee;
    }

    public Integer getPenaltyLastFee() {
        return penaltyLastFee;
    }

    public void setPenaltyLastFee(Integer penaltyLastFee) {
        this.penaltyLastFee = penaltyLastFee;
    }

    public Integer getPenaltyStatus() {
        return penaltyStatus;
    }

    public void setPenaltyStatus(Integer penaltyStatus) {
        this.penaltyStatus = penaltyStatus;
    }

    public Integer getPenaltyType() {
        return penaltyType;
    }

    public void setPenaltyType(Integer penaltyType) {
        this.penaltyType = penaltyType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastModifyDate() {
        return lastModifyDate;
    }

    public void setLastModifyDate(Date lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public Integer getOldPenaltyStatus() {
        return oldPenaltyStatus;
    }

    public void setOldPenaltyStatus(Integer oldPenaltyStatus) {
        this.oldPenaltyStatus = oldPenaltyStatus;
    }

    @Override
    public String toString() {
        return "FinancePenaltyEntity{" +
                "id=" + id +
                ", penaltySn='" + penaltySn + '\'' +
                ", orderSn='" + orderSn + '\'' +
                ", penaltyUid='" + penaltyUid + '\'' +
                ", penaltyUserType=" + penaltyUserType +
                ", penaltyFee=" + penaltyFee +
                ", penaltyLastFee=" + penaltyLastFee +
                ", penaltyStatus=" + penaltyStatus +
                ", penaltyType=" + penaltyType +
                ", remark='" + remark + '\'' +
                ", createTime=" + createTime +
                ", lastModifyDate=" + lastModifyDate +
                ", isDel=" + isDel +
                '}';
    }
}