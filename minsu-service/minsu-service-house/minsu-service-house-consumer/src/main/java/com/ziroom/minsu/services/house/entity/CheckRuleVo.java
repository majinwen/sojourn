/**
 * @FileName: CheckRuleVo.java
 * @Package com.ziroom.minsu.services.house.entity
 * 
 * @author yd
 * @created 2016年12月7日 下午2:19:07
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.entity;

import java.io.Serializable;

/**
 * <p>入住规则 vo</p>
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
public class CheckRuleVo implements Serializable {

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = -9044343501469530705L;
	
	/**
	 * 展示名称
	 */
	private String name;
	
	/**
	 * 展示值
	 */
	private String val;
	
	/**
	 * 点击类型  0=不可点击  1=点击  默认0
	 */
	private int clickType;
	
	/**
	 * 事件类型
	 */
	private String type;

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

	/**
	 * @return the val
	 */
	public String getVal() {
		return val;
	}

	/**
	 * @param val the val to set
	 */
	public void setVal(String val) {
		this.val = val;
	}

	/**
	 * @return the clickType
	 */
	public int getClickType() {
		return clickType;
	}

	/**
	 * @param clickType the clickType to set
	 */
	public void setClickType(int clickType) {
		this.clickType = clickType;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	

}
