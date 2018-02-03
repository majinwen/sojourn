package com.ziroom.minsu.services.cms.api.inner;

/**
 * <p>优惠券信息</p>
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
public interface ActCouponService {


    /**
     * 获取当前订单的默认优惠券信息
     * @author afi
     * @param paramJson
     * @return
     */
    public String getDefaultCouponByUid(String paramJson);

    /**
     * 验证当前电话是否领取优惠券活动
     * @author afi
     * @param paramJson
     * @return
     */
    String checkActivityByMobile(String paramJson);

    /**
     * 电话领取优惠券活动
     * @author afi
     * @param paramJson
     * @return
     */
    String pullActivityByMobile(String paramJson);

    /**
     * 获取优惠券信息
     * @author lishaochuan
     * @create 2016年6月8日下午9:06:03
     * @param couponSn
     * @return
     */
    public String getCouponBySn(String couponSn);


    /**
     * 获取当前用户下的所有优惠券
     * @author afi
     * @param paramJson
     * @return
     */
    String getCouponListByUid(String paramJson);


    /**
     * 获取当前用户当前订单满足的信息
     * @author afi
     * @param paramJson
     * @return
     */
    String getCouponListCheckByUid(String paramJson);


    /**
     * 通过活动编号获取优惠券列表
     * @author lishaochuan
     * @create 2016年6月8日下午9:07:53
     * @param paramJson
     * @return
     */
    public String getCouponListByActSn(String paramJson);
    
    
    /**
     * 通过优惠券号获取 优惠券信息、绑定信息
     * @author lishaochuan
     * @create 2016年6月14日下午7:59:04
     * @param couponSn
     * @return
     */
    public String getActCouponUserVoByCouponSn(String couponSn);
    
    
    /**
     * 绑定优惠券
     * @author lishaochuan
     * @create 2016年6月15日上午10:51:51
     * @param paramJson
     * @return
     */
    public String bindCoupon(String paramJson);


    /**
     * 根据手机号绑定优惠券
     * @author lisc
     * @param json
     * @return
     */
    public String bindCouponByMobile(String json);
    
    
    /**
	 * 批量同步修改优惠券状态信息
	 * @author lishaochuan
	 * @create 2016年6月16日下午3:44:17
	 * @param paramJson
	 * @return
	 */
	public String syncCouponStatus(String paramJson);

	/**
     * 兑换码
     * @author liyingjie
     * @create 2016年7月22日上午10:51:51
     * @param paramJson
     * @return
     */
	public String exchangeCode(String paramJson);


    /**
     * 兑换活动码
     * @author afi
     * @create 2016年7月22日下午7:59:04
     * @param paramJson
     * @return
     */
    public String exchangeGroup(String paramJson);


    /**
     * 获取当前未领取的数量
     * @param groupSn
     * @return
     */
    public String getNoExchangeCountByGroupSn(String groupSn);

    /**
     * 绑定优惠券 （手机号和订单号绑定一条记录）
     * @param paramJson
     * @return
     */
    String bindCouponByPhoneAndOrder(String paramJson);

    /**
     * 多个手机号领取优惠券
     * @author jixd
     * @created 2017年02月16日 15:25:41
     * @param
     * @return
     */
    String bindCouponByPhoneNums(String paramJson);

    /**
     * 根据活动组（多个） 获取手机号领取的数量
     * @author jixd
     * @created 2017年03月30日 15:40:44
     * @param
     * @return
     */
    String countMobileGroupSns(String paramJson);

    /**
     * 是否有机会领取优惠券
     * @author jixd
     * @created 2017年06月15日 15:25:20
     * @param
     * @return
     */
    String hasChanceToGetCoupon(String paramJson);

    /**
     * 获取uid列表 还有一个月过期
     * @author jixd
     * @created 2017年06月16日 18:29:02
     * @param
     * @return
     */
    String listOneMonthExpireUidByGroupSnPage(String paramJson);

    /**
     *
     * @author yanb
     * @created 2017年10月24日 15:58:34
     * @param  * @param coupon_sn
     * @param paramJson
     * @return java.lang.String
     */
    public String cancelCoupon(String paramJson);

}