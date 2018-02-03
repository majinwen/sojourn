/**
 * @FileName: HouseInfoStaSit.java
 * @Package com.ziroom.minsu.services.house.dto
 * 
 * @author yd
 * @created 2016年8月22日 下午2:00:17
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.dto;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>房源相关信息完整度值</p>
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
public class HouseInfoStaSit extends BaseEntity{

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = -8392454441286659947L;

	/**
	 * 房源完整度 
	 */
	private Integer houseStatusSit;
	
	/**
	 * 房间信息完整度
	 */
	private Integer roomStatusSit;
	
	/**
	 * 可选信息完整度
	 */
	private Integer extStatusSit;

	/**
	 * @return the houseStatusSit
	 */
	public Integer getHouseStatusSit() {
		return houseStatusSit;
	}

	/**
	 * @param houseStatusSit the houseStatusSit to set
	 */
	public void setHouseStatusSit(Integer houseStatusSit) {
		this.houseStatusSit = houseStatusSit;
	}

	/**
	 * @return the roomStatusSit
	 */
	public Integer getRoomStatusSit() {
		return roomStatusSit;
	}

	/**
	 * @param roomStatusSit the roomStatusSit to set
	 */
	public void setRoomStatusSit(Integer roomStatusSit) {
		this.roomStatusSit = roomStatusSit;
	}

	/**
	 * @return the extStatusSit
	 */
	public Integer getExtStatusSit() {
		return extStatusSit;
	}

	/**
	 * @param extStatusSit the extStatusSit to set
	 */
	public void setExtStatusSit(Integer extStatusSit) {
		this.extStatusSit = extStatusSit;
	}
	
	
}
