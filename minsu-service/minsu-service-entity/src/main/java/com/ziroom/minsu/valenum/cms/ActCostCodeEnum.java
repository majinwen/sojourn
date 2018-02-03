package com.ziroom.minsu.valenum.cms;

/**
 * <p>费用项code</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年10月23日 11:18
 * @since 1.0
 */
public enum ActCostCodeEnum {
    SERVICE_FEE(1, "C02", "服务费");

    ActCostCodeEnum(int code, String codeStr, String name) {
        this.code = code;
        this.codeStr = codeStr;
        this.name = name;
    }

    private int code;

    private String codeStr;

    private String name;

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getCodeStr() {
        return codeStr;
    }


    public static ActCostCodeEnum getByCode(int code) {
        for (final ActCostCodeEnum actCostCodeEnum : ActCostCodeEnum.values()) {
            if (actCostCodeEnum.getCode() == code) {
                return actCostCodeEnum;
            }
        }
        return null;
    }
}
