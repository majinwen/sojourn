package com.ziroom.minsu.services.house.entity;

import com.ziroom.minsu.entity.base.MinsuEleEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 * <p>PC房源详情</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author jixd
 * @since 1.0
 * @version 1.0
 */
public class TenantHouseDetailPcVo{
 
	/**
	 * 房源或房间fid
	 */
	private String fid;
	
	/**
	 * 房源或房间名称
	 */
	private String houseName;
	
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
	 * 评论星级
	 */
	private Float gradeStar=0f;
	
	/**
	 * 房源或者房间图片
	 */
	private List<String> picList=new ArrayList<>();

    /**
     * 带描述的图片信息
     */
    private List<MinsuEleEntity> picDisList = new ArrayList<>();

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
	 * 房东fid
	 */
	private String landlordUid;
	
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
	 * 退订政策新版本 返回字段
	 */
	private String tradeRulesShowName;
	
	/**
	 * 截止时间
	 */
    private Date tillDate;


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
	 * @return the tillDate
	 */
	public Date getTillDate() {
		return tillDate;
	}

	/**
	 * @param tillDate the tillDate to set
	 */
	public void setTillDate(Date tillDate) {
		this.tillDate = tillDate;
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
		return tradeRulesShowName;
	}

	/**
	 * @param tradeRulesShowName the tradeRulesShowName to set
	 */
	public void setTradeRulesShowName(String tradeRulesShowName) {
		this.tradeRulesShowName = tradeRulesShowName;
	}

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public String getHouseName() {
		return houseName;
	}

	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}

	public Integer getHousePrice() {
		return housePrice;
	}

	public void setHousePrice(Integer housePrice) {
		this.housePrice = housePrice;
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

	public Float getGradeStar() {
		return gradeStar;
	}

	public void setGradeStar(Float gradeStar) {
		this.gradeStar = gradeStar;
	}

	public List<String> getPicList() {
		return picList;
	}

	public void setPicList(List<String> picList) {
		this.picList = picList;
	}

	public List<MinsuEleEntity> getPicDisList() {
		return picDisList;
	}

	public void setPicDisList(List<MinsuEleEntity> picDisList) {
		this.picDisList = picDisList;
	}

	public String getHouseAroundDesc() {
		return houseAroundDesc;
	}

	public void setHouseAroundDesc(String houseAroundDesc) {
		this.houseAroundDesc = houseAroundDesc;
	}

	public String getDefaultPic() {
		return defaultPic;
	}

	public void setDefaultPic(String defaultPic) {
		this.defaultPic = defaultPic;
	}

	public Integer getRoomNum() {
		return roomNum;
	}

	public void setRoomNum(Integer roomNum) {
		this.roomNum = roomNum;
	}

	public Integer getToiletNum() {
		return toiletNum;
	}

	public void setToiletNum(Integer toiletNum) {
		this.toiletNum = toiletNum;
	}

	public Integer getKitchenNum() {
		return kitchenNum;
	}

	public void setKitchenNum(Integer kitchenNum) {
		this.kitchenNum = kitchenNum;
	}

	public Integer getCheckInLimit() {
		return checkInLimit;
	}

	public void setCheckInLimit(Integer checkInLimit) {
		this.checkInLimit = checkInLimit;
	}

	public Integer getIstoilet() {
		return istoilet;
	}

	public void setIstoilet(Integer istoilet) {
		this.istoilet = istoilet;
	}

	public Integer getEvaluateCount() {
		return evaluateCount;
	}

	public void setEvaluateCount(Integer evaluateCount) {
		this.evaluateCount = evaluateCount;
	}

	public List<String> getDiscountMsg() {
		return discountMsg;
	}

	public void setDiscountMsg(List<String> discountMsg) {
		this.discountMsg = discountMsg;
	}

	public Integer getIsTogetherLandlord() {
		return isTogetherLandlord;
	}

	public void setIsTogetherLandlord(Integer isTogetherLandlord) {
		this.isTogetherLandlord = isTogetherLandlord;
	}

	public String getLandlordUid() {
		return landlordUid;
	}

	public void setLandlordUid(String landlordUid) {
		this.landlordUid = landlordUid;
	}

	public String getLandlordIcon() {
		return landlordIcon;
	}

	public void setLandlordIcon(String landlordIcon) {
		this.landlordIcon = landlordIcon;
	}

	public String getLandlordName() {
		return landlordName;
	}

	public void setLandlordName(String landlordName) {
		this.landlordName = landlordName;
	}

	public String getHouseDesc() {
		return houseDesc;
	}

	public void setHouseDesc(String houseDesc) {
		this.houseDesc = houseDesc;
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

	public Integer getMinDay() {
		return minDay;
	}

	public void setMinDay(Integer minDay) {
		this.minDay = minDay;
	}

	public String getCheckInTime() {
		return checkInTime;
	}

	public void setCheckInTime(String checkInTime) {
		this.checkInTime = checkInTime;
	}

	public String getCheckOutTime() {
		return checkOutTime;
	}

	public void setCheckOutTime(String checkOutTime) {
		this.checkOutTime = checkOutTime;
	}

	public Integer getSheetsReplaceRulesValue() {
		return sheetsReplaceRulesValue;
	}

	public void setSheetsReplaceRulesValue(Integer sheetsReplaceRulesValue) {
		this.sheetsReplaceRulesValue = sheetsReplaceRulesValue;
	}

	public String getSheetsReplaceRulesName() {
		return sheetsReplaceRulesName;
	}

	public void setSheetsReplaceRulesName(String sheetsReplaceRulesName) {
		this.sheetsReplaceRulesName = sheetsReplaceRulesName;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public String getOrderTypeName() {
		return orderTypeName;
	}

	public void setOrderTypeName(String orderTypeName) {
		this.orderTypeName = orderTypeName;
	}

	public String getDepositRulesCode() {
		return depositRulesCode;
	}

	public void setDepositRulesCode(String depositRulesCode) {
		this.depositRulesCode = depositRulesCode;
	}

	public String getDepositRulesValue() {
		return depositRulesValue;
	}

	public void setDepositRulesValue(String depositRulesValue) {
		this.depositRulesValue = depositRulesValue;
	}

	public String getDepositRulesName() {
		return depositRulesName;
	}

	public void setDepositRulesName(String depositRulesName) {
		this.depositRulesName = depositRulesName;
	}

	public String getCheckOutRulesCode() {
		return checkOutRulesCode;
	}

	public void setCheckOutRulesCode(String checkOutRulesCode) {
		this.checkOutRulesCode = checkOutRulesCode;
	}

	public String getCheckOutRulesName() {
		return checkOutRulesName;
	}

	public void setCheckOutRulesName(String checkOutRulesName) {
		this.checkOutRulesName = checkOutRulesName;
	}

	public String getCommunityName() {
		return communityName;
	}

	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}

	public String getLandlordMobile() {
		return landlordMobile;
	}

	public void setLandlordMobile(String landlordMobile) {
		this.landlordMobile = landlordMobile;
	}

	public String getHouseAddr() {
		return houseAddr;
	}

	public void setHouseAddr(String houseAddr) {
		this.houseAddr = houseAddr;
	}

	public Float getHouseArea() {
		return houseArea;
	}

	public void setHouseArea(Float houseArea) {
		this.houseArea = houseArea;
	}

	public List<HouseBedNumVo> getBedList() {
		return bedList;
	}

	public void setBedList(List<HouseBedNumVo> bedList) {
		this.bedList = bedList;
	}

	public List<HouseConfVo> getFacilityList() {
		return facilityList;
	}

	public void setFacilityList(List<HouseConfVo> facilityList) {
		this.facilityList = facilityList;
	}

	public List<HouseConfVo> getServeList() {
		return serveList;
	}

	public void setServeList(List<HouseConfVo> serveList) {
		this.serveList = serveList;
	}

	public Integer getIsAuth() {
		return isAuth;
	}

	public void setIsAuth(Integer isAuth) {
		this.isAuth = isAuth;
	}

	public Integer getUnregText1() {
		return unregText1;
	}

	public void setUnregText1(Integer unregText1) {
		this.unregText1 = unregText1;
	}

	public String getUnregText2() {
		return unregText2;
	}

	public void setUnregText2(String unregText2) {
		this.unregText2 = unregText2;
	}

	public String getUnregText3() {
		return unregText3;
	}

	public void setUnregText3(String unregText3) {
		this.unregText3 = unregText3;
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

	public Integer getCleaningFees() {
		return cleaningFees;
	}

	public void setCleaningFees(Integer cleaningFees) {
		this.cleaningFees = cleaningFees;
	}

}
