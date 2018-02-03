/**
 * @FileName: EvaluateBothItemVo.java
 * @Package com.ziroom.minsu.services.evaluate.entity
 * 
 * @author jixd
 * @created 2016年8月6日 下午3:41:57
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.evaluate.entity;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>PC评价列表双方互评</p>
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
public class EvaluateBothItemVo extends BaseEntity{
	/**
	 * 序列ID
	 */
	private static final long serialVersionUID = 488811520475937706L;
	/**
	 * 房客uid
	 */
	private String uid;
	/**
	 * 房东uid
	 */
	private String landUid;
	/**
	 * 房源fid
	 */
	private String houseFid;
	/**
	 * 房间fid
	 */
	private String roomFid;
	/**
	 * 出租方式  0整租 1合租
	 */
	private Integer rentWay;
	/**
	 * 用户昵称
	 */
	private String userName;
	/**
	 * 用户头像
	 */
	private String userPic;
	/**
	 * 房屋名称 整租或者分租
	 */
	private String houseName;
	
	/**
	 * 房客评价内容
	 */
	private String tentContent;
	
	/**
	 * 房东评价内容
	 */
	private String lanContent;	
	
	/**
	 * 房东回复内容
	 */
	private String lanReplyContent;
	
	/**
	 * 入住时间取最后更新时间
	 */
	private Date inDate;

	public String getLanReplyContent() {
		return lanReplyContent;
	}

	public void setLanReplyContent(String lanReplyContent) {
		this.lanReplyContent = lanReplyContent;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getLandUid() {
		return landUid;
	}

	public void setLandUid(String landUid) {
		this.landUid = landUid;
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

	public Integer getRentWay() {
		return rentWay;
	}

	public void setRentWay(Integer rentWay) {
		this.rentWay = rentWay;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPic() {
		return userPic;
	}

	public void setUserPic(String userPic) {
		this.userPic = userPic;
	}

	public String getHouseName() {
		return houseName;
	}

	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}

	public String getTentContent() {
		return tentContent;
	}

	public void setTentContent(String tentContent) {
		this.tentContent = tentContent;
	}

	public String getLanContent() {
		return lanContent;
	}

	public void setLanContent(String lanContent) {
		this.lanContent = lanContent;
	}

	public Date getInDate() {
		return inDate;
	}

	public void setInDate(Date inDate) {
		this.inDate = inDate;
	}
}
