/**
 * @FileName: ProductRulesEnum0022Enum.java
 * @Package com.ziroom.minsu.valenum.productrules
 * 
 * @author yd
 * @created 2017年3月17日 下午7:47:04
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.valenum.productrules;

/**
 * <p>标签类型</p>
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
public enum ProductRulesEnum0022Enum {

	TAG_HOUSE(1,"房源标签"),
	TAG_LANLORD(2,"房东标签"),
	TAG_SPECIAL_HOUSE(3,"特色房源"),
	TAG_TOP50_HOUSE(4,"TOP50");
	
	ProductRulesEnum0022Enum(int value,String name ){
		
		this.name = name;
		this.value = value;
	}
	
	private int value;
	
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
