package com.ziroom.minsu.valenum.common;


/**
 * 全部的枚举
 */
public enum AllEnum {
	ALL(0,"0", "否");

	AllEnum(int code,String str, String name) {
		this.code = code;
        this.str = str;
		this.name = name;
	}

	/** code */
	private int code;
    /** 字符串 */
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
