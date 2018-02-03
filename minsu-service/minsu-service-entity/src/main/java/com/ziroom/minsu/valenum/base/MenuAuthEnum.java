/**
 * @FileName: MenuAuthEnum.java
 * @Package com.ziroom.minsu.entity.base
 * 
 * @author yd
 * @created 2016年11月1日 上午9:25:26
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.valenum.base;

/**
 * <p>特权菜单 枚举</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
public enum MenuAuthEnum {

	COMMON_MENU(0,"普通菜单"),
	PRIVILEGED_MENU(1,"特权菜单");

	MenuAuthEnum(int code, String name) {
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
