package com.ziroom.minsu.valenum.cms;

/**
 * <p>TODO</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/6/20.
 * @version 1.0
 * @since 1.0
 */
public enum  IsCouponEnum {

    NULL(0, "不需要生成"),
    NO(1, "未生成"),
    YES(2, "已生成"),
    ING(3, "生成中");

    IsCouponEnum(int code, String name) {
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
