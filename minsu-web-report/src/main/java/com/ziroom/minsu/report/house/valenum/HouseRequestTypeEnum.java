package com.ziroom.minsu.report.house.valenum;


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
public enum HouseRequestTypeEnum {

	ENTIRE_RENT(0, "整租"),
	SUB_RENT(1, "分租"),
	SUB_ENTIRE(1000, "分租房源整租统计");

	HouseRequestTypeEnum(int code, String name) {
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
