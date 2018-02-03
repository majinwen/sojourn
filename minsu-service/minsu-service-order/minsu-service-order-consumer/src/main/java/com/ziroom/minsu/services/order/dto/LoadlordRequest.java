/**
 * @FileName: LoadlordRequest.java
 * @Package com.ziroom.minsu.services.order.dto
 * 
 * @author yd
 * @created 2016年4月6日 下午9:33:24
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.order.dto;

import java.io.Serializable;

/**
 * <p>房东端参数列表</p>
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
public class LoadlordRequest implements Serializable{

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = -3652409214556979617L;
	/**
	 * 订单编号
	 */
	private String orderSn;
	/**
	 * 修改后的订单状态
	 */
	private int orderStatus;
	/**
	 * 参数值
	 */
	private String paramValue;
	/**
	 * 待确认额外金额
	 */
	private Integer otherMoney;
	/**
	 * 房东uid
	 */
	private String landlordUid;

	/**
	 * 拒绝原因
	 */
	private String refuseCode;
	
	/**
	 * 拒绝原因描述
	 */
	private String refuseReason;

	/**
	 * 国家码
	 */
	private String countryCode;
	
	/**
	 * @return the countryCode
	 */
	public String getCountryCode() {
		return countryCode;
	}
	/**
	 * @param countryCode the countryCode to set
	 */
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getOrderSn() {
		return orderSn;
	}
	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}
	public int getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getParamValue() {
		return paramValue;
	}
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
	public Integer getOtherMoney() {
		return otherMoney;
	}
	public void setOtherMoney(Integer otherMoney) {
		this.otherMoney = otherMoney;
	}

    public String getLandlordUid() {
        return landlordUid;
    }

    public void setLandlordUid(String landlordUid) {
        this.landlordUid = landlordUid;
    }
	
	public String getRefuseCode() {
		return refuseCode;
	}
	public void setRefuseCode(String refuseCode) {
		this.refuseCode = refuseCode;
	}
	public String getRefuseReason() {
		return refuseReason;
	}
	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
	}
    
}
