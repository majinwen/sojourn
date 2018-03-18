/**
 * @FileName: NameValueEntity.java
 * @Package com.ziroom.minsu.report.common.entity
 * 
 * @author bushujie
 * @created 2016年9月18日 下午2:56:26
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.report.basedata.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>name和value格式json</p>
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
public class NameValueEntity {
	
	private String name;
	
	private List<Double> value=new ArrayList<Double>();
	
	/**
	 * @return the value
	 */
	public List<Double> getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(List<Double> value) {
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
}
