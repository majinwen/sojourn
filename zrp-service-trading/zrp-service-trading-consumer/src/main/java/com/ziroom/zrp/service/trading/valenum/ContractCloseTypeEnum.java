package com.ziroom.zrp.service.trading.valenum;

/**
 * <p>合同关闭类型</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuigh6
 * @version 1.0
 * @Date Created in 2017年10月19日 15:50
 * @since 1.0
 */
public enum ContractCloseTypeEnum {
	CUSTOMER_CLOSE(1,"客户关闭"),
	AUTO_CLOSE(2,"定时任务关闭"),
	ZO_CLOSE(3,"管家关闭");

	ContractCloseTypeEnum(int code, String name) {
		this.code = code;
		this.name = name;
	}

	private int code;
	private String name;

	public int getCode() {
		return code;
	}

	public String getName() {
		return name;
	}
}
