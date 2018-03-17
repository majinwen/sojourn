/**
 * @FileName: BedTypeSizeVo.java
 * @Package com.ziroom.minsu.mapp.house.vo
 * 
 * @author yd
 * @created 2016年8月23日 下午7:37:33
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.mapp.house.vo;

import java.io.Serializable;
import java.util.List;

/**
 * <p>床位类型和大小封装</p>
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
public class BedTypeSizeVo implements Serializable {

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = 5615214118867447414L;

	/**
	 * 床类型key
	 */
	private String value;

	/**
	 * 床类型值
	 */
	private String text;


	/**
	 * 床大小
	 */
	private List<BedTypeSizeVo> children;


	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}


	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}


	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}


	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}


	/**
	 * @return the children
	 */
	public List<BedTypeSizeVo> getChildren() {
		return children;
	}


	/**
	 * @param children the children to set
	 */
	public void setChildren(List<BedTypeSizeVo> children) {
		this.children = children;
	}
	
	
	
}
