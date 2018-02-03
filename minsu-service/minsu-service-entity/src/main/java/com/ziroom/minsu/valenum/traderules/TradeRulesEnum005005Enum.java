package com.ziroom.minsu.valenum.traderules;

/**
 * <p>长租退订政策</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author zl
 * @version 1.0
 * @since 1.0
 */
public enum TradeRulesEnum005005Enum {

	TradeRulesEnum005005001("TradeRulesEnum005005001","说明");

    private final String value;

    private final String name;

    TradeRulesEnum005005Enum(String value, String name) {
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
