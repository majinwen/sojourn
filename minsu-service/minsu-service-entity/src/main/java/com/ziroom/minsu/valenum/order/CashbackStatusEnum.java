package com.ziroom.minsu.valenum.order;

/**
 * <p>返现单状态枚举</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lishaochuan on 2016年9月7日
 * @since 1.0
 * @version 1.0
 */
public enum CashbackStatusEnum {

	INIT(10, "初始"),
	AUDIT(20, "已审核"),
	REJECT(30, "已驳回");
	
	/**
	 * 状态
	 */
	private int code;
	
	/**
	 * 状态名称
	 */
	private String name;
	
	CashbackStatusEnum(int code, String name) {
		this.code = code;
		this.name = name;
	}

	public int getCode() {
		return code;
	}

	public String getName() {
		return name;
	}
	
	
}
