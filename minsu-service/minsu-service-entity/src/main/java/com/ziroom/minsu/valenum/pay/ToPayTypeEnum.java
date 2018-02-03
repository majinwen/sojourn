package com.ziroom.minsu.valenum.pay;

public enum ToPayTypeEnum {
	
	NORMAL_PAY(1, "正常支付"),
	PUNISH_PAY(2, "罚款支付");

	ToPayTypeEnum(int code, String name) {
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
