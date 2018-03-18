/**
 * @FileName: HouseAddressVo.java
 * @Package com.ziroom.minsu.report.house.vo
 * 
 * @author baiwei
 * @created 2017年5月2日 下午5:49:31
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.report.house.vo;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.minsu.report.common.annotation.FieldMeta;

/**
 * <p>
 * HouseAddressVo
 * </p>
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
public class HouseAddressVo extends BaseEntity {

	/**
	 * 
	 */
	@FieldMeta(skip = true)
	private static final long serialVersionUID = 4996295868186256698L;

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

	@FieldMeta(name = "首次发布时间", order = 4)
	private Date firstDeployDate;

	@FieldMeta(name = "房源/房间状态", order = 4)
	private String houseStatusName;

	@FieldMeta(name = "房源/房间名称", order = 4)
	private String houseName;

	@FieldMeta(name = "房源地址表", order = 4)
	private String houseAddr;

	@FieldMeta(name = "房东UID", order = 4)
	private String landlordUid;

	@FieldMeta(name = "房东电话", order = 4)
	private String lanMobile;

	@FieldMeta(name = "房东姓名", order = 4)
	private String lanRealName;

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
	 * @return the houseName
	 */
	public String getHouseName() {
		return houseName;
	}

	/**
	 * @param houseName the houseName to set
	 */
	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}

	/**
	 * @return the houseAddr
	 */
	public String getHouseAddr() {
		return houseAddr;
	}

	/**
	 * @param houseAddr the houseAddr to set
	 */
	public void setHouseAddr(String houseAddr) {
		this.houseAddr = houseAddr;
	}

	/**
	 * @return the landlordUid
	 */
	public String getLandlordUid() {
		return landlordUid;
	}

	/**
	 * @param landlordUid the landlordUid to set
	 */
	public void setLandlordUid(String landlordUid) {
		this.landlordUid = landlordUid;
	}

	/**
	 * @return the lanMobile
	 */
	public String getLanMobile() {
		return lanMobile;
	}

	/**
	 * @param lanMobile the lanMobile to set
	 */
	public void setLanMobile(String lanMobile) {
		this.lanMobile = lanMobile;
	}

	/**
	 * @return the lanRealName
	 */
	public String getLanRealName() {
		return lanRealName;
	}

	/**
	 * @param lanRealName the lanRealName to set
	 */
	public void setLanRealName(String lanRealName) {
		this.lanRealName = lanRealName;
	}

	
}
