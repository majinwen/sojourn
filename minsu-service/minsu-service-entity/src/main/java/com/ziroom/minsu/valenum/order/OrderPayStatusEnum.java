package com.ziroom.minsu.valenum.order;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>订单支付状态枚举</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/1.
 * @version 1.0
 * @since 1.0
 */
public enum OrderPayStatusEnum {


    //0:未支付
    //1:已支付
    UN_PAY(0,"未支付"),

    HAS_PAY(1,"已支付");
    
    OrderPayStatusEnum(int payStatus, String statusName) {
        this.payStatus = payStatus;
        this.statusName = statusName;
    }



    /**
     * 获取
     * @param payStatus
     * @return
     */
    public static OrderPayStatusEnum getPayStatusByCode(int payStatus) {

        for (final OrderPayStatusEnum status : OrderPayStatusEnum.values()) {
            if (status.getPayStatus() == payStatus) {
                return status;
            }
        }
        return null;
    }

    /** 订单支付状态 */
    private int payStatus;

    /** 支付状态名称 */
    private String statusName;

    public int getPayStatus() {
        return payStatus;
    }

    public String getStatusName() {
        return statusName;
    }
}
