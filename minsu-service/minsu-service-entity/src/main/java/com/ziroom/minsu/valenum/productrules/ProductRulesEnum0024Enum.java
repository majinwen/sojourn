package com.ziroom.minsu.valenum.productrules;

import com.ziroom.minsu.valenum.house.HouseConfStyleEnum;

/**
 * 
 * <p>房屋守则</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author baiwei
 * @since 1.0
 * @version 1.0
 */
public enum ProductRulesEnum0024Enum {
	
	HOUSE_RULES_NOSMOKING(1,"不允许吸烟"),
	HOUSE_RULES_NOPET(2,"不允许携带宠物"),
	HOUSE_RULES_NOPARTY(3,"不允许举办活动或聚会"),
	HOUSE_RULES_NOCHILD(4,"不接待儿童（2岁以下）"),;

	ProductRulesEnum0024Enum(int value, String name) {
		this.value = value;
		this.name = name;
	}

	private final int value;
	
	private final String name;

	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	public static String getNameByValue(int value) {
		for (final ProductRulesEnum0024Enum pre : ProductRulesEnum0024Enum.values()) {
			if(pre.getValue() == value){
				return pre.getName();
			}
		}
		return null;
	}

}
