/**
 * @FileName: ProductRulesEnum.java
 * @Package com.ziroom.minsu.valenum.productrules
 * 
 * @author bushujie
 * @created 2016年4月3日 下午3:27:51
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.valenum.productrules;

/**
 * <p>产品属性规则枚举</p>
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
public enum ProductRulesEnum {
	
	ProductRulesEnum001("ProductRulesEnum001","房源分类"),
	ProductRulesEnum002("ProductRulesEnum002","配套设施"),
    ProductRulesEnum003("ProductRulesEnum003","入住时间"),
    ProductRulesEnum004("ProductRulesEnum004","退房时间"),
    ProductRulesEnum005("ProductRulesEnum005","床类型"),
    ProductRulesEnum006("ProductRulesEnum006","床规格"),
    ProductRulesEnum007("ProductRulesEnum007","房源价格"),
    ProductRulesEnum008("ProductRulesEnum008","押金规则"),
    ProductRulesEnum009("ProductRulesEnum009","入住人数"),
    ProductRulesEnum0010("ProductRulesEnum0010","下单类型"),
    ProductRulesEnum0011("ProductRulesEnum0011","是否与房东合住"),
    ProductRulesEnum0012("ProductRulesEnum0012","优惠政策"),
    ProductRulesEnum0013("ProductRulesEnum0013","民宿分类"),
    ProductRulesEnum0014("ProductRulesEnum0014","被单更换"),
    ProductRulesEnum0015("ProductRulesEnum0015","服务"),
    ProductRulesEnum0016("ProductRulesEnum0016","最小入住天数"),
    ProductRulesEnum0017("ProductRulesEnum0017","照片规则"),
    ProductRulesEnum0018("ProductRulesEnum0018","出租日历最长月数"),

	ProductRulesEnum002001("ProductRulesEnum002001","电器"),
	ProductRulesEnum002002("ProductRulesEnum002002","卫浴"),
	ProductRulesEnum002003("ProductRulesEnum002003","设施"),
    ProductRulesEnum0019("ProductRulesEnum0019","折扣规则"),
    ProductRulesEnum020("ProductRulesEnum020","空置间夜自动定价以及间隙价格"),
    ProductRulesEnum0022("ProductRulesEnum0022","标签类型"),
    ProductRulesEnum0023("ProductRulesEnum0023","房源排序管理权重最大值"),
    ProductRulesEnum0024("ProductRulesEnum0024","房屋守则"),
    ProductRulesEnum0027("ProductRulesEnum0027","房源户型限制规则"),
    ProductRulesEnum0028("ProductRulesEnum0028","床位限制规则")
    ;


	
	private final String value;

    private final String name;

    ProductRulesEnum(String value,String name) {
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
