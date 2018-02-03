package com.ziroom.minsu.services.cms.dto;

import com.ziroom.minsu.services.common.dto.PageRequest;

/**
 * <p>
 * 优惠券查询请求参数
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author lishaochuan on 2016年6月8日
 * @since 1.0
 * @version 1.0
 */
public class ActCouponRequest extends PageRequest {

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = 6744182451991830260L;

	private String groupSn;

	private String actSn;

    private String couponSn;

    private String customerMobile;

    private Integer couponStatus;

    private String uid;

    private String couponName;

    private String orderSn;

    public String getCustomerMobile() {
        return customerMobile;
    }

    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile;
    }

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

    public Integer getCouponStatus() {
        return couponStatus;
    }

    public void setCouponStatus(Integer couponStatus) {
        this.couponStatus = couponStatus;
    }

    public String getActSn() {
		return actSn;
	}

	public void setActSn(String actSn) {
		this.actSn = actSn;
	}


    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getGroupSn() {
        return groupSn;
    }

    public void setGroupSn(String groupSn) {
        this.groupSn = groupSn;
    }
}
