package com.ziroom.minsu.entity.order;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

/**
 * 
 * <p>
 * 付款单实体
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
public class FinancePayVouchersEntity extends BaseEntity {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -9188214287839993134L;

	/** 自增id 主键id */
	private Integer id;

    /**
     * 付款单号（业务关联id） 付款单号  订单_标识_序号
     */
    private String pvSn;

    /**
     * 订单编号
     */
    private String orderSn;

	/**
     * 关联付款单号 重新生成付款单时依赖的父付款单号
     */
    private String parentPvSn;

    /**
     * 关联银行卡表fid
     */
    private String bankcardFid;

    /**
     * 城市code
     */
    private String cityCode;

    /**
     * 付款单来源 1：定时任务 2：用户结算 3：用户提现 4：新旧订单折算 5：强制取消结算 6：超时取消 7：透支打款 8：清空优惠券金额 9：打款失败重新生成 10:清洁费 11：返现
     */
    private Integer paySourceType;

    /**
     * 收款人uid 如房东id，客户的id
     */
    private String receiveUid;

    /**
     * 收款人类型 1：房东、2：租客
     */
    private Integer receiveType;

    /**
     * 付款人uid 从哪个账户划款
     */
    private String payUid;

    /**
     * 付款人类型 1：房东、2：租客
     */
    private Integer payType;

    /**
     * 付款类型 银行付款
     */
    private String paymentType;

    /**
     * 付款金额 分
     */
    private Integer totalFee;

    /**
     * 审核状态 2：提交审核  3：审核驳回  4：审核通过
     */
    private Integer auditStatus;

    /**
     * 付款状态 0：未生效 1：未付款 2：已消费冻结 3：已申请打款 4：已打款 5：已打余额 6：已打冻结 7：提前退房取消 8：未绑定银行卡 9：失败 20：打款失败待处理 21：打款失败已处理
     */
    private Integer paymentStatus;

    /**
     * 历史付款单状态（数据库更新操作使用）
     */
    transient private Integer oldPaymentStatus;

	/**
     * 前一步的付款单状态
     */
    private Integer previousPaymentStatus;
	
	/**
     * 实际打款时间
     */
    private Date actualPayTime;

    /**
     * 产生费用时间
     */
    private Date generateFeeTime;

    /**
     * 执行时间 定时任务执行时间
     */
    private Date runTime;

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
     * 发送请求次数
     */
    private Integer isSend;

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

	public String getOrderSn() {
		return orderSn;
	}

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn == null ? null : orderSn.trim();
    }

	public String getParentPvSn() {
        return parentPvSn;
    }

    public void setParentPvSn(String parentPvSn) {
        this.parentPvSn = parentPvSn == null ? null : parentPvSn.trim();
    }

    public String getBankcardFid() {
        return bankcardFid;
    }

    public void setBankcardFid(String bankcardFid) {
        this.bankcardFid = bankcardFid == null ? null : bankcardFid.trim();
    }
    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode == null ? null : cityCode.trim();
    }

    public Integer getPaySourceType() {
        return paySourceType;
    }

    public void setPaySourceType(Integer paySourceType) {
        this.paySourceType = paySourceType;
    }

	public String getReceiveUid() {
		return receiveUid;
	}

	public void setReceiveUid(String receiveUid) {
		this.receiveUid = receiveUid == null ? null : receiveUid.trim();
	}

	public Integer getReceiveType() {
		return receiveType;
	}

	public void setReceiveType(Integer receiveType) {
		this.receiveType = receiveType;
	}

	public String getPayUid() {
		return payUid;
	}

    public void setPayUid(String payUid) {
        this.payUid = payUid == null ? null : payUid.trim();
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType == null ? null : paymentType.trim();
	}

	public Integer getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Integer totalFee) {
		this.totalFee = totalFee;
	}

	public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}

	public Integer getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(Integer paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

    public Integer getPreviousPaymentStatus() {
        return previousPaymentStatus;
    }

    public void setPreviousPaymentStatus(Integer previousPaymentStatus) {
        this.previousPaymentStatus = previousPaymentStatus;
    }

    public Date getActualPayTime() {
        return actualPayTime;
    }

    public void setActualPayTime(Date actualPayTime) {
        this.actualPayTime = actualPayTime;
    }

    public Date getGenerateFeeTime() {
        return generateFeeTime;
    }

    public void setGenerateFeeTime(Date generateFeeTime) {
        this.generateFeeTime = generateFeeTime;
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

	public Integer getIsSend() {
		return isSend;
	}

	public void setIsSend(Integer isSend) {
		this.isSend = isSend;
	}

	public Integer getIsDel() {
		return isDel;
	}

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

	public Integer getOldPaymentStatus() {
		return oldPaymentStatus;
	}

	public void setOldPaymentStatus(Integer oldPaymentStatus) {
		this.oldPaymentStatus = oldPaymentStatus;
	}

    
}
