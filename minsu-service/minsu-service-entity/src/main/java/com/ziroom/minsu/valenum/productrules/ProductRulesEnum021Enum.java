/**
 * @FileName: ProductRulesEnum021.java
 * @Package com.ziroom.minsu.valenum.productrules
 * 
 * @author yd
 * @created 2016年12月22日 下午8:30:43
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.valenum.productrules;

/**
 * <p>新房源价格限制</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
public enum ProductRulesEnum021Enum {
	ProductRulesEnum021001("ProductRulesEnum021001","房源最低价格"),
	ProductRulesEnum021002("ProductRulesEnum021002","房源最高价格");

	private final String value;

	private final String name;

	ProductRulesEnum021Enum(String value,String name) {
		this.value = value;
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	public static ProductRulesEnum021Enum getEnumByValue(String value) {
		for (final ProductRulesEnum021Enum enumration : ProductRulesEnum021Enum.values()) {
			if (enumration.getValue().equals(value)) {
				return enumration;
			}
		}
		return null;
	}
}
