/**
 * @FileName: LandlordStaticVo.java
 * @Package com.ziroom.minsu.report.customer.vo
 * 
 * @author zl
 * @created 2017年5月18日 上午10:26:47
 * 
 * Copyright 2016-2025 ziroom
 */
package com.ziroom.minsu.report.customer.vo;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.minsu.report.common.annotation.FieldMeta;

/**
 * <p>房东粘性信息</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zl
 * @since 1.0
 * @version 1.0
 */
public class LandlordStaticVo extends BaseEntity {

	
	/**
	 * 
	 */
	@FieldMeta(skip = true)
	private static final long serialVersionUID = -5588824756997985169L;
	
	
	/**
	 * 国家
	 */
	@FieldMeta(name="国家",order=1)
	private String countroy;	
	/**
	 * 大区
	 */
	@FieldMeta(name="大区",order=2)
	private String region;
	/**
	 * 城市
	 */
	@FieldMeta(name="城市",order=3)
	private String city;
	
	@FieldMeta(skip = true)
	private String cityCode;
	
	/**
	 * 房东UID
	 */
	@FieldMeta(name="房东UID",order=4)
	private String landlordUid;
	/**
	 * 上过架的房源数量
	 */
	@FieldMeta(name="累计上过架的房源数量",order=5)
	private Integer sjedHouseCount;
	/**
	 * 上架状态房源数量
	 */
	@FieldMeta(name="目前上架状态房源数量",order=6)
	private Integer sjingHouseCount;
	/**
	 * 累计浏览量
	 */
	@FieldMeta(name="累计浏览量",order=7)
	private Integer pvCount;
	/**
	 * 累计咨询量
	 */
	@FieldMeta(name="累计咨询量",order=8)
	private Integer adviseCount;
	/**
	 * 累计申请量
	 */
	@FieldMeta(name="累计申请量",order=9)
	private Integer bookOrderCount;
	/**
	 * 累计接单量
	 */
	@FieldMeta(name="累计接单量",order=10)
	private Integer acptOrderCount;
	/**
	 * 累计订单量
	 */
	@FieldMeta(name="累计支付订单量",order=11)
	private Integer paidOrderCount;
	/**
	 * 累计预订间夜量
	 */
	@FieldMeta(name="累计预订间夜量",order=12)
	private Integer bookDays;
	
	/**
	 * 累计入住客户量
	 */
	@FieldMeta(name="累计入住客户量",order=13)
	private Integer peopleNum;
	
	/**
	 * 累计入住间夜
	 */
	@FieldMeta(name="累计入住间夜",order=14)
	private Integer checkinDays;
	
	/**
	 * 累计房租收益
	 */
	@FieldMeta(name="累计房租收益(元)",order=15)
	private Integer paiedMoney;
	
	/**
	 * 累计收到评价量
	 */
	@FieldMeta(name="累计收到评价量",order=16)
	private Integer ratedCount;
	
	/**
	 * 累计发出评价量
	 */
	@FieldMeta(name="累计发出评价量",order=17)
	private Integer rateCount;
	
	/**
	 * 累计获得评分平均分
	 */
	@FieldMeta(name="累计获得评分平均分",order=18)
	private Float ratedScore;
	
	/**
	 * 累计发出评分平均分
	 */
	@FieldMeta(name="累计发出评分平均分",order=19)
	private Float rateScore;
	

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCountroy() {
		return countroy;
	}

	public String getRegion() {
		return region;
	}

	public String getCity() {
		return city;
	}

	public String getLandlordUid() {
		return landlordUid;
	}

	public Integer getSjedHouseCount() {
		return sjedHouseCount;
	}

	public Integer getSjingHouseCount() {
		return sjingHouseCount;
	}

	public Integer getPvCount() {
		return pvCount;
	}

	public Integer getAdviseCount() {
		return adviseCount;
	}

	public Integer getBookOrderCount() {
		return bookOrderCount;
	}

	public Integer getAcptOrderCount() {
		return acptOrderCount;
	}

	public Integer getPaidOrderCount() {
		return paidOrderCount;
	}

	public Integer getBookDays() {
		return bookDays;
	}

	public Integer getPeopleNum() {
		return peopleNum;
	}

	public Integer getCheckinDays() {
		return checkinDays;
	}

	public Integer getPaiedMoney() {
		return paiedMoney;
	}

	public Integer getRatedCount() {
		return ratedCount;
	}

	public Integer getRateCount() {
		return rateCount;
	}

	public Float getRatedScore() {
		return ratedScore;
	}

	public Float getRateScore() {
		return rateScore;
	}

	public void setCountroy(String countroy) {
		this.countroy = countroy;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setLandlordUid(String landlordUid) {
		this.landlordUid = landlordUid;
	}

	public void setSjedHouseCount(Integer sjedHouseCount) {
		this.sjedHouseCount = sjedHouseCount;
	}

	public void setSjingHouseCount(Integer sjingHouseCount) {
		this.sjingHouseCount = sjingHouseCount;
	}

	public void setPvCount(Integer pvCount) {
		this.pvCount = pvCount;
	}

	public void setAdviseCount(Integer adviseCount) {
		this.adviseCount = adviseCount;
	}

	public void setBookOrderCount(Integer bookOrderCount) {
		this.bookOrderCount = bookOrderCount;
	}

	public void setAcptOrderCount(Integer acptOrderCount) {
		this.acptOrderCount = acptOrderCount;
	}

	public void setPaidOrderCount(Integer paidOrderCount) {
		this.paidOrderCount = paidOrderCount;
	}

	public void setBookDays(Integer bookDays) {
		this.bookDays = bookDays;
	}

	public void setPeopleNum(Integer peopleNum) {
		this.peopleNum = peopleNum;
	}

	public void setCheckinDays(Integer checkinDays) {
		this.checkinDays = checkinDays;
	}

	public void setPaiedMoney(Integer paiedMoney) {
		this.paiedMoney = paiedMoney;
	}

	public void setRatedCount(Integer ratedCount) {
		this.ratedCount = ratedCount;
	}

	public void setRateCount(Integer rateCount) {
		this.rateCount = rateCount;
	}

	public void setRatedScore(Float ratedScore) {
		this.ratedScore = ratedScore;
	}

	public void setRateScore(Float rateScore) {
		this.rateScore = rateScore;
	}
	

}
