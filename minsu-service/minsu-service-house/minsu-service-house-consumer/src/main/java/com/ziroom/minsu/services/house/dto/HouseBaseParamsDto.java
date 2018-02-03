package com.ziroom.minsu.services.house.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class HouseBaseParamsDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9018203689289632595L;

	/**
	 * 房源fid
	 */
	@NotNull(message="{house.base.fid.null}")
	private String houseBaseFid;
	
	/**
	 * 房间fid
	 */ 
	private String roomFid;
	
	/**
	 * 0整租 1合租
	 */
	@NotNull(message="{house.rentway.null}")
	private Integer rentWay;
	
	/**
	 * 审核中的默认图片
	 */
	private String auditDefaultPic;
	
	/**
	 * 审核中的客厅数量
	 */
	private Integer auditHallNum;

	/**
	 * 房间类型
	 * 0:房间 1:共享客厅
	 */
	private Integer roomType;

	public Integer getRoomType() {
		return roomType;
	}

	public void setRoomType(Integer roomType) {
		this.roomType = roomType;
	}

	/**
	 * @return the auditHallNum
	 */
	public Integer getAuditHallNum() {
		return auditHallNum;
	}

	/**
	 * @param auditHallNum the auditHallNum to set
	 */
	public void setAuditHallNum(Integer auditHallNum) {
		this.auditHallNum = auditHallNum;
	}

	/**
	 * @return the auditDefaultPic
	 */
	public String getAuditDefaultPic() {
		return auditDefaultPic;
	}

	/**
	 * @param auditDefaultPic the auditDefaultPic to set
	 */
	public void setAuditDefaultPic(String auditDefaultPic) {
		this.auditDefaultPic = auditDefaultPic;
	}

	public String getHouseBaseFid() {
		return houseBaseFid;
	}

	public String getRoomFid() {
		return roomFid;
	}

	public Integer getRentWay() {
		return rentWay;
	}

	public void setHouseBaseFid(String houseBaseFid) {
		this.houseBaseFid = houseBaseFid;
	}

	public void setRoomFid(String roomFid) {
		this.roomFid = roomFid;
	}

	public void setRentWay(Integer rentWay) {
		this.rentWay = rentWay;
	}
	
}
