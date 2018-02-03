/**
 * @FileName: InviterCouponsVo.java
 * @Package com.ziroom.minsu.services.cms.entity
 * 
 * @author loushuai
 * @created 2017年12月5日 下午2:43:10
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.cms.entity;

/**
 * <p>邀请人积分可兑换现金优惠券Vo</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author loushuai
 * @since 1.0
 * @version 1.0
 */
public class InviterCouponsVo {

	/**
	 * 优惠券金额对应的优惠券活动号
	 */
	private String actSn;
	
	/**
	 * 优惠券金额
	 */
	private String couponMoney;
	
	/**
	 * 是否可选
	 */
	private Integer isClick;

	
	public String getActSn() {
		return actSn;
	}

	public void setActSn(String actSn) {
		this.actSn = actSn;
	}

	public String getCouponMoney() {
		return couponMoney;
	}

	public void setCouponMoney(String couponMoney) {
		this.couponMoney = couponMoney;
	}

	public Integer getIsClick() {
		return isClick;
	}

	public void setIsClick(Integer isClick) {
		this.isClick = isClick;
	}
	
}
