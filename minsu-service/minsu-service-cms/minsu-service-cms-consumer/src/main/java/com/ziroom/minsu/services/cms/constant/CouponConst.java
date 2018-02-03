package com.ziroom.minsu.services.cms.constant;

/**
 * <p>优惠券的领取状态</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author afi on 2016年12月9日
 * @since 1.0
 * @version 1.0
 */
public enum CouponConst {

	COUPON_ERROR (111000,"领取失败,请重试"),
	COUPON_PAR_NO (111001,"参数为空"),
	COUPON_PAR_ERROR(111002,"异常参数"),
	COUPON_ACT_NO (111003,"活动不存在"),
	COUPON_ACT_FINIS (111004,"活动已结束"),
	COUPON_HAS (111005,"已经领取过"),
	COUPON_MOBILE_NULL (111006,"电话为空"),
	COUPON_EMPTY (111007,"已经参加过"),
	COUPON_NO_SMOKE (111008,"没有抽中"),
	COUPON_NO_MORE (111009,"优惠券领完"),
	COUPON_UID_NULL(111010,"用户不存在"),
	COUPON_TYPE_ERROR(111011,"异常的优惠券时间限制类型"),
	COUPON_TIME_ERROR(111012,"优惠活动的优惠时长为空"),
	COUPON_INFO_ERROR(111013,"异常的优惠券信息"),
	COUPON_SYS_ERROR(111014,"系统异常"),
	COUPON_MOBILE_ERROR(111015,"领取电话不存在"),
	COUPON_UID_OVER_LIMITNUM(111016,"已经领取次数已达到最大限制"),

	GROUP_TYPE_NO_MATCH(111017,"活动组类型不匹配"),
	GROUP_CONDITION_HAS(111018,"活动优惠券已领取过"),

	COUPON_CODE_NULL(111019,"邀请码不存在");


	private int code;
	private String name;

	CouponConst(int code, String name) {
		this.code = code;
		this.name = name;
	}

	public int getCode() {
		return code;
	}

	public String getName() {
		return name;
	}
}
