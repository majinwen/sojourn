package com.ziroom.minsu.valenum.cms;

/**
 * <p>签约类型</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年10月12日 14:52
 * @since 1.0
 */
public enum SignTypeEnum {

    UNLIMITED(0, "不限制"),
    NEW_SIGN(1, "新签"),
    RENEW(2, "续约");

    SignTypeEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    private int code;

    private String name;

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

}
