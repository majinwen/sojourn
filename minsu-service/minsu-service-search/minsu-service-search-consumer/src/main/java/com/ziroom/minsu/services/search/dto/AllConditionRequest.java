
package com.ziroom.minsu.services.search.dto;

import java.util.Map;
import java.util.Set;

import com.ziroom.minsu.services.common.dto.PageRequest;

public class AllConditionRequest extends BaseSearchRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = -550969631941052574L;

	/** 房源id */
	private String houseId;

	/** 房间id */
	private String roomId;

	/** 房屋类型 整租 合租 */
	private Integer rentWay;

	/**
	 * 房源类型，多个“,”号分割
	 */
	private String houseType;

	/** 房源名称 */
	private String houseName;

	/** 城市code */
	private String cityCode;

	/** 区域code */
	private String areaCode;

	/** 热门景点 */
	private String hotReginScenic;

	/** 热门区域 */
	private String hotReginBusiness;

	/** 地铁 */
	private String subway;

	/** 极速 普通 */
	private Integer orderType;

	/** 房东uid */
	private String landlordUid;

	/** 房东名称 */
	private String landlordName;

	/** 房东昵称 */
	private String nickName;

	/** 入住人数 */
	private Integer personCount;

	/** 房间数 */
	private Integer roomCount;

	/** 是否智能锁 */
	private Integer isLock;

	/** 卫生间数量 */
	private Integer toiletCount;

	/** 是否独立卫生间 */
	private Integer isToilet;

	/** 阳台数量 */
	private Integer balconyCount;

	/** 提供服务 */
	private Set<String> services;

	/** 纬度，经度 */
	private String loc;

	/** 评价数量 */
	private Integer evaluateCount;

	/**
	 * 房源品质登记 A B C
	 */
	private String houseQualityGrade;

	/**
	 * 是否特色房源
	 */
	private Integer isFeatureHouse;

	/**
	 * 房源或者房间编号
	 */
	private String houseSn;

	/** 是否是最近房源 */
	private Integer isNew;

	/**
	 * 是否top50已经上线的房源
	 */
	private Integer isTop50Online;

	/** 品牌编码 */
	private String brandSn;

	/** top50标题 */
	private String top50Title;

	/** 是否top50榜单页显示房源 */
	private Integer isTop50ListShow;

	/** 是否与房东同住 */
	private Integer isLandTogether;

	/**
	 * 长租折扣类型，多个“,”号分割
	 */
	private String longTermDiscount;

	/**
	 * 灵活定价折扣类型，多个“,”号分割
	 */
	private String jiaxinDiscount;

	/**
	 * 当前的地铁线
	 */
	private String lineFid;

	/** 价格 */
	private Integer priceStart;

	private Integer priceEnd;

	/** 价格 */
	private String startTime;

	private String endTime;

	/** 名称 */
	private String q;

	private Map<String, Object> sorts;

	/**
	 * 排序类型
	 * 
	 * @see com.ziroom.minsu.valenum.search.SortTypeEnum
	 */
	private Integer sortType;

	
	public String getHouseId() {
		return houseId;
	}

	public String getRoomId() {
		return roomId;
	}

	public Integer getRentWay() {
		return rentWay;
	}

	public String getHouseType() {
		return houseType;
	}

	public String getHouseName() {
		return houseName;
	}

	public String getCityCode() {
		return cityCode;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public String getHotReginScenic() {
		return hotReginScenic;
	}

	public String getHotReginBusiness() {
		return hotReginBusiness;
	}

	public String getSubway() {
		return subway;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public String getLandlordUid() {
		return landlordUid;
	}

	public String getLandlordName() {
		return landlordName;
	}

	public String getNickName() {
		return nickName;
	}

	public Integer getPersonCount() {
		return personCount;
	}

	public Integer getRoomCount() {
		return roomCount;
	}

	public Integer getIsLock() {
		return isLock;
	}

	public Integer getToiletCount() {
		return toiletCount;
	}

	public Integer getIsToilet() {
		return isToilet;
	}

	public Integer getBalconyCount() {
		return balconyCount;
	}

	public Set<String> getServices() {
		return services;
	}

	public String getLoc() {
		return loc;
	}

	public Integer getEvaluateCount() {
		return evaluateCount;
	}

	public String getHouseQualityGrade() {
		return houseQualityGrade;
	}

	public Integer getIsFeatureHouse() {
		return isFeatureHouse;
	}

	public String getHouseSn() {
		return houseSn;
	}

	public Integer getIsNew() {
		return isNew;
	}

	public Integer getIsTop50Online() {
		return isTop50Online;
	}

	public String getBrandSn() {
		return brandSn;
	}

	public String getTop50Title() {
		return top50Title;
	}

	public Integer getIsTop50ListShow() {
		return isTop50ListShow;
	}

	public Integer getIsLandTogether() {
		return isLandTogether;
	}

	public String getLongTermDiscount() {
		return longTermDiscount;
	}

	public String getJiaxinDiscount() {
		return jiaxinDiscount;
	}

	public String getLineFid() {
		return lineFid;
	}

	public Integer getPriceStart() {
		return priceStart;
	}

	public Integer getPriceEnd() {
		return priceEnd;
	}

	public String getStartTime() {
		return startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public String getQ() {
		return q;
	}

	public Map<String, Object> getSorts() {
		return sorts;
	}

	public Integer getSortType() {
		return sortType;
	}

	public void setHouseId(String houseId) {
		this.houseId = houseId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public void setRentWay(Integer rentWay) {
		this.rentWay = rentWay;
	}

	public void setHouseType(String houseType) {
		this.houseType = houseType;
	}

	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public void setHotReginScenic(String hotReginScenic) {
		this.hotReginScenic = hotReginScenic;
	}

	public void setHotReginBusiness(String hotReginBusiness) {
		this.hotReginBusiness = hotReginBusiness;
	}

	public void setSubway(String subway) {
		this.subway = subway;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public void setLandlordUid(String landlordUid) {
		this.landlordUid = landlordUid;
	}

	public void setLandlordName(String landlordName) {
		this.landlordName = landlordName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public void setPersonCount(Integer personCount) {
		this.personCount = personCount;
	}

	public void setRoomCount(Integer roomCount) {
		this.roomCount = roomCount;
	}

	public void setIsLock(Integer isLock) {
		this.isLock = isLock;
	}

	public void setToiletCount(Integer toiletCount) {
		this.toiletCount = toiletCount;
	}

	public void setIsToilet(Integer isToilet) {
		this.isToilet = isToilet;
	}

	public void setBalconyCount(Integer balconyCount) {
		this.balconyCount = balconyCount;
	}

	public void setServices(Set<String> services) {
		this.services = services;
	}

	public void setLoc(String loc) {
		this.loc = loc;
	}

	public void setEvaluateCount(Integer evaluateCount) {
		this.evaluateCount = evaluateCount;
	}

	public void setHouseQualityGrade(String houseQualityGrade) {
		this.houseQualityGrade = houseQualityGrade;
	}

	public void setIsFeatureHouse(Integer isFeatureHouse) {
		this.isFeatureHouse = isFeatureHouse;
	}

	public void setHouseSn(String houseSn) {
		this.houseSn = houseSn;
	}

	public void setIsNew(Integer isNew) {
		this.isNew = isNew;
	}

	public void setIsTop50Online(Integer isTop50Online) {
		this.isTop50Online = isTop50Online;
	}

	public void setBrandSn(String brandSn) {
		this.brandSn = brandSn;
	}

	public void setTop50Title(String top50Title) {
		this.top50Title = top50Title;
	}

	public void setIsTop50ListShow(Integer isTop50ListShow) {
		this.isTop50ListShow = isTop50ListShow;
	}

	public void setIsLandTogether(Integer isLandTogether) {
		this.isLandTogether = isLandTogether;
	}

	public void setLongTermDiscount(String longTermDiscount) {
		this.longTermDiscount = longTermDiscount;
	}

	public void setJiaxinDiscount(String jiaxinDiscount) {
		this.jiaxinDiscount = jiaxinDiscount;
	}

	public void setLineFid(String lineFid) {
		this.lineFid = lineFid;
	}

	public void setPriceStart(Integer priceStart) {
		this.priceStart = priceStart;
	}

	public void setPriceEnd(Integer priceEnd) {
		this.priceEnd = priceEnd;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public void setQ(String q) {
		this.q = q;
	}

	public void setSorts(Map<String, Object> sorts) {
		this.sorts = sorts;
	}

	public void setSortType(Integer sortType) {
		this.sortType = sortType;
	}

}
