/**
 * @FileName: IsValidEnum.java
 * @Package com.ziroom.minsu.valenum.house
 * 
 * @author yd
 * @created 2016年12月13日 下午2:43:40
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.valenum.house;

/**
 * <p>周末价格开关</p>
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
public enum IsValidEnum {

	WEEK_CLOSE(0,"周末价格关闭"),
	WEEK_OPEN(1,"周末价格打开");
	
	
	IsValidEnum(int code,String value){
		
		this.code = code;
		this.value = value;
	}
	
	/**
	 * 枚举code
	 */
	private Integer code;
	
	/**
	 * 枚举值
	 */
	private String value;

	/**
	 * @return the code
	 */
	public Integer getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(Integer code) {
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
