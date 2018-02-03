package com.ziroom.minsu.valenum.order;

import com.ziroom.minsu.valenum.account.FillBussinessTypeEnum;

/**
 * <p>订单活动活动类型</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/6/15.
 * @version 1.0
 *活动类型1；房东折扣 2：优惠券 3.房东免佣金 @since 1.0
 */
public enum OrderAcTypeEnum {
    //1：房东的折扣活动 2：优惠券 3：房东免佣金 4.房东设置的折扣 5.房客享受空置间夜自动定价 6.今夜特价
    LAN_CUT(1,"房东的折扣活动"),
    COUPON(2,"优惠券"){
        @Override
        public int getPayType() {
            return OrderPayTypeEnum.coupon_pay.getPayType();
        }

        @Override
        public int getFillType() {
            return FillBussinessTypeEnum.coupon_fill.getCode();
        }
    },
    LAN_FREE_COMM(3,"房东免佣金"),
    LAN_CUT_NEW(4,"房东设置的折扣"),
    LEN_FLEXIBLE_PRICING(5,"房客享受空置间夜自动定价"),
    LAN_TONIGHT_DISCOUNT(6,"今夜特价"),
    FIRST_ORDER_REDUC(7,"首单立减"){
        @Override
        public int getPayType() {
            return OrderPayTypeEnum.act_pay.getPayType();
        }

        @Override
        public int getFillType() {
            return FillBussinessTypeEnum.act_fill.getCode();
        }
    };

    OrderAcTypeEnum(int code, String name) {
        this.code = code;
        this.name = name;

    }

    /**
     * 获取优惠券活动类型
     * @param code
     * @return
     */
    public static OrderAcTypeEnum getByCode(int code){
        for(final OrderAcTypeEnum actTypeEnum : OrderAcTypeEnum.values()){
            if(actTypeEnum.getCode() == code){
                return actTypeEnum;
            }
        }
        return null;
    }

    /**
     * 获取支付类型
     * @return
     */
    public int getPayType(){
        return -1;
    }

    /**
     * 获取充值类型
     * @return
     */
    public int getFillType(){
        return -1;
    }

    /** code */
    private int code;

    /** 名称 */
    private String name;

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
