/**
 * @FileName: IsHasTipEnum.java
 * @Package com.ziroom.minsu.valenum.order
 * 
 * @author yd
 * @created 2017年4月10日 上午11:07:40
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.valenum.order;

/**
 * <p>是否有提示信息</p>
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
public enum IsHasTipEnum {

	HAS_TIP(1,"包含提示信息"),
	NOT_HAS_TIP(0,"不包含提示信息");
	
	IsHasTipEnum(int value,String name){
		
		this.value = value;
		this.name = name;
	}
	
	/**
	 * 枚举值
	 */
	private int value;
	
    /**
     * 枚举名称
     */
	private String name;
	

	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(int value) {
		this.value = value;
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
