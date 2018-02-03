package com.ziroom.minsu.valenum.productrules;


/**
 * <p>收取房租类型</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/5.
 * @version 1.0
 * @since 1.0
 */
public enum ProductRulesEnum008Enum {

    ProductRulesEnum008001("ProductRulesEnum008001","按房租收取"),
    ProductRulesEnum008002("ProductRulesEnum008002","固定收取");

    private final String value;

    private final String name;

    ProductRulesEnum008Enum(String value,String name) {
        this.value = value;
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static ProductRulesEnum008Enum getEnumByValue(String value) {
        for (final ProductRulesEnum008Enum productRulesEnum008Enum : ProductRulesEnum008Enum.values()) {
            if (productRulesEnum008Enum.getValue().equals(value)) {
                return productRulesEnum008Enum;
            }
        }
        return null;
    }

}
