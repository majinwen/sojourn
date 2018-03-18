/**
 * @FileName: UserCusInfoVo.java
 * @Package com.ziroom.minsu.report.afi.entity
 * 
 * @author bushujie
 * @created 2016年9月20日 下午3:38:31
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.report.customer.vo;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.minsu.report.common.annotation.FieldMeta;

import java.util.Date;

/**
 * <p>房东信息报表实体类</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author lusp  2017/4/27
 * @since 1.0
 * @version 1.0
 */
public class UserLandlordInfoVo extends BaseEntity {
	
	
	@FieldMeta(skip = true)
	private static final long serialVersionUID = 1L;
	@FieldMeta(name="国家",order=1)
	private String nationName;
	@FieldMeta(name="大区",order=2)
	private String regionName;
	@FieldMeta(name="城市",order=3)
	private String cityName;
	@FieldMeta(name="房东UID",order=4)
	private String  landlordUID;
	@FieldMeta(name="房东性质",order=5)
	private String landlordNature;
	@FieldMeta(name="房东姓名",order=6)
	private String landlordName;
	@FieldMeta(name="房东电话",order=7)
	private String landlordTel;
	@FieldMeta(name="是否天使房东",order=8)
	private String isAngelLandlord;
	@FieldMeta(name="是否终身",order=9)
	private String isLife;
	@FieldMeta(name="天使房东开始时间",order=10)
	private Date startTime;
	@FieldMeta(name="天使房东结束时间",order=11)
	private Date endTime;
	@FieldMeta(name="房东性别",order=12)
	private String landlordSex;
	@FieldMeta(name="房东年龄",order=13)
	private Integer landlordAge;
	@FieldMeta(name="发布房源数量",order=14)
	private Integer pubNum;
	@FieldMeta(name="上架房源数量",order=15)
	private Integer groudNum;
	@FieldMeta(name="下架/强制下架房源数量",order=16)
	private Integer underNum;
	@FieldMeta(name="首套上架时间",order=17)
	private Date firstGroudTime;
	@FieldMeta(name="房东身份所属地",order=18)
	private String locationCity;

	private String cityCode;
	
	private String nationCode;
	
	private String idCardNo;

	public String getNationName() {
		return nationName;
	}

	public void setNationName(String nationName) {
		this.nationName = nationName;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getLandlordUID() {
		return landlordUID;
	}

	public void setLandlordUID(String landlordUID) {
		this.landlordUID = landlordUID;
	}

	public String getLandlordNature() {
		return landlordNature;
	}

	public void setLandlordNature(String landlordNature) {
		this.landlordNature = landlordNature;
	}

	public String getLandlordName() {
		return landlordName;
	}

	public void setLandlordName(String landlordName) {
		this.landlordName = landlordName;
	}

	public String getLandlordTel() {
		return landlordTel;
	}

	public void setLandlordTel(String landlordTel) {
		this.landlordTel = landlordTel;
	}

	public String getIsAngelLandlord() {
		return isAngelLandlord;
	}

	public void setIsAngelLandlord(String isAngelLandlord) {
		this.isAngelLandlord = isAngelLandlord;
	}

	public String getIsLife() {
		return isLife;
	}

	public void setIsLife(String isLife) {
		this.isLife = isLife;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getLandlordSex() {
		return landlordSex;
	}

	public void setLandlordSex(String landlordSex) {
		this.landlordSex = landlordSex;
	}

	public Integer getLandlordAge() {
		return landlordAge;
	}

	public void setLandlordAge(Integer landlordAge) {
		this.landlordAge = landlordAge;
	}

	public Integer getPubNum() {
		return pubNum;
	}

	public void setPubNum(Integer pubNum) {
		this.pubNum = pubNum;
	}

	public Integer getGroudNum() {
		return groudNum;
	}

	public void setGroudNum(Integer groudNum) {
		this.groudNum = groudNum;
	}

	public Integer getUnderNum() {
		return underNum;
	}

	public void setUnderNum(Integer underNum) {
		this.underNum = underNum;
	}

	public Date getFirstGroudTime() {
		return firstGroudTime;
	}

	public void setFirstGroudTime(Date firstGroudTime) {
		this.firstGroudTime = firstGroudTime;
	}

	public String getLocationCity() {
		return locationCity;
	}

	public void setLocationCity(String locationCity) {
		this.locationCity = locationCity;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getNationCode() {
		return nationCode;
	}

	public void setNationCode(String nationCode) {
		this.nationCode = nationCode;
	}

	public String getIdCardNo() {
		return idCardNo;
	}

	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}
	
}
