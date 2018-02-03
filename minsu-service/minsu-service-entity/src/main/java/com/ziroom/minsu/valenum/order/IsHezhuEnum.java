package com.ziroom.minsu.valenum.order;

/**
 * <p>是否与房东合住</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/3.
 * @version 1.0
 * @since 1.0
 */
public enum IsHezhuEnum {

    YES(1,"是"),

    NO(0,"否");

    IsHezhuEnum(int code, String name) {
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
