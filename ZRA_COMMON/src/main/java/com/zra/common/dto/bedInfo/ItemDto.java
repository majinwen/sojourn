package com.zra.common.dto.bedInfo;

import java.util.Date;

/**
 * Item(物品)实体类
 * @author tianxf9
 *
 */
public class ItemDto{
	private String id;
	private String type = "1"; // 物品分类 from by ItemTypeEnum(物品分类枚举)
	private String name; // 物品名称
	private String code; // 物品编码
	private String isAccessory = "0"; // 是否配件
	private String isDamage ="1"; // 是否损坏赔偿
	private Double price; // 物品价格
	private String isBackup = "1"; // 是否备库
	private String webName; // 网站显示名
	private String showOrder; // 显示顺序
	private String iconJoinId; // 图标
	private Double compensatePrice; // 赔付金额
	private Double halfPercent; // 半年内赔率
	private Double onePercent; // 0.5-1年赔率
	private Double twoPercent; // 1-2年赔率
	private Double threePercent; // 2-3年赔率
	private Double fourPercent; // 3-4年赔率
	private Double fivePercent; // 4-5年赔率
	private String state = "1"; // 物品状态(1 在用,0  停用)
	private Integer valid = 1 ;
	private Date createTime;
	private String createrId;
	private Date updateTime;
	private String updaterId;
	private Integer isDel = 0; //是否删除
	private Integer isRegister = 1; // 是否列入物业交割单
	private String cityId;
	// update by lixp 20150602增加区分装修费用的字段
	private Integer itemType; // 0:其他费用项目;1:装修费用项目
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getIsAccessory() {
		return isAccessory;
	}
	public void setIsAccessory(String isAccessory) {
		this.isAccessory = isAccessory;
	}
	public String getIsDamage() {
		return isDamage;
	}
	public void setIsDamage(String isDamage) {
		this.isDamage = isDamage;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getIsBackup() {
		return isBackup;
	}
	public void setIsBackup(String isBackup) {
		this.isBackup = isBackup;
	}
	public String getWebName() {
		return webName;
	}
	public void setWebName(String webName) {
		this.webName = webName;
	}
	public String getShowOrder() {
		return showOrder;
	}
	public void setShowOrder(String showOrder) {
		this.showOrder = showOrder;
	}
	public String getIconJoinId() {
		return iconJoinId;
	}
	public void setIconJoinId(String iconJoinId) {
		this.iconJoinId = iconJoinId;
	}
	public Double getCompensatePrice() {
		return compensatePrice;
	}
	public void setCompensatePrice(Double compensatePrice) {
		this.compensatePrice = compensatePrice;
	}
	public Double getHalfPercent() {
		return halfPercent;
	}
	public void setHalfPercent(Double halfPercent) {
		this.halfPercent = halfPercent;
	}
	public Double getOnePercent() {
		return onePercent;
	}
	public void setOnePercent(Double onePercent) {
		this.onePercent = onePercent;
	}
	public Double getTwoPercent() {
		return twoPercent;
	}
	public void setTwoPercent(Double twoPercent) {
		this.twoPercent = twoPercent;
	}
	public Double getThreePercent() {
		return threePercent;
	}
	public void setThreePercent(Double threePercent) {
		this.threePercent = threePercent;
	}
	public Double getFourPercent() {
		return fourPercent;
	}
	public void setFourPercent(Double fourPercent) {
		this.fourPercent = fourPercent;
	}
	public Double getFivePercent() {
		return fivePercent;
	}
	public void setFivePercent(Double fivePercent) {
		this.fivePercent = fivePercent;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Integer getValid() {
		return valid;
	}
	public void setValid(Integer valid) {
		this.valid = valid;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreaterId() {
		return createrId;
	}
	public void setCreaterId(String createrId) {
		this.createrId = createrId;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getUpdaterId() {
		return updaterId;
	}
	public void setUpdaterId(String updaterId) {
		this.updaterId = updaterId;
	}
	public Integer getIsDel() {
		return isDel;
	}
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
	public Integer getIsRegister() {
		return isRegister;
	}
	public void setIsRegister(Integer isRegister) {
		this.isRegister = isRegister;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public Integer getItemType() {
		return itemType;
	}
	public void setItemType(Integer itemType) {
		this.itemType = itemType;
	}
}
