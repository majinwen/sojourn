package com.ziroom.minsu.services.basedata.dto;

import com.ziroom.minsu.services.common.dto.PageRequest;

public class SubwayLineRequest extends PageRequest {

	private String lineFid;

	private String stationFid;

	// 国家code
	private String nationCode;

	// 省份code
	private String provinceCode;

	// 城市code
	private String cityCode;

	// 地铁线
	private String lineName;

	// 地铁站
	private String stationName;

	public String getLineFid() {
		return lineFid;
	}

	public void setLineFid(String lineFid) {
		this.lineFid = lineFid;
	}

	public String getStationFid() {
		return stationFid;
	}

	public void setStationFid(String stationFid) {
		this.stationFid = stationFid;
	}

	public String getNationCode() {
		return nationCode;
	}

	public void setNationCode(String nationCode) {
		this.nationCode = nationCode;
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

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

}
