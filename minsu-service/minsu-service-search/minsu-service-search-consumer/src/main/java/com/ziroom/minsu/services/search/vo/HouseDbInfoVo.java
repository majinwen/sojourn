package com.ziroom.minsu.services.search.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.minsu.entity.house.TonightDiscountEntity;

/**
 * <p>房源的数据库信息</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/6.
 * @version 1.0
 * @since 1.0
 */
public class HouseDbInfoVo extends BaseEntity {

    /** 序列化id */
    private static final long serialVersionUID = -2147121231215641L;

    /** 房源fid */
    private String houseFid;

    /** 房间fid */
    private String roomFid;

    /** 房源名称 */
    private String houseName;

    /** 屋子名称 */
    private String roomName;

    /** 房间类型 0：房间 1：客厅*/
    private Integer roomType;

    /** 房源地址 */
    private String houseAddr;

    /** 房源类型 */
    private Integer rentWay;

    /** 房源渠道类型 */
    private Integer houseChannel;

    /** 房源状态 */
    private Integer houseStatus;

    /** 房源状态 */
    private Integer roomStatus;

    /** 房东uid */
    private String landlordUid;

    /** 完整率 */
    private Double intactRate;

    /** 城市code */
    private String cityCode;
    
    /** 城市名称 */
    private String cityName;

    /** 区域code */
    private String areaCode;


    /** 基础价格 */
    private Integer leasePrice;

    /** 房间价格 */
    private Integer roomPrice;

    /** 截止时间 */
    private Date tillDate;

    /** 最小入住天数 */
    private Integer minDay;

    /** 分租最小入住天数 */
    private Integer roomMinDay;


//    /** 最大入住天数 */
//    private String maxDay;

    /** 房间数 */
    private Integer roomNum;

    /** 卫生间数量 */
    private Integer toiletNum;

    /** 是否卫生间 */
    private Integer isToilet;


    /** 阳台数量 */
    private Integer balconyNum;


    /** 是否阳台 */
    private Integer isBalcony;



    /** 房源的入住人数 */
    private Integer houseCheckInLimit;


    /** 房间的入住人数 */
    private Integer roomCheckInLimit;

    /** 房源权重 */
    private Integer houseWeight;

    /** 房间权重 */
    private Integer roomWeight;

    /** 房源的默认图片 */
    private String houseDefaultPicFid;

    /** 房源的原来的默认图片 */
    private String houseOldDefaultPicFid;


    /** 房间的默认图片 */
    private String roomDefaultPicFid;

    /** 房间的原来的默认图片 */
    private String roomOldDefaultPicFid;
    
    /**
     * 房源类型
     */
    private Integer houseType;
    
    private String houseTypeName;


    /**
     * 下单类型 及时还是普通
     * @see com.ziroom.minsu.valenum.order.OrderTypeEnum
     */
    private Integer orderType;


    /**
     * 分租下单类型 及时还是普通
     * @see com.ziroom.minsu.valenum.order.OrderTypeEnum
     */
    private Integer roomOrderType;

    /**
     * 是否与房东合住
     * @see com.ziroom.minsu.valenum.order.IsHezhuEnum
     */
    private Integer isHezhu;

    /** 刷新时间 */
    private Date refreshDate;

    /** 经度 */
    private String longitude;

    /** 纬度 */
    private String latitude;

    /** 占用天数 */
    private List<String>  occupyDays;

    /** 提供的服务集合 */
    private List<String>  services;

    /** 商圈 */
    private List<String>  hotReginBusiness;

    /** 景点 */
    private List<String>  hotReginScenic;


    /** 虚拟整租房间 */
    private List<String>  rooms;

    /** 合租的兄弟房间 */
    private List<String>  brotherRooms;

    /** 按照床位出租的兄弟床铺 */
    private List<String>  brotherBeds;

    /** 评价数量 */
    private Integer evaluateCount = 0;

    /** 最短景点距离 */
    private Double regionDisMin = 0.0;

    /** 最短景点距离 */
    private Double subWayDisMin = 0.0;

    /** 所在商圈的最大热度 */
    private Integer  hotMax = 0;

    /** 房源面积 */
    private String houseArea;

    /** 房间面积 */
    private String roomArea;
    
    /**
     * 获取配置信息
     */
    private List<ConfigVo> configList = new ArrayList<ConfigVo>();
    
    /**
     * 房源品质登记 A B C
     */
  	private String houseQualityGrade;
  	
  	/**
  	 * 夹心日期
  	 */
    private List<String>  priorityDate;
    
    /**
     * 长租折扣
     */
    private List<String> longTermLeaseDiscount;
    
    /**
     * 灵活定价
     */
    private List<String> flexiblePrice;
    
    /**
     * 当前城市热度
     */
    private Integer currentCityHot=0; 
    
    
    /**
     * 当前所属商圈景点最大热度
     */
    private Integer currentReginMaxHot=0; 
    
    /**
     * 当前房源热度
     */
    private Integer currentHot=0; 
    
    /**
     * 是否特色房源
     */
    private Integer isFeatureHouse=0;
    
    /** 房源编号 */
    private String houseSn;

    /** 房间编号 */
    private String roomSn;
    
    /**
     * 是否top50已经上线的房源
     */
    private Integer isTop50Online=0;
    
    /**
     * 是否top50榜单页显示房源
     */
    private Integer isTop50ListShow=0;
    
    
    /** 个性化标签 */
    private List<String> indivLabelTipsList;
    
    /** 品牌编码 */
    private String brandSn;
    
    /** top50标题 */
    private String top50Title;
    
    /** 房源序号 */
    private Integer houseIndexByLand=0;
    
    /** 房间序号  */
    private Integer roomIndexByHouse=0;    
    
    /** top50列表显示的房间fid */
    private String top50ListRoomFid; 
    
    /**  今夜特价    */
    private TonightDiscountEntity tonightDiscount;

    /** 60天内接受的单数 */
    private Long acceptOrder60DaysCount=0L;
    
    /** 60天总单数 */
    private Long order60DaysCount=0L;


    public Integer getRoomMinDay() {
        return roomMinDay;
    }

    public void setRoomMinDay(Integer roomMinDay) {
        this.roomMinDay = roomMinDay;
    }

    public Integer getRoomOrderType() {
        return roomOrderType;
    }

    public void setRoomOrderType(Integer roomOrderType) {
        this.roomOrderType = roomOrderType;
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

	public TonightDiscountEntity getTonightDiscount() {
		return tonightDiscount;
	}

	public void setTonightDiscount(TonightDiscountEntity tonightDiscount) {
		this.tonightDiscount = tonightDiscount;
	}

	public String getTop50ListRoomFid() {
		return top50ListRoomFid;
	}

	public void setTop50ListRoomFid(String top50ListRoomFid) {
		this.top50ListRoomFid = top50ListRoomFid;
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

	public List<String> getIndivLabelTipsList() {
		return indivLabelTipsList;
	}

	public void setIndivLabelTipsList(List<String> indivLabelTipsList) {
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

	public String getRoomSn() {
		return roomSn;
	}

	public void setHouseSn(String houseSn) {
		this.houseSn = houseSn;
	}

	public void setRoomSn(String roomSn) {
		this.roomSn = roomSn;
	}

	public Integer getIsFeatureHouse() {
		return isFeatureHouse;
	}

	public void setIsFeatureHouse(Integer isFeatureHouse) {
		this.isFeatureHouse = isFeatureHouse;
	}

	public List<String> getPriorityDate() {
		return priorityDate;
	}

	public List<String> getLongTermLeaseDiscount() {
		return longTermLeaseDiscount;
	}

	public List<String> getFlexiblePrice() {
		return flexiblePrice;
	}

	public Integer getCurrentCityHot() {
		return currentCityHot;
	}

	public Integer getCurrentReginMaxHot() {
		return currentReginMaxHot;
	}

	public Integer getCurrentHot() {
		return currentHot;
	}

	public void setPriorityDate(List<String> priorityDate) {
		this.priorityDate = priorityDate;
	}

	public void setLongTermLeaseDiscount(List<String> longTermLeaseDiscount) {
		this.longTermLeaseDiscount = longTermLeaseDiscount;
	}

	public void setFlexiblePrice(List<String> flexiblePrice) {
		this.flexiblePrice = flexiblePrice;
	}

	public void setCurrentCityHot(Integer currentCityHot) {
		this.currentCityHot = currentCityHot;
	}

	public void setCurrentReginMaxHot(Integer currentReginMaxHot) {
		this.currentReginMaxHot = currentReginMaxHot;
	}

	public void setCurrentHot(Integer currentHot) {
		this.currentHot = currentHot;
	}

	public String getHouseQualityGrade() {
		return houseQualityGrade;
	}

	public void setHouseQualityGrade(String houseQualityGrade) {
		this.houseQualityGrade = houseQualityGrade;
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

	public String getHouseDefaultPicFid() {
        return houseDefaultPicFid;
    }

    public void setHouseDefaultPicFid(String houseDefaultPicFid) {
        this.houseDefaultPicFid = houseDefaultPicFid;
    }

    public String getHouseOldDefaultPicFid() {
        return houseOldDefaultPicFid;
    }

    public void setHouseOldDefaultPicFid(String houseOldDefaultPicFid) {
        this.houseOldDefaultPicFid = houseOldDefaultPicFid;
    }

    public String getRoomDefaultPicFid() {
        return roomDefaultPicFid;
    }

    public void setRoomDefaultPicFid(String roomDefaultPicFid) {
        this.roomDefaultPicFid = roomDefaultPicFid;
    }

    public String getRoomOldDefaultPicFid() {
        return roomOldDefaultPicFid;
    }

    public void setRoomOldDefaultPicFid(String roomOldDefaultPicFid) {
        this.roomOldDefaultPicFid = roomOldDefaultPicFid;
    }

	public String getHouseArea() {
        return houseArea;
    }

    public void setHouseArea(String houseArea) {
        this.houseArea = houseArea;
    }

    public String getRoomArea() {
        return roomArea;
    }

    public void setRoomArea(String roomArea) {
        this.roomArea = roomArea;
    }

    public Double getRegionDisMin() {
        return regionDisMin;
    }

    public void setRegionDisMin(Double regionDisMin) {
        this.regionDisMin = regionDisMin;
    }

    public Double getSubWayDisMin() {
        return subWayDisMin;
    }

    public void setSubWayDisMin(Double subWayDisMin) {
        this.subWayDisMin = subWayDisMin;
    }

    public Integer getHotMax() {
        return hotMax;
    }

    public void setHotMax(Integer hotMax) {
        this.hotMax = hotMax;
    }

    public Integer getEvaluateCount() {
        return evaluateCount;
    }

    public void setEvaluateCount(Integer evaluateCount) {
        this.evaluateCount = evaluateCount;
    }

    public Double getIntactRate() {
        return intactRate;
    }

    public void setIntactRate(Double intactRate) {
        this.intactRate = intactRate;
    }

    public Integer getHouseCheckInLimit() {
        return houseCheckInLimit;
    }

    public void setHouseCheckInLimit(Integer houseCheckInLimit) {
        this.houseCheckInLimit = houseCheckInLimit;
    }

    public Integer getRoomCheckInLimit() {
        return roomCheckInLimit;
    }

    public void setRoomCheckInLimit(Integer roomCheckInLimit) {
        this.roomCheckInLimit = roomCheckInLimit;
    }

    public Integer getIsHezhu() {
        return isHezhu;
    }

    public void setIsHezhu(Integer isHezhu) {
        this.isHezhu = isHezhu;
    }

    public List<ConfigVo> getConfigList() {
        return configList;
    }

    public void setConfigList(List<ConfigVo> configList) {
        this.configList = configList;
    }


    public String getRoomFid() {
        return roomFid;
    }

    public void setRoomFid(String roomFid) {
        this.roomFid = roomFid;
    }

    public List<String> getOccupyDays() {
        return occupyDays;
    }

    public void setOccupyDays(List<String> occupyDays) {
        this.occupyDays = occupyDays;
    }

    public List<String> getServices() {
        return services;
    }

    public void setServices(List<String> services) {
        this.services = services;
    }

    public List<String> getHotReginBusiness() {
        return hotReginBusiness;
    }

    public void setHotReginBusiness(List<String> hotReginBusiness) {
        this.hotReginBusiness = hotReginBusiness;
    }

    public List<String> getHotReginScenic() {
        return hotReginScenic;
    }

    public void setHotReginScenic(List<String> hotReginScenic) {
        this.hotReginScenic = hotReginScenic;
    }

    public List<String> getRooms() {
        return rooms;
    }

    public void setRooms(List<String> rooms) {
        this.rooms = rooms;
    }

    public List<String> getBrotherRooms() {
        return brotherRooms;
    }

    public void setBrotherRooms(List<String> brotherRooms) {
        this.brotherRooms = brotherRooms;
    }

    public List<String> getBrotherBeds() {
        return brotherBeds;
    }

    public void setBrotherBeds(List<String> brotherBeds) {
        this.brotherBeds = brotherBeds;
    }

    public String getHouseFid() {
        return houseFid;
    }

    public void setHouseFid(String houseFid) {
        this.houseFid = houseFid;
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

    public Integer getRentWay() {
        return rentWay;
    }

    public void setRentWay(Integer rentWay) {
        this.rentWay = rentWay;
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

	public Integer getLeasePrice() {
        return leasePrice;
    }

    public void setLeasePrice(Integer leasePrice) {
        this.leasePrice = leasePrice;
    }

    public Date getTillDate() {
        return tillDate;
    }

    public void setTillDate(Date tillDate) {
        this.tillDate = tillDate;
    }

    public Integer getMinDay() {
        return minDay;
    }

    public void setMinDay(Integer minDay) {
        this.minDay = minDay;
    }


    public Integer getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(Integer roomNum) {
        this.roomNum = roomNum;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public Date getRefreshDate() {
        return refreshDate;
    }

    public void setRefreshDate(Date refreshDate) {
        this.refreshDate = refreshDate;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLandlordUid() {
        return landlordUid;
    }

    public void setLandlordUid(String landlordUid) {
        this.landlordUid = landlordUid;
    }

    public Integer getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(Integer roomPrice) {
        this.roomPrice = roomPrice;
    }

    public Integer getHouseWeight() {
        return houseWeight;
    }

    public void setHouseWeight(Integer houseWeight) {
        this.houseWeight = houseWeight;
    }

    public Integer getHouseStatus() {
        return houseStatus;
    }

    public void setHouseStatus(Integer houseStatus) {
        this.houseStatus = houseStatus;
    }

    public Integer getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(Integer roomStatus) {
        this.roomStatus = roomStatus;
    }


    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Integer getRoomType() {
        return roomType;
    }

    public void setRoomType(Integer roomType) {
        this.roomType = roomType;
    }

    public Integer getToiletNum() {
        return toiletNum;
    }

    public void setToiletNum(Integer toiletNum) {
        this.toiletNum = toiletNum;
    }

    public Integer getBalconyNum() {
        return balconyNum;
    }

    public void setBalconyNum(Integer balconyNum) {
        this.balconyNum = balconyNum;
    }

    public Integer getIsToilet() {
        return isToilet;
    }

    public void setIsToilet(Integer isToilet) {
        this.isToilet = isToilet;
    }

    public Integer getIsBalcony() {
        return isBalcony;
    }

    public void setIsBalcony(Integer isBalcony) {
        this.isBalcony = isBalcony;
    }

    public Integer getRoomWeight() {
        return roomWeight;
    }

    public void setRoomWeight(Integer roomWeight) {
        this.roomWeight = roomWeight;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public Integer getHouseChannel() {
        return houseChannel;
    }

    public void setHouseChannel(Integer houseChannel) {
        this.houseChannel = houseChannel;
    }
}
