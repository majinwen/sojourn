/**
 * @FileName: HouseAuditVoNew.java
 * @Package com.ziroom.minsu.report.house.vo
 * 
 * @author baiwei
 * @created 2017年5月4日 下午5:10:22
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.report.house.vo;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.minsu.report.common.annotation.FieldMeta;

/**
 * <p>TODO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author baiwei
 * @since 1.0
 * @version 1.0
 */
public class HouseAuditVoNew extends BaseEntity {

	@FieldMeta(skip = true)
	private static final long serialVersionUID = 932036899146072133L;

	@FieldMeta(name = "国家", order = 1)
	private String nationName;

	@FieldMeta(name = "大区", order = 2)
	private String regionName;

	@FieldMeta(name = "城市", order = 3)
	private String cityName;
	
	@FieldMeta(skip = true)
	private String cityCode;
	
	@FieldMeta(name = "房源/房间编号", order = 4)
	private String houseSn;
	
	@FieldMeta(name = "房源/房间状态", order = 4)
	private String houseStatusName;
	
	@FieldMeta(name = "首次发布时间", order = 4)
	private Date firstDeployDate;
	
	@FieldMeta(name = "品质不通过时间",order = 4)
	private Date firstRefuseDate;
	
	@FieldMeta(name = "上架时间",order = 4)
	private Date firstUpDate;
	
	@FieldMeta(name = "下架时间",order = 4)
	private Date firstDownDate;
	
	@FieldMeta(name = "强制下架时间",order = 4)
	private Date firstQzDownDate;
	
	@FieldMeta(name = "品质审核不通过次数",order = 4)
	private int times;
	
	@FieldMeta(skip = true)
	private String firstRefuseReason;
	
	@FieldMeta(name = "未通过原因",order = 4)
	private String firstRefuseReasonName;
	
	@FieldMeta(skip = true)
	private Integer rentWay;
	
	@FieldMeta(skip = true)
	private String fid;
	
	/**
	 * @return the nationName
	 */
	public String getNationName() {
		return nationName;
	}

	/**
	 * @param nationName the nationName to set
	 */
	public void setNationName(String nationName) {
		this.nationName = nationName;
	}

	/**
	 * @return the regionName
	 */
	public String getRegionName() {
		return regionName;
	}

	/**
	 * @param regionName the regionName to set
	 */
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	/**
	 * @return the cityName
	 */
	public String getCityName() {
		return cityName;
	}

	/**
	 * @param cityName the cityName to set
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	/**
	 * @return the cityCode
	 */
	public String getCityCode() {
		return cityCode;
	}

	/**
	 * @param cityCode the cityCode to set
	 */
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	/**
	 * @return the houseSn
	 */
	public String getHouseSn() {
		return houseSn;
	}

	/**
	 * @param houseSn the houseSn to set
	 */
	public void setHouseSn(String houseSn) {
		this.houseSn = houseSn;
	}

	/**
	 * @return the houseStatusName
	 */
	public String getHouseStatusName() {
		return houseStatusName;
	}

	/**
	 * @param houseStatusName the houseStatusName to set
	 */
	public void setHouseStatusName(String houseStatusName) {
		this.houseStatusName = houseStatusName;
	}

	/**
	 * @return the firstDeployDate
	 */
	public Date getFirstDeployDate() {
		return firstDeployDate;
	}

	/**
	 * @param firstDeployDate the firstDeployDate to set
	 */
	public void setFirstDeployDate(Date firstDeployDate) {
		this.firstDeployDate = firstDeployDate;
	}

	/**
	 * @return the firstRefuseDate
	 */
	public Date getFirstRefuseDate() {
		return firstRefuseDate;
	}

	/**
	 * @param firstRefuseDate the firstRefuseDate to set
	 */
	public void setFirstRefuseDate(Date firstRefuseDate) {
		this.firstRefuseDate = firstRefuseDate;
	}

	/**
	 * @return the firstUpDate
	 */
	public Date getFirstUpDate() {
		return firstUpDate;
	}

	/**
	 * @param firstUpDate the firstUpDate to set
	 */
	public void setFirstUpDate(Date firstUpDate) {
		this.firstUpDate = firstUpDate;
	}

	/**
	 * @return the firstDownDate
	 */
	public Date getFirstDownDate() {
		return firstDownDate;
	}

	/**
	 * @param firstDownDate the firstDownDate to set
	 */
	public void setFirstDownDate(Date firstDownDate) {
		this.firstDownDate = firstDownDate;
	}

	/**
	 * @return the firstQzDownDate
	 */
	public Date getFirstQzDownDate() {
		return firstQzDownDate;
	}

	/**
	 * @param firstQzDownDate the firstQzDownDate to set
	 */
	public void setFirstQzDownDate(Date firstQzDownDate) {
		this.firstQzDownDate = firstQzDownDate;
	}

	/**
	 * @return the times
	 */
	public int getTimes() {
		return times;
	}

	/**
	 * @param times the times to set
	 */
	public void setTimes(int times) {
		this.times = times;
	}

	/**
	 * @return the firstRefuseReasonName
	 */
	public String getFirstRefuseReasonName() {
		return firstRefuseReasonName;
	}

	/**
	 * @param firstRefuseReasonName the firstRefuseReasonName to set
	 */
	public void setFirstRefuseReasonName(String firstRefuseReasonName) {
		this.firstRefuseReasonName = firstRefuseReasonName;
	}

	/**
	 * @return the firstRefuseReason
	 */
	public String getFirstRefuseReason() {
		return firstRefuseReason;
	}

	/**
	 * @param firstRefuseReason the firstRefuseReason to set
	 */
	public void setFirstRefuseReason(String firstRefuseReason) {
		this.firstRefuseReason = firstRefuseReason;
	}

	/**
	 * @return the rentWay
	 */
	public Integer getRentWay() {
		return rentWay;
	}

	/**
	 * @param rentWay the rentWay to set
	 */
	public void setRentWay(Integer rentWay) {
		this.rentWay = rentWay;
	}

	/**
	 * @return the fid
	 */
	public String getFid() {
		return fid;
	}

	/**
	 * @param fid the fid to set
	 */
	public void setFid(String fid) {
		this.fid = fid;
	}
	
	
	
	
}
