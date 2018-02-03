/**
 * @FileName: StatsTenantEvaRequest.java
 * @Package com.ziroom.minsu.services.evaluate.dto
 * 
 * @author yd
 * @created 2016年4月8日 下午6:23:42
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.evaluate.dto;

import java.io.Serializable;

/**
 * <p>评价请求参数</p>
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
public class StatsTenantEvaRequest implements Serializable{

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = 641811091160373146L;
	
	/**
	 * 业务fid
	 */
	private String fid;
	
	/**
	 * 房客的uid
	 */
	private String tenantUid;

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public String getTenantUid() {
		return tenantUid;
	}

	public void setTenantUid(String tenantUid) {
		this.tenantUid = tenantUid;
	}

	
	
	

}
