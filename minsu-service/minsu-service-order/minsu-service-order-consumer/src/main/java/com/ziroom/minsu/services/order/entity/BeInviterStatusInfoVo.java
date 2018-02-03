/**
 * @FileName: BeInviterStatusInfoVo.java
 * @Package com.ziroom.minsu.services.order.entity
 * 
 * @author loushuai
 * @created 2017年12月4日 下午2:30:21
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.order.entity;

import java.util.Date;

/**
 * <p>被邀请人状态请求Vo</p>
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
public class BeInviterStatusInfoVo {

	/**
	 *  被邀请人uid
	 */
	private String beInviterUid;
	
	/**
	 * 被邀请人订单状态是20的订单数量
	 */
	private Integer orderNum;
	
	/**
	 * 订单创建时间
	 */
	private Date orderCreateTime;
	
	/**
	 * 订单创建时间
	 */
	private Integer isHasEffectiveOrder;

	public String getBeInviterUid() {
		return beInviterUid;
	}

	public void setBeInviterUid(String beInviterUid) {
		this.beInviterUid = beInviterUid;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public Date getOrderCreateTime() {
		return orderCreateTime;
	}

	public void setOrderCreateTime(Date orderCreateTime) {
		this.orderCreateTime = orderCreateTime;
	}

	public Integer getIsHasEffectiveOrder() {
		return isHasEffectiveOrder;
	}

	public void setIsHasEffectiveOrder(Integer isHasEffectiveOrder) {
		this.isHasEffectiveOrder = isHasEffectiveOrder;
	} 
	
}