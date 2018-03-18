package com.ziroom.zrp.service.trading.valenum;

/**
 * <p>合同签约来源</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuiyh9
 * @version 1.0
 * @Date Created in 2017年09月18日 18:02
 * @since 1.0
 */
public enum ContractSourceEnum {
	ZRAMS(1, "自如寓管理后台"),
	MOBILESITE(2, "M站"),
	APP(3, "app");


	private int code;
	private String name;

	ContractSourceEnum(int code, String name) {
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
