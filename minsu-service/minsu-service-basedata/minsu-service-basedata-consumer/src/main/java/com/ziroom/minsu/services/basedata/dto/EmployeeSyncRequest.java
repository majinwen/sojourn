/**
 * @FileName: EmployeeSyncRequest.java
 * @Package com.ziroom.minsu.services.basedata.dto
 * 
 * @author jixd
 * @created 2016年4月23日 下午4:19:18
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.basedata.dto;

/**
 * <p>请求参数</p>
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
public class EmployeeSyncRequest {
	/**
	 * 当前页
	 */
	private int page;
	/**
	 * 每页大小
	 */
	private int size;
	/**
	 * 开始时间
	 */
	private String startDate;
	/**
	 * 是否启用  1启用
	 */
	private int isEnable;
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public int getIsEnable() {
		return isEnable;
	}
	public void setIsEnable(int isEnable) {
		this.isEnable = isEnable;
	}
	
}
