/**
 * @FileName: RoomTypeEnum.java
 * @Package com.ziroom.minsu.valenum.house
 * 
 * @author loushuai
 * @created 2017年11月20日 下午4:01:26
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.valenum.house;

import com.asura.framework.base.util.Check;

/**
 * <p>TODO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author loushuai
 * @since 1.0
 * @version 1.0
 */
public enum RoomTypeEnum {

	ROOM_TYPE(0,"房间"),
	HALL_TYPE(1,"客厅");
	
	private Integer code;
	
	private String name;
	
	RoomTypeEnum(Integer code, String name) {
		this.code = code;
		this.name = name;
	}
	
	public static RoomTypeEnum getEnumByCode(Integer code){
		if(Check.NuNObj(code)){
			return null;
		}
		for (RoomTypeEnum temp : RoomTypeEnum.values()) {
			if(code==temp.getCode()){
				return temp;
			}
		}
		return null;
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
