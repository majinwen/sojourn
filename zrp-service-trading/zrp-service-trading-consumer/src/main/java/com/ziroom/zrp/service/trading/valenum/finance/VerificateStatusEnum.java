package com.ziroom.zrp.service.trading.valenum.finance;

/**
 * <p>财务-核销状态</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年09月25日 11:32
 * @since 1.0
 */
public enum VerificateStatusEnum {
    NO(0,"未核销"),
    DONE(1,"已核销"),
    PART(2,"部分核销");

    VerificateStatusEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * 状态code
     */
    private int code;
    /**
     * 状态名称
     */
    private String name;

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
