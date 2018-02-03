/**
 * @FileName: PhotographerGradeEnum.java
 * @Package com.ziroom.minsu.valenum.photographer
 * 
 * @author yd
 * @created 2016年11月4日 下午2:21:09
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.valenum.photographer;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>摄影师等级</p>
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
public enum PhotographerGradeEnum {
	
	GRADE_A("A","A"),
	GRADE_B("B","B"),
	GRADE_C("C","C"),
	GRADE_D("D","D"),
	GRADE_E("E","E");
	
	/**
	 * 枚举code
	 */
	private String code;
	
	/**
	 *  枚举value
	 */
	private String value;
	
	private static final Map<String,String> enumMap = new LinkedHashMap<String,String>();
	
	static {
		for (PhotographerGradeEnum valEnum : PhotographerGradeEnum.values()) {  
			enumMap.put(valEnum.getCode(), valEnum.getValue());  
        }  
	}
	
	PhotographerGradeEnum(String code,String value){
		this.code = code;
		this.value = value;
	}

	public String getCode() {
		return code;
	}

	public String getValue() {
		return value;
	}

	public static Map<String, String> getEnumMap() {
		return enumMap;
	}
	
}
