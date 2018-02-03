/**
 * @FileName: ImMsgListDto.java
 * @Package com.ziroom.minsu.services.message.dto
 * 
 * @author yd
 * @created 2017年4月1日 下午3:55:18
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.dto;

import java.util.List;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>TODO</p>
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
public class ImMsgListDto  extends BaseEntity{

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = 9148550703900485181L;
	/**
	 * IM 消息会话更新时间
	 */
	private String imLastmodifyTime;
	
	/**
	 * 房东uid
	 */
	private String landlordUid;
	
	/**
	 * 房客uid
	 */
	private String tenantUid;
	/**
	 * 会话id集合
	 */
	private  List<String>  sessionFids;
	/**
	 * @return the imLastmodifyTime
	 */
	public String getImLastmodifyTime() {
		return imLastmodifyTime;
	}
	/**
	 * @param imLastmodifyTime the imLastmodifyTime to set
	 */
	public void setImLastmodifyTime(String imLastmodifyTime) {
		this.imLastmodifyTime = imLastmodifyTime;
	}
	/**
	 * @return the landlordUid
	 */
	public String getLandlordUid() {
		return landlordUid;
	}
	/**
	 * @param landlordUid the landlordUid to set
	 */
	public void setLandlordUid(String landlordUid) {
		this.landlordUid = landlordUid;
	}
	/**
	 * @return the tenantUid
	 */
	public String getTenantUid() {
		return tenantUid;
	}
	/**
	 * @param tenantUid the tenantUid to set
	 */
	public void setTenantUid(String tenantUid) {
		this.tenantUid = tenantUid;
	}
	/**
	 * @return the sessionFids
	 */
	public List<String> getSessionFids() {
		return sessionFids;
	}
	/**
	 * @param sessionFids the sessionFids to set
	 */
	public void setSessionFids(List<String> sessionFids) {
		this.sessionFids = sessionFids;
	}
	
	
}
