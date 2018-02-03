/**
 * @FileName: HouseMq.java
 * @Package com.ziroom.minsu.services.house.entity
 * 
 * @author liujun
 * @created 2016年4月25日 下午10:40:16
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.common.mq;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>房源MQ</p>
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
public class HouseMq extends BaseEntity{


    private static final long serialVersionUID = 4546575676573L;

	/**
	 * 房源逻辑id
	 */
	private String houseBaseFid;
	
	/**
	 * 房间逻辑id
	 */
	private String houseRoomFid;
	
	/**
	 * 房源出租方式
	 */
	private Integer rentWay;

	/**
	 * 初始状态
	 */
	private Integer fromStatus;
	
	/**
	 * 改变后状态
	 */
	private Integer toStatus;
	
	/**
	 * minimize constructor
	 */
	public HouseMq() {
		
	}

	/**
	 * maximize constructor
	 * @param houseBaseFid
	 * @param houseRoomFid
	 * @param rentWay
	 * @param fromStatus
	 * @param toStatus
	 */
	public HouseMq(String houseBaseFid, String houseRoomFid, Integer rentWay, Integer fromStatus, Integer toStatus) {
		this.houseBaseFid = houseBaseFid;
		this.houseRoomFid = houseRoomFid;
		this.rentWay = rentWay;
		this.fromStatus = fromStatus;
		this.toStatus = toStatus;
	}

	public String getHouseBaseFid() {
		return houseBaseFid;
	}

	public void setHouseBaseFid(String houseBaseFid) {
		this.houseBaseFid = houseBaseFid;
	}

	public String getHouseRoomFid() {
		return houseRoomFid;
	}

	public void setHouseRoomFid(String houseRoomFid) {
		this.houseRoomFid = houseRoomFid;
	}

	public Integer getRentWay() {
		return rentWay;
	}

	public void setRentWay(Integer rentWay) {
		this.rentWay = rentWay;
	}

	public Integer getFromStatus() {
		return fromStatus;
	}

	public void setFromStatus(Integer fromStatus) {
		this.fromStatus = fromStatus;
	}

	public Integer getToStatus() {
		return toStatus;
	}

	public void setToStatus(Integer toStatus) {
		this.toStatus = toStatus;
	}
	
}
