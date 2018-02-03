package com.ziroom.minsu.valenum.order;

/**
 * <p>罚款单状态</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd on 2017/05/10.
 * @version 1.0
 * @since 1.0
 */
public enum PenaltyStatusEnum {
    WAITING(0,"未清"),
    DOING(10,"清还中"),
    FINISH(20,"已清"),
    ABOLISH(30,"作废");
    PenaltyStatusEnum(int code, String name) {
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
