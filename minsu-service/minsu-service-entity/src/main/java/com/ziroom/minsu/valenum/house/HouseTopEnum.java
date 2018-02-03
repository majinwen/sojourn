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
 * <p>HouseTop状态枚举</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author lunan
 * @since 1.0
 * @version 1.0
 */
public enum HouseTopEnum {


	NEW_TOP(0,"新建"),
	ONLINE_TOP(1,"上线"),
	DOWNLINE_TOP(2,"下线"),;

	HouseTopEnum(int value, String name){
		
		this.name = name;
		this.value = value;
	}
	
	private int value;
	
	private String name;
	
	private static final Map<Integer,String> enumMap = new LinkedHashMap<Integer,String>();
	
	static {
		for (HouseTopEnum columnStyleEnum : HouseTopEnum.values()) {
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
