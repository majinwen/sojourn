/**
 * @FileName: ChatRecordsRequest.java
 * @Package com.ziroom.minsu.api.im.controller.dto
 * 
 * @author yd
 * @created 2016年9月18日 下午2:26:30
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.web.im.chat.controller.dto;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p> 消息回复 请求参数 </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author lunan
 * @since 1.0
 * @version 1.0
 */
public class MsgCustomizationRequest extends BaseEntity{

	private static final long serialVersionUID = -4791125773394763120L;

	private String uid;

	/**
     * 房客uid
     */
    private String tenantUid;

	/**
     * 房东uid
     */
    private String landlordUid;
	/**
	 * 当前用户类型（1=房东 2=房客）
	 */
	private Integer senderType;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Integer getSenderType() {
		return senderType;
	}

	public void setSenderType(Integer senderType) {
		this.senderType = senderType;
	}

	public String getTenantUid() {
		return tenantUid;
	}

	public void setTenantUid(String tenantUid) {
		this.tenantUid = tenantUid;
	}

	public String getLandlordUid() {
		return landlordUid;
	}

	public void setLandlordUid(String landlordUid) {
		this.landlordUid = landlordUid;
	}
    
    
}
