package com.ziroom.minsu.entity.conf;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

public class SubwayOutEntity extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5596242333284788397L;

	private Integer id;

	private String fid;

	private String stationFid;

	private String outName;

	private Double longitude;

	private Double latitude;

	private Integer sort;

	private String createFid;

	private Date createTime;

	private Date lastModifyDate;

	private Byte isDel;
	
	private Double googleLongitude;
	
	private Double googleLatitude;
	

	/**
	 * @return the googleLongitude
	 */
	public Double getGoogleLongitude() {
		return googleLongitude;
	}

	/**
	 * @param googleLongitude the googleLongitude to set
	 */
	public void setGoogleLongitude(Double googleLongitude) {
		this.googleLongitude = googleLongitude;
	}

	/**
	 * @return the googleLatitude
	 */
	public Double getGoogleLatitude() {
		return googleLatitude;
	}

	/**
	 * @param googleLatitude the googleLatitude to set
	 */
	public void setGoogleLatitude(Double googleLatitude) {
		this.googleLatitude = googleLatitude;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid == null ? null : fid.trim();
	}

	public String getStationFid() {
		return stationFid;
	}

	public void setStationFid(String stationFid) {
		this.stationFid = stationFid == null ? null : stationFid.trim();
	}

	public String getOutName() {
		return outName;
	}

	public void setOutName(String outName) {
		this.outName = outName == null ? null : outName.trim();
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

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getCreateFid() {
		return createFid;
	}

	public void setCreateFid(String createFid) {
		this.createFid = createFid == null ? null : createFid.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastModifyDate() {
		return lastModifyDate;
	}

	public void setLastModifyDate(Date lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}

	public Byte getIsDel() {
		return isDel;
	}

	public void setIsDel(Byte isDel) {
		this.isDel = isDel;
	}
	
	
}