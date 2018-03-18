package com.ziroom.zrp.service.trading.valenum;

import com.asura.framework.base.util.Check;

public enum RoomStatusEnum {
	
	DZZ("0","待租中"),
	YCZ("1","已出租"),
	PZZ("2","配置中"),
	YXD("3","已下定"),
	SD("4","锁定"),
	YXJ("5","已下架"),
	YDZ("6","预定进行中"),
	KYD("7","可预定"),
	QYZ("8","签约进行中");
	
	
	private String status;
	private String name;
	
	
	private RoomStatusEnum(String status,String name) {
		this.name = name;
		this.status = status;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public static boolean validRoomStatus(String roomStatus){
		boolean valid = false;
		if(Check.NuNStr(roomStatus)) return valid;
		if(RoomStatusEnum.DZZ.getStatus().equals(roomStatus) || RoomStatusEnum.KYD.getStatus().equals(roomStatus)){
			valid = true;
		}
		return valid;
	}

}
