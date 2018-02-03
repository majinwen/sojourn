/**
 * @FileName: OrderRequest.java
 * @Package com.ziroom.minsu.services.order.dto
 * 
 * @author liyingjie
 * @created 2016年4月2日 上午10:02:43
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.order.dto;

import java.util.Date;
import java.util.List;

import com.ziroom.minsu.services.common.dto.PageRequest;

/**
 * <p>
 * 收益请求参数
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liyingjie
 * @since 1.0
 * @version 1.0
 */
public class OrderProfitRequest extends PageRequest {

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = -6485230181824233964L;

	/**
	 * 用户uid
	 */
	private String uid;

	/**
	 * 月份 month
	 */
	private int month;
	
	/**
	 * 房源houseFid
	 */
	private String houseFid;
	
	/**
	 * 房间roomFid
	 */
	private String roomFid;

	/**
	 * 限制 开始时间
	 */
	private Date beginTime;
	
	/**
	 * 限制 结束时间
	 */
	private Date endTime;
	
	/**
	 * 出租方式
	 */
	private int rentWay;
	
	/**
	 * 1:实收订单列表 2：应收订单列表
	 */
	private int type;
	
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public String getHouseFid() {
		return houseFid;
	}

	public void setHouseFid(String houseFid) {
		this.houseFid = houseFid;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getRoomFid() {
		return roomFid;
	}

	public void setRoomFid(String roomFid) {
		this.roomFid = roomFid;
	}

	public int getRentWay() {
		return rentWay;
	}

	public void setRentWay(int rentWay) {
		this.rentWay = rentWay;
	}
	
}
