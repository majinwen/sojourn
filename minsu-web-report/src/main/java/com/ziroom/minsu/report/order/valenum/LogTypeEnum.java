package com.ziroom.minsu.report.order.valenum;


/**
 * <p>统计类型表</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author liyingjie on 2016/4/22.
 * @version 1.0
 * @since 1.0
 */
public enum LogTypeEnum {

	DAY_INC_ORDER(0, "日增订单量"),
	DAY_INC_PAY_ORDER(1, "日增支付订单量");

	LogTypeEnum(int code, String name) {
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
}
