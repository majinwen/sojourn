/**
 * @FileName: RoomFinishStatusEnum.java
 * @Package com.ziroom.minsu.valenum.house
 * 
 * @author yd
 * @created 2016年8月20日 下午6:59:35
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.valenum.house;

/**
 * <p>完成状态</p>
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
public enum RoomFinishStatusEnum {


	UN_FINISH(0,"未完成"),
	FINISH(1,"已完成");

	
	RoomFinishStatusEnum(int code,String name) {
		this.code = code;
		this.name = name;
	}

	/** code */
	private int code;

	/** 名称 */
	private String name;

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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
