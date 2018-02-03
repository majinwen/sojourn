package com.ziroom.minsu.entity.order;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

public class FinancePunishEntity extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4138202645035535198L;
	
	/** 主键id*/
	private Integer id;

	/** 扣款单fid*/
	private String punishSn;
	
	/** 订单编号*/
	private String orderSn;
	
	/** 城市code*/
	private String cityCode;
	
	/** 扣款人uid*/
	private String punishUid;
	
	/** 扣款人类型*/
	private Integer punishType;
	
	/** 扣款金额*/
	private Integer punishFee;
	
	/** 扣款状态*/
	private Integer punishStatus;
	
	/** 描述*/
	private String punishDescribe;
	
	/** 截止支付时间*/
	private Date lastPayTime;
	
	/** 实际收款时间*/
	private Date actualPayTime;
	
	/** 创建人id*/
	private String createId;
	
	/** 创建时间*/
	private Date createTime;
	
	/** 最后修改时间*/
	private Date lastModifyDate;
	
	/** 是否删除 0：否，1：是*/
	private Integer isDel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPunishSn() {
        return punishSn;
    }

    public void setPunishSn(String punishSn) {
        this.punishSn = punishSn == null ? null : punishSn.trim();
    }

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn == null ? null : orderSn.trim();
	}

	public String getPunishUid() {
		return punishUid;
	}

	public void setPunishUid(String punishUid) {
		this.punishUid = punishUid == null ? null : punishUid.trim();
	}

	public Integer getPunishType() {
		return punishType;
	}

	public void setPunishType(Integer punishType) {
		this.punishType = punishType;
	}

	public Integer getPunishFee() {
		return punishFee;
	}

	public void setPunishFee(Integer punishFee) {
		this.punishFee = punishFee;
	}

	public Integer getPunishStatus() {
		return punishStatus;
	}

	public void setPunishStatus(Integer punishStatus) {
		this.punishStatus = punishStatus;
	}

    public String getPunishDescribe() {
        return punishDescribe;
    }

    public void setPunishDescribe(String punishDescribe) {
        this.punishDescribe = punishDescribe == null ? null : punishDescribe.trim();
    }

	public Date getLastPayTime() {
		return lastPayTime;
	}

	public void setLastPayTime(Date lastPayTime) {
		this.lastPayTime = lastPayTime;
	}

	public Date getActualPayTime() {
		return actualPayTime;
	}

	public void setActualPayTime(Date actualPayTime) {
		this.actualPayTime = actualPayTime;
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

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	
}