package com.ziroom.minsu.valenum.traderules;

/**
 * <p>长租规则</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2016/12/2.
 * @version 1.0
 * @since 1.0
 */
public enum  TradeRulesEnum0020 {

    TradeRulesEnum0020001("TradeRulesEnum0020001","长期住宿入住最小天数"),
    TradeRulesEnum0020002("TradeRulesEnum0020002","长期住宿收取房东服务费类型"),
    TradeRulesEnum0020003("TradeRulesEnum0020003","长期住宿收取租客服务费类型")
    ;

    private final String value;

    private final String name;
   

    TradeRulesEnum0020(String value,String name) {
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
