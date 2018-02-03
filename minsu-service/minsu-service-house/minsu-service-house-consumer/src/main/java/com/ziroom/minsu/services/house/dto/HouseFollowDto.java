/**
 * @FileName: HouseFollowDto.java
 * @Package com.ziroom.minsu.services.house.dto
 * 
 * @author bushujie
 * @created 2017年2月23日 下午4:47:27
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.dto;

import com.ziroom.minsu.services.common.dto.PageRequest;

/**
 * <p>房源跟进查询参数</p>
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
public class HouseFollowDto extends PageRequest{

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 4333088543782051410L;
	
	/**
	 *  客服跟进开始时间（处理多久的历史数据的条件）
	 */
	private String startDate;
	
	/**
	 * 客服跟进起始时间
	 */
	private String beforeDate;
	
	/**
	 * 专员跟进起始时间
	 */
	private String attacheStartDate;

	/**
	 * 房东uid
	 */
	private String landlordUid;
	/**
	 * 房源编号
	 */
	private String houseSn;
	/**
	 * 房东姓名
	 */
	private String landlordName;
	/**
	 * 房东电话
	 */
	private String landlordMobile;
	/**
	 * 跟进人姓名
	 */
	private String empName;
	/**
	 * 跟进状态
	 */
	private Integer followStatus;
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
	 *  房东uid集合
	 */
	private String uidStr;
	
	/**
	 * 是否排除被锁记录 1:是，0：否
	 */
	private Integer isNotLock;
	
	/**
	 * 操作人fid
	 */
	private String operateFid;
	
	/**
	 * 操作时间 yyyyMMddHHmm
	 */
	private String operateDate;
	
	/**
	 * 房源审核说明
	 */
	private String auditCause;
	

	/**
	 * @return the auditCause
	 */
	public String getAuditCause() {
		return auditCause;
	}

	/**
	 * @param auditCause the auditCause to set
	 */
	public void setAuditCause(String auditCause) {
		this.auditCause = auditCause;
	}

	/**
	 * @return the isNotLock
	 */
	public Integer getIsNotLock() {
		return isNotLock;
	}

	/**
	 * @param isNotLock the isNotLock to set
	 */
	public void setIsNotLock(Integer isNotLock) {
		this.isNotLock = isNotLock;
	}

	/**
	 * @return the operateFid
	 */
	public String getOperateFid() {
		return operateFid;
	}

	/**
	 * @param operateFid the operateFid to set
	 */
	public void setOperateFid(String operateFid) {
		this.operateFid = operateFid;
	}

	/**
	 * @return the operateDate
	 */
	public String getOperateDate() {
		return operateDate;
	}

	/**
	 * @param operateDate the operateDate to set
	 */
	public void setOperateDate(String operateDate) {
		this.operateDate = operateDate;
	}

	/**
	 * @return the uidStr
	 */
	public String getUidStr() {
		return uidStr;
	}

	/**
	 * @param uidStr the uidStr to set
	 */
	public void setUidStr(String uidStr) {
		this.uidStr = uidStr;
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
	 * @return the empName
	 */
	public String getEmpName() {
		return empName;
	}

	/**
	 * @param empName the empName to set
	 */
	public void setEmpName(String empName) {
		this.empName = empName;
	}

	/**
	 * @return the followStatus
	 */
	public Integer getFollowStatus() {
		return followStatus;
	}

	/**
	 * @param followStatus the followStatus to set
	 */
	public void setFollowStatus(Integer followStatus) {
		this.followStatus = followStatus;
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
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the beforeDate
	 */
	public String getBeforeDate() {
		return beforeDate;
	}

	/**
	 * @param beforeDate the beforeDate to set
	 */
	public void setBeforeDate(String beforeDate) {
		this.beforeDate = beforeDate;
	}
	
	/**
	 * @return the attacheStartDate
	 */
	public String getAttacheStartDate() {
		return attacheStartDate;
	}

	/**
	 * @param attacheStartDate the attacheStartDate to set
	 */
	public void setAttacheStartDate(String attacheStartDate) {
		this.attacheStartDate = attacheStartDate;
	}
}
