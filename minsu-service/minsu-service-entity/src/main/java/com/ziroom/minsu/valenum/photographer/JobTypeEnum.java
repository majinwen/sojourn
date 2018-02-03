/**
 * @FileName: JobTypeEnum.java
 * @Package com.ziroom.minsu.valenum.photographer
 * 
 * @author yd
 * @created 2016年11月4日 下午3:12:30
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.valenum.photographer;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>摄影师工作类型</p>
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
public enum JobTypeEnum {

	FULL_TIME(1,"专职"),
	PART_TIME_JOB(2,"兼职");
	
	/**
	 * 枚举code
	 */
	private int code;
	
	/**
	 * 枚举值
	 */
	private String value;

	private static final Map<Integer,String> enumMap = new LinkedHashMap<Integer,String>();
	
	static {
		for (JobTypeEnum valEnum : JobTypeEnum.values()) {  
			enumMap.put(valEnum.getCode(), valEnum.getValue());  
        }  
	}
	
	JobTypeEnum(int code,String value){
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
