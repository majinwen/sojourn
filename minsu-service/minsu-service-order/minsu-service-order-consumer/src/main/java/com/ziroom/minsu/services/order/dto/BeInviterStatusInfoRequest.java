/**
 * @FileName: BeInviterStatusInfoRequest.java
 * @Package com.ziroom.minsu.services.order.dto
 * 
 * @author loushuai
 * @created 2017年12月4日 下午2:20:14
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.order.dto;

import java.util.List;
/**
 * <p>被邀请人状态请求参数</p>
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
public class BeInviterStatusInfoRequest {

	/**
	 * 订单状态
	 */
	private Integer orderStatus;
	
	/**
	 * 所有被邀请的好友的信息集合
	 */
	private List<String> beInviterInfoList;

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public List<String> getBeInviterInfoList() {
		return beInviterInfoList;
	}

	public void setBeInviterInfoList(List<String> beInviterInfoList) {
		this.beInviterInfoList = beInviterInfoList;
	}
	
}
