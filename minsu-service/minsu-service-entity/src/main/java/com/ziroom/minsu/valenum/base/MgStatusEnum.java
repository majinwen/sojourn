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
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 动画状态枚举
 * @author yanb
 * @since 1.0
 * @version 1.0
 */
public enum MgStatusEnum {

	MG_STATUS_OFF(0,"首页动画关闭"),
	MG_STATUS_OPEN_CACHE(1,"展示,用缓存"),
	MG_STATUS_OPEN_LOAD(2,"展示并拉取新的动画");

	private int code;

	private String name;



	private MgStatusEnum(int code, String name) {
		this.code = code;
		this.name = name;
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
	
	public static MgStatusEnum getByCode(int code){
		for(MgStatusEnum temp : MgStatusEnum.values()){
			if(code==temp.getCode()){
				return temp;
			}
		}
		return null;
	}

	
}
