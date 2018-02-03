/**
 * @FileName: ColumnStyleEnum.java
 * @Package com.ziroom.minsu.valenum.house
 * 
 * @author yd
 * @created 2017年3月16日 下午8:33:26
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.valenum.house;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>TODO</p>
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
public enum ColumnStyleEnum {
	
	
	Column_Style_101(101,"左对齐"),
	Column_Style_102(102,"右对齐"),
	Column_Style_103(103,"中对齐"),;
	
	ColumnStyleEnum(int value,String name){
		
		this.name = name;
		this.value = value;
	}
	
	private int value;
	
	private String name;
	
	private static final Map<Integer,String> enumMap = new LinkedHashMap<Integer,String>();
	
	static {
		for (ColumnStyleEnum columnStyleEnum : ColumnStyleEnum.values()) {  
			enumMap.put(columnStyleEnum.getValue(), columnStyleEnum.getName());  
        }  
	}

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
	
	public static Map<Integer,String> getEnumMap() {
    	return enumMap;
    }
	
}
