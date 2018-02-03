package com.ziroom.minsu.valenum.order;

import com.asura.framework.base.util.Check;

/**
 * <p>强制取消类型</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/26.
 * @version 1.0
 * @since 1.0
 */
public enum  OrderCancelTypeEnum {

    REFUND(1,"直接退款"),

    CONTINUE(2,"协助租房");

    OrderCancelTypeEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }


    /**
     * 获取当前的类型
     * @author afi
     * @param code
     * @return
     */
    public static OrderCancelTypeEnum getByCode(Integer code){
        if(Check.NuNObj(code)){
            return null;
        }
        for(final OrderCancelTypeEnum ose : OrderCancelTypeEnum.values()){
            if(ose.getCode() == code){
                return ose;
            }
        }
        return null;
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
