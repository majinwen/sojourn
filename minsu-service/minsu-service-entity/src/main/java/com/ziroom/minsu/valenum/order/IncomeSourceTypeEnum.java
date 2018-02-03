package com.ziroom.minsu.valenum.order;

/**
 * <p>付款单来源</p>
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
public enum IncomeSourceTypeEnum {
	TASK(1, "定时任务"), 
	USER_SETTLEMENT(2, "用户结算"),
	FORCE_CANCEL(3, "强制取消");

	IncomeSourceTypeEnum(int code, String name) {
		this.code = code;
		this.name = name;
	}

	/** code */
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
