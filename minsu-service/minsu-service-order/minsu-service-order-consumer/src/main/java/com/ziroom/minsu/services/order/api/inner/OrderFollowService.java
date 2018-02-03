package com.ziroom.minsu.services.order.api.inner;

/**
 * <p>订单的跟进记录</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2016/12/14.
 * @version 1.0
 * @since 1.0
 */
public interface OrderFollowService {



    /**
     * 分页查询24小时之内的信息
     * @author afi
     * @param params
     * @return
     */
    String getOrderFollowByPage(String params);

    /**
     * 保存订单的跟进记录
     * @author afi
     * @create 2016年12月14日上午10:25:29
     * @param params
     * @return
     */
     String saveOrderFollow(String params);


    /**
     * 查看跟进记录
     * @author afi
     * @create 2016年12月14日上午10:25:29
     * @param orderSn
     * @return
     */
     String getOrderFollowListByOrderSn(String orderSn);


}
