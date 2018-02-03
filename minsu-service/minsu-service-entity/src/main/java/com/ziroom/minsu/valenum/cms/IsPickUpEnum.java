/**
 * @FileName: IsPickUpEnum.java
 * @Package com.ziroom.minsu.valenum.cms
 * 
 * @author yd
 * @created 2016年10月9日 下午2:36:13
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.valenum.cms;

/**
 * <p>当前礼物是否已被领取 0=未领取 默认 1=已领取</p>
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
public enum IsPickUpEnum {
	//当前礼物是否已被领取 0=未领取 默认 1=已领取
	NO_PICK_UP(0,"未领取"),
	PICK_UP(1,"已领取");

	IsPickUpEnum(int code, String name){

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
