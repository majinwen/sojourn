/**
 * @FileName: NameValuePair.java
 * @Package com.ziroom.minsu.services.common.entity
 * 
 * @author liujun
 * @created 2016年9月18日
 * 
 * Copyright 2016 ziroom
 */
package com.ziroom.minsu.services.common.entity;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>名值对实体</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liujun
 * @since 1.0
 * @version 1.0
 */
public class NameValuePair<N, V> extends BaseEntity {
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -5750051302360017952L;
	
	private N name;
	
	private V value;
	
	/**
     * Default constructor.
     * 
     */
    public NameValuePair() {
        this (null, null);
    }
	
	/**
	 * @param name
	 * @param value
	 */
	public NameValuePair(N name, V value) {
		this.name = name;
		this.value = value;
	}

	public N getName() {
		return name;
	}
	
	public void setName(N name) {
		this.name = name;
	}
	
	public V getValue() {
		return value;
	}
	
	public void setValue(V value) {
		this.value = value;
	}
}
