package com.ziroom.minsu.valenum.evaluate;

/**
 * <p>评价状态枚举类型</p>
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
public enum EvaluateRulesEnum {

    EvaluateRulesEnum001("EvaluateRulesEnum001","评价是否审核"),
    EvaluateRulesEnum002("EvaluateRulesEnum002","评价字数限制"),
    EvaluateRulesEnum003("EvaluateRulesEnum003","关闭评价天数"),
    EvaluateRulesEnum004("EvaluateRulesEnum004","关闭房东公开回复天数"),
    EvaluateRulesEnum005("EvaluateRulesEnum005","订单结束后展示评价天数");

    private final String value;

    private final String name;

    EvaluateRulesEnum(String value,String name) {
        this.value = value;
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
