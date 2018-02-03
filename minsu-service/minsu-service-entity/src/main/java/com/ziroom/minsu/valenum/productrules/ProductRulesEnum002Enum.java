package com.ziroom.minsu.valenum.productrules;

/**
 * 
 * <p>配套设施枚举</p>
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
public enum ProductRulesEnum002Enum {

    ProductRulesEnum002001("ProductRulesEnum002001","电器"),
    ProductRulesEnum002002("ProductRulesEnum002002","卫浴"),
    ProductRulesEnum002003("ProductRulesEnum002003","设施"),
    ProductRulesEnum002004("ProductRulesEnum002004","其他");

    private final String value;

    private final String name;

    ProductRulesEnum002Enum(String value,String name) {
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
