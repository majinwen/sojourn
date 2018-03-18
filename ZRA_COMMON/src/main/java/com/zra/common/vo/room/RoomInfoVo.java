package com.zra.common.vo.room;

import java.io.Serializable;

/**
 * <p>签约房间信息VO</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author xiangb
 * @version 1.0
 * @Date Created in 2017年9月14日
 * @since 1.0
 */
public class RoomInfoVo implements Serializable{
	
	private static final long serialVersionUID = 8317085438248234172L;
	/**
	 * 楼层
	 */
	private String floorNumber;
	/**
	 * 房间号
	 */
	//private String roomNumber;
	/**
	 * 房间面积
	 */
	//private String roomArea;
	/**
	 * 朝向
	 */
	private String roomDirection;
	
	/**
     * 房型名称
     */
    private String houseTypeName;

	public String getFloorNumber() {
		return floorNumber;
	}

	public void setFloorNumber(String floorNumber) {
		this.floorNumber = floorNumber;
	}


	public String getRoomDirection() {
		return roomDirection;
	}

	public void setRoomDirection(String roomDirection) {
		this.roomDirection = roomDirection;
	}

	public String getHouseTypeName() {
		return houseTypeName;
	}

	public void setHouseTypeName(String houseTypeName) {
		this.houseTypeName = houseTypeName;
	}
    
}
