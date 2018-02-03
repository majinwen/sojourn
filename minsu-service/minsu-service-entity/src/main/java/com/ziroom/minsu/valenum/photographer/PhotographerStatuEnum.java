/**
 * @FileName: PhotographerStatuEnum.java
 * @Package com.ziroom.minsu.valenum.photographer
 * 
 * @author yd
 * @created 2016年11月4日 下午2:35:06
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.valenum.photographer;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>摄影师账号状态
 *  1=正常 2=异常 3=删除
 * </p>
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
public enum PhotographerStatuEnum {
 
	NORMAL(1,"正常"),
	EXCEPTION(2,"异常"),
	DELETE(3,"删除");
	
	/**
	 * 枚举code
	 */
	private int code;
	
	/**
	 * 枚举value
	 */
	private String value;
	
	private static final Map<Integer,String> enumMap = new LinkedHashMap<Integer,String>();
	
	static {
		for (PhotographerStatuEnum valEnum : PhotographerStatuEnum.values()) {  
			enumMap.put(valEnum.getCode(), valEnum.getValue());  
        }  
	}
	
	PhotographerStatuEnum(int code,String value){
		this.code = code;
		this.value = value;
	}
	
	public int getCode() {
		return code;
	}

	public String getValue() {
		return value;
	}

	public static Map<Integer,String> getEnumMap() {
		return enumMap;
	}
	
	
	
	
}
