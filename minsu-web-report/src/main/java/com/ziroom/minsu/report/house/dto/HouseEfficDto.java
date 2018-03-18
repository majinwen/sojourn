package com.ziroom.minsu.report.house.dto;

import java.util.List;

import com.ziroom.minsu.services.common.dto.PageRequest;

/**  
 * @Title: HouseEfficReportDto.java  
 * @Package com.ziroom.minsu.report.house.dto  
 * @Description: TODO
 * @author loushuai  
 * @date 2017年4月25日  
 * @version V1.0  
 */
public class HouseEfficDto extends PageRequest{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6511038693633445659L;
	
	/** 房源表fid */
	private  String houseBaseFid;
	
	 /** 出租类型 */
    private  Integer rentWay ;
	
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
    
    /** 首次上架时间 */
    private  String firstUpBeginDate ;
    
    /** 首次上架时间 */
    private  String firstUpEndDate ;
    
    /** 房源状态 */
    private  Integer houseStatus ;

    /** 房源基础表fid集合 */
    private List<String> houseBaseFidList;
    
    /** 省份Code集合 */
    private List<String> provinceCodeList;
    
	public HouseEfficDto() {
		super();
	}

	public HouseEfficDto(String houseBaseFid, Integer rentWay,
			String nationCode, String nationName, String regionCode,
			String regionName, String provinceCode, String cityCode,
			String cityName, String firstUpBeginDate, String firstUpEndDate,
			Integer houseStatus, List<String> houseBaseFidList,
			List<String> provinceCodeList) {
		super();
		this.houseBaseFid = houseBaseFid;
		this.rentWay = rentWay;
		this.nationCode = nationCode;
		this.nationName = nationName;
		this.regionCode = regionCode;
		this.regionName = regionName;
		this.provinceCode = provinceCode;
		this.cityCode = cityCode;
		this.cityName = cityName;
		this.firstUpBeginDate = firstUpBeginDate;
		this.firstUpEndDate = firstUpEndDate;
		this.houseStatus = houseStatus;
		this.houseBaseFidList = houseBaseFidList;
		this.provinceCodeList = provinceCodeList;
	}

	public String getHouseBaseFid() {
		return houseBaseFid;
	}

	public void setHouseBaseFid(String houseBaseFid) {
		this.houseBaseFid = houseBaseFid;
	}

	public Integer getRentWay() {
		return rentWay;
	}

	public void setRentWay(Integer rentWay) {
		this.rentWay = rentWay;
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


	public String getProvinceCode() {
		return provinceCode;
	}


	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	
}
