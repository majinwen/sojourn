/**
 * @FileName: ExchangeHouseLanlordDto.java
 * @Package com.ziroom.minsu.services.house.dto
 * 
 * @author yd
 * @created 2016年12月6日 上午10:11:18
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.dto;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>交换房源房东uid的vo</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
public class ExchangeHouseLanlordDto extends BaseEntity{

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = 6707826715444160235L;

	/**
	 * 房源fid
	 */
	private String houseFid;
	
	/**
	 * 出租方式
	 */
	private Integer rentWay;
	
	/**
	 * 新房东uid
	 */
	private String newLanUid;

	/**
	 * @return the houseFid
	 */
	public String getHouseFid() {
		return houseFid;
	}

	/**
	 * @param houseFid the houseFid to set
	 */
	public void setHouseFid(String houseFid) {
		this.houseFid = houseFid;
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
	 * @return the newLanUid
	 */
	public String getNewLanUid() {
		return newLanUid;
	}

	/**
	 * @param newLanUid the newLanUid to set
	 */
	public void setNewLanUid(String newLanUid) {
		this.newLanUid = newLanUid;
	}
	
	
}
