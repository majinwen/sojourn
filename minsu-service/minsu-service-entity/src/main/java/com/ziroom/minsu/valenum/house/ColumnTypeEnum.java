/**
 * @FileName: ColumnTypeEnum.java
 * @Package com.ziroom.minsu.valenum.house
 * 
 * @author yd
 * @created 2017年3月16日 下午8:27:41
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.valenum.house;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>TODO</p>
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
public enum ColumnTypeEnum {

	Column_Type_101(101,"亮点标题"),
	Column_Type_102(102,"描述标题"),
	Column_Type_103(103,"文章标题"),
	Column_Type_104(104,"标题1"),
	Column_Type_105(105,"标题2"),
	Column_Type_201(201,"亮点文本"),
	Column_Type_202(202,"文章文本"),
	Column_Type_203(203,"图片注释"),
	Column_Type_204(204,"文章出处"),
	Column_Type_301(301,"图片"),
	Column_Type_302(302,"头图"),
	
	Column_Type_401(401,"视频"),
	Column_Type_501(501,"音频"),;
	
	 ColumnTypeEnum(int value,String name){
		
		this.name = name;
		this.value = value;
	}
	
	private int value;
	
	private String name;
	
	private static final Map<Integer,String> enumMap = new LinkedHashMap<Integer,String>();
	
	static {
		for (ColumnTypeEnum columnTypeEnum : ColumnTypeEnum.values()) {  
			enumMap.put(columnTypeEnum.getValue(), columnTypeEnum.getName());  
        }  
	}

	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(int value) {
		this.value = value;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	public static Map<Integer,String> getEnumMap() {
    	return enumMap;
    }
}
