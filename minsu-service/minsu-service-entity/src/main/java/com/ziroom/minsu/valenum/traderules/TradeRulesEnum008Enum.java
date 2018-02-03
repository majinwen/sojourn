package com.ziroom.minsu.valenum.traderules;

/**
 * <p>收取房东佣金类型</p>
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
public enum TradeRulesEnum008Enum {

    TradeRulesEnum008001("TradeRulesEnum008001","固定收取（无效）"),
    TradeRulesEnum008002("TradeRulesEnum008002","按租金比例");

    private final String value;

    private final String name;

    TradeRulesEnum008Enum(String value,String name) {
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
