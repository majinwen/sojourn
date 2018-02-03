package com.ziroom.minsu.services.cms.api.inner;

/**
 * <p>
 * 优惠券活动相关
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author lishaochuan on 2016年6月23日
 * @since 1.0
 * @version 1.0
 */
public interface ActivityFullService {


    /**
     * 根据活动编号获取优惠券活动信息
     * @author afi
     * @param actSn
     * @return
     */
    public String getActivityFullBySn(String actSn);

    /**
     * 根据活动号获取活动列表 详情信息
     * @author jixd
     * @created 2017年06月15日 16:54:37
     * @param
     * @return
     */
    public String listActivityFullByGroupSn(String groupSn);


    /**
     * 通过活动编号生成优惠券信息
     * @param actSn
     * @return
     */
    public String generateCouponByActSn(String actSn);

    /**
     * 生成或者追加优惠券
     * @author afi
     * @param actSn
     * @return
     */
    public String generateCouponByActSnExt(String actSn,Integer extNum);


    /**
     * 查询优惠券列表
     * @author afi
     * @param json
     * @return
     */
    public String getCouponFullList(String json);

    /**
     * 查找优惠券限制的房源
     * @author jixd
     * @created 2016年11月21日 15:56:42
     * @param
     * @return
     */
    String findLimitHouseByActsn (String actSn);

}
