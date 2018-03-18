package com.ziroom.zrp.service.trading.valenum;

import com.asura.framework.base.util.Check;

public enum GenderEnum {
	MAN(1,"女"),
	WOMAN(2,"男");
	
	private int code;
	private String name;
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
	private GenderEnum(int code, String name) {
		this.code = code;
		this.name = name;
	}
	
	public static String getNameByCode(int code){
		if(Check.NuNObj(code)){
			return null;
		}
		for(GenderEnum gender : GenderEnum.values()){
			if(gender.getCode() == code){
				return gender.getName();
			}
		}
		return null;
	}
	

}
