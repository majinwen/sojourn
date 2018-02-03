
package com.ziroom.minsu.services.search.dto;


import java.util.Map;
import java.util.Set;


public class HouseInfoRequest extends BaseSearchRequest {


	/**
	 * 
	 */
	private static final long serialVersionUID = -6781163700490862459L;

	/** 城市code */
	private String cityCode;

	/** 区域code */
	private String areaCode;

	/** 热门景点 */
	private String hotReginScenic;

	/** 热门区域 */
	private String hotReginBusiness;

	/** 热搜的区域，(包含景点、商圈) */
	private String hotSearchRegion;

	/** 地铁 */
	private String subway;

	/** 名称 */
	private String q;

	/** 房屋类型 整租 合租 */
	private Integer rentWay;

	/** 多种出租方式，逗号隔开 */
	private String multiRentWay;

	/** 房间类型 0-房间 1-客厅 */
	private Integer roomType;

	/** 极速 普通 */
	private Integer orderType;

	/** 价格 */
	private Integer priceStart;

	private Integer priceEnd;

	/** 入住人数 */
	private Integer personCount;

	/** 房间数 */
	private Integer roomCount;

	/** 提供服务 */
	private Set<String> services;

	private String startTime;

	private String endTime;


	private String indexOrder;

	private String indexOrderType;

	private Map<String, Object> sorts;

	/**
	 * 排序类型
	 * @see com.ziroom.minsu.valenum.search.SortTypeEnum
	 */
	private Integer sortType; 

	/**
	 * 当前的地铁线
	 */
	private String lineFid;


	/**
	 * 当前的经纬度
	 */
	private String loc;

	
    /**
     * 房源类型，多个“,”号分割
     */
    private String houseType;

    /**
     * 床类型
     */
    private String bedType;
    
    /**
     * 长租折扣类型，多个“,”号分割
     */
    private String longTermDiscount;
    
    /**
     * 是否top50已经上线的房源
     */
    private Integer isTop50Online;
    
    
    /**
     * 灵活定价折扣类型，多个“,”号分割
     */
    private String jiaxinDiscount;
    
    
    /**
     * 是否top50列表展示房源
     */
    private Integer isTop50ListShow;
    
    /**
     * 是否与房东同住
     */
    private Integer isLandTogether;  
    
    
    /**
     * 房东姓名
     */
    private String landlordName;
    
    /**
     * 房源或者房间名称
     */
    private String houseName;
    
    /**
     * 房源名称
     */
    private String houseSn;
    
    
	public String getLandlordName() {
		return landlordName;
	}

	public String getHouseName() {
		return houseName;
	}

	public String getHouseSn() {
		return houseSn;
	}

	public void setLandlordName(String landlordName) {
		this.landlordName = landlordName;
	}

	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}

	public void setHouseSn(String houseSn) {
		this.houseSn = houseSn;
	}

	public Integer getIsLandTogether() {
		return isLandTogether;
	}

	public void setIsLandTogether(Integer isLandTogether) {
		this.isLandTogether = isLandTogether;
	}

	public Integer getIsTop50ListShow() {
		return isTop50ListShow;
	}

	public void setIsTop50ListShow(Integer isTop50ListShow) {
		this.isTop50ListShow = isTop50ListShow;
	}

	public String getJiaxinDiscount() {
		return jiaxinDiscount;
	}

	public void setJiaxinDiscount(String jiaxinDiscount) {
		this.jiaxinDiscount = jiaxinDiscount;
	}

	public Integer getIsTop50Online() {
		return isTop50Online;
	}

	public void setIsTop50Online(Integer isTop50Online) {
		this.isTop50Online = isTop50Online;
	}

	public String getLongTermDiscount() {
		return longTermDiscount;
	}

	public void setLongTermDiscount(String longTermDiscount) {
		this.longTermDiscount = longTermDiscount;
	}

	public String getSubway() {
		return subway;
	}

	public void setSubway(String subway) {
		this.subway = subway;
	}

	public String getLoc() {
		return loc;
	}
	
	public void setLoc(String loc) {
		this.loc = loc;
	}

	public String getHouseType() {
		return houseType;
	}

	public void setHouseType(String houseType) {
		this.houseType = houseType;
	}

    public String getBedType() {
        return bedType;
    }

    public void setBedType(String bedType) {
        this.bedType = bedType;
    }

    public String getIndexOrder() {
		return indexOrder;
	}

	public void setIndexOrder(String indexOrder) {
		this.indexOrder = indexOrder;
	}

	public String getIndexOrderType() {
		return indexOrderType;
	}

	public void setIndexOrderType(String indexOrderType) {
		this.indexOrderType = indexOrderType;
	}

	public Map<String, Object> getSorts() {
		return sorts;
	}

	public void setSorts(Map<String, Object> sorts) {
		this.sorts = sorts;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getHotReginScenic() {
		return hotReginScenic;
	}

	public void setHotReginScenic(String hotReginScenic) {
		this.hotReginScenic = hotReginScenic;
	}

	public String getHotReginBusiness() {
		return hotReginBusiness;
	}

	public void setHotReginBusiness(String hotReginBusiness) {
		this.hotReginBusiness = hotReginBusiness;
	}

	public String getHotSearchRegion() {
		return hotSearchRegion;
	}

	public void setHotSearchRegion(String hotSearchRegion) {
		this.hotSearchRegion = hotSearchRegion;
	}

	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}


    public Integer getRentWay() {
        return rentWay;
    }

    public void setRentWay(Integer rentWay) {
        this.rentWay = rentWay;
    }

    public String getMultiRentWay() {
        return multiRentWay;
    }

    public void setMultiRentWay(String multiRentWay) {
        this.multiRentWay = multiRentWay;
    }

    public Integer getRoomType() {
        return roomType;
    }

    public void setRoomType(Integer roomType) {
        this.roomType = roomType;
    }

    public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public Integer getPriceStart() {
		return priceStart;
	}

	public void setPriceStart(Integer priceStart) {
		this.priceStart = priceStart;
	}

	public Integer getPriceEnd() {
		return priceEnd;
	}

	public void setPriceEnd(Integer priceEnd) {
		this.priceEnd = priceEnd;
	}

	public Integer getPersonCount() {
		return personCount;
	}

	public void setPersonCount(Integer personCount) {
		this.personCount = personCount;
	}

	public Integer getRoomCount() {
		return roomCount;
	}

	public void setRoomCount(Integer roomCount) {
		this.roomCount = roomCount;
	}

	public Set<String> getServices() {
		return services;
	}

	public void setServices(Set<String> services) {
		this.services = services;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Integer getSortType() {
		return sortType;
	}

	public void setSortType(Integer sortType) {
		this.sortType = sortType;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getLineFid() {
		return lineFid;
	}

	public void setLineFid(String lineFid) {
		this.lineFid = lineFid;
	}
}
