package com.ziroom.minsu.entity.order;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>活动返现表实体</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lishaochuan on 2016年9月7日
 * @since 1.0
 * @version 1.0
 */
public class FinanceCashbackEntity extends BaseEntity {
    /**
	 * 序列化ID
	 */
	private static final long serialVersionUID = -897713992149052132L;

	/**
     * 主键id
     */
    private Integer id;

    /**
     * 返现单号
     */
    private String cashbackSn;

    /**
     * 返现单状态 10：初始 20：已审核 30：已驳回 
     * @see com.ziroom.minsu.valenum.order.CashbackStatusEnum
     */
    private Integer cashbackStatus;

    /**
     * 订单号
     */
    private String orderSn;

    /**
     * 付款单号
     */
    private String pvSn;

    /**
     * 充值单号
     */
    private String fillSn;

    /**
     * 活动码
     */
    private String actSn;

    /**
     * 收款人类型 收款人类型：1：房东、2：租客
     * @see com.ziroom.minsu.valenum.order.ReceiveTypeEnum
     */
    private Integer receiveType;

    /**
     * 收款人uid
     */
    private String receiveUid;

    /**
     * 返现金额 分
     */
    private Integer totalFee;

    /**
     * 申请备注
     */
    private String applyRemark;

    /**
     * 创建人id
     */
    private String createId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后修改时间
     */
    private Date lastModifyDate;

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

    public String getCashbackSn() {
        return cashbackSn;
    }

    public void setCashbackSn(String cashbackSn) {
        this.cashbackSn = cashbackSn == null ? null : cashbackSn.trim();
    }

    public Integer getCashbackStatus() {
        return cashbackStatus;
    }

    public void setCashbackStatus(Integer cashbackStatus) {
        this.cashbackStatus = cashbackStatus;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn == null ? null : orderSn.trim();
    }

    public String getPvSn() {
        return pvSn;
    }

    public void setPvSn(String pvSn) {
        this.pvSn = pvSn == null ? null : pvSn.trim();
    }

    public String getFillSn() {
        return fillSn;
    }

    public void setFillSn(String fillSn) {
        this.fillSn = fillSn == null ? null : fillSn.trim();
    }

    public String getActSn() {
        return actSn;
    }

    public void setActSn(String actSn) {
        this.actSn = actSn == null ? null : actSn.trim();
    }

    public Integer getReceiveType() {
        return receiveType;
    }

    public void setReceiveType(Integer receiveType) {
        this.receiveType = receiveType;
    }

    public String getReceiveUid() {
        return receiveUid;
    }

    public void setReceiveUid(String receiveUid) {
        this.receiveUid = receiveUid == null ? null : receiveUid.trim();
    }

    public Integer getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Integer totalFee) {
        this.totalFee = totalFee;
    }

    public String getApplyRemark() {
        return applyRemark;
    }

    public void setApplyRemark(String applyRemark) {
        this.applyRemark = applyRemark == null ? null : applyRemark.trim();
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId == null ? null : createId.trim();
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
}