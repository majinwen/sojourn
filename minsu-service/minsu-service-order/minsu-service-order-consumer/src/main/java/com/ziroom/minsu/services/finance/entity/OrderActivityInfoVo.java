package com.ziroom.minsu.services.finance.entity;

import com.ziroom.minsu.entity.order.OrderActivityEntity;

import java.util.Date;

/**
 * 订单活动信息 加入生成收款单的截止时间
 * @author jixd
 * @created 2017年06月06日 13:51:47
 * @param
 * @return
 */
public class OrderActivityInfoVo extends OrderActivityEntity{

    private static final long serialVersionUID = 2578363458735595586L;
    /**
     * 付款单生成时间
     */
    private Date paymentTime;

    public Date getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }
}
