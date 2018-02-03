package com.ziroom.minsu.valenum.traderules;

/**
 * <p>收取租客佣金类型</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/3.
 * @version 1.0
 * @since 1.0
 */
public enum TradeRulesEnum009Enum {

    TradeRulesEnum009001("TradeRulesEnum009001","按租金比例")
//    ,
//    TradeRulesEnum009002("TradeRulesEnum009002","按租金比例")
    ;

    private final String value;

    private final String name;

    TradeRulesEnum009Enum(String value,String name) {
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
