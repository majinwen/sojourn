package com.ziroom.minsu.services.cms.constant;

import com.ziroom.minsu.services.common.constant.MessageConst;

/**
 * <p>订单常亮</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/6/14.
 * @version 1.0
 * @since 1.0
 */
public class CmsMessageConst extends MessageConst {

	/**
     * 当前活动码已存在
     *
     */
    public static final String ACT_ACSN_HAS = "cms.acsn.has";

    /**
     * 当前优惠券已经被使用
     *
     */
    public static final String ACT_COUPON_HAS = "cms.ac.has";
    
    /**
     * 已生成优惠券不允许修改
     */
    public static final String CMS_AC_HAS_UPDATE = "cms.ac.has.update";

	/**
	 * 绑定优惠券请求参数为空
	 */
	public static final String CMS_BIND_REQUEST_NULL = "cms.bind.request.null";
	
	/**
	 * 优惠券号为空
	 */
	public static final String CMS_BIND_COUPON_SN_NULL = "cms.bind.coupon.sn.null";
	
	/**
	 * 绑定用户信息为空
	 */
	public static final String CMS_BIND_USER_NULL = "cms.bind.user.null";
	
	/**
	 * 优惠券输入有误，请重新输入
	 */
	public static final String CMS_BIND_COUPON_NULL = "cms.bind.coupon.null";

	/**
	 * 您的优惠券被他人领取，无法使用
	 */
	public static final String CMS_BIND_COUPON_GET = "cms.bind.coupon.get";
	
	/**
	 * 您的优惠券已被其他订单使用
	 */
	public static final String CMS_BIND_COUPON_USED = "cms.bind.coupon.used";

	/**
	 * 您的优惠券已过期
	 */
	public static final String CMS_BIND_COUPON_EXPIRED = "cms.bind.coupon.expired";
	
	/**
	 * 优惠券状态错误
	 */
	public static final String CMS_BIND_COUPON_STATUS = "cms.bind.coupon.status";
	
	/**
	 * 该活动只能领一张券
	 */
	public static final String CMS_BIND_ACT_NUM = "cms.bind.act.num";
	
	
	/**
	 * 活动已过期
	 */
	public static final String CMS_BIND_ACT_EXPIRED = "cms.bind.act.expired";
	
	/**
	 * 该活动已启用
	 */
	public static final String CMS_ACTIVITY_ENABLE = "cms.activity.enable";
	
	/**
	 * 已启用的活动不允许修改
	 */
	public static final String CMS_ACTIVITY_ENABLE_UPDATE = "cms.activity.enable.update";
	
	
	/**
	 * 您已申请过
	 */
	public static final String CMS_APPLY_HAVE_MOBILE = "cms.apply.have.mobile";
}
