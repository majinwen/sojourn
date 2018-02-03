package com.ziroom.minsu.valenum.productrules;

/**
 * 
 * <p>房源户型数量限制配置</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zl
 * @since 1.0
 * @version 1.0
 */
public enum ProductRulesEnum0027 {
	
	ProductRulesEnum0027001("ProductRulesEnum0027001","房间最大数"),
	ProductRulesEnum0027002("ProductRulesEnum0027002","客厅最大数"),
	ProductRulesEnum0027003("ProductRulesEnum0027003","卫生间最大数"),
	ProductRulesEnum0027004("ProductRulesEnum0027004","厨房最大数"),
	ProductRulesEnum0027005("ProductRulesEnum0027005","阳台最大数")   ;

	ProductRulesEnum0027(String value, String name) {
		this.value = value;
		this.name = name;
	}

	private final String value;
	
	private final String name;

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

}
