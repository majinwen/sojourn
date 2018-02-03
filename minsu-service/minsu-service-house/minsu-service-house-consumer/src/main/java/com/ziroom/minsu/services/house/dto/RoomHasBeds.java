/**
 * @FileName: RoomHasBeds.java
 * @Package com.ziroom.minsu.services.house.dto
 * 
 * @author jixd
 * @created 2016年8月15日 上午10:41:57
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.dto;

import java.util.List;

import com.ziroom.minsu.entity.house.HouseBedMsgEntity;
import com.ziroom.minsu.entity.house.HouseRoomMsgEntity;

/**
 * <p>房间信息包含床铺</p>
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
public class RoomHasBeds {

	private HouseRoomMsgEntity roomMsg;
	
	private List<HouseBedMsgEntity> beds;

	public HouseRoomMsgEntity getRoomMsg() {
		return roomMsg;
	}

	public void setRoomMsg(HouseRoomMsgEntity roomMsg) {
		this.roomMsg = roomMsg;
	}

	public List<HouseBedMsgEntity> getBeds() {
		return beds;
	}

	public void setBeds(List<HouseBedMsgEntity> beds) {
		this.beds = beds;
	}
	
	
}
