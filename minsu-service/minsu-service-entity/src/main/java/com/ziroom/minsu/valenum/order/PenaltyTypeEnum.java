package com.ziroom.minsu.valenum.order;
/**
 * <p>罚款类型</p>
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
public enum PenaltyTypeEnum {

    LAN_CANCLE_FIXED(10,"房东申请取消扣除固定金额"),
    LAN_CANCLE_FIRST_RENT(11,"房东申请取消扣除首晚房费");

    PenaltyTypeEnum(int code, String name) {
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

