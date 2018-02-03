package com.ziroom.minsu.valenum.pay;

public enum PlatFormPayEnum {
	order(1, "订单"),
	punish(2, "账单");

	PlatFormPayEnum(int code, String name) {
		this.code = code;
		this.name = name;
	}

	/** code */
	private int code;

	/** 名称 */
	private String name;

	public int getCode() {
		return code;
	}

	public String getName() {
		return name;
	}
}
