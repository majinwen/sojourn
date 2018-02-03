package com.ziroom.minsu.services.basedata.dto;

import java.util.List;

public class SubwayLineSaveRequest {

    // 创建人id
    private String createFid;

	// 线路逻辑ID
	private String lineFid;

	// 线路名称
	private String lineName;

	// 国家code
	private String nationCode;

	// 省份code
	private String provinceCode;

	// 城市code
	private String cityCode;

	// 线路排序
	private Integer lineSort;

	// 站点逻辑ID
	private String stationFid;

	// 站点名称
	private String stationName;

	// 站点经度
	private Double stationLongitude;

	// 站点纬度
	private Double stationLatitude;

	// 站点排序
	private Integer stationSort;

	// 出站口列表
	private List<SubwayOutSaveRequest> outList;
	
	//地图类型
	private String mapType;

	/**
	 * @return the mapType
	 */
	public String getMapType() {
		return mapType;
	}

	/**
	 * @param mapType the mapType to set
	 */
	public void setMapType(String mapType) {
		this.mapType = mapType;
	}

	public String getLineFid() {
		return lineFid;
	}

	public void setLineFid(String lineFid) {
		this.lineFid = lineFid;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
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

	public Integer getLineSort() {
		return lineSort;
	}

	public void setLineSort(Integer lineSort) {
		this.lineSort = lineSort;
	}

	public String getStationFid() {
		return stationFid;
	}

	public void setStationFid(String stationFid) {
		this.stationFid = stationFid;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public Double getStationLongitude() {
		return stationLongitude;
	}

	public void setStationLongitude(Double stationLongitude) {
		this.stationLongitude = stationLongitude;
	}

	public Double getStationLatitude() {
		return stationLatitude;
	}

	public void setStationLatitude(Double stationLatitude) {
		this.stationLatitude = stationLatitude;
	}

	public Integer getStationSort() {
		return stationSort;
	}

	public void setStationSort(Integer stationSort) {
		this.stationSort = stationSort;
	}

	public List<SubwayOutSaveRequest> getOutList() {
		return outList;
	}

	public void setOutList(List<SubwayOutSaveRequest> outList) {
		this.outList = outList;
	}

    public String getCreateFid() {
        return createFid;
    }

    public void setCreateFid(String createFid) {
        this.createFid = createFid;
    }
}
