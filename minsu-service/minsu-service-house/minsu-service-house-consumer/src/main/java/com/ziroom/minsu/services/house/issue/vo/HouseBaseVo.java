/**
 * @FileName: HouseBaseVo.java
 * @Package com.ziroom.minsu.services.house.issue.vo
 * 
 * @author bushujie
 * @created 2017年6月15日 下午4:29:48
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.issue.vo;

/**
 * <p>基础返回信息</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
public class HouseBaseVo {
	
	/**
	 * 房源fid
	 */
	private String houseBaseFid;
	
	/**
	 * 房间fid
	 */
	private String roomFid;
	
	/**
	 * 出租方式 0：整租，1：合租
	 */
	private Integer rentWay;
	/**
	 * 房间类型 0：房间，1：客厅
	 */
	private Integer roomType;

	public Integer getRoomType() {
		return roomType;
	}

	public void setRoomType(Integer roomType) {
		this.roomType = roomType;
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
	 * @return the roomFid
	 */
	public String getRoomFid() {
		return roomFid;
	}

	/**
	 * @param roomFid the roomFid to set
	 */
	public void setRoomFid(String roomFid) {
		this.roomFid = roomFid;
	}

	/**
	 * @return the rentWay
	 */
	public Integer getRentWay() {
		return rentWay;
	}

	/**
	 * @param rentWay the rentWay to set
	 */
	public void setRentWay(Integer rentWay) {
		this.rentWay = rentWay;
	}

	@Override
	public String toString() {
		return "HouseBaseVo{" +
				"houseBaseFid='" + houseBaseFid + '\'' +
				", roomFid='" + roomFid + '\'' +
				", rentWay=" + rentWay +
				", roomType=" + roomType +
				'}';
	}
}
