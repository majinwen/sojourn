/**
 * @FileName: AssembleRoomMsgDto.java
 * @Package com.ziroom.minsu.services.house.dto
 * 
 * @author zl
 * @created 2017年7月3日 下午6:04:54
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.dto;

import java.io.Serializable;
import java.util.List;

import com.ziroom.minsu.entity.house.HouseBedMsgEntity;
import com.ziroom.minsu.entity.house.HouseConfMsgEntity;
import com.ziroom.minsu.entity.house.HousePriceWeekConfEntity;
import com.ziroom.minsu.services.house.entity.RoomMsgVo;

/**
 * <p>组合房间信息</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zl
 * @since 1.0
 * @version 1.0
 */
public class AssembleRoomMsgDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4668666149521814028L;
	
	
	private RoomMsgVo roomMsgVo;
	
	/**
	 * 
	 */
	private HouseBaseExtDto houseBaseExtDto;
	
	/**
	 * 周末价格开关 0：关 ，1：开
	 */
	private Integer weekendPriceSwitch;
	
	/**
	 * 周末价格
	 */
	private List<HousePriceWeekConfEntity> weekendPriceList;  
	
	/**
	 * 长租折扣开关0：关 ，1：开
	 */
	private Integer fullDayRateSwitch;
	
	/**
	 * 长租折扣
	 */
	private List<HouseConfMsgEntity> fullDayRateList;
	
	/**
	 * 要删除的床位
	 */
	List<HouseBedMsgEntity> delbedList;
	
	/**
	 * 要新增的床位
	 */
	List<HouseBedMsgEntity> addbedList; 
	

	public List<HouseBedMsgEntity> getDelbedList() {
		return delbedList;
	}

	public List<HouseBedMsgEntity> getAddbedList() {
		return addbedList;
	}

	public void setDelbedList(List<HouseBedMsgEntity> delbedList) {
		this.delbedList = delbedList;
	}

	public void setAddbedList(List<HouseBedMsgEntity> addbedList) {
		this.addbedList = addbedList;
	}

	public RoomMsgVo getRoomMsgVo() {
		return roomMsgVo;
	}

	public HouseBaseExtDto getHouseBaseExtDto() {
		return houseBaseExtDto;
	}

	public Integer getWeekendPriceSwitch() {
		return weekendPriceSwitch;
	}

	public List<HousePriceWeekConfEntity> getWeekendPriceList() {
		return weekendPriceList;
	}

	public Integer getFullDayRateSwitch() {
		return fullDayRateSwitch;
	}

	public List<HouseConfMsgEntity> getFullDayRateList() {
		return fullDayRateList;
	}

	public void setRoomMsgVo(RoomMsgVo roomMsgVo) {
		this.roomMsgVo = roomMsgVo;
	}

	public void setHouseBaseExtDto(HouseBaseExtDto houseBaseExtDto) {
		this.houseBaseExtDto = houseBaseExtDto;
	}

	public void setWeekendPriceSwitch(Integer weekendPriceSwitch) {
		this.weekendPriceSwitch = weekendPriceSwitch;
	}

	public void setWeekendPriceList(List<HousePriceWeekConfEntity> weekendPriceList) {
		this.weekendPriceList = weekendPriceList;
	}

	public void setFullDayRateSwitch(Integer fullDayRateSwitch) {
		this.fullDayRateSwitch = fullDayRateSwitch;
	}

	public void setFullDayRateList(List<HouseConfMsgEntity> fullDayRateList) {
		this.fullDayRateList = fullDayRateList;
	}

}
