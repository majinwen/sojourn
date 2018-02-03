package com.ziroom.minsu.valenum.cms;

/**
 * <p>客户类型</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年10月12日 14:49
 * @since 1.0
 */
public enum ActCustomerTypeEnum {

    UNLIMITED(0, "不限制"),
    PERSON(1, "个人"),
    ENTERPRISE(2, "企业");

    ActCustomerTypeEnum(int code, String name) {
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
