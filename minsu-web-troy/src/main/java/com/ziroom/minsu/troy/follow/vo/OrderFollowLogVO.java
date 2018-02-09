package com.ziroom.minsu.troy.follow.vo;

import com.ziroom.minsu.entity.order.OrderFollowLogEntity;

/**
 * <p>展示的状态</p>
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
public class OrderFollowLogVO  extends OrderFollowLogEntity {

    private static final long serialVersionUID = 3023421446703L;

    /**
     * 订单状态
     */
    private String orderStatusName;


    /**
     * 支付状态
     */
    private String payStatusName;

    /**
     * 顺序
     */
    private Integer  index;


    public String getOrderStatusName() {
        return orderStatusName;
    }

    public void setOrderStatusName(String orderStatusName) {
        this.orderStatusName = orderStatusName;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getPayStatusName() {
        return payStatusName;
    }

    public void setPayStatusName(String payStatusName) {
        this.payStatusName = payStatusName;
    }
}
