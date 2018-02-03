/**
 * @FileName: HouseRoomWithBedsListPcDto.java
 * @Package com.ziroom.minsu.services.house.dto
 * 
 * @author jixd
 * @created 2016年8月15日 下午2:46:30
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.dto;

import java.io.Serializable;
import java.util.List;

import com.ziroom.minsu.entity.house.HouseBaseMsgEntity;

/**
 * <p>房间列表带床铺信息</p>
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
public class HouseRoomsWithBedsPcDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -393524718563533429L;
	/**
	 * 房源基础信息
	 */
	private HouseBaseMsgEntity houseBaseMsgEntity;
	/**
	 * 
	 */
	private List<RoomHasBeds> roomList;

	public List<RoomHasBeds> getRoomList() {
		return roomList;
	}

	public void setRoomList(List<RoomHasBeds> roomList) {
		this.roomList = roomList;
	}

	public HouseBaseMsgEntity getHouseBaseMsgEntity() {
		return houseBaseMsgEntity;
	}

	public void setHouseBaseMsgEntity(HouseBaseMsgEntity houseBaseMsgEntity) {
		this.houseBaseMsgEntity = houseBaseMsgEntity;
	}
	
	
}
