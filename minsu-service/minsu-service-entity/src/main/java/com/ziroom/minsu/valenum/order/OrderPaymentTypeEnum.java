package com.ziroom.minsu.valenum.order;

/**
 * <p>付款类型</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lishaochuan on 2016年4月20日
 * @since 1.0
 * @version 1.0
 */
public enum OrderPaymentTypeEnum {

	YHFK("yhfk", "银行付款"),
	YLFH("ylfh", "原路返回"),
	ACCOUNT("account", "付款到余额");

	OrderPaymentTypeEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}

	/** 编码 */
	private String code;

	/** 名称 */
	private String name;

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}
}
