package com.ziroom.minsu.services.search.vo;

import java.util.Date;
import java.util.Set;

import org.apache.solr.client.solrj.beans.Field;

import com.asura.framework.base.entity.BaseEntity;
import com.asura.framework.base.util.DateUtil;

/**
 * <p>房源信息</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/3/26.
 * @version 1.0
 * @since 1.0
 */
public class HouseInfo extends BaseEntity{
	
	/**
	 * 必须声明数据来源
	 * @see com.ziroom.minsu.valenum.search.SearchDataSourceTypeEnum
	 * @param dataSource
	 */
	public HouseInfo(Integer dataSource){
		this.dataSource = dataSource;
	}

    @Field
    private String id;

    /** 房源id */
    @Field
    private String houseId;

    /** 房间id */
    @Field
    private String roomId;

    /** 床铺id */
    @Field
    private String bedId;


    /** 房间 */
    @Field
    private Set<String>  rooms;

    /** 兄弟房间 */
    @Field
    private Set<String>  brotherRooms;

    /** 兄弟床位 */
    @Field
    private Set<String>  brotherBeds;

    /** 房屋类型 整租 合租 */
    @Field
    private Integer rentWay;

    @Field
    private String rentWayName;

    @Field
    private Integer roomType;
    
    /**
     * 房源类型
     */
    @Field
    private Integer houseType;
    
    @Field
    private String houseTypeName;

    /** 城市code */
    @Field
    private String cityCode;
    
    /** 城市名称 */
    @Field
    private String cityName;

    /** 区域code */
    @Field
    private String areaCode;

    /** 区域名称 */
    @Field
    private String areaName;

    /** 景点 */
    @Field
    private Set<String> hotReginScenic;


    /** 热门区域 */
    @Field
    private Set<String> hotReginBusiness;

    /** 地铁 */
    @Field
    private Set<String> subway;

    /** 地铁线 */
    @Field
    private Set<String> lineFidSet;

    /** 名称 */
    @Field
    private String houseName;

    /** 地址 */
    @Field
    private String houseAddr;

    /** 名称 */
    @Field
    private String houseNameAuto;

    /** 地址 */
    @Field
    private String houseAddrAuto;



    /** 极速 普通 */
    @Field
    private Integer orderType;

    @Field
    private String orderTypeName;

    /** 图片 */
    @Field
    private String picUrl;

    /** 房东uid */
    @Field
    private String landlordUid;

    /** 房东图片 */
    @Field
    private String landlordUrl;

    /** 房东名称 */
    @Field
    private String landlordName;

    /** 房东类型 */
    @Field
    private Integer landlordType;

    /** 房东昵称 */
    @Field
    private String nickName;

    /** 价格 */
    @Field
    private Integer price;

    /** 价格 */
    @Field
    private Set<String> prices;


    /** 周末价格 */
    @Field
    private Set<String> weekPrices;

    /** 入住人数 */
    @Field
    private Integer personCount;

    /** 房间数 */
    @Field
    private Integer roomCount;


    /** 是否智能锁 */
    @Field
    private Integer isLock;

    /** 床铺信息 */
    @Field
    private Set<String> bedList;

    /** 卫生间数量 */
    @Field
    private Integer toiletCount;

    /** 是否独立卫生间 */
    @Field
    private Integer isToilet;

    /** 阳台数量 */
    @Field
    private Integer balconyCount;

    /** 是否阳台 */
    @Field
    private Integer isBalcony;

    /** 提供服务 */
    @Field
    private Set<String> services;

    /** 占用天数 */
    @Field
    private Set<String> occupyDays;

    /** 入住的最小天数 */
    @Field
    private Integer minDay;

    /** 更新时间 */
    @Field
    private Long refreshDate;

    /** 上架时间 */
    @Field
    private Long passDate;

    /** 权重 */
    @Field
    private Long weights;

    /** 权重构成明细 */
    @Field
    private String weightsComposition;

    /** 房子的上架时间 */
    @Field
    private Long houseEndTime =0L;

    /** 纬度，经度 */
    @Field
    private String loc;

    /** 评价数量 */
    @Field
    private Integer evaluateCount;

    /** 评价分 */
    @Field
    private Double evaluateScore;
    
    /** 真实评价分 */
    @Field
    private Double realEvaluateScore;

    /** 创建时间 */
    @Field
    private String createTime = DateUtil.dateFormat(new Date(), "yyyy-MM-dd HH:mm:ss");

    /**
     * 房源品质登记 A B C
     */
    @Field
  	private String houseQualityGrade; 
    
    /** 夹心日期 */
    @Field
    private Set<String> priorityDate;
    
    /**
     * 长租折扣
     */
    @Field
    private Set<String> longTermLeaseDiscount;
    
    /**
     * 灵活定价
     */
    @Field
    private Set<String> flexiblePrice;

    
    /**
     * 当前所属商圈景点最大热度
     */
    @Field
    private Integer currentReginMaxHot=0; 
    
    /**
     * 当前城市热度
     */
    @Field
    private Integer currentCityHot=0; 
    
    /**
     * 是否特色房源
     */
    @Field
    private Integer isFeatureHouse=0;
    
    /**
     * 房源或者房间编号
     */
    @Field
  	private String houseSn; 
    

    /** 权重标记排序 */
    @Field
    private Integer adIndex = 0;

    /** 是否是最近房源 */
    @Field
    private Integer isNew = 0;
    
    /** 商圈景点热度排序标记 */
    @Field
    private Integer currentReginMaxHotIndex = 0;
    
    /** 特色房源排序标记 */
    @Field
    private Integer featureHouseIndex = 0;  
    
    /**
     * 是否top50已经上线的房源
     */
    @Field
    private Integer isTop50Online=0;
    
    
    /** 个性化标签 */
    @Field
    private Set<String> indivLabelTipsList;
    
    /** 品牌编码 */
    @Field
    private String brandSn;
    
    /** top50标题 */
    @Field
    private String top50Title;  
    
    /** 房源序号 */
    @Field
    private Integer houseIndexByLand=0;
    
    /** 房间序号  */
    @Field
    private Integer roomIndexByHouse=0; 
    
    /**  是否top50榜单页显示房源  */
    @Field
    private Integer isTop50ListShow=0;
    
    /** 是否与房东同住  */
    @Field
    private Integer isLandTogether=0; 
    
    /** 今日特价生效时间 */
    @Field
    private Long tonightDisOpenDate;
    
    /** 今日特价截止时间 */
    @Field
    private Long tonightDisDeadlineDate;

    /** 今夜特价折扣值 */
    @Field
    private Double tonightDiscount;

    /** 60天内接受的单数 */
    @Field
    private Long acceptOrder60DaysCount=0L;
    
    /** 60天总单数 */
    @Field
    private Long order60DaysCount=0L;

    /** 状态 */
    @Field
    private  Integer status;

    /** 状态名称 */
    @Field
    private String statusName;

    /** 库存总量*/
    @Field
    private Integer totalStock;

    /**
     * 将来日期的订单锁定库存数
     */
    @Field
    private Set<String> dayOrderLockedStocks;

    /**
     * 将来日期的业主锁定库存数
     */
    @Field
    private Set<String> daySelfLockedStocks;

    /** 最大价格 */
    @Field
    private Integer priceMax;

    /** 房型数目 */
    @Field
    private Integer houseModelCount;

    /** 次序 */
    @Field
    private Integer order;

    /** 数据来源 */
    @Field
    private Integer dataSource;

    public Integer getDataSource() {
        return dataSource;
    }

    public void setDataSource(Integer dataSource) {
        this.dataSource = dataSource;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Integer getTotalStock() {
        return totalStock;
    }

    public void setTotalStock(Integer totalStock) {
        this.totalStock = totalStock;
    }

    public Set<String> getDayOrderLockedStocks() {
        return dayOrderLockedStocks;
    }

    public void setDayOrderLockedStocks(Set<String> dayOrderLockedStocks) {
        this.dayOrderLockedStocks = dayOrderLockedStocks;
    }

    public Set<String> getDaySelfLockedStocks() {
        return daySelfLockedStocks;
    }

    public void setDaySelfLockedStocks(Set<String> daySelfLockedStocks) {
        this.daySelfLockedStocks = daySelfLockedStocks;
    }

    public Integer getPriceMax() {
        return priceMax;
    }

    public void setPriceMax(Integer priceMax) {
        this.priceMax = priceMax;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Integer getHouseModelCount() {
        return houseModelCount;
    }

    public void setHouseModelCount(Integer houseModelCount) {
        this.houseModelCount = houseModelCount;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Integer getLandlordType() {
        return landlordType;
    }

    public void setLandlordType(Integer landlordType) {
        this.landlordType = landlordType;
    }

    public Long getAcceptOrder60DaysCount() {
		return acceptOrder60DaysCount;
	}

	public Long getOrder60DaysCount() {
		return order60DaysCount;
	}

	public void setAcceptOrder60DaysCount(Long acceptOrder60DaysCount) {
		this.acceptOrder60DaysCount = acceptOrder60DaysCount;
	}

	public void setOrder60DaysCount(Long order60DaysCount) {
		this.order60DaysCount = order60DaysCount;
	}

	public Long getTonightDisOpenDate() {
		return tonightDisOpenDate;
	}

	public Long getTonightDisDeadlineDate() {
		return tonightDisDeadlineDate;
	}

	public void setTonightDisOpenDate(Long tonightDisOpenDate) {
		this.tonightDisOpenDate = tonightDisOpenDate;
	}

	public void setTonightDisDeadlineDate(Long tonightDisDeadlineDate) {
		this.tonightDisDeadlineDate = tonightDisDeadlineDate;
	}

    public Double getTonightDiscount() {
        return tonightDiscount;
    }

    public void setTonightDiscount(Double tonightDiscount) {
        this.tonightDiscount = tonightDiscount;
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

	public Integer getHouseIndexByLand() {
		return houseIndexByLand;
	}

	public Integer getRoomIndexByHouse() {
		return roomIndexByHouse;
	}

	public void setHouseIndexByLand(Integer houseIndexByLand) {
		this.houseIndexByLand = houseIndexByLand;
	}

	public void setRoomIndexByHouse(Integer roomIndexByHouse) {
		this.roomIndexByHouse = roomIndexByHouse;
	}

	public String getBrandSn() {
		return brandSn;
	}

	public String getTop50Title() {
		return top50Title;
	}

	public void setBrandSn(String brandSn) {
		this.brandSn = brandSn;
	}

	public void setTop50Title(String top50Title) {
		this.top50Title = top50Title;
	}

	public Set<String> getIndivLabelTipsList() {
		return indivLabelTipsList;
	}

	public void setIndivLabelTipsList(Set<String> indivLabelTipsList) {
		this.indivLabelTipsList = indivLabelTipsList;
	}
    
	public Integer getIsTop50Online() {
		return isTop50Online;
	}

	public void setIsTop50Online(Integer isTop50Online) {
		this.isTop50Online = isTop50Online;
	}

	public String getHouseSn() {
		return houseSn;
	}

	public void setHouseSn(String houseSn) {
		this.houseSn = houseSn;
	}

	public Integer getCurrentCityHot() {
		return currentCityHot;
	}

	public void setCurrentCityHot(Integer currentCityHot) {
		this.currentCityHot = currentCityHot;
	}

	public Integer getIsFeatureHouse() {
		return isFeatureHouse;
	}

	public void setIsFeatureHouse(Integer isFeatureHouse) {
		this.isFeatureHouse = isFeatureHouse;
	}

	public Integer getCurrentReginMaxHotIndex() {
		return currentReginMaxHotIndex;
	}

	public Integer getFeatureHouseIndex() {
		return featureHouseIndex;
	} 

	public void setCurrentReginMaxHotIndex(Integer currentReginMaxHotIndex) {
		this.currentReginMaxHotIndex = currentReginMaxHotIndex;
	}

	public void setFeatureHouseIndex(Integer featureHouseIndex) {
		this.featureHouseIndex = featureHouseIndex;
	} 

	public Set<String> getPriorityDate() {
		return priorityDate;
	}

	public Set<String> getLongTermLeaseDiscount() {
		return longTermLeaseDiscount;
	}

	public Set<String> getFlexiblePrice() {
		return flexiblePrice;
	}

	public Integer getCurrentReginMaxHot() {
		return currentReginMaxHot;
	}


	public void setPriorityDate(Set<String> priorityDate) {
		this.priorityDate = priorityDate;
	}

	public void setLongTermLeaseDiscount(Set<String> longTermLeaseDiscount) {
		this.longTermLeaseDiscount = longTermLeaseDiscount;
	}

	public void setFlexiblePrice(Set<String> flexiblePrice) {
		this.flexiblePrice = flexiblePrice;
	}

	public void setCurrentReginMaxHot(Integer currentReginMaxHot) {
		this.currentReginMaxHot = currentReginMaxHot;
	}

	public String getHouseQualityGrade() {
		return houseQualityGrade;
	}

	public void setHouseQualityGrade(String houseQualityGrade) {
		this.houseQualityGrade = houseQualityGrade;
	}

	public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getLandlordName() {
        return landlordName;
    }

    public void setLandlordName(String landlordName) {
        this.landlordName = landlordName;
    }

    public Integer getMinDay() {
        return minDay;
    }

    public void setMinDay(Integer minDay) {
        this.minDay = minDay;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getEvaluateCount() {
        return evaluateCount;
    }

    public void setEvaluateCount(Integer evaluateCount) {
        this.evaluateCount = evaluateCount;
    }

    public Long getHouseEndTime() {
        return houseEndTime;
    }

    public void setHouseEndTime(Long houseEndTime) {
        this.houseEndTime = houseEndTime;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public Long getWeights() {
        return weights;
    }

    public void setWeights(Long weights) {
        this.weights = weights;
    }

    public String getWeightsComposition() {
        return weightsComposition;
    }

    public void setWeightsComposition(String weightsComposition) {
        this.weightsComposition = weightsComposition;
    }

    public String getLandlordUrl() {
        return landlordUrl;
    }

    public void setLandlordUrl(String landlordUrl) {
        this.landlordUrl = landlordUrl;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getBedId() {
        return bedId;
    }

    public void setBedId(String bedId) {
        this.bedId = bedId;
    }

    public Set<String> getRooms() {
        return rooms;
    }

    public void setRooms(Set<String> rooms) {
        this.rooms = rooms;
    }

    public Set<String> getBrotherRooms() {
        return brotherRooms;
    }

    public void setBrotherRooms(Set<String> brotherRooms) {
        this.brotherRooms = brotherRooms;
    }

    public Set<String> getBrotherBeds() {
        return brotherBeds;
    }

    public void setBrotherBeds(Set<String> brotherBeds) {
        this.brotherBeds = brotherBeds;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHouseNameAuto() {
        return houseNameAuto;
    }

    public void setHouseNameAuto(String houseNameAuto) {
        this.houseNameAuto = houseNameAuto;
    }

    public String getHouseAddrAuto() {
        return houseAddrAuto;
    }

    public void setHouseAddrAuto(String houseAddrAuto) {
        this.houseAddrAuto = houseAddrAuto;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Long getRefreshDate() {
        return refreshDate;
    }

    public void setRefreshDate(Long refreshDate) {
        this.refreshDate = refreshDate;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public String getHouseAddr() {
        return houseAddr;
    }

    public void setHouseAddr(String houseAddr) {
        this.houseAddr = houseAddr;
    }


    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
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

    public Set<String> getOccupyDays() {
        return occupyDays;
    }

    public void setOccupyDays(Set<String> occupyDays) {
        this.occupyDays = occupyDays;
    }


    public Set<String> getHotReginScenic() {
        return hotReginScenic;
    }

    public void setHotReginScenic(Set<String> hotReginScenic) {
        this.hotReginScenic = hotReginScenic;
    }

    public Set<String> getHotReginBusiness() {
        return hotReginBusiness;
    }

    public void setHotReginBusiness(Set<String> hotReginBusiness) {
        this.hotReginBusiness = hotReginBusiness;
    }


    public String getOrderTypeName() {
        return orderTypeName;
    }

    public void setOrderTypeName(String orderTypeName) {
        this.orderTypeName = orderTypeName;
    }

    public Integer getRentWay() {
        return rentWay;
    }

    public void setRentWay(Integer rentWay) {
        this.rentWay = rentWay;
    }

    public String getRentWayName() {
        return rentWayName;
    }

    public void setRentWayName(String rentWayName) {
        this.rentWayName = rentWayName;
    }

    public Integer getRoomType() {
        return roomType;
    }

    public void setRoomType(Integer roomType) {
        this.roomType = roomType;
    }

    public Double getEvaluateScore() {
        return evaluateScore;
    }

    public void setEvaluateScore(Double evaluateScore) {
        this.evaluateScore = evaluateScore;
    }
    
    public Double getRealEvaluateScore() {
		return realEvaluateScore;
	}

	public void setRealEvaluateScore(Double realEvaluateScore) {
		this.realEvaluateScore = realEvaluateScore;
	}

	public String getLandlordUid() {
        return landlordUid;
    }

    public void setLandlordUid(String landlordUid) {
        this.landlordUid = landlordUid;
    }

    public Set<String> getPrices() {
        return prices;
    }

    public void setPrices(Set<String> prices) {
        this.prices = prices;
    }

    public Set<String> getSubway() {
        return subway;
    }

    public void setSubway(Set<String> subway) {
        this.subway = subway;
    }

    public Set<String> getBedList() {
        return bedList;
    }

    public void setBedList(Set<String> bedList) {
        this.bedList = bedList;
    }

    public Integer getToiletCount() {
        return toiletCount;
    }

    public void setToiletCount(Integer toiletCount) {
        this.toiletCount = toiletCount;
    }

    public Integer getIsToilet() {
        return isToilet;
    }

    public void setIsToilet(Integer isToilet) {
        this.isToilet = isToilet;
    }

    public Integer getIsLock() {
        return isLock;
    }

    public void setIsLock(Integer isLock) {
        this.isLock = isLock;
    }

    public Integer getBalconyCount() {
        return balconyCount;
    }

    public void setBalconyCount(Integer balconyCount) {
        this.balconyCount = balconyCount;
    }

    public Integer getIsBalcony() {
        return isBalcony;
    }

    public void setIsBalcony(Integer isBalcony) {
        this.isBalcony = isBalcony;
    }

    public Integer getAdIndex() {
        return adIndex;
    }

    public void setAdIndex(Integer adIndex) {
        this.adIndex = adIndex;
    }

    public Integer getIsNew() {
        return isNew;
    }

    public void setIsNew(Integer isNew) {
        this.isNew = isNew;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public Set<String> getLineFidSet() {
        return lineFidSet;
    }

    public void setLineFidSet(Set<String> lineFidSet) {
        this.lineFidSet = lineFidSet;
    }

    public Set<String> getWeekPrices() {
        return weekPrices;
    }

    public void setWeekPrices(Set<String> weekPrices) {
        this.weekPrices = weekPrices;
    }

	public Integer getHouseType() {
		return houseType;
	}

	public void setHouseType(Integer houseType) {
		this.houseType = houseType;
	}

	public String getHouseTypeName() {
		return houseTypeName;
	}

	public void setHouseTypeName(String houseTypeName) {
		this.houseTypeName = houseTypeName;
	}


    public Long getPassDate() {
        return passDate;
    }

    public void setPassDate(Long passDate) {
        this.passDate = passDate;
    }
}
