package com.ziroom.minsu.services.basedata.dto;

public class SubwayOutSaveRequest {

	private String outFid;

	// 出口名称
	private String outName;

	// 出口经度
	private Double outLongitude;

	// 出口经度
	private Double outLatitude;

	// 出口纬度
	private Integer outSort;

	public String getOutFid() {
		return outFid;
	}

	public void setOutFid(String outFid) {
		this.outFid = outFid;
	}

	public String getOutName() {
		return outName;
	}

	public void setOutName(String outName) {
		this.outName = outName;
	}

	public Double getOutLongitude() {
		return outLongitude;
	}

	public void setOutLongitude(Double outLongitude) {
		this.outLongitude = outLongitude;
	}

	public Double getOutLatitude() {
		return outLatitude;
	}

	public void setOutLatitude(Double outLatitude) {
		this.outLatitude = outLatitude;
	}

	public Integer getOutSort() {
		return outSort;
	}

	public void setOutSort(Integer outSort) {
		this.outSort = outSort;
	}

}
