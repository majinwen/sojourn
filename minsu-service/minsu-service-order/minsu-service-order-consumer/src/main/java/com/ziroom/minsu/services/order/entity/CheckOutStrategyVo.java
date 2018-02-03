package com.ziroom.minsu.services.order.entity;

import com.asura.framework.base.entity.BaseEntity;

public class CheckOutStrategyVo extends BaseEntity {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -9090398282496973591L;

	/**
	 * 是否提前退房
	 */
	private Boolean isEarly;

	/** 违约金 */
	private Integer penaltyMoney;

    /** 是否智能锁 */
    private Integer isLock = 0;


	/** 佣金 */
	private Integer userComm;

	public Integer getUserComm() {
		return userComm;
	}

	public void setUserComm(Integer userComm) {
		this.userComm = userComm;
	}

    public Integer getIsLock() {
        return isLock;
    }

    public void setIsLock(Integer isLock) {
        this.isLock = isLock;
    }

    public Boolean getIsEarly() {
		return isEarly;
	}

	public void setIsEarly(Boolean isEarly) {
		this.isEarly = isEarly;
	}

	public Integer getPenaltyMoney() {
		return penaltyMoney;
	}

	public void setPenaltyMoney(Integer penaltyMoney) {
		this.penaltyMoney = penaltyMoney;
	}

}
