package com.ziroom.minsu.report.house.dto;

import java.util.List;

import com.ziroom.minsu.services.common.dto.PageRequest;

public class HouseViscosityDto extends PageRequest{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 8764269805371079872L;

	/** 房源表fid */
	private  String houseBaseFid;
	
	/** 房源表fid */
	private  String roomFid;
	
	/** 国家 */
	private  String nationCode;
	
	/** 国家名称 */
	private  String nationName;
	
	/** 房源大区Code  */
    private  String regionCode ;
    
    /** 房源大区名称  */
    private  String regionName ;
    
    /** 房源大区Code  */
    private  String provinceCode ;
    
    /** 城市 */
    private  String cityCode ;
    
    /** 城市 */
    private  String cityName ;
    
    /** 出租类型 */
    private  Integer rentWay ;
    
    /** 首次上架时间 */
    private  String firstUpBeginDate ;
    
    /** 首次上架时间 */
    private  String firstUpEndDate ;
    
    /** 房源状态 */
    private  Integer houseStatus ;
    
    /** 数据查询期间开始时间 */
    private  String dataQueryBeaginDate ;
    
    /** 数据查询期间结束时间 */
    private  String dataQueryEndDate ;

    /** 省份Code集合 */
    private List<String> provinceCodeList;
    
	public HouseViscosityDto() {
		super();
	}

	public HouseViscosityDto(String houseBaseFid, String roomFid,
			String nationCode, String nationName, String regionCode,
			String regionName, String provinceCode, String cityCode,
			String cityName, Integer rentWay, String firstUpBeginDate,
			String firstUpEndDate, Integer houseStatus,
			String dataQueryBeaginDate, String dataQueryEndDate,
			List<String> provinceCodeList) {
		super();
		this.houseBaseFid = houseBaseFid;
		this.roomFid = roomFid;
		this.nationCode = nationCode;
		this.nationName = nationName;
		this.regionCode = regionCode;
		this.regionName = regionName;
		this.provinceCode = provinceCode;
		this.cityCode = cityCode;
		this.cityName = cityName;
		this.rentWay = rentWay;
		this.firstUpBeginDate = firstUpBeginDate;
		this.firstUpEndDate = firstUpEndDate;
		this.houseStatus = houseStatus;
		this.dataQueryBeaginDate = dataQueryBeaginDate;
		this.dataQueryEndDate = dataQueryEndDate;
		this.provinceCodeList = provinceCodeList;
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

	public Integer getRentWay() {
		return rentWay;
	}

	public void setRentWay(Integer rentWay) {
		this.rentWay = rentWay;
	}

	public String getFirstUpBeginDate() {
		return firstUpBeginDate;
	}

	public void setFirstUpBeginDate(String firstUpBeginDate) {
		this.firstUpBeginDate = firstUpBeginDate;
	}

	public String getFirstUpEndDate() {
		return firstUpEndDate;
	}

	public void setFirstUpEndDate(String firstUpEndDate) {
		this.firstUpEndDate = firstUpEndDate;
	}

	public Integer getHouseStatus() {
		return houseStatus;
	}

	public void setHouseStatus(Integer houseStatus) {
		this.houseStatus = houseStatus;
	}

	public String getDataQueryBeaginDate() {
		return dataQueryBeaginDate;
	}

	public void setDataQueryBeaginDate(String dataQueryBeaginDate) {
		this.dataQueryBeaginDate = dataQueryBeaginDate;
	}

	public String getDataQueryEndDate() {
		return dataQueryEndDate;
	}

	public void setDataQueryEndDate(String dataQueryEndDate) {
		this.dataQueryEndDate = dataQueryEndDate;
	}

	public List<String> getProvinceCodeList() {
		return provinceCodeList;
	}

	public void setProvinceCodeList(List<String> provinceCodeList) {
		this.provinceCodeList = provinceCodeList;
	}

}
