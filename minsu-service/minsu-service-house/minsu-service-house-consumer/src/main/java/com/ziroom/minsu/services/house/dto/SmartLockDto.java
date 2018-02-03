/**
 * @FileName: HousePicDto.java
 * @Package com.ziroom.minsu.services.house.dto
 * 
 * @author bushujie
 * @created 2016年4月9日 下午5:36:24
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.dto;

import java.io.Serializable;
import java.util.List;


/**
 * <p>智能锁回调dto</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liujun
 * @since 1.0
 * @version 1.0
 */
public class SmartLockDto implements Serializable{
	
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -592218652288735116L;

	/**
	 * 房源逻辑id
	 */
	private String houseFid;

	/**
	 * 房间逻辑id
	 */
	private List<String> roomFidList;

	public String getHouseFid() {
		return houseFid;
	}

	public void setHouseFid(String houseFid) {
		this.houseFid = houseFid;
	}

	public List<String> getRoomFidList() {
		return roomFidList;
	}

	public void setRoomFidList(List<String> roomFidList) {
		this.roomFidList = roomFidList;
	}
	
}
