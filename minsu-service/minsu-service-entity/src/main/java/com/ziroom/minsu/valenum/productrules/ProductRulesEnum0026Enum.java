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
 * <p>今夜特价规则</p>
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
public enum ProductRulesEnum0026Enum {
	ProductRulesEnum0026001("ProductRulesEnum0026001","设置今夜特价起始点"),
	ProductRulesEnum0026002("ProductRulesEnum0026002","今夜特价生效起始点");

	private final String value;

	private final String name;

	ProductRulesEnum0026Enum(String value,String name) {
		this.value = value;
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	public static ProductRulesEnum0026Enum getEnumByValue(String value) {
		for (final ProductRulesEnum0026Enum enumration : ProductRulesEnum0026Enum.values()) {
			if (enumration.getValue().equals(value)) {
				return enumration;
			}
		}
		return null;
	}
}
