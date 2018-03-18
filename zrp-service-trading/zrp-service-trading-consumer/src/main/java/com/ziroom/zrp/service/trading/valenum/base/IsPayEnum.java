package com.ziroom.zrp.service.trading.valenum.base;

/**
 * <p>是否支付</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年11月07日 17:24
 * @since 1.0
 */
public enum IsPayEnum {
    NO(0,"未支付"),
    YES(1,"已支付"),
    PART(2,"部分支付");

    IsPayEnum(int code, String name) {
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
