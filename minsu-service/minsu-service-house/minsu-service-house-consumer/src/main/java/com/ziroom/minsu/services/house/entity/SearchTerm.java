/**
 * @FileName: SearchTerm.java
 * @Package com.ziroom.minsu.services.house.entity
 * 
 * @author bushujie
 * @created 2016年4月18日 下午6:24:03
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.entity;

/**
 * <p>搜索条件</p>
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
public class SearchTerm {
	
	//条件值
	private String termValue;
	
	//条件名称
	private String termName;

	/**
	 * @return the termValue
	 */
	public String getTermValue() {
		return termValue;
	}

	/**
	 * @param termValue the termValue to set
	 */
	public void setTermValue(String termValue) {
		this.termValue = termValue;
	}

	/**
	 * @return the termName
	 */
	public String getTermName() {
		return termName;
	}

	/**
	 * @param termName the termName to set
	 */
	public void setTermName(String termName) {
		this.termName = termName;
	}
}
