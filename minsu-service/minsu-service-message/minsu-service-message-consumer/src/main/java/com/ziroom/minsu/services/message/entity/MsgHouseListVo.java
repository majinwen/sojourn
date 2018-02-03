/**
 * @FileName: MsgKeyVo.java
 * @Package com.ziroom.minsu.services.message.entity
 * 
 * @author yd
 * @created 2016年4月18日 上午11:27:34
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.entity;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

/**
 * 
 * <p>消息列表查询列表</p>
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
public class MsgHouseListVo extends BaseEntity{

	/**
	 * 序列ID
	 */
	private static final long serialVersionUID = -2310932692120198562L;


    /**
     * 消息体（即：消息内容）
     */
    private String msgContent;

    /**
     * 消息发送人类型（1=房东 2=房客）
     */
    private Integer msgSenderType;

    /**
     * 房源留言关联表fid
     */
    private String msgHouseFid;

    /**
     * 是否已读 默认0（0：已读 1：未读）
     */
    private Integer isRead;
    
    /**
     * 创建时间
     */
    private Date createTime;
	
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
     * 房客uid
     */
    private String tenantUid;

    /**
     * 房东uid
     */
    private String landlordUid;

    /**
     * 出租类型 0：整租，1：合租，2：床位
     */
    private Integer rentWay;
    /**
     * 房源名称
     */
    private String houseName;

    /**
     * 房客头像
     */
    private String tenantPicUrl;
    
    /**
     * 房东头像
     */
    private String lanlordPicUrl;
	/**
	 * 用户昵称
	 */
	private String nickName;
	
	/**
	 * 消息未读数
	 */
	private Integer unReadNum;
	
	/**
	 * 最后修改时间
	 */
	private Date imLastmodifyTime;
	

	/**
	 * @return the imLastmodifyTime
	 */
	public Date getImLastmodifyTime() {
		return imLastmodifyTime;
	}

	/**
	 * @param imLastmodifyTime the imLastmodifyTime to set
	 */
	public void setImLastmodifyTime(Date imLastmodifyTime) {
		this.imLastmodifyTime = imLastmodifyTime;
	}

	public String getLanlordPicUrl() {
		return lanlordPicUrl;
	}

	public void setLanlordPicUrl(String lanlordPicUrl) {
		this.lanlordPicUrl = lanlordPicUrl;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getTenantPicUrl() {
		return tenantPicUrl;
	}

	public void setTenantPicUrl(String tenantPicUrl) {
		this.tenantPicUrl = tenantPicUrl;
	}

	public String getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}

	public Integer getMsgSenderType() {
		return msgSenderType;
	}

	public void setMsgSenderType(Integer msgSenderType) {
		this.msgSenderType = msgSenderType;
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

	public Integer getRentWay() {
		return rentWay;
	}

	public void setRentWay(Integer rentWay) {
		this.rentWay = rentWay;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	public String getHouseName() {
		return houseName;
	}

	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}

	public Integer getUnReadNum() {
		return unReadNum;
	}

	public void setUnReadNum(Integer unReadNum) {
		this.unReadNum = unReadNum;
	}
}
