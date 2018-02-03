package com.ziroom.minsu.valenum.productrules;

/**
 * <p>房东取消订单处罚措施</p>
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
public enum ProductRulesEnum0025Enum {
    ProductRulesEnum0025001("ProductRulesEnum0025001","处罚时间规则"),
    ProductRulesEnum0025002("ProductRulesEnum0025002","强制取消罚金"),

    /**
     * 数据库没有，自定义
     */
    ProductRulesEnum0025001001("ProductRulesEnum0025001001","收取订单首晚房费"),
    ProductRulesEnum0025001003("ProductRulesEnum0025001003","取消天使房东资格"),
    ProductRulesEnum0025001004("ProductRulesEnum0025001004","增加取消订单评价"),
    ProductRulesEnum0025001005("ProductRulesEnum0025001005","更新排序因子"),
    ProductRulesEnum0025001006("ProductRulesEnum0025001006","出租日历锁定"),
    ProductRulesEnum0025001007("ProductRulesEnum0025001007","赠送房客优惠券");

    private final String value;
    private final String name;

    ProductRulesEnum0025Enum(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static ProductRulesEnum0025Enum getEnumByValue(String value) {
        for (final ProductRulesEnum0025Enum enumration : ProductRulesEnum0025Enum.values()) {
            if (enumration.getValue().equals(value)) {
                return enumration;
            }
        }
        return null;
    }
}
