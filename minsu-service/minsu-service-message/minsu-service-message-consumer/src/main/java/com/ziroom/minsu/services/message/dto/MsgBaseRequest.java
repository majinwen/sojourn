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
public class MsgBaseRequest extends PageRequest{

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = -7735293442033310149L;
	
	/**
     * 消息发送人类型（1=房东 2=房客）
     */
    private Integer msgSenderType;

    /**
     * 单条记录是否删除 默认0(0：不删除 1：删除)
     */
    private Integer isDel;

    /**
     * 房源留言关联表fid
     */
    private String msgHouseFid;

    /**
     * 是否已读 默认0（0：已读 1：未读）
     */
    private Integer isRead;
    
    /**
     * 起始时间
     */
    private String startTime;
    
    /**
     * 结束时间
     */
    private String endTime;
    
    /**
     * 房源fid
     */
    private String houseFid;
    
    
	/**
	 * @return the houseFid
	 */
	public String getHouseFid() {
		return houseFid;
	}

	/**
	 * @param houseFid the houseFid to set
	 */
	public void setHouseFid(String houseFid) {
		this.houseFid = houseFid;
	}

	public Integer getMsgSenderType() {
		return msgSenderType;
	}

	public void setMsgSenderType(Integer msgSenderType) {
		this.msgSenderType = msgSenderType;
	}

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	public String getMsgHouseFid() {
		return msgHouseFid;
	}

	public void setMsgHouseFid(String msgHouseFid) {
		this.msgHouseFid = msgHouseFid;
	}

	public Integer getIsRead() {
		return isRead;
	}

	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
    
    

}
