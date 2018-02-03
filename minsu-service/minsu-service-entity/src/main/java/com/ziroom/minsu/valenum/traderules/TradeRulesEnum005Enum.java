package com.ziroom.minsu.valenum.traderules;


import com.asura.framework.base.util.Check;

/**
 * <p>退订政策</p>
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
public enum TradeRulesEnum005Enum {

    TradeRulesEnum005001("TradeRulesEnum005001","严格退订政策"),
    TradeRulesEnum005002("TradeRulesEnum005002","适中退订政策"),
    TradeRulesEnum005003("TradeRulesEnum005003","灵活退订政策"),
    TradeRulesEnum005004("TradeRulesEnum005004","长期住宿退订政策"),
    TradeRulesEnum005005("TradeRulesEnum005005","说明");

    private final String value;

    private final String name;

    TradeRulesEnum005Enum(String value,String name) {
        this.value = value;
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
    
    public static TradeRulesEnum005Enum getEnumByValue(String value) {
        for (final TradeRulesEnum005Enum tradeRulesEnum005Enum : TradeRulesEnum005Enum.values()) {
            if (tradeRulesEnum005Enum.getValue().equals(value)) {
                return tradeRulesEnum005Enum;
            }
        }
        return null;
    }


    /**
     * 获取
     * @author afi
     * @param obj
     * @return
     */
    public static double getdoubleValue(Object obj){
        if(Check.NuNObj(obj)){
            return 0;
        }
        try {
            Double rst = Double.parseDouble(getStrValue(obj));
            return rst;
        }catch (Exception e){
            return 0;
        }
    }

    /**
     * 获取对象的字符值
     * @author afi
     * @param obj
     * @return
     */
    public static String getStrValue(Object obj){
        if(Check.NuNObj(obj)){
            return "";
        }
        try {
            String rst = String.valueOf(obj);
            return rst;
        }catch (Exception e){
            return "";
        }
    }


    /**
     * 获取对象的字符值
     * @author afi
     * @param obj
     * @return
     */
    public static int getintValue(Object obj){
        if(Check.NuNObj(obj)){
            return 0;
        }
        try {
            Integer rst = Integer.valueOf(getStrValue(obj));
            return rst;
        }catch (Exception e){
            return 0;
        }
    }

}
