package com.ziroom.zrp.service.trading.valenum;


/**
 * <p>签约主体类型枚举</p>
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
 * @deprecated
 */
public enum SignTypeEnum {

	PERSONAL(0,"个人"), ENTERPRISE(1, "企业");

	private int code;

	private String name;

	private SignTypeEnum(int code, String name) {
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
