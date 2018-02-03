/**
 * @FileName: FieldSelectVo.java
 * @Package com.ziroom.minsu.services.common.entity
 * 
 * @author bushujie
 * @created 2017年6月14日 下午2:49:12
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.common.entity;

/**
 * <p>选择项对象</p>
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
public class FieldSelectVo<T> {
	
	/**
	 * 文字描述
	 */
	private String text;
	
	/**
	 * 值
	 */
	private T value;
	
	/**
	 * 说明
	 */
	private String explain;
	
	/**
	 * 是否被选中
	 */
	private boolean isSelect;
	
	public FieldSelectVo(T value,String text,boolean isSelect){
		super();
		this.value=value;
		this.text=text; 
		this.isSelect=isSelect;
	}

	public FieldSelectVo(T value,String text,String explain,boolean isSelect){
		this.value=value;
		this.text=text;
		this.explain=explain;
		this.isSelect=isSelect;
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
	 * @return the value
	 */
	public T getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(T value) {
		this.value = value;
	}

	/**
	 * @return the explain
	 */
	public String getExplain() {
		return explain;
	}

	/**
	 * @param explain the explain to set
	 */
	public void setExplain(String explain) {
		this.explain = explain;
	}

	/**
	 * @return the isSelect
	 */
	public boolean getIsSelect() {
		return isSelect;
	}

	/**
	 * @param isSelect the isSelect to set
	 */
	public void setIsSelect(boolean isSelect) {
		this.isSelect = isSelect;
	}
}
