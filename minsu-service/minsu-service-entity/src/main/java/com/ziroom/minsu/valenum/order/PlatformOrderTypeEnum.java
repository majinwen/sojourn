package com.ziroom.minsu.valenum.order;

/**
 * <p>支付平台订单类型枚举</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author liyingjie 2016/4/9.
 * @version 1.0
 * @since 1.0
 */
 public enum PlatformOrderTypeEnum {

    out_pay(1,"外部支付"),
    freeze_pay(90,"账户余额支付(余额转冻结)"),
    card_pay(91,"积分卡支付"),
    consume_pay(94,"账户余额支付(余额消费)"),
    cash_coupon_pay(93,"代金券");
   
    
    PlatformOrderTypeEnum(int orderType, String orderTypeName) {
        this.orderType = orderType;
        this.orderTypeName = orderTypeName;
    }



    /**
     * 获取
     * @param orderType
     * @return
     */
    public static PlatformOrderTypeEnum getOrderTypeByCode(int orderType) {
    	for (final PlatformOrderTypeEnum type : PlatformOrderTypeEnum.values()) {
            if (type.getOrderType() == orderType) {
                return type;
            }
        }
        return null;
    }



    /** 支付类型*/
    private int orderType;

    /** 支付类型名称 */
    private String orderTypeName;

    public int getOrderType() {
        return orderType;
    }

    public String getOrderTypeName() {
        return orderTypeName;
    }
}
