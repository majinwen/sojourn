/**
 * @FileName: TenantHouseDetailVo.java
 * @Package com.ziroom.minsu.services.house.entity
 * 
 * @author bushujie
 * @created 2016年4月30日 下午8:48:45
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.entity;

import com.ziroom.minsu.entity.base.MinsuEleEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
/**
 * <p>房客端房源详情</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
public class TenantHouseDetailVo {
	
	/**
	 * 房源或房间fid
	 */
	private String fid;
	
	/**
	 * 房源或房间名称
	 */
	private String houseName;

	/**
	 * @see com.ziroom.minsu.valenum.house.HouseStatusEnum
	 */
	private Integer houseStatus;

	/**
	 * 房源或房间价格
	 */
	private Integer housePrice;
	
	/**
	 * 出租方式 0：整租，1：合租，2：床位
	 */
	private Integer rentWay;
	
	/**
	 * 出租方式名称
	 */
	private String rentWayName;
	/**
	 * 房间类型 0: 房间  1:共享客厅
	 */
	private Integer roomType;

	public Integer getRoomType() {
		return roomType;
	}

	public void setRoomType(Integer roomType) {
		this.roomType = roomType;
	}

	/**
	 * 评论星级
	 */
	private Float gradeStar=0f;
	
	/**
	 * 房源或者房间图片
	 */
	private List<String> picList=new ArrayList<String>();


    /**
     * 带描述的图片信息
     */
    private List<MinsuEleEntity> picDisList = new ArrayList<>();

	/**
	 * 房东fid
	 */
	private String landlordUid;
	
	/**
	 * 房源周边信息
	 */
	private String houseAroundDesc;
	
	/**
	 * 默认图片
	 */
	private String defaultPic;
	
	/**
	 * 卧室数量
	 */
	private Integer roomNum;
	
	/**
	 * 厕所数量
	 */
	private Integer toiletNum;
	
	/**
	 * 厨房数量
	 */
	private Integer kitchenNum;
	
	/**
	 * 入住人数限制 0：不限制
	 */
	private Integer checkInLimit;
	
	/**
	 * 是否独立卫生间 0：否，1：是
	 */
	private Integer istoilet=0;
	
	/**
	 * 评论总数
	 */
	private Integer evaluateCount=0;
	
	/**
	 *  打折列表
	 */
	private List<String> discountMsg=new ArrayList<String>(); 
	
	/**
	 * 是否与房东同住
	 */
	private Integer isTogetherLandlord=0;
	
	/**
	 * 房东头像
	 */
	private String landlordIcon;
	
	/**
	 * 房东姓名
	 */
	private String landlordName;
	
	/**
	 * 房源描述
	 */
	private String houseDesc;
	
	/**
	 * 经度
	 */
	private Double longitude;

	/**
	 * 纬度
	 */
	private Double latitude;

	/**
	 * 谷歌经度
	 */
	private Double googleLongitude;

	/**
	 * 谷歌纬度
	 */
	private Double googleLatitude;
	
	/**
	 * 最少入住天数
	 */
	private Integer minDay;
	
	/**
	 * 入住时间
	 */
	private String checkInTime;
	
	/**
	 * 退房时间
	 */
	private String checkOutTime;
	
	/**
	 * 床单更换规则
	 */
	private Integer sheetsReplaceRulesValue;
	
	/**
	 * 床单更换规则值
	 */
	private String sheetsReplaceRulesName;
	
	/**
	 * 下单类型
	 */
	private Integer orderType;
	
	/**
	 * 下单类型名称
	 */
	private String orderTypeName;
	
	/**
	 * 押金规则code
	 */
	private String depositRulesCode;
	
	/**
	 * 押金规则值
	 */
	private String depositRulesValue;
	
	/**
	 * 押金规则名称
	 */
	private String depositRulesName;

	/**
	 * 退订政策code
	 */
	private String checkOutRulesCode;
	
	/**
	 * 退订政策name
	 */
	private String checkOutRulesName;
	
	/**
	 * 一条评论信息
	 */
	private TenantEvalVo tenantEvalVo;

	
	/**
	 * 小区名称
	 */
	private String communityName;
	
	/**
	 * 房东电话
	 */
	private String landlordMobile;
	
	/**
	 * 房源详细地址
	 */
	private String houseAddr;
	
	/**
	 * 房源或者房间的面积
	 */
	private Float houseArea;
	
	/**
	 * 房源或者房间床类型数量
	 */
	private List<HouseBedNumVo> bedList=new ArrayList<HouseBedNumVo>();
	
	/**
	 * 房源配套设置
	 */
	private List<HouseConfVo> facilityList=new ArrayList<HouseConfVo>();
	
	/**
	 * 服务
	 */
	private List<HouseConfVo> serveList=new ArrayList<HouseConfVo>();

	/**
	 * 房东是否认证
	 */
	private Integer isAuth=1;
	
	/**
	 * 免费提前退订最小天数
	 */
	private Integer unregText1;
	
	/**
	 * 违约需扣房租描述
	 */
	private String unregText2;
	
	/**
	 * 入住后提前退房需扣房租描述
	 */
	private String unregText3;
	
	/**
	 * 无责任退房扣除全部房租百分比
	 */
	private String unregText4;
	
	/**
	 * 取消订单剩余房租扣除百分比
	 */
	private String unregText5;
	
	/**
	 * 退房剩余房租扣除百分比
	 */
	private String unregText6;
	
	/**
	 * 分享房源url
	 */
	private String houseShareUrl;
	
	/**
	 * 是否有智能锁(0:否,1:是)
	 */
	private Integer isLock;
	
	/**
	 * 房屋守则
	 */
	private String houseRules;
	
	/**
	 * 房源或者房间清洁费
	 */
	private Integer cleaningFees;
	
	/**
	 * 退订政策新版本 返回字段
	 */
	private String TradeRulesShowName;
	
	/**
	 * 预定页面  订单提示
	 */
	private String bookUnregText1;
	
	/**
	 * 截止时间
	 */
    private Date tillDate;
    
    /**
     * 预定订单页面 押金提示展示
     */
    private String msgInfo;

    /**
     * 房源名称下-房源信息展示
     */
    private LinkedList<String> houseNameInfoList;
    
    /**
     * 房源美居介绍信息
     */
    private MercureInfoVo mercureInfoVo;
    
    /**
     * 房源 设施 默认展示5个
     */
    private LinkedList<EnumVo> listHouseFacilities;
    
    /**
     * 房源的服务  设施  电器 卫浴 列表
     */
    private LinkedList<EnumVo> listHouseFacServ;
    
    /**
     * 入住规则
     */
    private LinkedList<CheckRuleVo> listCheckRuleVo;
    
    /**
     * 房源折扣配置
     */
    private List<HouseConfVo> listHouseDiscount;
    
	/**
	 * 长租天数
	 */
	private String longTermDays;
	
	/**
	 * top50 房源特性
	 */
	private HouseTopInfoVo  houseTopInfoVo = new HouseTopInfoVo();
	
	/**
	 * 用户自我介绍
	 */
	private String customerIntroduce;

	/**
	 * 是否设置今夜特价 0:否,1:是
	 */
	private Integer isToNightDiscount;

	/**
	 * 今夜特价信息
	 */
	private ToNightDiscount toNightDiscount;

	/**
	 * 原价
	 */
	private Integer originalPrice;
	
	/**
	 * m站使用，今夜特价状态 0：今夜特价未开始，1：今夜特价已开始
	 */
	private Integer toNightDiscountStatus;
	

	/**
	 * m站使用 今夜特价结束时间
	 */
	private Long toNightDiscountEndTime;
	
	/**
	 * m站使用 今夜特价开始时间
	 */
	private Long toNightDiscountStartTime;
	
	/**
	 * @return the toNightDiscountEndTime
	 */
	public Long getToNightDiscountEndTime() {
		return toNightDiscountEndTime;
	}

	/**
	 * @param toNightDiscountEndTime the toNightDiscountEndTime to set
	 */
	public void setToNightDiscountEndTime(Long toNightDiscountEndTime) {
		this.toNightDiscountEndTime = toNightDiscountEndTime;
	}

	/**
	 * @return the toNightDiscountStartTime
	 */
	public Long getToNightDiscountStartTime() {
		return toNightDiscountStartTime;
	}

	/**
	 * @param toNightDiscountStartTime the toNightDiscountStartTime to set
	 */
	public void setToNightDiscountStartTime(Long toNightDiscountStartTime) {
		this.toNightDiscountStartTime = toNightDiscountStartTime;
	}

	/**
	 * @return the toNightDiscountStatus
	 */
	public Integer getToNightDiscountStatus() {
		return toNightDiscountStatus;
	}

	/**
	 * @param toNightDiscountStatus the toNightDiscountStatus to set
	 */
	public void setToNightDiscountStatus(Integer toNightDiscountStatus) {
		this.toNightDiscountStatus = toNightDiscountStatus;
	}

	public Integer getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(Integer originalPrice) {
		this.originalPrice = originalPrice;
	}

	public Integer getIsToNightDiscount() {
		return isToNightDiscount;
	}

	public void setIsToNightDiscount(Integer isToNightDiscount) {
		this.isToNightDiscount = isToNightDiscount;
	}

	public ToNightDiscount getToNightDiscount() {
		return toNightDiscount;
	}

	public void setToNightDiscount(ToNightDiscount toNightDiscount) {
		this.toNightDiscount = toNightDiscount;
	}

	/**
	 * @return the customerIntroduce
	 */
	public String getCustomerIntroduce() {
		return customerIntroduce;
	}

	/**
	 * @param customerIntroduce the customerIntroduce to set
	 */
	public void setCustomerIntroduce(String customerIntroduce) {
		this.customerIntroduce = customerIntroduce;
	}

	/**
	 * @return the houseTopInfoVo
	 */
	public HouseTopInfoVo getHouseTopInfoVo() {
		return houseTopInfoVo;
	}

	/**
	 * @param houseTopInfoVo the houseTopInfoVo to set
	 */
	public void setHouseTopInfoVo(HouseTopInfoVo houseTopInfoVo) {
		this.houseTopInfoVo = houseTopInfoVo;
	}

	public Double getGoogleLongitude() {
		return googleLongitude;
	}

	public void setGoogleLongitude(Double googleLongitude) {
		this.googleLongitude = googleLongitude;
	}

	public Double getGoogleLatitude() {
		return googleLatitude;
	}

	public void setGoogleLatitude(Double googleLatitude) {
		this.googleLatitude = googleLatitude;
	}

	/**
	 * @return the longTermDays
	 */
	public String getLongTermDays() {
		return longTermDays;
	}

	/**
	 * @param longTermDays the longTermDays to set
	 */
	public void setLongTermDays(String longTermDays) {
		this.longTermDays = longTermDays;
	}

	/**
	 * @return the listHouseDiscount
	 */
	public List<HouseConfVo> getListHouseDiscount() {
		return listHouseDiscount;
	}

	/**
	 * @param listHouseDiscount the listHouseDiscount to set
	 */
	public void setListHouseDiscount(List<HouseConfVo> listHouseDiscount) {
		this.listHouseDiscount = listHouseDiscount;
	}

	/**
	 * @return the listCheckRuleVo
	 */
	public LinkedList<CheckRuleVo> getListCheckRuleVo() {
		return listCheckRuleVo;
	}

	/**
	 * @param listCheckRuleVo the listCheckRuleVo to set
	 */
	public void setListCheckRuleVo(LinkedList<CheckRuleVo> listCheckRuleVo) {
		this.listCheckRuleVo = listCheckRuleVo;
	}

	/**
	 * @return the listHouseFacServ
	 */
	public LinkedList<EnumVo> getListHouseFacServ() {
		return listHouseFacServ;
	}

	/**
	 * @param listHouseFacServ the listHouseFacServ to set
	 */
	public void setListHouseFacServ(LinkedList<EnumVo> listHouseFacServ) {
		this.listHouseFacServ = listHouseFacServ;
	}

	/**
	 * @return the listHouseFacilities
	 */
	public LinkedList<EnumVo> getListHouseFacilities() {
		return listHouseFacilities;
	}

	/**
	 * @param listHouseFacilities the listHouseFacilities to set
	 */
	public void setListHouseFacilities(LinkedList<EnumVo> listHouseFacilities) {
		this.listHouseFacilities = listHouseFacilities;
	}

	/**
	 * @return the houseNameInfoList
	 */
	public LinkedList<String> getHouseNameInfoList() {
		return houseNameInfoList;
	}

	/**
	 * @param houseNameInfoList the houseNameInfoList to set
	 */
	public void setHouseNameInfoList(LinkedList<String> houseNameInfoList) {
		this.houseNameInfoList = houseNameInfoList;
	}

	/**
	 * @return the mercureInfoVo
	 */
	public MercureInfoVo getMercureInfoVo() {
		return mercureInfoVo;
	}

	/**
	 * @param mercureInfoVo the mercureInfoVo to set
	 */
	public void setMercureInfoVo(MercureInfoVo mercureInfoVo) {
		this.mercureInfoVo = mercureInfoVo;
	}

	/**
	 * @return the msgInfo
	 */
	public String getMsgInfo() {
		return msgInfo;
	}

	/**
	 * @param msgInfo the msgInfo to set
	 */
	public void setMsgInfo(String msgInfo) {
		this.msgInfo = msgInfo;
	}

	/**
	 * @return the bookUnregText1
	 */
	public String getBookUnregText1() {
		return bookUnregText1;
	}

	/**
	 * @param bookUnregText1 the bookUnregText1 to set
	 */
	public void setBookUnregText1(String bookUnregText1) {
		this.bookUnregText1 = bookUnregText1;
	}

	/**
	 * @return the unregText4
	 */
	public String getUnregText4() {
		return unregText4;
	}

	/**
	 * @param unregText4 the unregText4 to set
	 */
	public void setUnregText4(String unregText4) {
		this.unregText4 = unregText4;
	}

	/**
	 * @return the unregText5
	 */
	public String getUnregText5() {
		return unregText5;
	}

	/**
	 * @param unregText5 the unregText5 to set
	 */
	public void setUnregText5(String unregText5) {
		this.unregText5 = unregText5;
	}

	/**
	 * @return the unregText6
	 */
	public String getUnregText6() {
		return unregText6;
	}

	/**
	 * @param unregText6 the unregText6 to set
	 */
	public void setUnregText6(String unregText6) {
		this.unregText6 = unregText6;
	}

	/**
	 * @return the tradeRulesShowName
	 */
	public String getTradeRulesShowName() {
		return TradeRulesShowName;
	}

	/**
	 * @param tradeRulesShowName the tradeRulesShowName to set
	 */
	public void setTradeRulesShowName(String tradeRulesShowName) {
		TradeRulesShowName = tradeRulesShowName;
	}

	public Date getTillDate() {
		return tillDate;
	}

	public void setTillDate(Date tillDate) {
		this.tillDate = tillDate;
	}

	public Integer getCleaningFees() {
		return cleaningFees;
	}

	public void setCleaningFees(Integer cleaningFees) {
		this.cleaningFees = cleaningFees;
	}

	/**
	 * @return the unregText1
	 */
	public Integer getUnregText1() {
		return unregText1;
	}

	/**
	 * @param unregText1 the unregText1 to set
	 */
	public void setUnregText1(Integer unregText1) {
		this.unregText1 = unregText1;
	}

	/**
	 * @return the unregText2
	 */
	public String getUnregText2() {
		return unregText2;
	}

	/**
	 * @param unregText2 the unregText2 to set
	 */
	public void setUnregText2(String unregText2) {
		this.unregText2 = unregText2;
	}

	/**
	 * @return the unregText3
	 */
	public String getUnregText3() {
		return unregText3;
	}

	/**
	 * @param unregText3 the unregText3 to set
	 */
	public void setUnregText3(String unregText3) {
		this.unregText3 = unregText3;
	}

	/**
	 * @return the isAuth
	 */
	public Integer getIsAuth() {
		return isAuth;
	}

	/**
	 * @param isAuth the isAuth to set
	 */
	public void setIsAuth(Integer isAuth) {
		this.isAuth = isAuth;
	}

	/**
	 * @return the facilityList
	 */
	public List<HouseConfVo> getFacilityList() {
		return facilityList;
	}

	/**
	 * @param facilityList the facilityList to set
	 */
	public void setFacilityList(List<HouseConfVo> facilityList) {
		this.facilityList = facilityList;
	}

	/**
	 * @return the bedList
	 */
	public List<HouseBedNumVo> getBedList() {
		return bedList;
	}

	/**
	 * @param bedList the bedList to set
	 */
	public void setBedList(List<HouseBedNumVo> bedList) {
		this.bedList = bedList;
	}

	/**
	 * @return the houseArea
	 */
	public Float getHouseArea() {
		return houseArea;
	}

	/**
	 * @param houseArea the houseArea to set
	 */
	public void setHouseArea(Float houseArea) {
		this.houseArea = houseArea;
	}

	/**
	 * @return the communityName
	 */
	public String getCommunityName() {
		return communityName;
	}

	/**
	 * @param communityName the communityName to set
	 */
	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}

	/**
	 * @return the landlordMobile
	 */
	public String getLandlordMobile() {
		return landlordMobile;
	}

	/**
	 * @param landlordMobile the landlordMobile to set
	 */
	public void setLandlordMobile(String landlordMobile) {
		this.landlordMobile = landlordMobile;
	}

	/**
	 * @return the houseAddr
	 */
	public String getHouseAddr() {
		return houseAddr;
	}

	/**
	 * @param houseAddr the houseAddr to set
	 */
	public void setHouseAddr(String houseAddr) {
		this.houseAddr = houseAddr;
	}

	/**
	 * @return the tenantEvalVo
	 */
	public TenantEvalVo getTenantEvalVo() {
		return tenantEvalVo;
	}

	/**
	 * @param tenantEvalVo the tenantEvalVo to set
	 */
	public void setTenantEvalVo(TenantEvalVo tenantEvalVo) {
		this.tenantEvalVo = tenantEvalVo;
	}

	/**
	 * @return the evaluateCount
	 */
	public Integer getEvaluateCount() {
		return evaluateCount;
	}

	/**
	 * @param evaluateCount the evaluateCount to set
	 */
	public void setEvaluateCount(Integer evaluateCount) {
		this.evaluateCount = evaluateCount;
	}

	/**
	 * @return the istoilet
	 */
	public Integer getIstoilet() {
		return istoilet;
	}

	/**
	 * @param istoilet the istoilet to set
	 */
	public void setIstoilet(Integer istoilet) {
		this.istoilet = istoilet;
	}

	/**
	 * @return the checkInLimit
	 */
	public Integer getCheckInLimit() {
		return checkInLimit;
	}

	/**
	 * @param checkInLimit the checkInLimit to set
	 */
	public void setCheckInLimit(Integer checkInLimit) {
		this.checkInLimit = checkInLimit;
	}

	/**
	 * @return the roomNum
	 */
	public Integer getRoomNum() {
		return roomNum;
	}

	/**
	 * @param roomNum the roomNum to set
	 */
	public void setRoomNum(Integer roomNum) {
		this.roomNum = roomNum;
	}

	/**
	 * @return the toiletNum
	 */
	public Integer getToiletNum() {
		return toiletNum;
	}

	/**
	 * @param toiletNum the toiletNum to set
	 */
	public void setToiletNum(Integer toiletNum) {
		this.toiletNum = toiletNum;
	}

	/**
	 * @return the kitchenNum
	 */
	public Integer getKitchenNum() {
		return kitchenNum;
	}

	/**
	 * @param kitchenNum the kitchenNum to set
	 */
	public void setKitchenNum(Integer kitchenNum) {
		this.kitchenNum = kitchenNum;
	}

	/**
	 * @return the defaultPic
	 */
	public String getDefaultPic() {
		return defaultPic;
	}

	/**
	 * @param defaultPic the defaultPic to set
	 */
	public void setDefaultPic(String defaultPic) {
		this.defaultPic = defaultPic;
	}

	/**
	 * @return the houseAroundDesc
	 */
	public String getHouseAroundDesc() {
		return houseAroundDesc;
	}

	/**
	 * @param houseAroundDesc the houseAroundDesc to set
	 */
	public void setHouseAroundDesc(String houseAroundDesc) {
		this.houseAroundDesc = houseAroundDesc;
	}

	/**
	 * @return the landlordUid
	 */
	public String getLandlordUid() {
		return landlordUid;
	}

	/**
	 * @param landlordUid the landlordUid to set
	 */
	public void setLandlordUid(String landlordUid) {
		this.landlordUid = landlordUid;
	}

	/**
	 * @return the fid
	 */
	public String getFid() {
		return fid;
	}

	/**
	 * @param fid the fid to set
	 */
	public void setFid(String fid) {
		this.fid = fid;
	}

	/**
	 * @return the houseName
	 */
	public String getHouseName() {
		return houseName;
	}

	/**
	 * @param houseName the houseName to set
	 */
	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}

	/**
	 * @return the housePrice
	 */
	public Integer getHousePrice() {
		return housePrice;
	}

	/**
	 * @param housePrice the housePrice to set
	 */
	public void setHousePrice(Integer housePrice) {
		this.housePrice = housePrice;
	}

	/**
	 * @return the rentWay
	 */
	public Integer getRentWay() {
		return rentWay;
	}

	/**
	 * @param rentWay the rentWay to set
	 */
	public void setRentWay(Integer rentWay) {
		this.rentWay = rentWay;
	}

	/**
	 * @return the rentWayName
	 */
	public String getRentWayName() {
		return rentWayName;
	}

	/**
	 * @param rentWayName the rentWayName to set
	 */
	public void setRentWayName(String rentWayName) {
		this.rentWayName = rentWayName;
	}

	/**
	 * @return the discountMsg
	 */
	public List<String> getDiscountMsg() {
		return discountMsg;
	}

	/**
	 * @param discountMsg the discountMsg to set
	 */
	public void setDiscountMsg(List<String> discountMsg) {
		this.discountMsg = discountMsg;
	}

	/**
	 * @return the isTogetherLandlord
	 */
	public Integer getIsTogetherLandlord() {
		return isTogetherLandlord;
	}

	/**
	 * @param isTogetherLandlord the isTogetherLandlord to set
	 */
	public void setIsTogetherLandlord(Integer isTogetherLandlord) {
		this.isTogetherLandlord = isTogetherLandlord;
	}

	/**
	 * @return the landlordIcon
	 */
	public String getLandlordIcon() {
		return landlordIcon;
	}

	/**
	 * @param landlordIcon the landlordIcon to set
	 */
	public void setLandlordIcon(String landlordIcon) {
		this.landlordIcon = landlordIcon;
	}

	/**
	 * @return the landlordName
	 */
	public String getLandlordName() {
		return landlordName;
	}

	/**
	 * @param landlordName the landlordName to set
	 */
	public void setLandlordName(String landlordName) {
		this.landlordName = landlordName;
	}

	/**
	 * @return the houseDesc
	 */
	public String getHouseDesc() {
		return houseDesc;
	}

	/**
	 * @param houseDesc the houseDesc to set
	 */
	public void setHouseDesc(String houseDesc) {
		this.houseDesc = houseDesc;
	}

	/**
	 * @return the minDay
	 */
	public Integer getMinDay() {
		return minDay;
	}

	/**
	 * @param minDay the minDay to set
	 */
	public void setMinDay(Integer minDay) {
		this.minDay = minDay;
	}

	/**
	 * @return the checkInTime
	 */
	public String getCheckInTime() {
		return checkInTime;
	}

	/**
	 * @param checkInTime the checkInTime to set
	 */
	public void setCheckInTime(String checkInTime) {
		this.checkInTime = checkInTime;
	}

	/**
	 * @return the checkOutTime
	 */
	public String getCheckOutTime() {
		return checkOutTime;
	}

	/**
	 * @param checkOutTime the checkOutTime to set
	 */
	public void setCheckOutTime(String checkOutTime) {
		this.checkOutTime = checkOutTime;
	}

	/**
	 * @return the sheetsReplaceRulesValue
	 */
	public Integer getSheetsReplaceRulesValue() {
		return sheetsReplaceRulesValue;
	}

	/**
	 * @param sheetsReplaceRulesValue the sheetsReplaceRulesValue to set
	 */
	public void setSheetsReplaceRulesValue(Integer sheetsReplaceRulesValue) {
		this.sheetsReplaceRulesValue = sheetsReplaceRulesValue;
	}

	/**
	 * @return the sheetsReplaceRulesName
	 */
	public String getSheetsReplaceRulesName() {
		return sheetsReplaceRulesName;
	}

	/**
	 * @param sheetsReplaceRulesName the sheetsReplaceRulesName to set
	 */
	public void setSheetsReplaceRulesName(String sheetsReplaceRulesName) {
		this.sheetsReplaceRulesName = sheetsReplaceRulesName;
	}

	/**
	 * @return the orderType
	 */
	public Integer getOrderType() {
		return orderType;
	}

	/**
	 * @param orderType the orderType to set
	 */
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	/**
	 * @return the orderTypeName
	 */
	public String getOrderTypeName() {
		return orderTypeName;
	}

	/**
	 * @param orderTypeName the orderTypeName to set
	 */
	public void setOrderTypeName(String orderTypeName) {
		this.orderTypeName = orderTypeName;
	}

	/**
	 * @return the depositRulesCode
	 */
	public String getDepositRulesCode() {
		return depositRulesCode;
	}

	/**
	 * @param depositRulesCode the depositRulesCode to set
	 */
	public void setDepositRulesCode(String depositRulesCode) {
		this.depositRulesCode = depositRulesCode;
	}

	/**
	 * @return the depositRulesValue
	 */
	public String getDepositRulesValue() {
		return depositRulesValue;
	}

	/**
	 * @param depositRulesValue the depositRulesValue to set
	 */
	public void setDepositRulesValue(String depositRulesValue) {
		this.depositRulesValue = depositRulesValue;
	}

	/**
	 * @return the checkOutRulesCode
	 */
	public String getCheckOutRulesCode() {
		return checkOutRulesCode;
	}

	/**
	 * @param checkOutRulesCode the checkOutRulesCode to set
	 */
	public void setCheckOutRulesCode(String checkOutRulesCode) {
		this.checkOutRulesCode = checkOutRulesCode;
	}

	/**
	 * @return the checkOutRulesName
	 */
	public String getCheckOutRulesName() {
		return checkOutRulesName;
	}

	/**
	 * @param checkOutRulesName the checkOutRulesName to set
	 */
	public void setCheckOutRulesName(String checkOutRulesName) {
		this.checkOutRulesName = checkOutRulesName;
	}

	/**
	 * @return the gradeStar
	 */
	public Float getGradeStar() {
		return gradeStar;
	}

	/**
	 * @param gradeStar the gradeStar to set
	 */
	public void setGradeStar(Float gradeStar) {
		this.gradeStar = gradeStar;
	}

	/**
	 * @return the picList
	 */
	public List<String> getPicList() {
		return picList;
	}

	/**
	 * @param picList the picList to set
	 */
	public void setPicList(List<String> picList) {
		this.picList = picList;
	}
	
	/**
	 * @return the longitude
	 */
	public Double getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the latitude
	 */
	public Double getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	
	/**
	 * @return the depositRulesName
	 */
	public String getDepositRulesName() {
		return depositRulesName;
	}

	/**
	 * @param depositRulesName the depositRulesName to set
	 */
	public void setDepositRulesName(String depositRulesName) {
		this.depositRulesName = depositRulesName;
	}
	
	/**
	 * @return the serveList
	 */
	public List<HouseConfVo> getServeList() {
		return serveList;
	}

	/**
	 * @param serveList the serveList to set
	 */
	public void setServeList(List<HouseConfVo> serveList) {
		this.serveList = serveList;
	}


    public List<MinsuEleEntity> getPicDisList() {
        return picDisList;
    }

    public void setPicDisList(List<MinsuEleEntity> picDisList) {
        this.picDisList = picDisList;
    }

	public String getHouseShareUrl() {
		return houseShareUrl;
	}

	public void setHouseShareUrl(String houseShareUrl) {
		this.houseShareUrl = houseShareUrl;
	}
	
	public Integer getIsLock() {
		return isLock;
	}
	
	public void setIsLock(Integer isLock) {
		this.isLock = isLock;
	}

	public String getHouseRules() {
		return houseRules;
	}

	public void setHouseRules(String houseRules) {
		this.houseRules = houseRules;
	}


	public Integer getHouseStatus() {
		return houseStatus;
	}

	public void setHouseStatus(Integer houseStatus) {
		this.houseStatus = houseStatus;
	}
	
}
