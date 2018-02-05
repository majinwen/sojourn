/**
 * @FileName: ChatRecordsRequest.java
 * @Package com.ziroom.minsu.api.im.controller.dto
 * 
 * @author yd
 * @created 2016年9月18日 下午2:26:30
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.api.im.controller.dto;

import javax.validation.constraints.Min;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.minsu.api.common.dto.BaseParamDto;

/**
 * <p> 聊天记录 请求参数 </p>
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
public class ChatRecordsRequest extends BaseEntity{

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = 6428878578375475571L;

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
	
	/**
	 * 每页展示数量
	 */
	private Integer limit;
	

	/**
	 * 当前页码
	 */
	private Integer page;


	/**
	 * @return the limit
	 */
	public Integer getLimit() {
		return limit;
	}

	/**
	 * @param limit the limit to set
	 */
	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	/**
	 * @return the page
	 */
	public Integer getPage() {
		return page;
	}

	/**
	 * @param page the page to set
	 */
	public void setPage(Integer page) {
		this.page = page;
	}

	/**
	 * @return the senderType
	 */
	public Integer getSenderType() {
		return senderType;
	}

	/**
	 * @param senderType the senderType to set
	 */
	public void setSenderType(Integer senderType) {
		this.senderType = senderType;
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
    
    
}
