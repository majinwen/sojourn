/**
 * @FileName: BaseParamDto.java
 * @Package com.ziroom.minsu.api.common.dto
 * 
 * @author bushujie
 * @created 2016年4月20日 下午8:34:26
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.api.common.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

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
	 * 登录token
	 */
	private String loginToken;
	
	/**
	 * 用户uid
	 */
	private String uid;
	
	/**
	 * 页大小
	 */
	@Min(value = 1, message = "{api.limit.min}")
	private int limit = 50;
	
	/**
	 * 当前页
	 */
	@Min(value = 1, message = "{api.page.min}")
	private int page = 1;

	/**
	 * @return the uid
	 */
	public String getUid() {
		return uid;
	}

	/**
	 * @param uid the uid to set
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}

	/**
	 * @return the loginToken
	 */
	public String getLoginToken() {
		return loginToken;
	}

	/**
	 * @param loginToken the loginToken to set
	 */
	public void setLoginToken(String loginToken) {
		this.loginToken = loginToken;
	}

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
	
}
