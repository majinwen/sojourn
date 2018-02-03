/**
 * @FileName: HouseBusinessVo.java
 * @Package com.ziroom.minsu.services.house.entity
 * 
 * @author bushujie
 * @created 2016年7月6日 下午2:52:44
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.entity;

import com.ziroom.minsu.entity.house.HouseBusinessMsgEntity;

/**
 * <p>房源商机列表vo</p>
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
public class HouseBusinessListVo extends HouseBusinessMsgEntity{

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -5192111239549742503L;

	/**
	 * 房东姓名
	 */
	private String landlordName;
	/**
	 * 房东手机号
	 */
	private String landlordMobile;
	
	/**
	 * 地推管家员工号
	 */
	private String dtGuardCode;
	/**
	 * 地推管家姓名
	 */
	private String dtGuardName;
	
	/**
	 * 地推管家姓名
	 */
	private String dtGuardMobile;
	
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
