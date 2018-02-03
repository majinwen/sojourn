/**
 * @FileName: FieldTextVo.java
 * @Package org.eclipse.jdt.ui
 * 
 * @author bushujie
 * @created 2017年6月14日 下午2:01:56
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.common.entity;

/**
 * <p>正常字段对象</p>
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
public class FieldTextVo <T> extends FieldBaseVo{
	
	/**
	 * 值
	 */
	private T value;
	
	public FieldTextVo(boolean isEdit,T value){
		this.value=value;
		super.setIsEdit(isEdit);
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
}
