/**
 * @FileName: HouseDescAndConfDto.java
 * @Package com.ziroom.minsu.services.house.dto
 *
 * @author lusp
 * @created 2017年7月6日 上午11:41:38
 *
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.dto;

import com.ziroom.minsu.services.house.entity.HouseBaseDetailVo;

/**
 * 
 * <p></p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author lusp
 * @since 1.0
 * @version 1.0
 */
public class HouseDescAndConfDto extends HouseAndConfDto{

	/**
	 * 房源描述
	 */
	private HouseBaseDetailVo houseBaseDetailVo;
	
	/**
	 * 房间fid
	 */
	private String roomFid;

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

	public HouseBaseDetailVo getHouseBaseDetailVo() {
		return houseBaseDetailVo;
	}

	public void setHouseBaseDetailVo(HouseBaseDetailVo houseBaseDetailVo) {
		this.houseBaseDetailVo = houseBaseDetailVo;
	}
}
