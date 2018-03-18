/**
 * @FileName: RoomTypeEnum.java
 * @Package com.ziroom.minsu.spider.airbnb.entity.enums
 * 
 * @author zl
 * @created 2016年10月9日 下午9:36:43
 * 
 * Copyright 2016-2025 ziroom
 */
package com.ziroom.minsu.spider.airbnb.entity.enums;

import java.util.HashMap;
import java.util.Map;


/**
 * 房间类型
 * <p>TODO</p>
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
public enum RoomTypeEnum {
	

	ENTIRE("Entire home/apt","整套房子/公寓"),
	PRIVATE("Private room","独立房间"),
	SHARED("Shared room","合住房间");
	
	RoomTypeEnum(String code,String name) {
		this.code = code;
		this.name = name; 
	}
	
	
	private static Map<String,String> codeNameMap=new HashMap<String, String>();
	private static Map<String,String> nameCodeMap=new HashMap<String,String>();
	
	static{
		
		for (RoomTypeEnum roomTypeEnum : RoomTypeEnum.values()) {
			codeNameMap.put(roomTypeEnum.getCode(), roomTypeEnum.getName());
			nameCodeMap.put(roomTypeEnum.getName(), roomTypeEnum.getCode());
		}
	}
	
	public static String getCodeByName(String name){
		if (name==null || name.length()==0) {
			return null;
		}		
		return nameCodeMap.get(name);
	}
	
	public static String geNametByCode(String code){
		if (code==null) {
			return null;
		}		
		return codeNameMap.get(code);
	}

	/** code */
	private String code;

	/** 名称 */
	private String name;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	} 
	
	
	public static String[] getCodes(){
		String[] codes=new String[RoomTypeEnum.values().length];
		for (int i=0;i<codes.length;i++) {
			codes[i]=RoomTypeEnum.values()[i].getCode();
        }
		return codes;
	} 
    
}
