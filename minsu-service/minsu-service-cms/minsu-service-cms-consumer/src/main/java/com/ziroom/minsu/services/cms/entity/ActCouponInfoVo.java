package com.ziroom.minsu.services.cms.entity;

import com.ziroom.minsu.entity.cms.ActCouponInfoEntity;

/**
 * <p>
 * 优惠券活动信息Vo
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
public class ActCouponInfoVo extends ActCouponInfoEntity {

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = -6411592051001711236L;

	/** 优惠券已生成数量 */
	private Integer haveCouponNum;

	/** 优惠券已使用数量 */
	private Integer useCouponNum;

	/** 优惠券已冻结数量 */
	private Integer frozenCouponNum;

	/** 城市名称 */
	private String cityName;

	
	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Integer getHaveCouponNum() {
		return haveCouponNum;
	}

	public void setHaveCouponNum(Integer haveCouponNum) {
		this.haveCouponNum = haveCouponNum;
	}

	public Integer getUseCouponNum() {
		return useCouponNum;
	}

	public void setUseCouponNum(Integer useCouponNum) {
		this.useCouponNum = useCouponNum;
	}

	public Integer getFrozenCouponNum() {
		return frozenCouponNum;
	}

	public void setFrozenCouponNum(Integer frozenCouponNum) {
		this.frozenCouponNum = frozenCouponNum;
	}

}
