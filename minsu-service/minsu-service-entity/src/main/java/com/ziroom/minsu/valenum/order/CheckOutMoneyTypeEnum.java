package com.ziroom.minsu.valenum.order;

/**
 * <p>退房结算的方式</p>
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
public enum CheckOutMoneyTypeEnum {


    DAY(1,"按照天结算"),

    FIX(2,"固定");

    CheckOutMoneyTypeEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }


    /** code */
    private int code;

    /** 支付状态名称 */
    private String name;

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
