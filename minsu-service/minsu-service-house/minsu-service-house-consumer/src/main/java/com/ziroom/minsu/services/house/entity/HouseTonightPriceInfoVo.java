/**
 * @FileName: HouseDetailNewVo.java
 * @Package com.ziroom.minsu.services.house.entity
 * 
 * @author yd
 * @created 2016年12月7日 下午4:58:04
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.ziroom.minsu.entity.base.MinsuEleEntity;
import com.ziroom.minsu.entity.search.LabelTipsEntity;

/**
 * <p>房源今夜特价相关信息的vo</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author lusp  2017/05/15
 * @since 1.0
 * @version 1.0
 */
public class HouseTonightPriceInfoVo implements Serializable{


	/**
	 * 序列id
	 */
	private static final long serialVersionUID = 390434186936151466L;

	/**
	 * 今夜特价是否生效
	 */
	private boolean isEffective;
	
	/**
	 * 今夜特价折扣
	 */
	private double discount;
	
	/**
	 * 今夜特价开始时间
	 */
	private Date startTime;
	
	/**
	 * 今夜特价结束时间
	 */
	private Date endTime;
	
	
	/**
	 * @return the startTime
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * 开始倒计时
	 */
	private long countdownBegin;
	
	/**
	 * 结束倒计时
	 */
	private long countdownEnd;

	/**
	 * @return the isEffective
	 */
	public boolean isEffective() {
		return isEffective;
	}

	/**
	 * @param isEffective the isEffective to set
	 */
	public void setEffective(boolean isEffective) {
		this.isEffective = isEffective;
	}

	/**
	 * @return the discount
	 */
	public double getDiscount() {
		return discount;
	}

	/**
	 * @param discount the discount to set
	 */
	public void setDiscount(double discount) {
		this.discount = discount;
	}

	/**
	 * @return the countdownBegin
	 */
	public long getCountdownBegin() {
		return countdownBegin;
	}

	/**
	 * @param countdownBegin the countdownBegin to set
	 */
	public void setCountdownBegin(long countdownBegin) {
		this.countdownBegin = countdownBegin;
	}

	/**
	 * @return the countdownEnd
	 */
	public long getCountdownEnd() {
		return countdownEnd;
	}

	/**
	 * @param countdownEnd the countdownEnd to set
	 */
	public void setCountdownEnd(long countdownEnd) {
		this.countdownEnd = countdownEnd;
	}
	
	

}
