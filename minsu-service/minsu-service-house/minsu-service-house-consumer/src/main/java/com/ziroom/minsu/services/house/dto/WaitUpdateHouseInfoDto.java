/**
 * @FileName: WaitUpdateHouseInfoDto.java
 * @Package com.ziroom.minsu.services.house.dto
 * 
 * @author yd
 * @created 2017年7月7日 上午11:02:05
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.dto;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>查询 待修改的房源数据</p>
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
public class WaitUpdateHouseInfoDto extends BaseEntity{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 6892019780282361185L;
	
	/**
	 * 房源fid
	 */
	private  String houseFid;
	
	/**
	 * 房间fid
	 */
	private  String roomFid;
	
	/**
	 * 出租方式
	 */
	private  Integer rentWay;

	/**
	 * @return the houseFid
	 */
	public String getHouseFid() {
		return houseFid;
	}

	/**
	 * @param houseFid the houseFid to set
	 */
	public void setHouseFid(String houseFid) {
		this.houseFid = houseFid;
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
	
	
	
	

}
