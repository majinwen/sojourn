package com.ziroom.minsu.services.order.entity;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.ziroom.minsu.entity.order.FinancePunishEntity;

public class FinancePunishVo {

	/** 订单编号 */
	private String orderSn;

	/** 扣款人uid */
	private String punishUid;

	/** 扣款人类型 */
	private Integer punishType;

	/** 扣款金额 */
	private Integer punishFee;

	/** 描述 */
	private String punishDescribe;

	/** 截止支付时间 */
	private Date lastPayTime;

	/** 创建人id */
	private String createId;

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public String getPunishUid() {
		return punishUid;
	}

	public void setPunishUid(String punishUid) {
		this.punishUid = punishUid;
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

	public String getPunishDescribe() {
		return punishDescribe;
	}

	public void setPunishDescribe(String punishDescribe) {
		this.punishDescribe = punishDescribe;
	}

	public Date getLastPayTime() {
		return lastPayTime;
	}

	public void setLastPayTime(Date lastPayTime) {
		this.lastPayTime = lastPayTime;
	}

	public String getCreateId() {
		return createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	public FinancePunishEntity getFinancePunishEntity() {
		FinancePunishEntity punish = new FinancePunishEntity();
		BeanUtils.copyProperties(this, punish);
		return punish;
	}

}
