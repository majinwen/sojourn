package com.ziroom.minsu.entity.order;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

/**
 * 
 * <p>
 * 收款单
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liyingjie
 * @since 1.0
 * @version 1.0
 */
public class FinancePaymentVouchersEntity extends BaseEntity {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -2663294922612557111L;

	/** 自增id */
	private Integer id;

	/** 逻辑fid */
	private String fid;

	/** 支付订单号、账单编号 */
	private String paymentSn;

	/** 订单编号 */
	private String orderSn;
	
	/** 城市code */
	private String cityCode;

	/** 收款单来源类型 1：房客（订单支付） 2：房东（账单） */
	private Integer sourceType;

	/** 收款单 状态： 1：订单 2：账单 3：优惠券 */
	private Integer paymentType;

	/** 支付类型 */
	private Integer payType;

	/** 支付人uid */
	private String paymentUid;

	/** 收款金额 */
	private Integer totalFee;

	/** 应收金额 */
	private Integer needMoney;

	/** 支付流水号 */
	private String tradeNo;

	/** 收款单 状态： 1：未同步 2：同步成功 3：同步失败 */
	private Integer syncStatus;

	/** 实际同步时间 */
    private Date actualSyncTime;

    private Integer isSend;

	/** 执行时间 */
	private Date runTime;

	/** 支付时间 */
	private Date payTime;
	
    private String createId;

	/** 创建时间 */
	private Date createTime;

	/** 最后修改时间 */
	private Date lastModifyDate;

	/** 是否删除 0：否，1：是 */
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

    public String getPaymentSn() {
        return paymentSn;
    }

    public void setPaymentSn(String paymentSn) {
        this.paymentSn = paymentSn == null ? null : paymentSn.trim();
    }

    public String getOrderSn() {
        return orderSn;
    }

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn == null ? null : orderSn.trim();
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public Integer getSourceType() {
		return sourceType;
	}

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public String getPaymentUid() {
		return paymentUid;
	}

	public void setPaymentUid(String paymentUid) {
		this.paymentUid = paymentUid == null ? null : paymentUid.trim();
	}

	public Integer getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Integer totalFee) {
		this.totalFee = totalFee;
	}

	public Integer getNeedMoney() {
		return needMoney;
	}

	public void setNeedMoney(Integer needMoney) {
		this.needMoney = needMoney;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo == null ? null : tradeNo.trim();
	}

	public Integer getSyncStatus() {
		return syncStatus;
	}

	public void setSyncStatus(Integer syncStatus) {
		this.syncStatus = syncStatus;
	}

    public Date getActualSyncTime() {
        return actualSyncTime;
    }

    public void setActualSyncTime(Date actualSyncTime) {
        this.actualSyncTime = actualSyncTime;
    }

    public Integer getIsSend() {
        return isSend;
    }

	public void setIsSend(Integer isSend) {
		this.isSend = isSend;
	}

    public Date getRunTime() {
        return runTime;
    }

    public void setRunTime(Date runTime) {
        this.runTime = runTime;
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

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
    
}
