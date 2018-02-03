package com.ziroom.minsu.valenum.common;

public enum YesOrNoOrFrozenEnum {
	NO(0, "0","否"),
	YES(1,"1", "是"),
    FROZEN(2,"2", "是");

	YesOrNoOrFrozenEnum(int code, String str, String name) {
		this.code = code;
        this.str = str;
		this.name = name;
	}

	/** code */
	private int code;

    /** str */
    private String str;

	/** 名称 */
	private String name;

	public int getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

    public String getStr() {
        return str;
    }
}
