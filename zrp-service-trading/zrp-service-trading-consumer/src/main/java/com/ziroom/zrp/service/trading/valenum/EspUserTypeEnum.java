package com.ziroom.zrp.service.trading.valenum;


/**
 * <p>电子签约用户类型枚举</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author lusp
 * @version 1.0
 * @Date Created in 2017年10月23日 10:02
 * @since 1.0
 */
public enum EspUserTypeEnum {

	PERSON(0,"个人"),
	COMPANY(1, "企业");

	private int code;

	private String name;

	EspUserTypeEnum(int code, String name) {
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
