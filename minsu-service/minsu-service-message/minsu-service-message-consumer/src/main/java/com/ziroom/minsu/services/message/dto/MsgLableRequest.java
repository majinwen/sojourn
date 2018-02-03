/**
 * @FileName: MsgLableRequest.java
 * @Package com.ziroom.minsu.services.message.dto
 * 
 * @author yd
 * @created 2016年4月16日 下午7:18:27
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.dto;

import com.ziroom.minsu.services.common.dto.PageRequest;

/**
 * <p>标签请求参数</p>
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
public class MsgLableRequest extends PageRequest{

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = 8186938943868318509L;
	
	/**
	 * 留言关键词
	 */
	private String msgKey;
	/**
	 * 房东uid 如果适应于全局 house_fid 为0
	 */
	private String landlordFid;

	/**
	 * 房源fid  如果适应于全局 house_fid 为0
	 */
	private String houseFid;

	/**
	 * 是否适应于全局 默认 0（0：不适应全局 1：适应全局）
	 */
	private Integer isGlobal;

	/**
	 * 是否发布 默认0 发布（0：发布 1：不发布）
	 */
	private Integer isRelease;
	
	/**
	 * 标签类型 默认1 （1=房源标签）
	 */
	private Integer lableType;
	
	/**
	 * 关键词所关联内容
	 */
	private String msgContent;

	public String getMsgKey() {
		return msgKey;
	}

	public void setMsgKey(String msgKey) {
		this.msgKey = msgKey;
	}

	public String getLandlordFid() {
		return landlordFid;
	}

	public void setLandlordFid(String landlordFid) {
		this.landlordFid = landlordFid;
	}

	public String getHouseFid() {
		return houseFid;
	}

	public void setHouseFid(String houseFid) {
		this.houseFid = houseFid;
	}

	public Integer getIsGlobal() {
		return isGlobal;
	}

	public void setIsGlobal(Integer isGlobal) {
		this.isGlobal = isGlobal;
	}

	public Integer getIsRelease() {
		return isRelease;
	}

	public void setIsRelease(Integer isRelease) {
		this.isRelease = isRelease;
	}

	public Integer getLableType() {
		return lableType;
	}

	public void setLableType(Integer lableType) {
		this.lableType = lableType;
	}

	public String getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}
	
	

}
