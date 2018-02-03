/**
 * @FileName: AuthIdentifyEnum.java
 * @Package com.ziroom.minsu.valenum.base
 * 
 * @author loushuai
 * @created 2017年9月1日 下午7:45:03
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.valenum.base;

/**
 * <p>自如网标识</p>
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
public enum AuthIdentifyEnum {

	ZIROOM_MINSU_IM(0,"自如民宿","ZIROOM_MINSU_IM"),
	ZIROOM_ZRY_IM(1,"自如驿","ZIROOM_MINSU_IM"),
	ZIROOM_CHANGZU_IM(2,"自如长租","ZIROOM_CHANGZU_IM");
	
	private int code;
	
	private String name;
	
	private String type;

	
	private AuthIdentifyEnum(int code, String name, String type) {
		this.code = code;
		this.name = name;
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public static AuthIdentifyEnum getByCode(int code){
		for(AuthIdentifyEnum temp : AuthIdentifyEnum.values()){
			if(code==temp.getCode()){
				return temp;
			}
		}
		return null;
	}

	
}
