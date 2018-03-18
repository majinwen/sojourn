/**
 * @FileName: RoomPropertyTypeEnum.java
 * @Package com.ziroom.minsu.spider.airbnb.entity.enums
 * 
 * @author zl
 * @created 2016年10月9日 下午9:46:16
 * 
 * Copyright 2016-2025 ziroom
 */
package com.ziroom.minsu.spider.airbnb.entity.enums;

import java.util.HashMap;
import java.util.Map;


/**
 * 
 * 房源类型
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
public enum RoomPropertyTypeEnum {

	APARTMENT(1,"公寓"),
	DETACHEDHOUSE(2,"独立屋"),
	BEDANDBREAKFAST(3,"住宿加早餐"),
	LOGCABIN(4,"小木屋"),
	TREEHOUSE(6,"树屋"),
	DORMITORY(9,"宿舍"),
	LIGHTHOUSE(10,"灯塔"),
	VILLA(11,"别墅"),
	ISLAND(19,"岛屿"),
	KEEKWILEEHOUSE(23,"土房"),
	CABIN(24,"小屋"),
	CAMPER(32,"露营车/房车"),
	OTHER(33,"其它"),
	TENT(34,"帐篷"),
	ATTIC(35,"阁楼"),
	ROWHOUSE(36,"连栋住宅"),
	APARTMENT2(37,"公寓"),
	BUNGLOW(38,"小平房"),
	HOTEL(40,"宾馆"),
	WHOLEFLOOR(41,"整层楼");
	
	RoomPropertyTypeEnum(Integer code,String name) {
		this.code = code;
		this.name = name; 
	}

	/** code */
	private Integer code;

	/** 名称 */
	private String name;
	
	private static Map<Integer,String> codeNameMap=new HashMap<Integer, String>();
	private static Map<String,Integer> nameCodeMap=new HashMap<String,Integer>();
	
	static{
		
		for (RoomPropertyTypeEnum roomPropertyTypeEnum : RoomPropertyTypeEnum.values()) {
			codeNameMap.put(roomPropertyTypeEnum.getCode(), roomPropertyTypeEnum.getName());
			nameCodeMap.put(roomPropertyTypeEnum.getName(), roomPropertyTypeEnum.getCode());
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
	

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	} 
		
    
}
