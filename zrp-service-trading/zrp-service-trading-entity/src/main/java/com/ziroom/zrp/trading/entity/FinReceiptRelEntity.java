package com.ziroom.zrp.trading.entity;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;
/**
 * 收款单与应收关联表
 * @author jixd
 * @created 2017年11月21日 11:42:25
 * @param
 * @return
 */
public class FinReceiptRelEntity extends BaseEntity{
    private static final long serialVersionUID = -9110843993362177100L;
    /**
     * 主键自增
     */
    private Integer id;

    /**
     * 业务主键
     */
    private String fid;

    /**
     * 收款单编号
     */
    private String receiptedNo;

    /**
     * 应收账单编号
     */
    private String receiptableNo;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除(0:未删除；1:已删除)
     */
    private Integer isDel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid == null ? null : fid.trim();
    }

    public String getReceiptedNo() {
        return receiptedNo;
    }

    public void setReceiptedNo(String receiptedNo) {
        this.receiptedNo = receiptedNo == null ? null : receiptedNo.trim();
    }

    public String getReceiptableNo() {
        return receiptableNo;
    }

    public void setReceiptableNo(String receiptableNo) {
        this.receiptableNo = receiptableNo == null ? null : receiptableNo.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
}