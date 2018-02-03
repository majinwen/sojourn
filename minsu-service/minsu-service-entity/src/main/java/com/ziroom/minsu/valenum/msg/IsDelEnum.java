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
 * <p>删除枚举</p>
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
public enum IsDelEnum {

	NOT_DEL(0,"不删除"),
	DEL(1,"删除"),
	PRIORITY(2,"空置间夜自动定价或者长期住宿开关(关闭状态，0表示打开)");
	IsDelEnum(int code ,String chineseName){
		
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
	public static IsDelEnum getIsDelEnum(int code){
		for (final IsDelEnum  isDelEnum : IsDelEnum.values()) {
			if(isDelEnum.code == code){
				return isDelEnum;
			}
		} 
		return null;
	}
	
	
}
