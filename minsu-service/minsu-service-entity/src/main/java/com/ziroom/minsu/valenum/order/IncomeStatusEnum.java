package com.ziroom.minsu.valenum.order;


/**
 * <p>收入表状态</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/22.
 * @version 1.0
 * @since 1.0
 */
public enum IncomeStatusEnum {

	NO(1, "未收款"),
	YES(2, "已收款"),
	CANCLE(3, "提前退房取消");

	IncomeStatusEnum(int code, String name) {
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
