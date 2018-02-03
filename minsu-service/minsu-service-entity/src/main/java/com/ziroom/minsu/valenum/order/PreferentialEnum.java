/**
 * @FileName: PreferentialSourcesEnum.java
 * @Package com.ziroom.minsu.valenum.order
 * 
 * @author yd
 * @created 2017年6月6日 上午11:46:05
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.valenum.order;

/**
 * <p>下单优惠来源</p>
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
public enum PreferentialEnum {

	OTHER(0,"其他"),
	LANLORD(1,"房东"),
	TENANT(2,"房客"),
	PLATFORM(3,"平台");
	
	PreferentialEnum(int code,String name){
		
		this.code = code;
		this.name = name;
	}
	
	private int code;
	
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
