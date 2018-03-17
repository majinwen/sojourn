/**
 * @FileName: BaseAjaxValue.java
 * @Package com.asura.amp.common.entity
 * 
 * @author zhangshaobin
 * @created 2013-1-16 下午5:20:41
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.amp.common.entity;

/**
 * <p>Ajax返回的Json数据（父类抽象）</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zhangshaobin
 * @since 1.0
 * @version 1.0
 */
public class BaseAjaxValue {
	/**
	 * 成功
	 */
	public final static String CODE_SUCCESS = "200";
	
	/**
	 * 失败
	 */
	public final static String CODE_FAILURE = "300";
	
	/**
	 * 超时
	 */
	public final static String CODE_TIMEOUT = "301";
}
