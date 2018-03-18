package com.ziroom.zrp.service.trading.valenum.finance;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuigh6
 * @version 1.0
 * @Date Created in 2017年11月23日 16:19
 * @since 1.0
 */
public enum AccountFlagEnum {
	BDZH(0,"不调账户"),
	CZXF(1,"充值消费"),
	DJJXF(2,"冻结金消费");

	private int code;
	private String name;

	AccountFlagEnum(int code, String name) {
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
