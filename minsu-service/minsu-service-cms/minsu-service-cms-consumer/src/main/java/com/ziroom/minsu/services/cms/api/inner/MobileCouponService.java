package com.ziroom.minsu.services.cms.api.inner;

/**
 * <p>TODO</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2016/12/9.
 * @version 1.0
 * @since 1.0
 */
public interface MobileCouponService {

    /**
     * 电话领取优惠券活动
     * @author afi
     * @param paramJson
     * @return
     */
    public String pullActCouponByMobile(String paramJson);


    /**
     * 电话领取优惠券活动
     * @author afi
     * @param paramJson
     * @return
     */
    public String pullGroupCouponByMobile(String paramJson);
    
    
    /**
     * uid 根据组号 绑定优惠卷 
     * 组内 ： uid绑定单张优惠卷 和 多张优惠卷 都走这个
     * @author yd
     * @param paramJson
     * @return
     */
    public String  pullGroupCouponByUid(String paramJson);

    /**
     * 根据uid领取活动号
     * @param paramJson
     * @return
     */
    String pullActCouponByUid(String paramJson);
    
    /**
     * 
     * 带排名uid领卷
     *
     * @author bushujie
     * @created 2017年9月21日 下午12:13:33
     *
     * @param paramJson
     * @return
     */
    String pullGroupCouponByUidRank(String paramJson);
    
    /**
     * 
     * 带排名手机号领卷
     *
     * @author bushujie
     * @created 2017年9月21日 下午2:00:57
     *
     * @param paramJson
     * @return
     */
    String pullGroupCouponByMobileRank(String paramJson);

    /**
     * 接受邀请-灌券
     * 新版邀请好友下单
     * @author yanb
     * @param * @param paramJson
     * @return java.lang.String
     * @created 2017年12月14日 14:37:04
     */
    public String acceptPullCouponByUid(String paramJson);
}
