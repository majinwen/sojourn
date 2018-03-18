package com.ziroom.minsu.spider.xiaozhunew.entity;

import java.util.Date;
import java.util.List;

public class XiaozhuNewHouseInfoEntity {
    /**
     * 自增id
     */
    private Integer id;

    /**
     * 房源编号
     */
    private String houseSn;

    /**
     * 房源名称
     */
    private String houseName;

    /**
     * 房源url
     */
    private String detailUrl;

    /**
     * 地址
     */
    private String address;

    /**
     * 城市
     */
    private String city;

    /**
     * 评价分数
     */
    private Float reviewScore;

    /**
     * 房源价格
     */
    private Integer housePrice;

    /**
     * 房源类型code
     */
    private Integer rentType;

    /**
     * 房源类型
     */
    private String rentTypeName;

    /**
     * 面积
     */
    private Double houseArea;

    /**
     * 卧室数量
     */
    private Integer roomNum;

    /**
     * 客厅数量
     */
    private Integer hallNum;

    /**
     * 厕所数量
     */
    private Integer toiletNum;

    /**
     * 厕所类型
     */
    private String toiletType;

    /**
     * 厨房数量
     */
    private Integer kitchenNum;

    /**
     * 阳台数量
     */
    private Integer balconyNum;

    /**
     * 可容纳人数
     */
    private Integer personCapacity;

    /**
     * 床数量
     */
    private Integer bedCount;

    /**
     * 床信息
     */
    private String bedInfo;

    /**
     * 经度
     */
    private Double longitude;

    /**
     * 纬度
     */
    private Double latitude;

    /**
     * 评价数量
     */
    private Integer reviewCount;

    /**
     * 房东其他房源评价数量
     */
    private Integer hostOtherReviewCount;

    /**
     * 最小入住天数
     */
    private String minNights;

    /**
     * 最大入住天数
     */
    private String maxNights;

    /**
     * 便利设施，分隔符“|”
     */
    private String listingAmenities;

    /**
     * 是否接待境外人士
     */
    private String allowsForeigner;

    /**
     * 被单更换
     */
    private String sheetReplace;

    /**
     * 押金及其他费用
     */
    private String chargeRule;

    /**
     * 房东编号
     */
    private String hostSn;

    /**
     * 房东名称
     */
    private String hostName;

    /**
     * 创建日期
     */
    private Date createDate;

    /**
     * 最后修改时间
     */
    private Date lastModifyDate;
    
    private List<XiaozhuNewHousePriceEntity> priceEntities;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHouseSn() {
        return houseSn;
    }

    public void setHouseSn(String houseSn) {
        this.houseSn = houseSn == null ? null : houseSn.trim();
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName == null ? null : houseName.trim();
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl == null ? null : detailUrl.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public Float getReviewScore() {
        return reviewScore;
    }

    public void setReviewScore(Float reviewScore) {
        this.reviewScore = reviewScore;
    }

    public Integer getHousePrice() {
        return housePrice;
    }

    public void setHousePrice(Integer housePrice) {
        this.housePrice = housePrice;
    }

    public Integer getRentType() {
        return rentType;
    }

    public void setRentType(Integer rentType) {
        this.rentType = rentType;
    }

    public String getRentTypeName() {
        return rentTypeName;
    }

    public void setRentTypeName(String rentTypeName) {
        this.rentTypeName = rentTypeName == null ? null : rentTypeName.trim();
    }

    public Double getHouseArea() {
        return houseArea;
    }

    public void setHouseArea(Double houseArea) {
        this.houseArea = houseArea;
    }

    public Integer getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(Integer roomNum) {
        this.roomNum = roomNum;
    }

    public Integer getHallNum() {
        return hallNum;
    }

    public void setHallNum(Integer hallNum) {
        this.hallNum = hallNum;
    }

    public Integer getToiletNum() {
        return toiletNum;
    }

    public void setToiletNum(Integer toiletNum) {
        this.toiletNum = toiletNum;
    }

    public String getToiletType() {
        return toiletType;
    }

    public void setToiletType(String toiletType) {
        this.toiletType = toiletType == null ? null : toiletType.trim();
    }

    public Integer getKitchenNum() {
        return kitchenNum;
    }

    public void setKitchenNum(Integer kitchenNum) {
        this.kitchenNum = kitchenNum;
    }

    public Integer getBalconyNum() {
        return balconyNum;
    }

    public void setBalconyNum(Integer balconyNum) {
        this.balconyNum = balconyNum;
    }

    public Integer getPersonCapacity() {
        return personCapacity;
    }

    public void setPersonCapacity(Integer personCapacity) {
        this.personCapacity = personCapacity;
    }

    public Integer getBedCount() {
        return bedCount;
    }

    public void setBedCount(Integer bedCount) {
        this.bedCount = bedCount;
    }

    public String getBedInfo() {
        return bedInfo;
    }

    public void setBedInfo(String bedInfo) {
        this.bedInfo = bedInfo == null ? null : bedInfo.trim();
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Integer getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(Integer reviewCount) {
        this.reviewCount = reviewCount;
    }

    public Integer getHostOtherReviewCount() {
        return hostOtherReviewCount;
    }

    public void setHostOtherReviewCount(Integer hostOtherReviewCount) {
        this.hostOtherReviewCount = hostOtherReviewCount;
    }

    public String getMinNights() {
        return minNights;
    }

    public void setMinNights(String minNights) {
        this.minNights = minNights == null ? null : minNights.trim();
    }

    public String getMaxNights() {
        return maxNights;
    }

    public void setMaxNights(String maxNights) {
        this.maxNights = maxNights == null ? null : maxNights.trim();
    }

    public String getListingAmenities() {
        return listingAmenities;
    }

    public void setListingAmenities(String listingAmenities) {
        this.listingAmenities = listingAmenities == null ? null : listingAmenities.trim();
    }

    public String getAllowsForeigner() {
        return allowsForeigner;
    }

    public void setAllowsForeigner(String allowsForeigner) {
        this.allowsForeigner = allowsForeigner == null ? null : allowsForeigner.trim();
    }

    public String getSheetReplace() {
        return sheetReplace;
    }

    public void setSheetReplace(String sheetReplace) {
        this.sheetReplace = sheetReplace == null ? null : sheetReplace.trim();
    }

    public String getChargeRule() {
        return chargeRule;
    }

    public void setChargeRule(String chargeRule) {
        this.chargeRule = chargeRule == null ? null : chargeRule.trim();
    }

    public String getHostSn() {
        return hostSn;
    }

    public void setHostSn(String hostSn) {
        this.hostSn = hostSn == null ? null : hostSn.trim();
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName == null ? null : hostName.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getLastModifyDate() {
        return lastModifyDate;
    }

    public void setLastModifyDate(Date lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
    }

	public List<XiaozhuNewHousePriceEntity> getPriceEntities() {
		return priceEntities;
	}

	public void setPriceEntities(List<XiaozhuNewHousePriceEntity> priceEntities) {
		this.priceEntities = priceEntities;
	}
    
}