package com.ziroom.minsu.valenum.customer;

/**
 * <p>黑名单类型枚举</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author jixd
 * @since 1.0
 * @version 1.0
 */
public enum CustomerBlackTypeEnum {

	ALL(0,"全部"),
	LANDLORD(1,"房东"),
	TENANT(2,"房客");

	CustomerBlackTypeEnum(int code, String name) {
		this.code = code;
		this.name = name;
	}

	/** 编码 */
	private int code;

	/** 名称 */
	private String name;

	public int getCode() {
		return code;
	}

	public String getName() {
		return name;
	}
	
	public static String getCustomerBlackNameByCode(int code) {
		for (CustomerBlackTypeEnum type : CustomerBlackTypeEnum.values()) {
			if (type.getCode() == code) {
				return type.getName();
			}
		}
		return "";
	}
	
}
