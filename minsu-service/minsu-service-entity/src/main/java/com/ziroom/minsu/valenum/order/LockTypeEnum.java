package com.ziroom.minsu.valenum.order;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

/**
 * <p>锁房类型</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/19.
 * @version 1.0
 * @since 1.0
 */
public enum LockTypeEnum {

    ORDER(1,"订单"),

    LANDLADY(2,"房东"),

    SYSTEM(3,"系统"),


    /**
     * 一下的两个状态只用于逻辑处理，不做真正的数据存储
     */
    EXT_CAN_OUT(3,"当前天被锁定，但是可以离开"),
    EXT_CANOT_OUT(4,"当前天只能入住，不能离开")
    ;

    LockTypeEnum(int code, String name) {
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
