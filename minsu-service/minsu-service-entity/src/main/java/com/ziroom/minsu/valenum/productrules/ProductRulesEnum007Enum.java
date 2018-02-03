package com.ziroom.minsu.valenum.productrules;


/**
 * 
 * <p>房源价格</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liujun
 * @since 1.0
 * @version 1.0
 */
public enum ProductRulesEnum007Enum {

    ProductRulesEnum007001("ProductRulesEnum007001","房源最低价格"),
    ProductRulesEnum007002("ProductRulesEnum007002","房源最高价格");

    private final String value;

    private final String name;

    ProductRulesEnum007Enum(String value,String name) {
        this.value = value;
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static ProductRulesEnum007Enum getEnumByValue(String value) {
        for (final ProductRulesEnum007Enum enumration : ProductRulesEnum007Enum.values()) {
            if (enumration.getValue().equals(value)) {
                return enumration;
            }
        }
        return null;
    }

}
