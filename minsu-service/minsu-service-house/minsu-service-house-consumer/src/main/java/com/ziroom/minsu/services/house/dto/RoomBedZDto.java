/**
 * @FileName: RoomBedZDto.java
 * @Package com.ziroom.minsu.services.house.dto
 * 
 * @author yd
 * @created 2016年8月23日 下午9:29:29
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.dto;

import java.io.Serializable;
import java.util.List;

import com.ziroom.minsu.entity.house.HouseBedMsgEntity;

/**
 * <p>整租添加房间蚕食</p>
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
public class RoomBedZDto implements Serializable{

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = 5757318207398861136L;

	/**
	 * 房间fid
	 */
	private String roomFid;
	
	/**
	 * 房源fid
	 */
	private String houseBaseFid;
	
	/**
	 * 房间最低价格
	 */
	private int roomPrice;
	
	/**
	 * 床位集合
	 */
	private List<HouseBedMsgEntity> listBeds;
	
	/**
	 * 创建人 fid
	 */
	private String createdFid;
	
	

	
	
	/**
	 * @return the createdFid
	 */
	public String getCreatedFid() {
		return createdFid;
	}

	/**
	 * @param createdFid the createdFid to set
	 */
	public void setCreatedFid(String createdFid) {
		this.createdFid = createdFid;
	}

	/**
	 * @return the roomPrice
	 */
	public int getRoomPrice() {
		return roomPrice;
	}

	/**
	 * @param roomPrice the roomPrice to set
	 */
	public void setRoomPrice(int roomPrice) {
		this.roomPrice = roomPrice;
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
	 * @return the listBeds
	 */
	public List<HouseBedMsgEntity> getListBeds() {
		return listBeds;
	}

	/**
	 * @param listBeds the listBeds to set
	 */
	public void setListBeds(List<HouseBedMsgEntity> listBeds) {
		this.listBeds = listBeds;
	}
	
	
}
