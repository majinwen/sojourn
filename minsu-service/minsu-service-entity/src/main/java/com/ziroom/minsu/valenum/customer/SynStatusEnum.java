package com.ziroom.minsu.valenum.customer;

public enum SynStatusEnum {
	WAIT(0, "未同步"),
	IP_ERROR(1, "异常ip"),
	FAIL(2, "同步失败"),
	SUCCESS(3, "同步成功");

	SynStatusEnum(int code, String name) {
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
