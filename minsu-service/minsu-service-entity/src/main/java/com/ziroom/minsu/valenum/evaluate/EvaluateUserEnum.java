package com.ziroom.minsu.valenum.evaluate;

import com.asura.framework.base.util.Check;

/**
 * <p>评价人类型</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @since 1.0
 * @version 1.0
 */
public enum EvaluateUserEnum {
    LAN(1,"房东评价"),
    TEN(2,"房客评价"),
    LAN_REPLY(10,"房东回复"),
    LAN_CANCEL_ORDER_SYSTEM_EVAL(3,"房东强制取消系统评价");
    EvaluateUserEnum(int code,String value){
        this.code = code;
        this.value = value;
    };
    private int code;

    private String value;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**
     *
     * @author jixd
     * @created 2016年11月07日 18:32:50
     * @param
     * @return
     */
    public static EvaluateUserEnum getEvaluateUserByCode(Integer code) {
        if (Check.NuNObj(code)){
            return  null;
        }
        for (final EvaluateUserEnum userEnum : EvaluateUserEnum.values()) {
            if (userEnum.getCode() == code) {
                return userEnum;
            }
        }
        return null;
    }
}
