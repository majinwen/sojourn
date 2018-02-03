/**
 * @FileName: HouseFollowSaveDto.java
 * @Package com.ziroom.minsu.services.house.dto
 * 
 * @author bushujie
 * @created 2017年2月25日 下午2:47:58
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.dto;

/**
 * <p>房源跟进保存参数Dto</p>
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
public class HouseFollowSaveDto {
	
	 /**
	  * 房源跟进fid
	  */
	private String followFid;
	/**
	 * 房源出租方式 0：整租，1：合租
	 */
	private Integer rentWay;
	/**
	 * 房源编号
	 */
	private String houseSn;
	
	/**
	 * 锁业务code 11001：客服跟进审核未通过房源，11002：专员跟进审核未通过房源
	 */
	private String houseLockCode;
	
	/**
	 * 创建人fid
	 */
	private String createFid;
	
	/**
	 * 房东uid
	 */
	private String landlordUid;
	
	/**
	 * 房东手机号
	 */
	private String customerMobile;
	
	/**
	 * @return the customerMobile
	 */
	public String getCustomerMobile() {
		return customerMobile;
	}
	/**
	 * @param customerMobile the customerMobile to set
	 */
	public void setCustomerMobile(String customerMobile) {
		this.customerMobile = customerMobile;
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
	 * @return the houseLockCode
	 */
	public String getHouseLockCode() {
		return houseLockCode;
	}
	/**
	 * @param houseLockCode the houseLockCode to set
	 */
	public void setHouseLockCode(String houseLockCode) {
		this.houseLockCode = houseLockCode;
	}
	/**
	 * @return the createFid
	 */
	public String getCreateFid() {
		return createFid;
	}
	/**
	 * @param createFid the createFid to set
	 */
	public void setCreateFid(String createFid) {
		this.createFid = createFid;
	}
	/**
	 * @return the followFid
	 */
	public String getFollowFid() {
		return followFid;
	}
	/**
	 * @param followFid the followFid to set
	 */
	public void setFollowFid(String followFid) {
		this.followFid = followFid;
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
}
