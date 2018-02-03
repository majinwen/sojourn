/**
 * @FileName: IsDelEnum.java
 * @Package com.ziroom.minsu.valenum
 * 
 * @author yd
 * @created 2016年4月16日 下午5:22:46
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.valenum.msg;

/**
 * <p>TODO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author lunan
 * @since 1.0
 * @version 1.0
 */
public enum MsgTypeEnum {

	SYSTEM_USER(1,"系统添加"),
	CUSTOM_USER(2,"用户自定义");

	MsgTypeEnum(int code , String name){
		
		this.code = code;
		this.name = name;
	}
	
	/**
	 * 枚举编码
	 */
	private int code;
	
	/**
	 * 名称
	 */
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

	/**
	 * 根据code获取对象targetTypeEnum
	 *
	 * @author lunan
	 */
	public static MsgTypeEnum getTargetTypeEnum(int code){
		for (final MsgTypeEnum targetTypeEnum : MsgTypeEnum.values()) {
			if(targetTypeEnum.code == code){
				return targetTypeEnum;
			}
		} 
		return null;
	}
	
	
}
