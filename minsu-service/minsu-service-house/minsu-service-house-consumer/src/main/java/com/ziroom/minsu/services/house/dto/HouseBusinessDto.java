/**
 * @FileName: HouseBusinessDto.java
 * @Package com.ziroom.minsu.services.house.dto
 * 
 * @author bushujie
 * @created 2016年7月6日 上午11:56:38
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.dto;

import com.ziroom.minsu.services.common.dto.PageRequest;

/**
 * <p>商机列表查询参数</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
public class HouseBusinessDto extends PageRequest {
	
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 8869243698481288481L;
	/**
	 * 地推管家员工号
	 */
	private String dtGuardCode;
	/**
	 * 地推管家姓名
	 */
	private String dtGuardName;
	/**
	 * 地推管家手机号
	 */
	private String dtGuardMobile;
	/**
	 * 房东姓名
	 */
	private String landlordName;
	/**
	 * 房东手机号
	 */
	private String landlordMobile;
	/**
	 * 地推进度
	 */
	private Integer businessPlan;
	/**
	 * 商机编号
	 */
	private String busniessSn;
	/**
	 * 房源地址
	 */
	private String houseAddr;
	/**
	 * 国家code
	 */
	private String nationCode;
	/**
	 * 省份code
	 */
	private String provinceCode;
	/**
	 * 城市code
	 */
	private String cityCode;
	/**
	 * 区域code
	 */
	private String areaCode;
	
	/**
	 * @return the dtGuardCode
	 */
	public String getDtGuardCode() {
		return dtGuardCode;
	}
	/**
	 * @param dtGuardCode the dtGuardCode to set
	 */
	public void setDtGuardCode(String dtGuardCode) {
		this.dtGuardCode = dtGuardCode;
	}
	/**
	 * @return the dtGuardName
	 */
	public String getDtGuardName() {
		return dtGuardName;
	}
	/**
	 * @param dtGuardName the dtGuardName to set
	 */
	public void setDtGuardName(String dtGuardName) {
		this.dtGuardName = dtGuardName;
	}
	/**
	 * @return the landlordName
	 */
	public String getLandlordName() {
		return landlordName;
	}
	/**
	 * @param landlordName the landlordName to set
	 */
	public void setLandlordName(String landlordName) {
		this.landlordName = landlordName;
	}
	/**
	 * @return the landlordMobile
	 */
	public String getLandlordMobile() {
		return landlordMobile;
	}
	/**
	 * @param landlordMobile the landlordMobile to set
	 */
	public void setLandlordMobile(String landlordMobile) {
		this.landlordMobile = landlordMobile;
	}
	/**
	 * @return the businessPlan
	 */
	public Integer getBusinessPlan() {
		return businessPlan;
	}
	/**
	 * @param businessPlan the businessPlan to set
	 */
	public void setBusinessPlan(Integer businessPlan) {
		this.businessPlan = businessPlan;
	}
	/**
	 * @return the busniessSn
	 */
	public String getBusniessSn() {
		return busniessSn;
	}
	/**
	 * @param busniessSn the busniessSn to set
	 */
	public void setBusniessSn(String busniessSn) {
		this.busniessSn = busniessSn;
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
	 * @return the nationCode
	 */
	public String getNationCode() {
		return nationCode;
	}
	/**
	 * @param nationCode the nationCode to set
	 */
	public void setNationCode(String nationCode) {
		this.nationCode = nationCode;
	}
	/**
	 * @return the provinceCode
	 */
	public String getProvinceCode() {
		return provinceCode;
	}
	/**
	 * @param provinceCode the provinceCode to set
	 */
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
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
	 * @return the areaCode
	 */
	public String getAreaCode() {
		return areaCode;
	}
	/**
	 * @param areaCode the areaCode to set
	 */
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	/**
	 * @return the dtGuardMobile
	 */
	public String getDtGuardMobile() {
		return dtGuardMobile;
	}
	/**
	 * @param dtGuardMobile the dtGuardMobile to set
	 */
	public void setDtGuardMobile(String dtGuardMobile) {
		this.dtGuardMobile = dtGuardMobile;
	}
}
