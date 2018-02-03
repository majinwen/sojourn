/**
 * @FileName: HousePicTypeEnum.java
 * @Package com.ziroom.minsu.valenum.house
 * 
 * @author bushujie
 * @created 2016年4月14日 上午12:15:54
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.valenum.top;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 
 * <p>静态资源类型</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liujun
 * @since 1.0
 * @version 1.0
 */
public enum StaticResourceTypeEnum {
	TEXT(1,"文本"),
	PIC(2,"图片");
	
	/** code */
	private int code;
	
	/** 名称 */
	private String name;
	
	private static final Map<Integer,String> enumMap = new LinkedHashMap<Integer,String>();
	
	static {
		for (StaticResourceTypeEnum valEnum : StaticResourceTypeEnum.values()) {  
			enumMap.put(valEnum.getCode(), valEnum.getName());  
        }  
	}
	
	StaticResourceTypeEnum(int code,String name){
		 this.code = code;
	     this.name = name;
	}
	
	public static StaticResourceTypeEnum getEnumByCode(int code) {
        for (final StaticResourceTypeEnum enumeration : StaticResourceTypeEnum.values()) {
            if (enumeration.getCode() == code) {
                return enumeration;
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

	public static Map<Integer,String> getEnumMap() {
    	return enumMap;
    }
}
