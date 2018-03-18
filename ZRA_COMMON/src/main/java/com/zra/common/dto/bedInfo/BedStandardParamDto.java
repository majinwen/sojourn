package com.zra.common.dto.bedInfo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by PC on 2016/9/9.
 */
@Data
@NoArgsConstructor
public class BedStandardParamDto {

	//床位配置方案标准bid
	private String standardBid;
	private String bedBid;

	private String roomId;
	private String shortRent;
	private Integer bedNum;

	private String userId;
	private String currentState;

	private Integer isAdd;  //1是新建。2是修改

	public String getStandardBid() {
		return standardBid;
	}

	public void setStandardBid(String standardBid) {
		this.standardBid = standardBid;
	}

	public String getBedBid() {
		return bedBid;
	}

	public void setBedBid(String bedBid) {
		this.bedBid = bedBid;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getShortRent() {
		return shortRent;
	}

	public void setShortRent(String shortRent) {
		this.shortRent = shortRent;
	}

	public Integer getBedNum() {
		return bedNum;
	}

	public void setBedNum(Integer bedNum) {
		this.bedNum = bedNum;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCurrentState() {
		return currentState;
	}

	public void setCurrentState(String currentState) {
		this.currentState = currentState;
	}

	public Integer getIsAdd() {
		return isAdd;
	}

	public void setIsAdd(Integer isAdd) {
		this.isAdd = isAdd;
	}
}