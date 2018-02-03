package com.ziroom.minsu.services.order.entity;

import com.ziroom.minsu.entity.order.OrderEntity;

import java.util.Date;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 *  邀请好友下单活动定时任务用的订单实体类,方便直接进行类型转换
 * @author yanb
 * @version 1.0
 * @Date Created in 2017年12月12日 21:45
 * @since 1.0
 */
public class OrderInviteVo extends OrderEntity {

    private static final long serialVersionUID = 7228203438451250203L;

    /** 入住人uid */
    private String userUid;

    /** 订单号 */
    private String orderSn;

    /** 最后修改时间 */
    private Date lastModifyDate;

    @Override
    public Date getLastModifyDate() {
        return lastModifyDate;
    }

    @Override
    public void setLastModifyDate(Date lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
    }

    @Override
    public String getUserUid() {
        return userUid;
    }

    @Override
    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    @Override
    public String getOrderSn() {
        return orderSn;
    }

    @Override
    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

}
