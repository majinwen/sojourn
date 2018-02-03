/**
 * @FileName: BeInviterStatusEnum.java
 * @Package com.ziroom.minsu.valenum.cms
 * 
 * @author loushuai
 * @created 2017年12月4日 上午11:24:41
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.valenum.cms;

import com.asura.framework.base.util.Check;

/**
 * <p>邀请下单活动-被邀请人状态</p>
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
public enum BeInviterStatusEnum {

	HAS_ACCEPT_INVITE(0,"接受邀请","快给Ta推荐美美的民宿吧！"),
	HAS_CREATE_ORDER(1,"预订成功","当Ta入住后即可获得奖励哦！"),
	SUCCESS_ACCEPT(2,"开启旅程","奖励已激活！"),
	HAS_INVALID(3,"已失效","oops,失去一次机会......");
	
	private Integer statusCode;
	
	private String statusName;
	
	private String statusShow;

	private BeInviterStatusEnum(Integer statusCode, String statusName,
			String statusShow) {
		this.statusCode = statusCode;
		this.statusName = statusName;
		this.statusShow = statusShow;
	}
	
	public static BeInviterStatusEnum getByStatusCode(Integer statusCode){
		if(Check.NuNObj(statusCode)){
			return null;
		}
		for (BeInviterStatusEnum beInviterStatusEnum : BeInviterStatusEnum.values()) {
			if(statusCode == beInviterStatusEnum.getStatusCode()){
				return beInviterStatusEnum;
			}
		}
		return null;
	}
	

	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getStatusShow() {
		return statusShow;
	}

	public void setStatusShow(String statusShow) {
		this.statusShow = statusShow;
	}
	
}
