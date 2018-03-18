package com.ziroom.minsu.spider.airbnb.entity;

import java.util.Date;
import java.util.List;

public class AirbnbHouseInfoEntity {
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
     * 国家
     */
    private String country;

    /**
     * 城市
     */
    private String city;

    /**
     * 商圈
     */
    private String houseregion;

    /**
     * 地址
     */
    private String fullAddress;

    /**
     * 街区
     */
    private String street;

    /**
     * 经度
     */
    private Double longitude;

    /**
     * 纬度
     */
    private Double latitude;

    /**
     * 房源类型
     */
    private String houseType;

    /**
     * 房源类型code
     */
    private Integer houseTypeValue;

    /**
     * 房间类型
     */
    private String roomType;

    /**
     * 房间类型code
     */
    private String roomTypeValue;

    /**
     * 床数量
     */
    private Integer bedroomCount;

    /**
     * 可容纳人数
     */
    private Integer personCapacity;

    /**
     * 卫生间数量
     */
    private Float toiletCount;

    /**
     * 床类型
     */
    private String bedType;

    /**
     * 床数量
     */
    private Integer bedCount;

    /**
     * 房源默认图片url
     */
    private String houseImg;

    /**
     * 等级
     */
    private Float starRating;

    /**
     * 评价数量
     */
    private Integer reviewCount;

    /**
     * 评价分数
     */
    private Integer reviewScore;

    /**
     * 收藏数量
     */
    private Integer collectCount;

    /**
     * 最小入住天数
     */
    private Integer minNights;

    /**
     * 是否允许小孩 0：否，1：是
     */
    private Integer allowsChildren;

    /**
     * 是否允许婴儿  0：否，1：是
     */
    private Integer allowsInfants;

    /**
     * 是否允许宠物 0：否，1：是
     */
    private Integer allowsPets;

    /**
     * 是否允许吸烟 0：否，1：是
     */
    private Integer allowsSmoking;

    /**
     * 是否允许举办活动 0：否，1：是
     */
    private Integer allowsEvents;

    /**
     * 房屋规则
     */
    private String houseRules;

    /**
     * 是否可以立即预定 0：否，1：是
     */
    private Integer instantBookable;

    /**
     * 退订政策
     */
    private String cancellationPolicy;

    /**
     * 便利设施ids，分隔符“,”
     */
    private String listingAmenities;

    /**
     * 入住时间（有可能是“灵活”）
     */
    private String checkInTime;

    /**
     * 退房时间（有可能是“灵活”）
     */
    private String checkOutTime;

    /**
     * 房源日价格
     */
    private Integer housePrice;

    /**
     * 保证金
     */
    private Integer securityDeposit;

    /**
     * 清洁费
     */
    private Integer cleaningFee;

    /**
     * 货币编码
     */
    private String priceCurrency;

    /**
     * 描述信息语言缩写
     */
    private String descriptionLocale;

    /**
     * 最后修改日历的时间
     */
    private Date calendarLastUpdatedAt;

    /**
     * 房东编号
     */
    private String hostSn;

    /**
     * 是否超级房东 0：否，1：是
     */
    private Integer isSuperhost;

    /**
     * 房东头像url
     */
    private String hostImg;

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
    
    /**
     * 附加房东信息列表
     */
    private List<AirbnbAdditionalHostsEntity> additionalHostsEntities;
    
    /**
     * 房源价格列表
     */
    private List<AirbnbHousePriceEntity> priceEntities;
    

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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getHouseregion() {
        return houseregion;
    }

    public void setHouseregion(String houseregion) {
        this.houseregion = houseregion == null ? null : houseregion.trim();
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress == null ? null : fullAddress.trim();
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street == null ? null : street.trim();
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

    public String getHouseType() {
        return houseType;
    }

    public void setHouseType(String houseType) {
        this.houseType = houseType == null ? null : houseType.trim();
    }

    public Integer getHouseTypeValue() {
        return houseTypeValue;
    }

    public void setHouseTypeValue(Integer houseTypeValue) {
        this.houseTypeValue = houseTypeValue;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType == null ? null : roomType.trim();
    }

    public String getRoomTypeValue() {
        return roomTypeValue;
    }

    public void setRoomTypeValue(String roomTypeValue) {
        this.roomTypeValue = roomTypeValue;
    }

    public Integer getBedroomCount() {
        return bedroomCount;
    }

    public void setBedroomCount(Integer bedroomCount) {
        this.bedroomCount = bedroomCount;
    }

    public Integer getPersonCapacity() {
        return personCapacity;
    }

    public void setPersonCapacity(Integer personCapacity) {
        this.personCapacity = personCapacity;
    }

    public Float getToiletCount() {
        return toiletCount;
    }

    public void setToiletCount(Float toiletCount) {
        this.toiletCount = toiletCount;
    }

    public String getBedType() {
        return bedType;
    }

    public void setBedType(String bedType) {
        this.bedType = bedType == null ? null : bedType.trim();
    }

    public Integer getBedCount() {
        return bedCount;
    }

    public void setBedCount(Integer bedCount) {
        this.bedCount = bedCount;
    }

    public String getHouseImg() {
        return houseImg;
    }

    public void setHouseImg(String houseImg) {
        this.houseImg = houseImg == null ? null : houseImg.trim();
    }

    public Float getStarRating() {
        return starRating;
    }

    public void setStarRating(Float starRating) {
        this.starRating = starRating;
    }

    public Integer getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(Integer reviewCount) {
        this.reviewCount = reviewCount;
    }

    public Integer getReviewScore() {
        return reviewScore;
    }

    public void setReviewScore(Integer reviewScore) {
        this.reviewScore = reviewScore;
    }

    public Integer getCollectCount() {
        return collectCount;
    }

    public void setCollectCount(Integer collectCount) {
        this.collectCount = collectCount;
    }

    public Integer getMinNights() {
        return minNights;
    }

    public void setMinNights(Integer minNights) {
        this.minNights = minNights;
    }

    public Integer getAllowsChildren() {
        return allowsChildren;
    }

    public void setAllowsChildren(Integer allowsChildren) {
        this.allowsChildren = allowsChildren;
    }

    public Integer getAllowsInfants() {
        return allowsInfants;
    }

    public void setAllowsInfants(Integer allowsInfants) {
        this.allowsInfants = allowsInfants;
    }

    public Integer getAllowsPets() {
        return allowsPets;
    }

    public void setAllowsPets(Integer allowsPets) {
        this.allowsPets = allowsPets;
    }

    public Integer getAllowsSmoking() {
        return allowsSmoking;
    }

    public void setAllowsSmoking(Integer allowsSmoking) {
        this.allowsSmoking = allowsSmoking;
    }

    public Integer getAllowsEvents() {
        return allowsEvents;
    }

    public void setAllowsEvents(Integer allowsEvents) {
        this.allowsEvents = allowsEvents;
    }

    public String getHouseRules() {
        return houseRules;
    }

    public void setHouseRules(String houseRules) {
        this.houseRules = houseRules == null ? null : houseRules.trim();
    }

    public Integer getInstantBookable() {
        return instantBookable;
    }

    public void setInstantBookable(Integer instantBookable) {
        this.instantBookable = instantBookable;
    }

    public String getCancellationPolicy() {
        return cancellationPolicy;
    }

    public void setCancellationPolicy(String cancellationPolicy) {
        this.cancellationPolicy = cancellationPolicy == null ? null : cancellationPolicy.trim();
    }

    public String getListingAmenities() {
        return listingAmenities;
    }

    public void setListingAmenities(String listingAmenities) {
        this.listingAmenities = listingAmenities == null ? null : listingAmenities.trim();
    }

    public String getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(String checkInTime) {
        this.checkInTime = checkInTime == null ? null : checkInTime.trim();
    }

    public String getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(String checkOutTime) {
        this.checkOutTime = checkOutTime == null ? null : checkOutTime.trim();
    }

    public Integer getHousePrice() {
        return housePrice;
    }

    public void setHousePrice(Integer housePrice) {
        this.housePrice = housePrice;
    }

    public Integer getSecurityDeposit() {
        return securityDeposit;
    }

    public void setSecurityDeposit(Integer securityDeposit) {
        this.securityDeposit = securityDeposit;
    }

    public Integer getCleaningFee() {
        return cleaningFee;
    }

    public void setCleaningFee(Integer cleaningFee) {
        this.cleaningFee = cleaningFee;
    }

    public String getPriceCurrency() {
        return priceCurrency;
    }

    public void setPriceCurrency(String priceCurrency) {
        this.priceCurrency = priceCurrency == null ? null : priceCurrency.trim();
    }

    public String getDescriptionLocale() {
        return descriptionLocale;
    }

    public void setDescriptionLocale(String descriptionLocale) {
        this.descriptionLocale = descriptionLocale == null ? null : descriptionLocale.trim();
    }

    public Date getCalendarLastUpdatedAt() {
        return calendarLastUpdatedAt;
    }

    public void setCalendarLastUpdatedAt(Date calendarLastUpdatedAt) {
        this.calendarLastUpdatedAt = calendarLastUpdatedAt;
    }

    public String getHostSn() {
        return hostSn;
    }

    public void setHostSn(String hostSn) {
        this.hostSn = hostSn == null ? null : hostSn.trim();
    }

    public Integer getIsSuperhost() {
        return isSuperhost;
    }

    public void setIsSuperhost(Integer isSuperhost) {
        this.isSuperhost = isSuperhost;
    }

    public String getHostImg() {
        return hostImg;
    }

    public void setHostImg(String hostImg) {
        this.hostImg = hostImg == null ? null : hostImg.trim();
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

	public List<AirbnbAdditionalHostsEntity> getAdditionalHostsEntities() {
		return additionalHostsEntities;
	}

	public List<AirbnbHousePriceEntity> getPriceEntities() {
		return priceEntities;
	}

	public void setAdditionalHostsEntities(
			List<AirbnbAdditionalHostsEntity> additionalHostsEntities) {
		this.additionalHostsEntities = additionalHostsEntities;
	}

	public void setPriceEntities(List<AirbnbHousePriceEntity> priceEntities) {
		this.priceEntities = priceEntities;
	}
    
}