package com.ziroom.zrp.service.trading.valenum;


/**
 * <p>合同是否可修改枚举</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author lusp
 * @version 1.0
 * @Date Created in 2017年11月1日 18:02
 * @since 1.0
 */
public enum ContractIsModifyEnum {

	KXG(1, "可修改"),
	BKXG(0, "不可修改");


	private int code;
	private String name;

	ContractIsModifyEnum(int code, String name) {
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
