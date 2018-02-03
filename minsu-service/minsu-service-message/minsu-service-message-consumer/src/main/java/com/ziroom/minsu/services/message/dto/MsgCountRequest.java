/**
 * @FileName: MsgBaseRequest.java
 * @Package com.ziroom.minsu.services.message.dto
 * 
 * @author yd
 * @created 2016年4月16日 下午4:41:49
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.dto;

import java.io.Serializable;

import com.ziroom.minsu.services.common.dto.PageRequest;

/**
 * <p>留言基本查询参数</p>
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
public class MsgCountRequest implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1137131099931674292L;

	/**
     * 消息发送人类型（1=房东 2=房客）
     */
    private Integer msgSenderType;
    /**
     * 房东uid
     */
    private String landlordUid;
    /**
     * 房客uid
     */
    private String tenantUid;
    
	public Integer getMsgSenderType() {
		return msgSenderType;
	}
	public void setMsgSenderType(Integer msgSenderType) {
		this.msgSenderType = msgSenderType;
	}
	public String getLandlordUid() {
		return landlordUid;
	}
	public void setLandlordUid(String landlordUid) {
		this.landlordUid = landlordUid;
	}
	public String getTenantUid() {
		return tenantUid;
	}
	public void setTenantUid(String tenantUid) {
		this.tenantUid = tenantUid;
	}
    
    
}
