package com.ziroom.minsu.services.house.entity;

import java.util.ArrayList;
import java.util.List;

import com.ziroom.minsu.entity.house.HouseMonthRevenueEntity;
import com.ziroom.minsu.entity.house.RoomMonthRevenueEntity;

/**
 * 
 * <p>房东房源月收益vo</p>
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
public class HouseMonthRevenueVo extends HouseMonthRevenueEntity{
	
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -3831687911683615804L;
	
	private String houseName;
	
	private List<RoomMonthRevenueEntity> roomMonthRevenueList = new ArrayList<RoomMonthRevenueEntity>();
	
	public String getHouseName() {
		return houseName;
	}
	
	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}

	public List<RoomMonthRevenueEntity> getRoomMonthRevenueList() {
		return roomMonthRevenueList;
	}

	public void setRoomMonthRevenueList(List<RoomMonthRevenueEntity> roomMonthRevenueList) {
		this.roomMonthRevenueList = roomMonthRevenueList;
	}
}