package com.ziroom.minsu.services.order.dto;

import java.util.List;

/**
 * <p>返现审核/驳回参数</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lishaochuan on 2016年9月11日
 * @since 1.0
 * @version 1.0
 */
public class AuditCashbackRequest {

	String userId;
	
	List<String> cashbackSns;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<String> getCashbackSns() {
		return cashbackSns;
	}

	public void setCashbackSns(List<String> cashbackSns) {
		this.cashbackSns = cashbackSns;
	}
}
