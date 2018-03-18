package com.zra.common.dto.bedInfo;

import java.util.Date;

/**
 * 物品dto(对应的表bse_item_standard)
 * 
 * @author tianxf9
 *
 */
public class StandardItemDto {
	// 物品，床位标准配置关系表id
	private String id;
	// 床位配置标准Bid
	private String standardBid;
	// 物品Id
	private String itemBid;
	// 物品类别
	private String itemType;
	// 物品名称
	private String itemName;
	// 物品编码
	private String itemCode;
	// 物品数量
	private int itemNum;
	//物品价格
	private Double itemPrice;
	// 是否必配
	private Byte inputOptions;
	// 是否有库存
	private Byte inventoryManage;
	// 数量是否可以修改
	private Byte numModify;
	// 项目Id
	private String projectId;
	// 城市Id
	private String cityId;
	// 是否有效
	private Byte valid;
	// 创建时间
	private Date createTime;
	// 创建人
	private String creater;
	// 修改人
	private Date updateTime;
	// 修改时间
	private String updater;
	// 配置类别
	private Byte typeInfo;
	// 是否删除
	private Byte isDel;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStandardBid() {
		return standardBid;
	}

	public void setStandardBid(String standardBid) {
		this.standardBid = standardBid;
	}

	public String getItemBid() {
		return itemBid;
	}

	public void setItemBid(String itemBid) {
		this.itemBid = itemBid;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public int getItemNum() {
		return itemNum;
	}

	public void setItemNum(int itemNum) {
		this.itemNum = itemNum;
	}

	public Byte getInputOptions() {
		return inputOptions;
	}

	public void setInputOptions(Byte inputOptions) {
		this.inputOptions = inputOptions;
	}

	public Byte getInventoryManage() {
		return inventoryManage;
	}

	public void setInventoryManage(Byte inventoryManage) {
		this.inventoryManage = inventoryManage;
	}

	public Byte getNumModify() {
		return numModify;
	}

	public void setNumModify(Byte numModify) {
		this.numModify = numModify;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public Byte getValid() {
		return valid;
	}

	public void setValid(Byte valid) {
		this.valid = valid;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getUpdater() {
		return updater;
	}

	public void setUpdater(String updater) {
		this.updater = updater;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}


	public Byte getTypeInfo() {
		return typeInfo;
	}

	public void setTypeInfo(Byte typeInfo) {
		this.typeInfo = typeInfo;
	}

	public Byte getIsDel() {
		return isDel;
	}

	public void setIsDel(Byte isDel) {
		this.isDel = isDel;
	}

	public Double getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(Double itemPrice) {
		this.itemPrice = itemPrice;
	}
}
