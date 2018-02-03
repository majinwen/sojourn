/**
 * @FileName: IsGloabalEnum.java
 * @Package com.ziroom.minsu.valenum.msg
 * 
 * @author yd
 * @created 2016年4月18日 上午10:55:25
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.valenum.msg;

/**
 * <p>是否适应于全局</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
public enum IsGloabalEnum {

	NOT_ADAPT_GOLBAL(1,"适应全局"),
	ADAPT_GOLBAL(1,"适应全局");
	
	IsGloabalEnum(int code,String chineseName){
		this.code = code;
		this.chineseName = chineseName;
	}
	
	/**
	 * 枚举编码
	 */
	private int code;
	
	/**
	 * 中文名称
	 */
	private String chineseName;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getChineseName() {
		return chineseName;
	}

	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}
	
	/**
	 * 
	 * get  IsGloabalEnum by code
	 *
	 * @author yd
	 * @created 2016年4月18日 上午10:59:07
	 *
	 * @param code
	 * @return
	 */
	public static IsGloabalEnum getIsGloabalEnumByCode(int code){
		
		for (IsGloabalEnum isGloabalEnum : IsGloabalEnum.values()) {
			if(isGloabalEnum.getCode() == code){
				return isGloabalEnum;
			}
		}
		
		return null;
	}
}
