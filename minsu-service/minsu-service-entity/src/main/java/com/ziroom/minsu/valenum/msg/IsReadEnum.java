/**
 * @FileName: IsReadEnum.java
 * @Package com.ziroom.minsu.valenum.msg
 * 
 * @author yd
 * @created 2016年4月16日 下午5:30:07
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.valenum.msg;

/**
 * <p>是否已读的索引</p>
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
public enum IsReadEnum {

	READ(1,"已读"),
	UNREAD(0,"未读");

	IsReadEnum(int code ,String chineseName){
		
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
	 * 根据code获取对象IsReadEnum
	 *
	 * @author yd
	 * @created 2016年4月16日 下午5:26:36
	 *
	 * @param code
	 * @return
	 */
	public static IsReadEnum getIsReadEnum(int code){
		for (final IsReadEnum  isReadEnum : IsReadEnum.values()) {
			if(isReadEnum.code == code){
				return isReadEnum;
			}
		} 
		return null;
	}
	
}
