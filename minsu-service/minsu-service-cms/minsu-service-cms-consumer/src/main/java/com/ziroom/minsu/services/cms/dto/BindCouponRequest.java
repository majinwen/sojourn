package com.ziroom.minsu.services.cms.dto;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>绑定优惠券请求参数</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lishaochuan on 2016年6月15日
 * @since 1.0
 * @version 1.0
 */
public class BindCouponRequest extends BaseEntity {

	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 2175946071448453607L;

	/** 绑定用户uid*/
	private String uid;

	/** 组 */
	private String groupSn;
	
	/** 优惠券号*/
	private String couponSn;
	
	/** 活动码*/
	private String actSn;
	
	/** 手机号*/
	private String mobile;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getCouponSn() {
		return couponSn;
	}

	public void setCouponSn(String couponSn) {
		this.couponSn = couponSn;
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

	public String getGroupSn() {
		return groupSn;
	}

	public void setGroupSn(String groupSn) {
		this.groupSn = groupSn;
	}
}
