/**
 * @FileName: RentTypeEnum.java
 * @Package com.ziroom.minsu.spider.xiaozhunew.entity.enums
 * 
 * @author zl
 * @created 2016年10月14日 下午5:45:18
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
public enum RentTypeEnum {
	
	zhengzu(1,"整套出租"),
	danjian(2,"独立单间"),
	shafa(3,"合住房间");
	
	RentTypeEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

	
	private static Map<Integer,String> codeNameMap=new HashMap<Integer, String>();
	private static Map<String,Integer> nameCodeMap=new HashMap<String,Integer>();
	
	static{
		
		for (RentTypeEnum propertyTypeEnum : RentTypeEnum.values()) {
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

}
