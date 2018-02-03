package com.ziroom.minsu.valenum.order;

/**
 * <p>订单的结算方式</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/22.
 * @version 1.0
 * @since 1.0
 */
public enum CheckTypeEnum {

    DAY(1,"按照天结算"),

    ORDER(2,"按照订单结算");

    CheckTypeEnum(int code, String name) {
        this.code = code;
        this.name = name;
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
