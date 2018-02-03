/**
 * @FileName: BookOrderSourceEnum.java
 * @Package com.ziroom.minsu.valenum.photographer
 * 
 * @author yd
 * @created 2016年11月4日 下午7:26:48
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.valenum.photographer;

/**
 * <p>摄影师预约订单来源</p>
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
public enum BookOrderSourceEnum {

	MINSU_TROY(1,"民宿后台"),
	MINSU_LANDLORD(2,"房东");

	BookOrderSourceEnum(int code,String value){

		this.code = code;
		this.value =value;

	}

	/**
	 * 枚举 code
	 */
	private int code;

	/**
	 * 枚举value
	 */
	private String value;

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}



}
