package com.ziroom.zrp.service.trading.valenum;

/**
 * <p>合同签约类型枚举</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuiyhg
 * @version 1.0
 * @Date Created in 2017年09月18日 18:02
 * @since 1.0
 */
public enum ContractCategoryEnum {
	CF(1, "出房"),
	SH(2, "收房");


	private int code;
	private String name;

	ContractCategoryEnum(int code, String name) {
		this.code = code;
		this.name = name;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
