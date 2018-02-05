/**
 * @FileName: MsgLable.java
 * @Package com.ziroom.minsu.api.message.dto
 * 
 * @author jixd
 * @created 2016年4月30日 上午11:34:33
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.api.message.dto;

/**
 * <p>房源标签</p>
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
public class MsgLableDto {
	
	/*房东端fid*/
	private String landlordFid;
	/*房源fid*/
	private String houseFid;
	/*标签名称*/
	private String msgKey;
	
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

	public String getMsgKey() {
		return msgKey;
	}

	public void setMsgKey(String msgKey) {
		this.msgKey = msgKey;
	}
	
	
}
