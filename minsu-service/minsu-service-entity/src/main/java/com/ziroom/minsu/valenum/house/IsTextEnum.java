/**
 * @FileName: IsTextEnum.java
 * @Package com.ziroom.minsu.valenum.house
 * 
 * @author yd
 * @created 2017年7月3日 下午3:55:54
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.valenum.house;

/**
 * <p>是否是 大字段</p>
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
public enum IsTextEnum {

	IS_NOT_TEST(0,"不是大字段"),
	IS_TEST(1,"是大字段");

	IsTextEnum(int code,String desc ){

		this.code = code;
		this.desc = desc;
	}
	private  int code;


	private String desc;


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
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}


	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}



}
