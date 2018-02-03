package com.ziroom.minsu.valenum.order;

/**
 * <p>罚款状态</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lishaochuan on 2016年4月25日
 * @since 1.0
 * @version 1.0
 */
public enum PunishStatusEnum {
	NO(1, "未收款"),
	YES(2, "已收款");

	PunishStatusEnum(int code, String name) {
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
