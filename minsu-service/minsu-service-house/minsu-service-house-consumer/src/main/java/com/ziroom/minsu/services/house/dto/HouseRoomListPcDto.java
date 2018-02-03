/**
 * @FileName: HouseRoomListPcDto.java
 * @Package com.ziroom.minsu.services.house.dto
 * 
 * @author bushujie
 * @created 2016年8月11日 下午4:04:38
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.dto;

import java.util.ArrayList;
import java.util.List;

import com.ziroom.minsu.entity.house.HouseBaseMsgEntity;
import com.ziroom.minsu.entity.house.HouseRoomMsgEntity;

/**
 * <p>房源及房间信息dto</p>
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
public class HouseRoomListPcDto extends HouseBaseMsgEntity{
	
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -6617043620561506524L;
	
	 /**
	  * 房源房间集合
	  */
	private List<HouseRoomMsgEntity> roomList=new ArrayList<HouseRoomMsgEntity>();

	/**
	 * @return the roomList
	 */
	public List<HouseRoomMsgEntity> getRoomList() {
		return roomList;
	}

	/**
	 * @param roomList the roomList to set
	 */
	public void setRoomList(List<HouseRoomMsgEntity> roomList) {
		this.roomList = roomList;
	}
}
