
package com.ziroom.minsu.api.common.valeenum;

/**
 * 
 * <p>
 * 订单类型
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zl
 * @since 1.0
 * @version 1.0
 */
public enum OrderProjectTypeEnum {

	MINSU_ORDER(1, "民宿订单"), 
	ZIRUYI_ORDER(2, "自如驿订单");

	OrderProjectTypeEnum(Integer code, String value) {
		this.code = code;
		this.value = value;
	}

	/**
	 * code 编码
	 */
	private Integer code;

	/**
	 * 中文含义
	 */
	private String value;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
