/**
 * @FileName: EnumVo.java
 * @Package com.ziroom.minsu.services.basedata.entity
 * 
 * @author bushujie
 * @created 2016年3月22日 下午6:41:57
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>枚举值对象</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
public class EnumVo {

	/**
	 * 枚举值
	 */
	private String key;
	
	 /**
	  * 枚举值显示名称
	  */
	private String text;
	
	/**
	 * 设施 配套的icon2 URL地址
	 */
	private String iconTwoUrl;
	
	/**
	 * 设施 配套的icon2 URL地址
	 */
	private String iconThrUrl;


	/**
	 * 子项的值集合
	 */
	private List<EnumVo> subEnumVals=new ArrayList<EnumVo>();
	

	/**
	 * @return the iconTwoUrl
	 */
	public String getIconTwoUrl() {
		return iconTwoUrl;
	}

	/**
	 * @param iconTwoUrl the iconTwoUrl to set
	 */
	public void setIconTwoUrl(String iconTwoUrl) {
		this.iconTwoUrl = iconTwoUrl;
	}

	/**
	 * @return the iconThrUrl
	 */
	public String getIconThrUrl() {
		return iconThrUrl;
	}

	/**
	 * @param iconThrUrl the iconThrUrl to set
	 */
	public void setIconThrUrl(String iconThrUrl) {
		this.iconThrUrl = iconThrUrl;
	}

	/**
	 * @return the subEnumVals
	 */
	public List<EnumVo> getSubEnumVals() {
		return subEnumVals;
	}

	/**
	 * @param subEnumVals the subEnumVals to set
	 */
	public void setSubEnumVals(List<EnumVo> subEnumVals) {
		this.subEnumVals = subEnumVals;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
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
}
