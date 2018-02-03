/**
 * @FileName: FieldTextValueVo.java
 * @Package com.ziroom.minsu.services.common.entity
 * 
 * @author bushujie
 * @created 2017年6月14日 下午5:40:28
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.common.entity;

/**
 * <p>值和描述字段Vo</p>
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
public class FieldTextValueVo <T> extends FieldBaseVo {
	
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
	

	public FieldTextValueVo(T value,String text,boolean isEdit){
		this.value=value;
		this.text=text;
		super.setIsEdit(isEdit);
	}
	
	public FieldTextValueVo(T value,String text,boolean isEdit,String explain){
		this.value=value;
		this.text=text;
		super.setIsEdit(isEdit);
		this.explain=explain;
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
}
