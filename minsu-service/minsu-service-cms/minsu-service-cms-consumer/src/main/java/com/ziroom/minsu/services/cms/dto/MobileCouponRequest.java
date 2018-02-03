package com.ziroom.minsu.services.cms.dto;

import com.asura.framework.base.entity.BaseEntity;

import java.util.List;

/**
 * <p>电话领取活动</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author afi on 2016年8月25日
 * @since 1.0
 * @version 1.0
 */
public class MobileCouponRequest extends BaseEntity {

	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 2175946071448453607L;

	/**  组码 */
	private String groupSn;

	/** 活动码*/
	private String actSn;
	
	/** 手机号*/
	private String mobile;
	/** 订单号*/
	private String orderSn;

	/** 来源 */
	private Integer sourceType;

	/** 组码列表 */
	private List<String> groupSns;
	
	/**
	 * 用户uid
	 */
	private String uid;
	

	/**
	 * @return the uid
	 */
	public String getUid() {
		return uid;
	}

	/**
	 * @param uid the uid to set
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}

	public MobileCouponRequest() {
	}

	public String getActSn() {
		return actSn;
	}

	public void setActSn(String actSn) {
		this.actSn = actSn;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getSourceType() {
		return sourceType;
	}

	public void setSourceType(Integer sourceType) {
		this.sourceType = sourceType;
	}

	public String getGroupSn() {
		return groupSn;
	}

	public void setGroupSn(String groupSn) {
		this.groupSn = groupSn;
	}

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public List<String> getGroupSns() {
		return groupSns;
	}

	public void setGroupSns(List<String> groupSns) {
		this.groupSns = groupSns;
	}
}
