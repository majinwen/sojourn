package com.ziroom.minsu.services.house.dto;

import java.util.Date;

/**
 * 
 * <p>房间信息封装</p>
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
public class HouseRoomInfoDto {

	// 逻辑id
	private String fid;

	// 房源基本信息表逻辑id
	private String houseBaseFid;

	// 房间名称
	private String roomName;


	// 房间状态(10:待发布,11:已发布,20:信息审核通过,21:信息审核未通过,30:照片审核未通过,40:上架,41:下架,50:强制下架)
	private Integer roomStatus;

	// 房间价格(分)
	private Integer roomPrice;

	// 创建日期
	private Date createDate;

	//房间编号
	private String roomSn;

	//独立房间默认图片fid
	private String defaultPicFid;

	//旧房源默认图片fid
	private String oldDefaultPicFid;
	
	// 房间完成状态中文  0=未完成  1=完成
	private String roomFinishStatus;

	/**
	 * @return the fid
	 */
	public String getFid() {
		return fid;
	}

	/**
	 * @param fid the fid to set
	 */
	public void setFid(String fid) {
		this.fid = fid;
	}

	/**
	 * @return the houseBaseFid
	 */
	public String getHouseBaseFid() {
		return houseBaseFid;
	}

	/**
	 * @param houseBaseFid the houseBaseFid to set
	 */
	public void setHouseBaseFid(String houseBaseFid) {
		this.houseBaseFid = houseBaseFid;
	}

	/**
	 * @return the roomName
	 */
	public String getRoomName() {
		return roomName;
	}

	/**
	 * @param roomName the roomName to set
	 */
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	/**
	 * @return the roomStatus
	 */
	public Integer getRoomStatus() {
		return roomStatus;
	}

	/**
	 * @param roomStatus the roomStatus to set
	 */
	public void setRoomStatus(Integer roomStatus) {
		this.roomStatus = roomStatus;
	}

	/**
	 * @return the roomPrice
	 */
	public Integer getRoomPrice() {
		return roomPrice;
	}

	/**
	 * @param roomPrice the roomPrice to set
	 */
	public void setRoomPrice(Integer roomPrice) {
		this.roomPrice = roomPrice;
	}

	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the roomSn
	 */
	public String getRoomSn() {
		return roomSn;
	}

	/**
	 * @param roomSn the roomSn to set
	 */
	public void setRoomSn(String roomSn) {
		this.roomSn = roomSn;
	}

	/**
	 * @return the defaultPicFid
	 */
	public String getDefaultPicFid() {
		return defaultPicFid;
	}

	/**
	 * @param defaultPicFid the defaultPicFid to set
	 */
	public void setDefaultPicFid(String defaultPicFid) {
		this.defaultPicFid = defaultPicFid;
	}

	/**
	 * @return the oldDefaultPicFid
	 */
	public String getOldDefaultPicFid() {
		return oldDefaultPicFid;
	}

	/**
	 * @param oldDefaultPicFid the oldDefaultPicFid to set
	 */
	public void setOldDefaultPicFid(String oldDefaultPicFid) {
		this.oldDefaultPicFid = oldDefaultPicFid;
	}

	/**
	 * @return the roomFinishStatus
	 */
	public String getRoomFinishStatus() {
		return roomFinishStatus;
	}

	/**
	 * @param roomFinishStatus the roomFinishStatus to set
	 */
	public void setRoomFinishStatus(String roomFinishStatus) {
		this.roomFinishStatus = roomFinishStatus;
	}
	
	
}
