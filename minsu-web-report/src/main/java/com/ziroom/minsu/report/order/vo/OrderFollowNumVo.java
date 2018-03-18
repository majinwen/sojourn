package com.ziroom.minsu.report.order.vo;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.minsu.report.common.annotation.FieldMeta;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lishaochuan on 2016/12/16 14:09
 * @version 1.0
 * @since 1.0
 */
public class OrderFollowNumVo extends BaseEntity {

    @FieldMeta(skip = true)
    private static final long serialVersionUID = 2250644323222779950L;

    @FieldMeta(name="客服跟进时订单状态",order=10)
    private String orderStatus;

    @FieldMeta(name="订单数量",order=20)
    private String num;

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
