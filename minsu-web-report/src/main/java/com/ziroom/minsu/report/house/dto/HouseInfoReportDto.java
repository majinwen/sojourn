package com.ziroom.minsu.report.house.dto;

import java.util.List;

import com.ziroom.minsu.services.common.dto.PageRequest;

public class HouseInfoReportDto extends PageRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3079693473646750691L;

	/** 房源表fid */
	private  String houseBaseFid;

	/** 国家 */
	private  String nationCode;
	
	/** 国家名称 */
	private  String nationName;
	
	/** 房源大区Code  */
    private  String regionCode ;
    
    /** 房源大区名称  */
    private  String regionName ;
    
    /** 城市 */
    private  String cityCode ;
    
    /** 城市 */
    private  String cityName ;
    
    /** 首次上架时间 */
    private  String firstDeployBeginDate ;
    
    /** 首次上架时间 */
    private  String firstDeployEndDate ;
    
    /** 房源状态 */
    private  Integer houseStatus ;
    
    /** 房源状态名称 */
    private  String houseStatusName ;
    
    /** 出租类型 */
    private  Integer rentWay ;
    
    /** 出租类型 名称*/
    private  String rentWayName;
    
    /** 预定类型 (1:实时下单,2:普通下单)', */
    private  Integer orderType ;
    
    /** 预定类型 名称(1:实时下单,2:普通下单)', */
    private  String orderTypeName ;
    
    /** 房源级别 */
    private  String houseQualityGrade ;
    
    /** 房源级别名称 */
    private  String houseQualityGradeName ;
    
    /** 最小日租金 */
    private  Integer leaseMinPrice ;
    
    /** 最大日租金 */
    private  Integer leaseMaxPrice ;

    /** 房源基础表fid集合 */
    private List<String> houseBaseFidList;
    
    /** 省份Code集合 */
    private List<String> provinceCodeList;
	


	public HouseInfoReportDto() {
		super();
	}


	public HouseInfoReportDto(String houseBaseFid, String nationCode,
			String nationName, String regionCode, String regionName,
			String cityCode, String cityName, String firstDeployBeginDate,
			String firstDeployEndDate, Integer houseStatus,
			String houseStatusName, Integer rentWay, String rentWayName,
			Integer orderType, String orderTypeName, String houseQualityGrade,
			String houseQualityGradeName, Integer leaseMinPrice,
			Integer leaseMaxPrice, List<String> houseBaseFidList,
			List<String> provinceCodeList) {
		super();
		this.houseBaseFid = houseBaseFid;
		this.nationCode = nationCode;
		this.nationName = nationName;
		this.regionCode = regionCode;
		this.regionName = regionName;
		this.cityCode = cityCode;
		this.cityName = cityName;
		this.firstDeployBeginDate = firstDeployBeginDate;
		this.firstDeployEndDate = firstDeployEndDate;
		this.houseStatus = houseStatus;
		this.houseStatusName = houseStatusName;
		this.rentWay = rentWay;
		this.rentWayName = rentWayName;
		this.orderType = orderType;
		this.orderTypeName = orderTypeName;
		this.houseQualityGrade = houseQualityGrade;
		this.houseQualityGradeName = houseQualityGradeName;
		this.leaseMinPrice = leaseMinPrice;
		this.leaseMaxPrice = leaseMaxPrice;
		this.houseBaseFidList = houseBaseFidList;
		this.provinceCodeList = provinceCodeList;
	}



	public String getHouseBaseFid() {
		return houseBaseFid;
	}

	public void setHouseBaseFid(String houseBaseFid) {
		this.houseBaseFid = houseBaseFid;
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

	public String getFirstDeployBeginDate() {
		return firstDeployBeginDate;
	}

	public void setFirstDeployBeginDate(String firstDeployBeginDate) {
		this.firstDeployBeginDate = firstDeployBeginDate;
	}

	public String getFirstDeployEndDate() {
		return firstDeployEndDate;
	}

	public void setFirstDeployEndDate(String firstDeployEndDate) {
		this.firstDeployEndDate = firstDeployEndDate;
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

	public Integer getLeaseMinPrice() {
		return leaseMinPrice;
	}

	public void setLeaseMinPrice(Integer leaseMinPrice) {
		this.leaseMinPrice = leaseMinPrice;
	}

	public Integer getLeaseMaxPrice() {
		return leaseMaxPrice;
	}

	public void setLeaseMaxPrice(Integer leaseMaxPrice) {
		this.leaseMaxPrice = leaseMaxPrice;
	}

	public List<String> getHouseBaseFidList() {
		return houseBaseFidList;
	}

	public void setHouseBaseFidList(List<String> houseBaseFidList) {
		this.houseBaseFidList = houseBaseFidList;
	}

	public List<String> getProvinceCodeList() {
		return provinceCodeList;
	}

	public void setProvinceCodeList(List<String> provinceCodeList) {
		this.provinceCodeList = provinceCodeList;
	}
	
}
