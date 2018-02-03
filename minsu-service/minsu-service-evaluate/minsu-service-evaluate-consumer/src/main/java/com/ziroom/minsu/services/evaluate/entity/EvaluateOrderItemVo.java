package com.ziroom.minsu.services.evaluate.entity;

import com.asura.framework.base.entity.BaseEntity;

/**
 * Created by homelink on 2016/11/9.
 */
public class EvaluateOrderItemVo extends BaseEntity{
    private static final long serialVersionUID = 306985033452746364L;

    private String orderSn;

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }
}
