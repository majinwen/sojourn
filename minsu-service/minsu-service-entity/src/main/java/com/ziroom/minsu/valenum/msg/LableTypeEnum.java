/**
 * @FileName: LableTypeEnum.java
 * @Package com.ziroom.minsu.valenum.msg
 * 
 * @author yd
 * @created 2016年4月18日 上午11:12:47
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.valenum.msg;

/**
 * <p>标签类型</p>
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
public enum LableTypeEnum {
	
	HOUSE_LABLE(1,"房源标签");
	
	LableTypeEnum(int code,String chineseName){
		
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
	 * get LableTypeEnum by code
	 *
	 * @author yd
	 * @created 2016年4月18日 上午11:16:36
	 *
	 * @param code
	 * @return
	 */
	public static LableTypeEnum getLableTypeEnumByCode(int code){
		
		for (LableTypeEnum lableTypeEnum : LableTypeEnum.values()) {
			if(code == lableTypeEnum.getCode()){
				return lableTypeEnum;
			}
		}
		return null;
	}

}
