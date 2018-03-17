/**
 * @FileName: BaseParamDto.java
 * @Package com.ziroom.minsu.api.common.dto
 * 
 * @author bushujie
 * @created 2016年4月20日 下午8:34:26
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.mapp.common.dto;


/**
 * <p>基础参数</p>
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
public class  BaseParamDto {
	
	/**
	 * 页大小
	 */
	private int limit = 50;
	
	/**
	 * 当前页
	 */
	private int page = 1;
	/**
	 * 用户uid
	 */
	private String uid;
	


	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	
}
