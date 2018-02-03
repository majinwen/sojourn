package com.ziroom.minsu.services.house.entity;

import java.io.Serializable;

import javax.validation.constraints.NotNull;


/**
 * 
 * <p>房源基础vo</p>
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
public class HouseBaseVo implements Serializable{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1035542349371498159L;

	/**
	 * 房源fid
	 */
	@NotNull(message="{house.base.fid.null}")
	private String houseFid;
	
	/**
	 * 房间fid
	 */
	private String roomFid;
	
	/**
	 * 出租方式:0整租 1合租
	 */
	@NotNull(message="{house.rentWay.null}")
	private Integer rentWay;

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
	
}
