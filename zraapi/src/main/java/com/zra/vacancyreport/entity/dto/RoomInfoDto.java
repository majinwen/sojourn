package com.zra.vacancyreport.entity.dto;
/**
 * 
 * 房间信息dto
 * @author tianxf9
 *
 */
public class RoomInfoDto {
	
	//项目id
	private String projectId;
	
	//房间id
	private String roomId;
	//房间号
	private String roomNum;
	//户型id
	private String houseTypeId;
	
	//出租方式：1：房间；2：床位
	private Integer rentType;
	
	//房间状态
	private String roomState;
	
	//昨天房间状态
	private String yRoomState;
	
	//昨天的空置天数
	private int yEmptyNum;

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	
	public String getRoomNum() {
		return roomNum;
	}

	public void setRoomNum(String roomNum) {
		this.roomNum = roomNum;
	}

	public String getHouseTypeId() {
		return houseTypeId;
	}

	public void setHouseTypeId(String houseTypeId) {
		this.houseTypeId = houseTypeId;
	}

	public Integer getRentType() {
		return rentType;
	}

	public void setRentType(Integer rentType) {
		this.rentType = rentType;
	}

	public String getRoomState() {
		return roomState;
	}

	public void setRoomState(String roomState) {
		this.roomState = roomState;
	}

	public String getyRoomState() {
		return yRoomState;
	}

	public void setyRoomState(String yRoomState) {
		this.yRoomState = yRoomState;
	}

	public int getyEmptyNum() {
		return yEmptyNum;
	}

	public void setyEmptyNum(int yEmptyNum) {
		this.yEmptyNum = yEmptyNum;
	}
}
