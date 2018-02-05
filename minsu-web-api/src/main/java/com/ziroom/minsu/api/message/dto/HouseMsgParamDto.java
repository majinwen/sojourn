/**
 * @FileName: SysMsgParamDto.java
 * @Package com.ziroom.minsu.api.common.dto
 * 
 * @author jixd
 * @created 2016年4月21日 上午11:53:12
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.api.message.dto;

import com.ziroom.minsu.api.common.dto.BaseParamDto;

/**
 * <p>房源留言查询参数</p>
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
public class HouseMsgParamDto extends BaseParamDto {
	/**
     * 业务编号,t_msg_house fid 
     */
    private String fid;
    /**
     *  t_msg_base msg_house_fid
     */
    private String msgHouseFid;
    /**
     * 房源fid
     */
    private String houseFid;

    /**
     * 房间fid
     */
    private String roomFid;

    /**
     * 床位fid
     */
    private String bedFid;
    /**
     * 房东uid
     */
    private String uid;

    /**
     * 出租类型 0：整租，1：合租，2：床位
     */
    private Integer rentWay;
    
    /**
     * 消息发送人类型（1=房东 2=房客）
     */
    private Integer type;

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public String getHouseFid() {
		return houseFid;
	}

	public void setHouseFid(String houseFid) {
		this.houseFid = houseFid;
	}

	public String getRoomFid() {
		return roomFid;
	}

	public void setRoomFid(String roomFid) {
		this.roomFid = roomFid;
	}

	public String getBedFid() {
		return bedFid;
	}

	public void setBedFid(String bedFid) {
		this.bedFid = bedFid;
	}

	
	public Integer getRentWay() {
		return rentWay;
	}

	public void setRentWay(Integer rentWay) {
		this.rentWay = rentWay;
	}

	public String getMsgHouseFid() {
		return msgHouseFid;
	}

	public void setMsgHouseFid(String msgHouseFid) {
		this.msgHouseFid = msgHouseFid;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	
    
}
