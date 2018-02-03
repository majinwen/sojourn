package com.ziroom.minsu.valenum.order;

/**
 * <p>取消订单类型</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lishaochuan on 2017/1/4 10:37
 * @version 1.0
 * @since 1.0
 */
public enum CancelTypeEnum {

    NEGOTIATION(37, "协商取消"),
    LANDLOR_APPLY(38,"房东协商取消");

    CancelTypeEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * code
     */
    private int code;

    /**
     * 名称
     */
    private String name;

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
