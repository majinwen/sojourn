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
 * @author jixd
 * @since 1.0
 * @version 1.0
 */
public enum TargetTypeEnum {

	SINGLE_USER(1,"单个用户","users"),
	ALL_LANDLORD(2,"全部房东","chatgroups"),
	ALL_TENANT(3,"全部房客","chatrooms"),
	ALL_USER(4,"全部用户","");
	
	TargetTypeEnum(int code ,String name,String huanxinTargetType){
		
		this.code = code;
		this.name = name;
		this.huanxinTargetType = huanxinTargetType;
	}
	
	/**
	 * 枚举编码
	 */
	private int code;
	
	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * 环信发消息类型
	 */
	private String huanxinTargetType;

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
	 * @return the huanxinTargetType
	 */
	public String getHuanxinTargetType() {
		return huanxinTargetType;
	}

	/**
	 * @param huanxinTargetType the huanxinTargetType to set
	 */
	public void setHuanxinTargetType(String huanxinTargetType) {
		this.huanxinTargetType = huanxinTargetType;
	}

	/**
	 * 根据code获取对象targetTypeEnum
	 *
	 * @author jixd
	 */
	public static TargetTypeEnum getTargetTypeEnum(int code){
		for (final TargetTypeEnum  targetTypeEnum : TargetTypeEnum.values()) {
			if(targetTypeEnum.code == code){
				return targetTypeEnum;
			}
		} 
		return null;
	}
	
	
}
