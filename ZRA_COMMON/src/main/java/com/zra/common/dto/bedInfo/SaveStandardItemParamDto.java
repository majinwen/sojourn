package com.zra.common.dto.bedInfo;

/**
 * 保存床位配置方案和物品关系的参数
 * @author tianxf9
 *
 */
public class SaveStandardItemParamDto {
	
	//床位配置方案bid
	private String bedStandardBid;
	//物品id
	private String itemIds;
	//创建者
	private String userId;
	
	public String getBedStandardBid() {
		return bedStandardBid;
	}
	public void setBedStandardBid(String bedStandardBid) {
		this.bedStandardBid = bedStandardBid;
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
	
}
