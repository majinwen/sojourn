package com.ziroom.minsu.api.order.dto;

import java.util.Date;

import com.asura.framework.base.exception.BusinessException;
import com.ziroom.minsu.api.common.dto.BaseParamDto;
import com.ziroom.minsu.valenum.house.RentWayEnum;

/**
 * <p>
 * </p>
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
public class NeedPayFeeApiRequest extends BaseParamDto {

	/**
	 * 是否需要自动优惠券
	 * 1：需要，0：不需要
	 * 默认为0（只有couponSn参数为空时此字段才有效）
	 */
	private Integer isAutoCoupon = 0;

	/** 优惠券号 */
	private String couponSn;

	/** 房源 houseFid、房间 roomFid、床位bedFid */
	private String fid;

	/** 租住方式 */
	private Integer rentWay;

	/** 开始时间 */
	private Date startTime;

	/** 结束时间 */
	private Date endTime;
	
	public Integer getIsAutoCoupon() {
		return isAutoCoupon;
	}

	public void setIsAutoCoupon(Integer isAutoCoupon) {
		this.isAutoCoupon = isAutoCoupon;
	}

	public String getCouponSn() {
		return couponSn;
	}

	public void setCouponSn(String couponSn) {
		this.couponSn = couponSn;
	}

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public Integer getRentWay() {
		return rentWay;
	}

	public void setRentWay(Integer rentWay) {
		this.rentWay = rentWay;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

}
