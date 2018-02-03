/**
 * @FileName: OrderStaticsRequest.java
 * @Package com.ziroom.minsu.services.order.dto
 * 
 * @author liyingjie
 * @created 2016年4月2日 上午10:02:43
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.order.dto;

import java.util.Date;
import java.util.List;

import com.ziroom.minsu.services.common.dto.PageRequest;

/**
 * <p>
 * 订单请求参数
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liyingjie
 * @since 1.0
 * @version 1.0
 */
public class OrderStaticsRequest extends PageRequest {

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = -6485230181824233964L;

	
    /**
	 * 房东uid
	 */
	private String landlordUid;

	/**
	 * 时间限制
	 */
	private Date limitTime;

	/**
	 * 时间段
	 */
	private Integer sumTime;
	
	public String getLandlordUid() {
		return landlordUid;
	}

	public void setLandlordUid(String landlordUid) {
		this.landlordUid = landlordUid;
	}

	public Date getLimitTime() {
		return limitTime;
	}

	public void setLimitTime(Date limitTime) {
		this.limitTime = limitTime;
	}

	public Integer getSumTime() {
		return sumTime;
	}

	public void setSumTime(Integer sumTime) {
		this.sumTime = sumTime;
	}
	
	
	
	
}
