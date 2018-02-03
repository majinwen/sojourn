/**
 * @FileName: TradeRulesEnum007Enum.java
 * @Package com.ziroom.minsu.valenum.traderules
 * 
 * @author bushujie
 * @created 2016年4月25日 下午9:12:30
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.valenum.traderules;

import com.asura.framework.base.util.Check;

/**
 * <p>房东结算方式子项</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
public enum TradeRulesEnum007Enum {
	TradeRulesEnum007001("TradeRulesEnum007001","按订单结算"),
	TradeRulesEnum007002("TradeRulesEnum007002","按天结算");

    private final String value;

    private final String name;

    TradeRulesEnum007Enum(String value,String name) {
        this.value = value;
        this.name = name;
    }


    public static TradeRulesEnum007Enum getRuleByCode(String code){
        if (Check.NuNStr(code)){
            return null;
        }
        for(final TradeRulesEnum007Enum rule : TradeRulesEnum007Enum.values()){
            if(rule.getValue().equals(code)){
                return rule;
            }
        }
        return null;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
