package com.ziroom.minsu.report.house.vo;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.minsu.report.common.annotation.FieldMeta;

public class HouseInfoReportVo extends BaseEntity{

	/**
	 * 
	 */
	 @FieldMeta(skip = true)
	private static final long serialVersionUID = 8969925511389930152L;

	/** 房源表fid */
	private  String houseBaseFid;
	
	/**  房间roomfid */
	private  String roomFid;
	
	/** 国家 */
	private  String nationCode;
	
	/** 国家名称 */
	 @FieldMeta(name = "国家", order = 1)
	private  String nationName;
	
	/** 房源大区Code  */
    private  String regionCode ;
    
    /** 房源大区名称  */
    @FieldMeta(name = "大区", order = 2)
    private  String regionName ;
    
    /** 省份Code  */
    private  String provinceCode ;
    
    /** 城市Code */
    private  String cityCode ;
    
    /** 城市名称 */
    @FieldMeta(name = "城市", order = 3)
    private  String cityName ;
    
    /** 房源编号 */
    @FieldMeta(name = "房源或房间编号", order = 4)
    private  String houseSn ;
    
    /** 首次发布时间 */
    @FieldMeta(name = "首次发布时间", order = 5)
    private  String firstDeployDate ;
    
    /** 房源状态 */
    private  Integer houseStatus ;
    
    /** 房源状态名称 */
    @FieldMeta(name = "房源或房间状态", order = 6)
    private  String houseStatusName ;
    
    /** 出租类型 */
    private  Integer rentWay ;
    
    /** 出租类型 名称*/
    @FieldMeta(name = "出租类型", order = 7)
    private  String rentWayName;
    
    /** 预定类型 (1:实时下单,2:普通下单)', */
    private  Integer orderType ;
    
    /** 预定类型 名称(1:实时下单,2:普通下单)', */
    @FieldMeta(name = "下单类型", order = 8)
    private  String orderTypeName ;
    
    /** 日租金 */
    @FieldMeta(name = "日租金", order = 9)
    private  Integer leasePrice ;
    
    /** 房源面积  */
    @FieldMeta(name = "房源或房间面积", order = 10)
    private  String houseArea ;
    
    /** 房间数量  */
    @FieldMeta(name = "房间数量", order = 11)
    private  Integer roomNum ;
    
    /** 大厅数量  */
    @FieldMeta(name = "客厅数量", order = 12)
    private  Integer hallNum ;
    
    /** 厕所数量 */
    @FieldMeta(name = "厕所数量", order = 13)
    private  Integer toiletNum ;
    
    @FieldMeta(name = "厨房数量", order = 14)
    private  Integer kitchenNum ;
    
    /** 阳台数量  */
    @FieldMeta(name = "阳台数量", order = 15)
    private  Integer balconyNum ;
    
    /** 房源级别 */
    @FieldMeta(name = "房源级别", order = 16)
    private  String houseQualityGrade ;
    
    /** 房源级别名称 */
    
    private  String houseQualityGradeName ;
    
    

	public HouseInfoReportVo() {
		super();
	}


	public HouseInfoReportVo(String houseBaseFid, String nationCode,
			String nationName, String regionCode, String regionName,
			String provinceCode, String cityCode, String cityName,
			String houseSn, String firstDeployDate, Integer houseStatus,
			String houseStatusName, Integer rentWay, String rentWayName,
			Integer orderType, String orderTypeName, Integer leasePrice,
			String houseArea, Integer roomNum, Integer hallNum,
			Integer toiletNum, Integer kitchenNum, Integer balconyNum,
			String houseQualityGrade, String houseQualityGradeName) {
		super();
		this.houseBaseFid = houseBaseFid;
		this.nationCode = nationCode;
		this.nationName = nationName;
		this.regionCode = regionCode;
		this.regionName = regionName;
		this.provinceCode = provinceCode;
		this.cityCode = cityCode;
		this.cityName = cityName;
		this.houseSn = houseSn;
		this.firstDeployDate = firstDeployDate;
		this.houseStatus = houseStatus;
		this.houseStatusName = houseStatusName;
		this.rentWay = rentWay;
		this.rentWayName = rentWayName;
		this.orderType = orderType;
		this.orderTypeName = orderTypeName;
		this.leasePrice = leasePrice;
		this.houseArea = houseArea;
		this.roomNum = roomNum;
		this.hallNum = hallNum;
		this.toiletNum = toiletNum;
		this.kitchenNum = kitchenNum;
		this.balconyNum = balconyNum;
		this.houseQualityGrade = houseQualityGrade;
		this.houseQualityGradeName = houseQualityGradeName;
	}




	public String getHouseBaseFid() {
		return houseBaseFid;
	}

	public void setHouseBaseFid(String houseBaseFid) {
		this.houseBaseFid = houseBaseFid;
	}
	
	public String getRoomFid() {
		return roomFid;
	}


	public void setRoomFid(String roomFid) {
		this.roomFid = roomFid;
	}


	public String getNationCode() {
		return nationCode;
	}

	public void setNationCode(String nationCode) {
		this.nationCode = nationCode;
	}
	
	public String getNationName() {
		return nationName;
	}

	public void setNationName(String nationName) {
		this.nationName = nationName;
	}

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	
	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
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

	public String getHouseSn() {
		return houseSn;
	}

	public void setHouseSn(String houseSn) {
		this.houseSn = houseSn;
	}

	public String getFirstDeployDate() {
		return firstDeployDate;
	}

	public void setFirstDeployDate(String firstDeployDate) {
		this.firstDeployDate = firstDeployDate;
	}

	public Integer getHouseStatus() {
		return houseStatus;
	}

	public void setHouseStatus(Integer houseStatus) {
		this.houseStatus = houseStatus;
	}

	public String getHouseStatusName() {
		return houseStatusName;
	}

	public void setHouseStatusName(String houseStatusName) {
		this.houseStatusName = houseStatusName;
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

	public Integer getLeasePrice() {
		return leasePrice;
	}

	public void setLeasePrice(Integer leasePrice) {
		this.leasePrice = leasePrice;
	}

	public String getHouseArea() {
		return houseArea;
	}

	public void setHouseArea(String houseArea) {
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

	public String getHouseQualityGrade() {
		return houseQualityGrade;
	}

	public void setHouseQualityGrade(String houseQualityGrade) {
		this.houseQualityGrade = houseQualityGrade;
	}

	public String getHouseQualityGradeName() {
		return houseQualityGradeName;
	}

	public void setHouseQualityGradeName(String houseQualityGradeName) {
		this.houseQualityGradeName = houseQualityGradeName;
	}

	

	
	
}
