package com.ziroom.minsu.valenum.order;

/**
 * <p>
 * 订单的操作标记表
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author lishaochuan on 2016年7月29日
 * @since 1.0
 * @version 1.0
 */
public enum OrderFlagEnum {

	REMIND_ACCEPT_ORDER("remindAcceptOrder", "是否已提醒房东接受订单，0:否1：是");

	OrderFlagEnum(String code, String value) {
		this.code = code;
		this.value = value;
	}

	/** 编码 */
	private String code;

	/** 值 */
	private String value;

	public String getCode() {
		return code;
	}

	public String getName() {
		return value;
	}
}
