package com.ziroom.minsu.services.basedata.entity;

import com.asura.framework.base.entity.BaseEntity;

public class SubwayLineVo extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6155052714719896106L;

	private String lineFid;

	private String lineName;

	private String stationFid;

	private String stationName;

	private Double stationLongitude;

	private Double stationLatitude;

	private String outFid;

	private String outName;

	private Double outLongitude;

	private Double outLatitude;

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

}
