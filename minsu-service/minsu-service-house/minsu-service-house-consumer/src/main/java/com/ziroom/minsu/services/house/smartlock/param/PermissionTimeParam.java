/**
 * @FileName: PermissionTimeParam.java
 * @Package com.ziroom.minsu.services.house.smartlock.param
 * 
 * @author jixd
 * @created 2016年6月23日 下午2:48:06
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.smartlock.param;

/**
 * <p>密码有效时间</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author jixd
 * @since 1.0
 * @version 1.0
 */
public class PermissionTimeParam {
	/**
	 * 开始时间
	 */
	private Long begin;
	/**
	 * 结束时间
	 */
	private Long end;
	
	public Long getBegin() {
		return begin;
	}
	public void setBegin(Long begin) {
		this.begin = begin;
	}
	public Long getEnd() {
		return end;
	}
	public void setEnd(Long end) {
		this.end = end;
	}
	
	
}
