package com.zra.common.dto.meteritem;

/**
 * Created by PC on 2016/9/23.
 */

/**
 * 物品交割和合同的对应关系--xiaona--2016年9月23日
 */
public class MeterItemParamDto {
	//床位配置方案bid
	private String roomId;
	private String contractId;
	//物品id
	private String itemIds;
	//创建者
	private String userId;

	private Byte rentType; //表示是为床位还是为房间添加的物品

	private String cityId;

	private Integer typeInfo;  //0是房间，1是床位 类型，存在虚拟房间的时候

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public String getItemIds() {
		return itemIds;
	}

	public void setItemIds(String itemIds) {
		this.itemIds = itemIds;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Byte getRentType() {
		return rentType;
	}

	public void setRentType(Byte rentType) {
		this.rentType = rentType;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public Integer getTypeInfo() {
		return typeInfo;
	}

	public void setTypeInfo(Integer typeInfo) {
		this.typeInfo = typeInfo;
	}
}
