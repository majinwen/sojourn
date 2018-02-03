package com.ziroom.minsu.valenum.customer;

/**
 * <p>审核状态</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author yd on 2016年4月20日
 * @since 1.0
 * @version 1.0
 */
public enum AuditStatusEnum {

	SUBMITAUDIT(0, "未审核"),
	COMPLETE(1, "审核通过"),
	REJECTED(2, "审核未通过");

	AuditStatusEnum(int code, String name) {
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
