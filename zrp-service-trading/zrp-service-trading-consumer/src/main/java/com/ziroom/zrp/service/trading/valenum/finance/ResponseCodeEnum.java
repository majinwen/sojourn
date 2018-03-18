package com.ziroom.zrp.service.trading.valenum.finance;

/**
 * <p>财务-响应结果枚举</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年09月25日 14:48
 * @since 1.0
 */
public enum ResponseCodeEnum {

    SUCCESS(100000,"成功");

    ResponseCodeEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    private int code;

    private String name;
}
