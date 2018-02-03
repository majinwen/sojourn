/**
 * @FileName: HousePicTypeEnum.java
 * @Package com.ziroom.minsu.valenum.house
 * 
 * @author bushujie
 * @created 2016年4月14日 上午12:15:54
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.valenum.house;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>房源图片类型枚举</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
public enum HousePicTypeEnum {
	// 图片类型(0:卧室,1:客厅,2:厨房,3:卫生间,4:室外)
	WS(0,"卧室",2,10),
	KT(1,"客厅",2,20),
	CF(2,"厨房",0,10),
	WSJ(3,"卫生间",1,10),
	SW(4,"室外",0,10);
	
	public static final int MINIMUM_NUM_OF_HOUSE_PIC = 10;
	
	/** code */
	private int code;
	
	/** 名称 */
	private String name;
	
	/** 照片最小数量 */
	private int min;
	
	/** 照片最大数量 */
	private int max;
	
	private static final Map<Integer,String> enumMap = new LinkedHashMap<Integer,String>();
	
	static {
		for (HousePicTypeEnum valEnum : HousePicTypeEnum.values()) {  
			enumMap.put(valEnum.getCode(), valEnum.getName());  
        }  
	}
	
	HousePicTypeEnum(int code,String name,int min,int max){
		 this.code = code;
	     this.name = name;
	     this.min = min;
	     this.max = max;
	}
	
	public static HousePicTypeEnum getEnumByCode(int code) {
        for (final HousePicTypeEnum enumeration : HousePicTypeEnum.values()) {
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
    
    public int getMin() {
    	return min;
    }
    
    public int getMax() {
    	return max;
    }

	public static Map<Integer,String> getEnumMap() {
    	return enumMap;
    }
}
