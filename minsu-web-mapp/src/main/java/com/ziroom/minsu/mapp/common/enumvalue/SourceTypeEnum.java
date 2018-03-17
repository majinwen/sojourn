/**
 * @FileName: SourceTypeEnum.java
 * @Package com.ziroom.minsu.mapp.common.enumvalue
 * 
 * @author yd
 * @created 2016年5月25日 下午8:30:34
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.mapp.common.enumvalue;

import org.apache.velocity.app.event.ReferenceInsertionEventHandler.referenceInsertExecutor;

/**
 * <p>登录来源标示</p>
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
public enum SourceTypeEnum {
	
	Android(1,"安卓"),
	Ios(2,"ios");
	
	SourceTypeEnum(int code,String value){
		
		this.code = code;
		this.value = value;
	}
	
	/**
	 * 编号code
	 */
	private int code;
	
	/**
	 * 中文含义
	 */
	private String value;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	/**
	 * 
	 * get SourceTypeEnum by code
	 *
	 * @author yd
	 * @created 2016年5月25日 下午8:34:05
	 *
	 * @param code
	 * @return
	 */
	public static SourceTypeEnum getSourceTypeEnumByCode(int code){
	
		for (SourceTypeEnum sourceTypeEnum : SourceTypeEnum.values()) {
			
			if(sourceTypeEnum.getCode() == code){
				return sourceTypeEnum;
			}
		}
		return null;
	}
	

}
