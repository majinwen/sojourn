package com.ziroom.minsu.valenum.customer;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>分机绑定状态</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/5/6.
 * @version 1.0
 * @since 1.0
 */
public enum  ExtStatusEnum {

    // 0:未绑定 1：已经绑定 2：绑定失败 3：已解绑
    NO(0,"未绑定"),
    HAS_OK(1,"已经绑定"),
    ERROR(2,"绑定失败"),
    HAS_BREAK(3,"已解绑");


    /** code */
    private int code;

    /** 名称 */
    private String name;

    private static final Map<Integer,String> enumMap = new LinkedHashMap<Integer,String>();

    static {
        for (ExtStatusEnum extStatusEnum : ExtStatusEnum.values()) {
            enumMap.put(extStatusEnum.getCode(), extStatusEnum.getName());
        }
    }

    ExtStatusEnum(int code,String name){
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static Map<Integer,String> getEnumMap() {
        return enumMap;
    }
}
