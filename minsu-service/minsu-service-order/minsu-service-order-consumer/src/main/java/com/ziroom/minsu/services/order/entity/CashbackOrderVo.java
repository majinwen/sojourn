/**
 * @FileName: CashbackOrderVo.java
 * @Package com.ziroom.minsu.services.order.entity
 * 
 * @author yd
 * @created 2017年8月31日 上午11:51:45
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.order.entity;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>房东 返现活动的订单男</p>
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
public class CashbackOrderVo  extends BaseEntity {

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = 7915573176555498860L;
	
	/**
	 * 订单编号
	 */
	private  String orderSn;

	
	/**
	 * 房东uid
	 */
	private  String landlordUid;
	
	
	/**
	 * 创建时间
	 */
	private  Date  createTime;
	
	/**
	 * 当前房东的订单数量
	 */
	private  Integer num;

	/**
	 * @return the orderSn
	 */
	public String getOrderSn() {
		return orderSn;
	}

	/**
	 * @param orderSn the orderSn to set
	 */
	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
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
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the num
	 */
	public Integer getNum() {
		return num;
	}

	/**
	 * @param num the num to set
	 */
	public void setNum(Integer num) {
		this.num = num;
	}
	
	
	
}
