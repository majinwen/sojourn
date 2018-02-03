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
 * <p>IM删除枚举</p>
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
public enum IMIsDelEnum {

	NOT_DEL(0,"不删除"),
	LAN_DEL(1,"房东删除"),
	TEN_DEL(2,"房客删除"),
	ALL_DEL(3,"都删除");
	
	IMIsDelEnum(int code ,String chineseName){
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
	 * 根据code获取对象IsDelEnum
	 *
	 * @author yd
	 * @created 2016年4月16日 下午5:26:36
	 *
	 * @param code
	 * @return
	 */
	public static IMIsDelEnum getIsDelEnum(int code){
		for (final IMIsDelEnum  isDelEnum : IMIsDelEnum.values()) {
			if(isDelEnum.code == code){
				return isDelEnum;
			}
		} 
		return null;
	}
	
	
}
