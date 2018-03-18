package com.zra.cardCoupon.entity;

import java.io.Serializable;

/**
 * Created by cuigh6 on 2017/5/22.
 */
public class ExtCouponEntity implements Serializable{
	private String code; //优惠券优惠码
	private String name; //优惠券名字
	private String desc; //优惠券描述
	private String rule; //优惠券使用规则
	private String start_time; //有效开始时间
	private String end_time; //有效结束时间
	private int money; //金额，单位分
	private int use_status; //使用状态 0未使用 10已使用

	public int getUse_status() {
		return use_status;
	}

	public void setUse_status(int use_status) {
		this.use_status = use_status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}
}
