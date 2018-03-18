package com.ziroom.zrp.service.trading.valenum;
/**
 * 物品使用状态
 * @author xiangbin
 * 2017年11月18日
 */
public enum ItemUsedStateEnum {
	//0：正常1：损坏2：丢失
	
	NORMAL(0,"正常"),
	DAMAGE(1,"损坏"),
	LOSE(2,"丢失");
	
	private int status;
	private String name;
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	ItemUsedStateEnum(int status, String name) {
		this.status = status;
		this.name = name;
	}
	
	
}
