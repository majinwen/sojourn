/**
 * @FileName: HouseTypeEnum.java
 * @Package com.ziroom.minsu.spider.xiaozhunew.entity.enums
 * 
 * @author zl
 * @created 2016年10月14日 下午5:38:21
 * 
 * Copyright 2016-2025 ziroom
 */
package com.ziroom.minsu.spider.xiaozhunew.entity.enums;

import java.util.HashMap;
import java.util.Map;


/**
 * <p></p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zl
 * @since 1.0
 * @version 1.0
 */
public enum HouseTypeEnum {
	
	yiju(1,"一居"),
	liangju(2,"二居"),
	sanju(3,"三居"),
	siju(4,"四居"),
	sijuyishang(5,"其他");
	
	HouseTypeEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }
	
	
	private static Map<Integer,String> codeNameMap=new HashMap<Integer, String>();
	private static Map<String,Integer> nameCodeMap=new HashMap<String,Integer>();
	
	static{
		
		for (HouseTypeEnum propertyTypeEnum : HouseTypeEnum.values()) {
			codeNameMap.put(propertyTypeEnum.getCode(), propertyTypeEnum.getName());
			nameCodeMap.put(propertyTypeEnum.getName(), propertyTypeEnum.getCode());
		}
	}
	
	public static Integer getCodeByName(String name){
		if (name==null || name.length()==0) {
			return null;
		}		
		return nameCodeMap.get(name);
	}
	
	public static String geNametByCode(Integer code){
		if (code==null) {
			return null;
		}		
		return codeNameMap.get(code);
	}


    /** code */
    private int code;

    /** 名称 */
    private String name;

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
    
    public void getValues(){
    	
    }
    
}
