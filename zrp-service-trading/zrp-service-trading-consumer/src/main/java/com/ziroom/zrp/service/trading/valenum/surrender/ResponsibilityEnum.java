package com.ziroom.zrp.service.trading.valenum.surrender;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @Author wangxm113
 * @Date 2017年10月15日 14时06分
 * @Version 1.0
 * @Since 1.0
 */
public enum ResponsibilityEnum {
    //解约责任方 [0 公司，1 租客]
    COMPANY(0, "公司"),
    CUSTOMER(1, "租客");

    private int code;
    private String name;

    ResponsibilityEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
