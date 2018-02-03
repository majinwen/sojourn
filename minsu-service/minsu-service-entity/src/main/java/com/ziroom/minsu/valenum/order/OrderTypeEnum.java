package com.ziroom.minsu.valenum.order;

import com.asura.framework.base.util.Check;

/**
 * <p>订单类型</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/7.
 * @version 1.0
 * @since 1.0
 */
@Deprecated
public enum OrderTypeEnum {

    CONSTANTLY(1,1,"实时下单"),

    USUALY(0,0,"普通下单");

    OrderTypeEnum(int code,int value, String name) {
        this.code = code;
        this.value = value;
        this.name = name;
    }



    /**
     * 获取
     * @param code
     * @return
     */
    public static OrderTypeEnum getOrderTypeByCode(int code) {
        for (final OrderTypeEnum type : OrderTypeEnum.values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        return null;
    }

    /**
     * 获取
     * @param value
     * @return
     */
    public static OrderTypeEnum getOrderTypeByValue(Integer value) {
        if(Check.NuNObj(value)){
            return null;
        }
        for (final OrderTypeEnum type : OrderTypeEnum.values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        return null;
    }


    /** code */
    private int code;

    /** value */
    private int value;

    /** 名称 */
    private String name;

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }
}
